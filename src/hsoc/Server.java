package hsoc;

import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author hbk01 2020/04/10 19:59
 */
public class Server implements Runnable {
    private Config config;

    public Server(String[] args) {
        try {
            config = new Config(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(config.port());
            Socket socket = server.accept();
            String ip = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
            System.out.println("- connect to " + ip);
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String line = reader.readLine();
                if ("exit".equals(line)) {
                    break;
                } else if (line != null){
                    System.out.println("< " + line);
                }
            }
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
