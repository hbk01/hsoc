package hsoc;

import org.apache.commons.cli.*;

import java.io.File;
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
     * define the options opt and longOpt.
     */
    protected enum Name {
        /**
         * port
         */
        PORT("p", "port"),

        /**
         * address(client only)
         */
        ADDRESS("a", "address"),

        /**
         * help
         */
        HELP("h", "help"),

        /**
         * client
         */
        CLIENT("c", "client"),

        /**
         * msg(client only)
         */
        MSG("m", "msg"),

        /**
         * in client mode: send the file.
         * in server mode: receive the msg save in this file.
         */
        FILE("f", "file");

        public String opt;
        public String longOpt;

        /**
         * Create the object.
         * @param opt     option name
         * @param longOpt long option name
         */
        Name(String opt, String longOpt) {
            this.opt = opt;
            this.longOpt = longOpt;
        }

        /**
         * convert to string
         * @return string
         */
        @Override
        public String toString() {
            return longOpt;
        }
    }

    /**
     * Initial the config.
     * @param arguments args
     * @throws ParseException if parse error.
     */
    public Config(String[] arguments) throws ParseException {
        options.addOption(Option.builder(Name.HELP.opt)
                .longOpt(Name.HELP.longOpt)
                .desc("show help of the program.")
                .optionalArg(true)
                .build()
        );
        options.addOption(Option.builder(Name.CLIENT.opt)
                .longOpt(Name.CLIENT.longOpt)
                .desc("run in client mode.")
                .build()
        );
        options.addOption(Option.builder(Name.PORT.opt)
                .longOpt(Name.PORT.longOpt)
                .desc("in server mode, receive message this port. \n" +
                        "in client mode, send message to this port.")
                .hasArg(true)
                .argName("PORT")
                .type(int.class)
                .build()
        );
        options.addOption(Option.builder(Name.ADDRESS.opt)
                .longOpt(Name.ADDRESS.longOpt)
                .desc("connection this ip address. (client mode only)")
                .hasArg(true)
                .argName("ADDRESS")
                .type(String.class)
                .build()
        );
        options.addOption(Option.builder(Name.MSG.opt)
                .longOpt(Name.MSG.longOpt)
                .desc("send the msg to server. (client only)")
                .hasArg(true)
                .argName("MSG")
                .build()
        );
        options.addOption(Option.builder(Name.FILE.opt)
                .longOpt(Name.FILE.longOpt)
                .desc("in client mode, send the file to server." +
                        "in server mode, receive the msg and save in this file.")
                .hasArg(true)
                .argName("FILE")
                .build()
        );

        commandLine = new DefaultParser().parse(options, arguments);
    }

    /**
     * if true, we will run this program in client mode.
     * @return mode
     */
    public boolean runAsClient() {
        return commandLine.hasOption(Name.CLIENT.opt);
    }

    /**
     * Get the port to bind.
     * @return port. if no set, return 9010.
     */
    public int port() {
        if (commandLine.hasOption(Name.PORT.opt)) {
            String arg = commandLine.getOptionValue(Name.PORT.opt, "9010");
            return Integer.parseInt(arg);
        } else {
            return 9010;
        }
    }

    /**
     * Get the server's ip address, it's only client mode used.
     * @return ip address
     */
    public Inet4Address address() {
        if (commandLine.hasOption(Name.ADDRESS.opt)) {
            String address = commandLine.getOptionValue(Name.ADDRESS.opt, "");
            try {
                return (Inet4Address) Inet4Address.getByName(address);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get message. if not set, return "".
     * @return message
     */
    public String message() {
        return commandLine.hasOption(Name.MSG.opt) ?
                commandLine.getOptionValue(Name.MSG.opt, "") : "";
    }

    /**
     * Get file. if not set, return new File("")
     * @return file
     * @see File
     */
    public File file() {
        String path = commandLine.hasOption(Name.FILE.opt) ?
                commandLine.getOptionValue(Name.FILE.opt, "") : "";
        return "".equals(path) ? null : new File(path);
    }

    /**
     * Get the help option.
     * @return true if has help option.
     */
    public boolean help() {
        return commandLine.hasOption(Name.HELP.opt);
    }

    /**
     * print the program arguments help.
     */
    public void usage() {
        HelpFormatter help = new HelpFormatter();
        String header = "server be receiver, client be sender.";
        String footer = "feedback: https://github.com/hbk01/hsoc/issues\n" +
                "feedback: https://gitee.com/hbk01/hsoc/issues";
        help.printHelp("hsoc", header, options, footer, true);
    }

    /**
     * Check option.
     * @param opt option name
     * @return if has the option, return true
     */
    public boolean hasOption(Name opt) {
        return commandLine.hasOption(opt.longOpt);
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
        if (!"".equals(message())) {
            builder.append(tab).append("msg : ").append(message()).append(newline);
        }
        if (file() != null && !"".equals(file().getAbsolutePath())) {
            builder.append(tab).append("file: ").append(file().getAbsolutePath()).append(newline);
        }
        builder.append("}");
        return builder.toString();
    }
}
