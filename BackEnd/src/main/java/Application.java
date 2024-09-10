
import com.sun.net.httpserver.HttpServer;
import handlers.RequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Application {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(Statics.serverStartPort), 0);
        server.createContext("/api", new RequestHandler());
        server.setExecutor(Executors.newFixedThreadPool(Statics.threadPoolSize));
        server.start();
        System.out.println("Server started on port: " + Statics.serverStartPort);
    }
}
