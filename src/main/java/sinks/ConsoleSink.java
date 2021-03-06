package sinks;

import models.Log;
import server.Sink;
import java.util.Date;
import java.util.HashMap;

public class ConsoleSink extends Sink
{

    public ConsoleSink(HashMap<String, String> config) {
        super(config);
        init();
    }

    public String getLogAsString(Log log)
    {
        return "Log{" +
                "content='" + log.getContent() + '\'' +
                ", namespace='" + log.getNamespace() + '\'' +
                ", timeStamp=" + this.dateFormater.format(new Date(log.getTimeStamp())).toString() +
                ", level=" + log.getLevel() +
                '}';
    }

    @Override
    public void init() {
    }

    @Override
    public void writeMessage(Log log) {
        System.out.println(getLogAsString(log));
    }
}
