package pri.chaofan.blockchain.pojo;

import lombok.Data;
import pri.chaofan.blockchain.Utils.EncryptionUtils;

import java.security.NoSuchAlgorithmException;

@Data
public class Block {
    private int id;
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
    public Block(int id, String previousHash, String data, long nonce){
        this.id = id;
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.nonce = nonce;
        this.hash = computeHash();
    }


    public Block(int id,String data, long nonce){
        this.id = id;
        this.data = data;
        this.previousHash = "";
        this.timeStamp = System.currentTimeMillis();
        this.nonce = nonce;
        this.hash = computeHash();
    }

    public String computeHash(){
        String str = previousHash + Long.toString(timeStamp) + Long.toString(nonce);
        String hash = null;
        try {
            hash = EncryptionUtils.SHA256(str);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }


}
