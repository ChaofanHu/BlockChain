package pri.chaofan.blockchain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Node {

    @Value("${node.port}")
    private int port;

    @Value("${node.address}")
    private String address;
    private List<WebSocket> nodeList = new ArrayList<>();

}
