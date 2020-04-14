package hsoc;

import org.apache.commons.cli.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * be a server to response client.
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
            Socket socket = null;
            // check file types.
            if (config.hasOption(Config.Name.FILE)) {
                // save to file
                File file = config.file();
                if (file != null && file.exists()) {
                    if (file.isFile()) {
                        String tip = "- file [%s] is already exists. override it? (y/n): ";
                        System.out.printf(tip, file.getAbsolutePath());
                        Scanner input = new Scanner(System.in);
                        String select = input.nextLine();
                        if ("y".equals(select)) {
                            socket = server.accept();
                            String ip = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
                            System.out.println("- connect to " + ip);
                            Files.copy(socket.getInputStream(), file.toPath(),
                                    StandardCopyOption.REPLACE_EXISTING);
                        }
                        input.close();
                    } else {
                        String tip = "- [%s] is a directory, we need a file.";
                        System.out.printf(tip, file.getAbsolutePath());
                    }
                } else if (file != null) {
                    socket = server.accept();
                    String ip = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
                    System.out.println("- connect to " + ip);
                    Files.copy(socket.getInputStream(), file.toPath());
                }
            } else {
                socket = server.accept();
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(isReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                isReader.close();
                reader.close();
            }

            if (socket != null) {
                socket.close();
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
