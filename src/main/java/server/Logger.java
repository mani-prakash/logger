package server;


import Utils.Constants;
import models.Log;
import service.RabbitMQQueueConsumer;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Logger implements  ITaskHandler{

    IQueueConsumer queue ;
    Map<Level, String> sinkMap;
    Map<Level, ExecutorService> threadMap;
    Map<String, Sink> sinks;

    long count = 0 ;

    public Logger() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //init code here
        queue = new RabbitMQQueueConsumer(3, Constants.queue);
        sinkMap = new HashMap() ;
        sinks = new HashMap<String, Sink>();
        threadMap = new HashMap<Level, ExecutorService>();
    }

    public boolean setDefaultSink(String sink)
    {
        if( !sinks.containsKey(sink) )
        {
            return false;
        }
        sinkMap.put(Level.DEBUG, sink);
        sinkMap.put(Level.INFO, sink);
        sinkMap.put(Level.ERROR, sink);
        sinkMap.put(Level.FATAL, sink);
        sinkMap.put(Level.WARN, sink);
        threadMap.put(Level.DEBUG, Executors.newSingleThreadExecutor());
        threadMap.put(Level.INFO, Executors.newSingleThreadExecutor());
        threadMap.put(Level.ERROR, Executors.newSingleThreadExecutor());
        threadMap.put(Level.FATAL, Executors.newSingleThreadExecutor());
        threadMap.put(Level.WARN, Executors.newSingleThreadExecutor());
        return true;
    }

    public void addSink(String sinkType, Sink sink)
    {
        System.out.println(sinkType + " : " + sink);
        sinks.put(sinkType, sink);
    }
    public void setLevelSink(Level level, String sinkType, boolean isMulti)
    {
        sinkMap.put(level, sinkType);
        if( isMulti )
        {
            threadMap.put(level, Executors.newCachedThreadPool());
        }
        else {
            threadMap.put(level, Executors.newSingleThreadExecutor());
        }
    }

    public void start() throws InterruptedException {
        queue.startListening(this);
    }

    public void processLog(final Log log)
    {
        Level level = log.getLevel();
        final Sink sink = sinks.get(sinkMap.get(level));
        ExecutorService executorService = threadMap.get(level);
        executorService.submit(new Runnable()
        {
            public void run()
            {
                sink.writeMessage(log);
            }
        });
    }

    public void taskReceived(Log log)
    {
        processLog(log);
    }
}
