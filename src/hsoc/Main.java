package hsoc;

import org.apache.commons.cli.ParseException;

import java.util.Arrays;

/**
 * launch this program.
 * @author hbk01 2020/04/09 20:02
 */
public class Main {
    public static void main(String[] args) {
        try {
            Config config = new Config(args);

            if (config.hasOption(Config.Name.HELP)) {
                config.usage();
                System.exit(0);
            } else if (config.hasOption(Config.Name.VERSION)) {
                String msg = "hsoc %s build by hbk.";
                System.out.println(String.format(msg, Config.VERSION_NAME));
                System.exit(0);
            } else {
                System.out.println("Args: " + Arrays.toString(args));
                System.out.println(config.toString());
            }

            if (config.runAsClient()) {
                new Client(args).run();
            } else {
                new Server(args).run();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
