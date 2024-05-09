import constants.Constants;
import commands.Command;
import commands.CommandInvoker;
import commands.Ping;
import commands.PingReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisServer {
    private static ServerSocket serverSocket = null;
    private static void  initServer() throws IOException {
        serverSocket = new ServerSocket(Constants.REDIS_SERVER_PORT);
        serverSocket.setReuseAddress(true);
    }

    private static void initEventLoop()
    {

    }

    public static void main(String[] args) throws IOException {

        initServer();
        initEventLoop();

        Socket clientSocket = null;

        try {
            clientSocket = serverSocket.accept();

            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String inputLine ;
            while((inputLine = in.readLine()) != null)
            {
                if (!inputLine.matches("^[*$].*")) {
//                    printWriter.print(invoker.execute());
                    printWriter.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
