package hsoc;

import org.apache.commons.cli.ParseException;

/**
 * launch this program.
 * @author hbk01 2020/04/09 20:02
 */
public class Main {
    public static void main(String[] args) throws ParseException {
        Config config = new Config(args);
        new Thread(new Server(new String[]{})).start();
        new Thread(new Client(new String[]{"-a", "127.0.0.1"})).start();
    }
}
