package pri.chaofan.blockchain;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BlockchainService {
    private Random random;
    @Autowired
    @Resource
    private Blockchain blockchain;

    public BlockchainService(){
        random = new Random(8824);
    }

    public Block createGenesisBlock(){
        Block genesisBlock = new Block("Chaofan Hu", 1);
        blockchain.addBlock(genesisBlock);
        return genesisBlock;
    }

    public Block createNewBlock(String data){
        Block lastBlock = blockchain.getLastBlock();
        String lastHash = lastBlock.getHash();
        Block newBlock = new Block(lastHash,data, random.nextInt(1000));
        return newBlock;
    }

    public boolean addNewBlockToChain(Block newBlock){
        blockchain.addBlock(newBlock);
        return true;
    }

    public String proofOfWork(Block block) {
        String prefixString = new String(new char[4]).replace('\0', '0');
        String hash = block.getHash();
        while (!hash.substring(0, 4).equals(prefixString)) {
            block.setNonce(block.getNonce()+1);
            hash = block.computeHash();
        }
        return hash;
    }

    public List getBlockchain(){
        return blockchain.getBlockchain();
    }
}
