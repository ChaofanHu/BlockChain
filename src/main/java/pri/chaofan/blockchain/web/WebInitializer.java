package pri.chaofan.blockchain.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import pri.chaofan.blockchain.pojo.Node;

public class WebInitializer implements ApplicationRunner {
    @Autowired
    WebClient webClient;

    @Autowired
    WebServer webServer;

    @Autowired
    Node node;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        webClient.connectToPeer(node.getAddress());
        webServer.initP2PServer(node.getPort());
    }
}
