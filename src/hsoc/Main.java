package hsoc;

import org.apache.commons.cli.ParseException;

/**
 * launch this program.
 * @author hbk01 2020/04/09 20:02
 */
public class Main {
    public static void main(String[] args) throws ParseException {
        Config config = new Config(args);
        System.out.println(config.toString());
    }
}
