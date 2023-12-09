package pri.chaofan.blockchain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.List;

@Data
@AllArgsConstructor
@Component
public class Node {

    @Value("${block.port}")
    private int port;
    private String address;
    private List<WebSocket> nodeList;

}
