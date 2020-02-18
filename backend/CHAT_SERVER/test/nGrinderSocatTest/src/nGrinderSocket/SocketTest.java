package nGrinderSocket;
import java.io.IOException;
import java.net.URI;
import javax.websocket.*;

@ClientEndpoint
public class SocketTest {
    protected Session conn = null;

    public Boolean connect(String url)
    {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            System.out.println("222"+container.toString());

            conn = container.connectToServer(this, new URI(url));
            System.out.println("333"+conn.getRequestURI());

            conn.setMaxTextMessageBufferSize(16777216);
        }
        catch (Exception e) {
            System.out.println("SSS"+conn);

            System.out.println(e);
            return false;
        }
        return true;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("connected");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("disconnected");
    }

    public void disconnect() {
        try {
            if (conn.isOpen()) {
                conn.close();
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SocketTest socket = new SocketTest();
        socket.connect("ws://13.125.68.49:8000/chat/0/0");
    }
}