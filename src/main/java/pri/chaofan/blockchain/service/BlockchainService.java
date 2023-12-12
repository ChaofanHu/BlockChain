package pri.chaofan.blockchain.service;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pri.chaofan.blockchain.pojo.*;

import java.util.List;
import java.util.Random;

@Service
public class BlockchainService {
    private Random random;
    @Autowired
    @Resource
    private Blockchain blockchain;
    @Autowired
    Node node;

    public BlockchainService(){
        random = new Random(8824);
    }

    public Block createGenesisBlock(){
        if(blockchain.blockNumber() < 1){
            Block genesisBlock = new Block(1,"Chaofan Hu", 1);
            blockchain.addBlock(genesisBlock);
            return genesisBlock;
        }
        System.out.println("The genesis block has exist!");
        return null;
    }

    public Block createNewBlock(String data){
        Block lastBlock = blockchain.getLastBlock();
        String lastHash = lastBlock.getHash();
        Block newBlock = new Block(lastBlock.getId()+1,lastHash,data, random.nextInt(1000));
        return newBlock;
    }


    public boolean addNewBlockToChain(Block newBlock){
        blockchain.addBlock(newBlock);
        return true;
    }

    private void broadcastNewBlock(Block newBlock){
        Message msg = new Message();
        msg.setData(JSON.toJSONString(newBlock));
        msg.setMessageType(MessageConstant.RETURN_LAST_BLOCK);
        this.broadcast(JSON.toJSONString(msg));

    }
    private void broadcast(String msg){
        List<WebSocket> nodeList = this.node.getNodeList();
        if (nodeList == null){
            return;
        }
        for (WebSocket socket : nodeList){
            send(socket,msg);
        }
    }
    private void send(WebSocket socket, String message){
        socket.send(message);
    }

    public String mine(Block block) {
        String prefixString = new String(new char[4]).replace('\0', '0');
        String hash = block.getHash();
        while (!hash.substring(0, 4).equals(prefixString)) {
            block.setNonce(block.getNonce()+1);
            block.setTimeStamp(System.currentTimeMillis());
            hash = block.computeHash();
        }
        return hash;
    }

    public List getBlockchain(){
        return blockchain.getBlockchain();
    }

    public Block getLatestBlock(){
        return blockchain.getLastBlock();
    }
}
