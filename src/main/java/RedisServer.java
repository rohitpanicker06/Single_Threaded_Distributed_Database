import constants.Constants;
import eventloop.EventManager;
import eventloop.interfaces.EventListenerIF;
import handler.ClientHandler;
import handler.ResponseListeners;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisServer {
    private static ServerSocket serverSocket = null;
    private static EventManager<String> eventManager = null;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10); // You can adjust the pool size as needed

    private static final Logger log = LoggerFactory.getLogger(RedisServer.class);
    private static void  initServer(Integer port) throws IOException {
        serverSocket = new ServerSocket(Objects.requireNonNullElseGet(port, () -> Constants.REDIS_SERVER_PORT));
        serverSocket.setReuseAddress(true);
    }

    private static void initEventLoop()
    {
        eventManager = EventManager.getInstance();
    }

    private static void registerEventListeners()
    {
        EventListenerIF<String> eventLoopEventListener = new ResponseListeners();
        eventManager.addListener(eventLoopEventListener);
    }

    private static void initSubSystems(Integer port) throws IOException {
        initServer(port);
        initEventLoop();
        registerEventListeners();
    }

    public static void main(String[] args) throws IOException {
        Integer port = null;
        if(args.length >1)
        {
            port = Integer.parseInt(args[1]);
        }

        initSubSystems(port);

        Socket clientSocket = null;

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                log.debug("Received new client connection = " + clientSocket.toString());
                threadPool.submit(new ClientHandler(clientSocket));
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
