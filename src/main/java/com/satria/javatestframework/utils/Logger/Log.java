package com.satria.javatestframework.utils.Logger;

import org.apache.log4j.*;

import java.io.IOException;


public class Log {
    private static final Logger LOGGER = Logger.getLogger(Log.class);
    private static PatternLayout layout = new PatternLayout("%d{dd MMM yyyy HH:mm:ss} %5p %c{1} - %m%n");
    private static FileAppender appender;
    private static ConsoleAppender consoleAppender;

    static
    {
        try
        {
            consoleAppender = new ConsoleAppender(layout, "System.out");
            appender= new FileAppender(layout,"target/gate-logs/gate.log",true);
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * method to display errors in log.
     * @param className name of class in which error occurred.
     * @param methodName name of method in which error occurred.
     * @param exception stack trace of exception
     */
    public static void error (String className,String methodName,String exception)
    {
        LOGGER.addAppender(consoleAppender);
        LOGGER.addAppender(appender);
        LOGGER.setLevel((Level) Level.ERROR);
        LOGGER.info("ClassName :"+className);
        LOGGER.info("MethodName :"+methodName );
        LOGGER.info("Exception :" +exception);
        LOGGER.info("-----------------------------------------------------------------------------------");
    }

    /**
     * method to display information in logs
     * @param message message to be displayed
     */
    public static void info(String message){
        consoleAppender.setName("Console");
        LOGGER.addAppender(consoleAppender);
        LOGGER.addAppender(appender);
        LOGGER.setLevel((Level) Level.INFO);
        LOGGER.info(message);
    }

    public static void warn(String message){
        LOGGER.addAppender(consoleAppender);
        LOGGER.addAppender(appender);
        LOGGER.setLevel((Level) Level.WARN);
        LOGGER.warn(message);
    }

    public static void infoRed(String message){
        consoleAppender.setName("Console");
        LOGGER.addAppender(consoleAppender);
        LOGGER.addAppender(appender);
        LOGGER.setLevel((Level) Level.INFO);
        LOGGER.info("\u001b[0;31m"+ message+"\u001b[m");
    }

    public static void infoGreen(String message){
        consoleAppender.setName("Console");
        LOGGER.addAppender(consoleAppender);
        LOGGER.addAppender(appender);
        LOGGER.setLevel((Level) Level.INFO);
        LOGGER.info("\u001b[0;32m"+ message+"\u001b[m");
    }

    public static void infoYellow(String message){
        consoleAppender.setName("Console");
        LOGGER.addAppender(consoleAppender);
        LOGGER.addAppender(appender);
        LOGGER.setLevel((Level) Level.INFO);
        LOGGER.info("\u001b[0;33m"+ message+"\u001b[m");
    }

    public static void infoBlue(String message){
        consoleAppender.setName("Console");
        LOGGER.addAppender(consoleAppender);
        LOGGER.addAppender(appender);
        LOGGER.setLevel((Level) Level.INFO);
        LOGGER.info("\u001b[0;34m"+ message+"\u001b[m");
    }

    public static void infoMagenta(String message){
        consoleAppender.setName("Console");
        LOGGER.addAppender(consoleAppender);
        LOGGER.addAppender(appender);
        LOGGER.setLevel((Level) Level.INFO);
        LOGGER.info("\u001b[0;35m"+ message+"\u001b[m");
    }



}
