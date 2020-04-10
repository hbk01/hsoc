package hsoc;

import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.net.Socket;

/**
 * be a client to connect server.
 * @author hbk01 2020/04/10 19:31
 */
public class Client implements Runnable {
    private Config config;

    private Client() {
    }

    public Client(String[] args) {
        try {
            config = new Config(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(config.address(), config.port());
            socket.getOutputStream().write("hello".getBytes());
            System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
