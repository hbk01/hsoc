package hsoc;

import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * be a client to connect server.
 * @author hbk01 2020/04/10 19:31
 */
public class Client implements Runnable {
    private Config config;

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
            String ip = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
            System.out.println("- connect to " + ip);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("> ");
                String line = scanner.nextLine();
                socket.getOutputStream().write(line.getBytes());
                if ("exit".equals(line)) {
                    break;
                }
            }
            scanner.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
