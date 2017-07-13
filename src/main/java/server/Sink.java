package server;

import models.Log;

import java.util.HashMap;

public abstract class Sink extends Thread{

    HashMap<String ,String> config = null;

    public Sink(HashMap<String ,String> config) {
        this.config = config ;
    }

    public void addProperty(String key, String value)
    {
        config.put(key, value);
    }

    public String getProperty(String key)
    {
        return config.get(key);
    }

    public abstract void init() ;
    public abstract void writeMessage(Log log) ;

}
