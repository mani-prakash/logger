import server.Logger;
import server.LoggerHandler;

import java.lang.reflect.InvocationTargetException;

public class ServerMain
{
    public static void main(String args[]) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InterruptedException
    {
        Logger logger = new Logger();
        //LoggerHandler loggerHandler = new LoggerHandler()
        logger.start();
    }
}
