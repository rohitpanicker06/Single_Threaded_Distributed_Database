import constants.Constants;
import constants.commands.Command;
import constants.commands.CommandInvoker;
import constants.commands.Ping;
import constants.commands.PingReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisServer {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        PingReceiver pingReceiver = new PingReceiver();
        Command pingCommand  = new Ping(pingReceiver);
        CommandInvoker invoker = new CommandInvoker();
        invoker.setCommand(pingCommand);
        try {
            serverSocket = new ServerSocket(Constants.REDIS_SERVER_PORT);
            serverSocket.setReuseAddress(true);
            clientSocket = serverSocket.accept();

            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String inputLine ;
            while((inputLine = in.readLine()) != null)
            {
                if (!inputLine.matches("^[*$].*")) {
                    printWriter.print(invoker.execute());
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
