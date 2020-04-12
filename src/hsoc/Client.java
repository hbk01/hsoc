package hsoc;

import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

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
            OutputStream os = socket.getOutputStream();
            if (config.hasOption(Config.Name.MSG)) {
                os.write(config.message().getBytes());
            }
            if (config.hasOption(Config.Name.FILE)) {
                File file = config.file();
                if (file != null) {
                    if (file.isFile()) {
                        Files.copy(file.toPath(), os);
                    } else if (file.isDirectory()) {
                        System.out.println("- [" + file.getAbsolutePath() + "] is a directory. we can't send it.");
                    }
                }
            }
            os.flush();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
