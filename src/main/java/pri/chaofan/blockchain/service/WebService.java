package pri.chaofan.blockchain.service;

import com.alibaba.fastjson.JSON;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pri.chaofan.blockchain.pojo.*;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class WebService {
    @Autowired
    Blockchain blockchain;
    @Autowired
    Node node;
    @Autowired
    BlockchainService blockchainService;

    //redirect message to corresponding method.
    public void redirectMessage(WebSocket socket, String message){
        Message msg = JSON.parseObject(message, Message.class);
        switch (msg.getMessageType()) {
            //when client ask to get the latest block
            case MessageConstant.GET_LAST_BLOCK -> this.send(socket, this.returnLastBlock());

            //client receive the latest block from server
            case MessageConstant.RETURN_LAST_BLOCK -> this.blockMessageHandler(msg.getData());

            //client ask to get the blockchain
            case MessageConstant.GET_BLOCKCHAIN -> this.send(socket, returnBlockchain());

            //client receive the blockchain from server
            case MessageConstant.RETURN_BLOCKCHAIN -> this.blockchainMessageHandler(msg.getData());
        }
    }
    //deal with block messages from other nodes
    public synchronized void blockMessageHandler(String block){
        Block lastBlock = JSON.parseObject(block, Block.class);
        //current node's latest node

        Block currentLastBlock = blockchain.getBlockchain() != null ? blockchainService.getLatestBlock() : null;
        //if the received block's id are larger than this node's id, this node should be updated
        if(currentLastBlock == null){
            return;
        }
        if (lastBlock.getId()> currentLastBlock.getId()+1){
            this.broadcast(this.getBlockchain());
        } else if (lastBlock.getPreviousHash().equals(currentLastBlock.getHash())) {
            blockchainService.addNewBlockToChain(lastBlock);
            broadcast(this.returnLastBlock());
            System.out.println("Add this new block into blockchain");
        }
    }

    //deal with blockchain message from other node
    public synchronized  void blockchainMessageHandler(String blockchain){
        List<Block> blocks = JSON.parseArray(blockchain, Block.class);

        if (blocks == null){
            return;
        }
        Collections.sort(blocks, new Comparator<Block>() {
            @Override
            public int compare(Block o1, Block o2) {
                return o1.getId()- o2.getId();
            }
        });
        Block lastBlockReceived = blocks.get(blocks.size()-1);
        Block lastBlockOfThisNode = blockchainService.getLatestBlock();
        if (blockchainService.getBlockchain().size() == 0){
            // if the node's block chain is null
            for(Block block: blocks){
                blockchainService.addNewBlockToChain(block);
            }
        }else if (blocks.size() > blockchainService.getBlockchain().size() ){
            //add the new block to this chain
            if (lastBlockOfThisNode.getHash().equals(lastBlockReceived.getPreviousHash())){
                blockchainService.addNewBlockToChain(lastBlockReceived);
                broadcast(returnLastBlock());
            }else {
                //put the new blockchain to this node's chain
                for(Block block: blocks){
                    blockchainService.addNewBlockToChain(block);
                }
            }
        }
    }

    public String getLastBlock(){
        Message message = new Message();
        message.setMessageType(MessageConstant.GET_LAST_BLOCK);
        return JSON.toJSONString(message);
    }
    //return the latest block
    public String returnLastBlock(){
        Message msg = new Message();
        msg.setMessageType(MessageConstant.RETURN_LAST_BLOCK);
        msg.setData(JSON.toJSONString(blockchainService.getLatestBlock()));
        return JSON.toJSONString(msg);
    }
    public String getBlockchain(){
        Message message = new Message();
        message.setMessageType(MessageConstant.GET_BLOCKCHAIN);
        return JSON.toJSONString(message);
    }
    public String returnBlockchain(){
        Message message = new Message();
        message.setMessageType(MessageConstant.RETURN_BLOCKCHAIN);
        message.setData(JSON.toJSONString(blockchainService.getBlockchain()));
        return JSON.toJSONString(message);
    }

    //send message to all other nodes
    public void broadcast(String msg){
        List<WebSocket> nodeList = this.node.getNodeList();
        if (nodeList == null){
            return;
        }
        for (WebSocket socket : nodeList){
            send(socket,msg);
        }
    }
    //send message
    public void send(WebSocket socket, String message){
        socket.send(message);
    }

}
