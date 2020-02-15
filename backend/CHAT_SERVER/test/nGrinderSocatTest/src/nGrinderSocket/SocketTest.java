package nGrinderSocket;

import java.io.IOException;
import java.net.URI;
import javax.websocket.*;

@ClientEndpoint
public class SocketTest {
    private static Object waitLock = new Object();
    @OnMessage
    public void onMessage(String message) {
        //the new USD rate arrives from the websocket server side.
        System.out.println("Received msg: "+message);
    }
    private static void  wait4TerminateSignal(){
        synchronized(waitLock)
        {
            try {
                waitLock.wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) throws IOException, DeploymentException {
//        SocketTest socket = new SocketTest();
//        System.out.println(socket.connect("ws://13.125.68.49:8000/chat/0/0/"));
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        Session session=null;
        System.out.println(container);

//        try{
            session= container.connectToServer(SocketTest.class, URI.create("ws://13.125.68.49:8000/chat/0/0"));
            System.out.println(session);
        try{
            System.out.println("뭐여");

//            wait4TerminateSignal();
        } catch (Exception e) {
            System.out.println(e);
        }
        finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}