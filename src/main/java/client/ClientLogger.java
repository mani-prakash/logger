package client;

import models.Log;
import server.Level;
import service.RabbitMQ;

public class ClientLogger
{
    private String namespace;
    private RabbitMQ rabbitMQ;

    public ClientLogger(String namespace)
    {
        this.namespace = namespace;
        this.rabbitMQ = RabbitMQ.getInstance();
    }

    private Log getLog(String content, String namespace, Level level)
    {
        return new Log(content, level, namespace, System.currentTimeMillis());
    }

    public void error(String msg)
    {
        Log log = getLog(msg, namespace, Level.ERROR);
        rabbitMQ.addTask(log);
    }

    public void error(String msg, String namespace)
    {
        Log log = getLog(msg, namespace, Level.ERROR);
        rabbitMQ.addTask(log);
    }

    public void warning(String msg)
    {
        Log log = getLog(msg, namespace, Level.WARN);
        rabbitMQ.addTask(log);
    }

    public void warning(String msg, String namespace)
    {
        Log log = getLog(msg, namespace, Level.WARN);
        rabbitMQ.addTask(log);
    }

    public void fatal(String msg)
    {
        Log log = getLog(msg, namespace, Level.FATAL);
        rabbitMQ.addTask(log);
    }

    public void fatal(String msg, String namespace)
    {
        Log log = getLog(msg, namespace, Level.FATAL);
        rabbitMQ.addTask(log);
    }

    public void info(String msg)
    {
        Log log = getLog(msg, namespace, Level.INFO);
        rabbitMQ.addTask(log);
    }

    public void info(String msg, String namespace)
    {
        Log log = getLog(msg, namespace, Level.INFO);
        rabbitMQ.addTask(log);
    }

    public void debug(String msg)
    {
        Log log = getLog(msg, namespace, Level.DEBUG);
        rabbitMQ.addTask(log);
    }

    public void debug(String msg, String namespace)
    {
        Log log = getLog(msg, namespace, Level.DEBUG);
        rabbitMQ.addTask(log);
    }
}
