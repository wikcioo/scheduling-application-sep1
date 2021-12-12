package utilities;

public class Logger {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void info(String message) {
        System.out.println(ANSI_WHITE + "[INFO]: " + message + ANSI_RESET);
    }

    public static void success(String message) {
        System.out.println(ANSI_GREEN + "[SUCCESS]: " + message + ANSI_RESET);
    }

    public static void warn(String message) {
        System.out.println(ANSI_YELLOW + "[WARNING]: " + message + ANSI_RESET);
    }

    public static void error(String message) {
        System.out.println(ANSI_RED + "[ERROR]: " + message + ANSI_RESET);
    }
}
