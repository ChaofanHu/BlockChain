package pri.chaofan.blockchain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@AllArgsConstructor
public class Blockchain {
    private List<Block> blockChain;

    public Blockchain(){
        this.blockChain = new ArrayList<>();
    }

    public Block getLastBlock(){
        if (blockChain != null){
            return  blockChain.get(blockChain.size()-1);
        }
        System.out.println("The Block Chain is null");
        return null;
    }

    public void addBlock(Block block){
        blockChain.add(block);
    }

    public int blockNumber(){
        if (blockChain != null){
            return blockChain.size();
        }
        return 0;
    }

    public List<Block> getBlockchain(){
        return blockChain;
    }
}
