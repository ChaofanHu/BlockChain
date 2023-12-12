package pri.chaofan.blockchain.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pri.chaofan.blockchain.service.BlockchainService;
import pri.chaofan.blockchain.pojo.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class BlockChainController {

    @Autowired
    private BlockchainService blockchainService;

    @GetMapping("/genesis")
    public String addGenesisBlock(){
        Block genesisBlock = blockchainService.createGenesisBlock();
        return JSON.toJSONString(genesisBlock);
    }
    @GetMapping("/mine")
    public String mine(@RequestParam("data") String data){
        Map<String, Block> response = new HashMap<>();
        if(data == null){
            response.put("Null", null);
            return "NOT Valid Parameter";
        }
        Block newBlock = blockchainService.createNewBlock(data);
        String hash = blockchainService.mine(newBlock);
        newBlock.setHash(hash);
        newBlock.setId(blockchainService.getBlockchain().size()+1);
        blockchainService.addNewBlockToChain(newBlock);
        //broadcast the new block to other nodes
        blockchainService.broadcastNewBlock(newBlock);
        response.put(data, newBlock);
        return JSON.toJSONString(response);
    }
    @GetMapping("/getBlockchain")
    public String getBlockchain(){
        return JSON.toJSONString(blockchainService.getBlockchain());
    }

}
