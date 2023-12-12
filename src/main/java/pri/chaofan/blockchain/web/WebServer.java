package pri.chaofan.blockchain.web;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pri.chaofan.blockchain.pojo.Node;
import pri.chaofan.blockchain.service.BlockchainService;
import pri.chaofan.blockchain.service.WebService;

import java.net.InetSocketAddress;

@Component
public class WebServer {
    @Autowired
    WebService webService;
    @Autowired
    Node node;

    public void initP2PServer(int port) {

        WebSocketServer socketServer = new WebSocketServer(new InetSocketAddress(port)) {

            @Override
            public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
                node.getNodeList().add(webSocket);
                System.out.println(node.getNodeList());
            }

            @Override
            public void onClose(WebSocket webSocket, int i, String s, boolean b) {
                node.getNodeList().remove(webSocket);
            }

            @Override
            public void onMessage(WebSocket webSocket, String s) {
                System.out.println("Receive one Message");
                webService.redirectMessage(webSocket,s);
                System.out.println(node.getNodeList().get(0).getLocalSocketAddress());
            }

            @Override
            public void onError(WebSocket webSocket, Exception e) {
                node.getNodeList().remove(webSocket);
            }

            @Override
            public void onStart() {
                System.out.println("Start!");
            }
        };
        socketServer.start();
        System.out.println("listening websocket p2p port on: " + port);
    }
}
