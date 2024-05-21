import commands.*;
import constants.Constants;
import eventloop.EventManager;
import eventloop.EventMessage;
import eventloop.InMemoryEventSource;
import eventloop.SingleThreadEventLoop;
import eventloop.interfaces.EventListenerIF;
import eventloop.interfaces.EventSourceIF;
import handler.ClientHandler;
import handler.ResponseListeners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisServer {
    private static ServerSocket serverSocket = null;
    private static EventManager<String> eventManager = null;
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10); // You can adjust the pool size as needed

    private static final Logger log = LoggerFactory.getLogger(RedisServer.class);
    private static void  initServer() throws IOException {
        serverSocket = new ServerSocket(Constants.REDIS_SERVER_PORT);
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

    private static void initSubSystems() throws IOException {
        initServer();
        initEventLoop();
        registerEventListeners();
    }

    public static void main(String[] args) throws IOException {

        initSubSystems();
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
