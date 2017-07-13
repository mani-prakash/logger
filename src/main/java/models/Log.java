package models;

import server.Level;

import java.util.Date;

public class Log {
    private String content, namespace;
    private long timeStamp;
    private Level level;

    public Log(String content,Level level,String namespace, long timeStamp) {
        this.content = content ;
        this.level = level ;
        this.namespace = namespace ;
        this.timeStamp = timeStamp;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Level getLevel()
    {
        return level;
    }

    public void setLevel(Level level)
    {
        this.level = level;
    }

    public String getNamespace()
    {
        return namespace;
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    public long getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString()
    {
        return "Log{" +
                "content='" + content + '\'' +
                ", namespace='" + namespace + '\'' +
                ", timeStamp=" + new Date(timeStamp).toString() +
                ", level=" + level +
                '}';
    }
}
