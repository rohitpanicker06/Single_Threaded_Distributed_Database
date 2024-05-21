package handler;

import eventloop.EventManager;
import eventloop.EventMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {

        try {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String inputLine ;
            while((inputLine = in.readLine()) != null)
            {
                if (!inputLine.matches("^[*$].*")) {
                    EventMessage<String> message = new EventMessage<>(clientSocket, inputLine);
                    EventManager.getInstance().send(message);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
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
