package pri.chaofan.blockchain.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {
    public static String SHA256(String str) throws NoSuchAlgorithmException {
        MessageDigest digest;
        byte[] bytes;
        digest = MessageDigest.getInstance("SHA-256");
        bytes = digest.digest(str.getBytes(StandardCharsets.UTF_8));
        return getHash(bytes);
    }

    private static String getHash(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
