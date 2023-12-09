package pri.chaofan.blockchain.web;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import pri.chaofan.blockchain.pojo.Node;
import pri.chaofan.blockchain.service.WebService;

import java.net.URI;
import java.net.URISyntaxException;

public class WebClient {

    @Autowired
    WebService webService;
    @Autowired
    Node node;
    public void connectToPeer(String addr) {
        try {
            final WebSocketClient socketClient = new WebSocketClient(new URI(addr)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    //this client ask to query this last block of other node's blockchain
                    webService.send(this,webService.getLastBlock());
                    node.getNodeList().add(this);
                }

                @Override
                public void onMessage(String s) {
                    webService.redirectMessage(this,s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    node.getNodeList().remove(this);
                }

                @Override
                public void onError(Exception e) {
                    node.getNodeList().remove(this);
                }
            };
            socketClient.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
