package hsoc;


import org.apache.commons.cli.ParseException;

/**
 * launch this program.
 * @author hbk01 2020/04/09 20:02
 */
public class Main {

    public static void main(String[] args) {
        try {
            Config config = new Config(args);
            config.usage();
            System.out.println(config.runAsClient());
            System.out.println(config.port());
            System.out.println(config.address());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
