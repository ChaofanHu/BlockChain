package pri.chaofan.blockchain;

import lombok.Data;

@Data
public class Block {
    private String data;
    private String hash;
    private String previousHash;
    private long timeStamp;
    private long nonce;

    public Block(String previousHash, String data, long nonce){
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.nonce = nonce;
        this.hash = computeHash();
    }

    public Block(String data, long nonce){
        this.data = data;
        this.previousHash = "";
        this.timeStamp = System.currentTimeMillis();
        this.nonce = nonce;
        this.hash = computeHash();
    }

    public String computeHash(){
        String str = previousHash + Long.toString(timeStamp) + Long.toString(nonce);
        String hash = EncryptionUtils.SHA256(str);
        return hash;
    }


}
