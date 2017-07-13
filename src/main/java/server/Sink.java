package server;

import Utils.Constants;
import models.Log;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public abstract class Sink{

    public HashMap<String ,String> config = null;
    public Format dateFormater;

    public Sink(HashMap<String ,String> config) {
        String dateFormat = Constants.defalutDateFormat;
        this.config = config ;
        if(config.containsKey(Constants.time_format)) {
            System.out.println( "date format :" + config.get(Constants.time_format));
            dateFormat = config.get(Constants.time_format);
        }
        setDateFormat(dateFormat);
    }

    public void addProperty(String key, String value)
    {
        config.put(key, value);
    }

    public void setDateFormat(String dataFormat)
    {
        dateFormater = new SimpleDateFormat(dataFormat);
    }

    public String getProperty(String key)
    {
        return config.get(key);
    }

    public abstract void init() ;
    public abstract void writeMessage(Log log) ;

}
