package server;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by codedog29 on 7/13/17.
 */
public class Main {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, InterruptedException, NoSuchMethodException, InvocationTargetException {
        Logger logger = new Logger() ;
        logger.start();
//        Sink sink = new sinks.FileSink(new HashMap<>()) ;
//        System.out.println(sink.getClass().getName());
    }

}
