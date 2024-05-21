package handler;

import commands.CommandFactory;
import commands.CommandInvoker;
import eventloop.EventMessage;
import eventloop.interfaces.EventListenerIF;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ResponseListeners implements EventListenerIF<String> {
    @Override
    public void onMessage(EventMessage<String> message) throws IOException {
    String eventMessage = message.getMessage();
    CommandInvoker invoker = new CommandInvoker();
    invoker.setCommand(CommandFactory.getCommand(eventMessage));
    String result = invoker.execute();
    sendResponse(result, message.getSource());
    }

    @Override
    public void onError(EventMessage<String> message) {

    }

    private void sendResponse(String result, Object socket) throws IOException {
        Socket clientSocket = (Socket) socket;
        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
        printWriter.print(result);
        printWriter.flush();
    }
}
