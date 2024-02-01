package JavaDemo.Integrations.Logger;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Log {
    private static final Logger logger = LogManager.getLogger(Log.class);

    @Value("${log}")
    private boolean enableLog;
    private static boolean ENABLE_LOG;

    @PostConstruct
    public void initLog(){
        ENABLE_LOG = enableLog;
    }


    /**
     * method to display errors in log.
     * @param className name of class in which error occurred.
     * @param methodName name of method in which error occurred.
     * @param exception stack trace of exception
     */
    public static void error (String className,String methodName,String exception)
    {
        if(ENABLE_LOG){
            logger.error("ClassName :"+className);
            logger.error("MethodName :"+methodName );
            logger.error("Exception :" +exception);
            logger.error("-----------------------------------------------------------------------------------");
        }

    }

    /**
     * method to display information in logs
     * @param message message to be displayed
     */
    public static void info(String message){
        if(ENABLE_LOG) {
            logger.info(message);
        }
    }

    public static void warn(String message){
        if(ENABLE_LOG) {
            logger.warn(message);
        }
    }

    public static void infoRed(String message){
        if(ENABLE_LOG) {
            logger.info("\u001b[0;31m" + message + "\u001b[m");
        }
    }

    public static void infoGreen(String message){
        if(ENABLE_LOG) {
            logger.info("\u001b[0;32m" + message + "\u001b[m");
        }
    }

    public static void infoYellow(String message){
        if(ENABLE_LOG) {
            logger.info("\u001b[0;33m" + message + "\u001b[m");
        }
    }

    public static void infoBlue(String message){
        if(ENABLE_LOG) {
            logger.info("\u001b[0;34m" + message + "\u001b[m");
        }
    }

    public static void infoMagenta(String message){
        if(ENABLE_LOG) {
            logger.info("\u001b[0;35m" + message + "\u001b[m");
        }
    }



}
