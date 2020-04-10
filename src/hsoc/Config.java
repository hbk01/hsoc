package hsoc;

import org.apache.commons.cli.*;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Config the program how to run.
 * @author hbk01 2020/04/09 21:02
 */
public class Config {
    private Options options = new Options();
    private CommandLine commandLine;

    /**
     * Initial the config.
     * @param arguments args
     * @throws ParseException if parse error.
     */
    public Config(String[] arguments) throws ParseException {
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("show help of the program.")
                .optionalArg(true)
                .build()
        );
        options.addOption(Option.builder("c")
                .longOpt("client")
                .desc("run in client mode.")
                .build()
        );
        options.addOption(Option.builder("p")
                .longOpt("port")
                .desc("in server mode, receive message this port. \n" +
                        "in client mode, send message to this port.")
                .hasArg(true)
                .argName("PORT")
                .type(int.class)
                .build()
        );
        options.addOption(Option.builder("a")
                .longOpt("address")
                .desc("connection this ip address. (client mode only)")
                .hasArg(true)
                .argName("ADDRESS")
                .type(String.class)
                .build()
        );

        commandLine = new DefaultParser().parse(options, arguments);
    }

    /**
     * if true, we will run this program in client mode.
     * @return mode
     */
    public boolean runAsClient() {
        return commandLine.hasOption("client");
    }

    /**
     * Get the port to bind.
     * @return port. if no set, return 9010.
     */
    public int port() {
        if (commandLine.hasOption("port")) {
            String arg = commandLine.getOptionValue("port", "9010");
            return Integer.parseInt(arg);
        } else return 9010;
    }

    /**
     * Get the server's ip address, it's only client mode used.
     * @return ip address
     */
    public Inet4Address address() {
        if (commandLine.hasOption("address")) {
            String address = commandLine.getOptionValue("address", "");
            // String regex = "\\d{3}.\\d{3}.\\d{1,3}.\\d{1,3}";
            try {
                return (Inet4Address) Inet4Address.getByName(address);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }

    /**
     * Get the help option.
     * @return true if has help option.
     */
    public boolean help() {
        return commandLine.hasOption("help");
    }

    /**
     * print the program arguments help.
     */
    public void usage() {
        HelpFormatter help = new HelpFormatter();
        String header = "options: ";
        String footer = "feedback: https://github.com/hbk01/hsoc/issues\n" +
                "feedback: https://gitee.com/hbk01/hsoc/issues";
        help.printHelp("hsoc", header, options, footer, true);
    }

    /**
     * Check option.
     * @param opt option name
     * @return if has the option, return true
     */
    public boolean hasOption(String opt) {
        return commandLine.hasOption(opt);
    }

    /**
     * Get the arguments parser.
     * @return parser
     */
    public CommandLine getCommandLine() {
        return commandLine;
    }

    /**
     * return the config.
     * @return config
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String tab = "\t";
        String newline = System.lineSeparator();
        builder.append("Config {").append(newline);
        builder.append(tab).append("port: ").append(port()).append(newline);
        if (runAsClient()) {
            builder.append(tab).append("addr: ").append(address().getHostAddress()).append(newline);
        }
        builder.append(tab).append("mode: ").append(runAsClient() ? "client" : "server").append(newline);
        builder.append("}");
        return builder.toString();
    }
}
