package handler;

import eventloop.EventManager;
import eventloop.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import static resp.RespUtils.parseRespArray;

public class ClientHandler implements Runnable{

    private final Socket clientSocket;
    private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);

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
                if (inputLine.startsWith("*")) {

                    List<String> respArray = parseRespArray(in, inputLine);
                    StringBuffer stringBuffer = new StringBuffer();
                    for(String s: respArray)
                    {
                        stringBuffer.append(s).append(" ");
                    }
                    log.debug("Event message parsed = " + stringBuffer.toString());
                    EventMessage<String> message = new EventMessage<>(clientSocket, stringBuffer.toString());
                    EventManager.getInstance().send(message);
                } else {
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
