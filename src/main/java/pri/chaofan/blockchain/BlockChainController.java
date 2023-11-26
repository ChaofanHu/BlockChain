package pri.chaofan.blockchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class BlockChainController {

    @Autowired
    private BlockchainService blockchainService;

    @GetMapping("/genesis")
    public Block addGenesisBlock(){
        Block genesisBlock = blockchainService.createGenesisBlock();
        return genesisBlock;
    }
    @GetMapping("/mine")
    public Map mine(@RequestParam("data") String data){
        Map<String, Block> response = new HashMap<>();
        if(data == null){
            response.put("Null", null);
            return response;
        }
        Block newBlock = blockchainService.createNewBlock(data);
        String hash = blockchainService.proofOfWork(newBlock);
        newBlock.setHash(hash);

        blockchainService.addNewBlockToChain(newBlock);
        response.put(data, newBlock);
        return response;
    }
    @GetMapping("/getBlockchain")
    public List getBlockchain(){
        return blockchainService.getBlockchain();
    }
}
