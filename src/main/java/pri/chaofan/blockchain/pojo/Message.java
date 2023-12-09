package pri.chaofan.blockchain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private static final long ID = 1L;
    private int messageType;
    private String data;
}
