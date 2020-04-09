package hsoc;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * launch this program.
 *
 * @author hbk01 2020/04/09 20:02
 */
public class Main {

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("show help of the program.")
                .build()
        );
        options.addOption(Option.builder("c")
                .longOpt("client")
                .desc("run as client.")
                .build()
        );
        options.addOption(Option.builder("p")
                .longOpt("port")
                .desc("bind the port.")
                .hasArg(true)
                .argName("PORT")
                .build()
        );
        options.addOption(Option.builder("a")
                .longOpt("address")
                .desc("connection this ip address. (run as client only)")
                .hasArg(true)
                .argName("ADDRESS")
                .build()
        );
        HelpFormatter help = new HelpFormatter();
        help.printHelp("hsoc", options);
    }
}
