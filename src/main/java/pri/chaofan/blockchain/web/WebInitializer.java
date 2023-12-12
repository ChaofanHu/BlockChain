package pri.chaofan.blockchain.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pri.chaofan.blockchain.pojo.Node;

@Component
public class WebInitializer implements ApplicationRunner {
    @Autowired
    WebClient webClient;

    @Autowired
    WebServer webServer;

    @Autowired
    Node node;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        webServer.initP2PServer(node.getPort());
        webClient.connectToPeer(node.getAddress());
    }
}
