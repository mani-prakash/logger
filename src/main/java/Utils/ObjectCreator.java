package Utils;

import models.Log;
import org.json.JSONObject;
import server.Level;

public class ObjectCreator
{
    public static Log getLogObject(String data)
    {
        return getLogObject(new JSONObject(data));
    }
    public static Log getLogObject(JSONObject jsonLog)
    {
        Log log = new Log(jsonLog.getString("content"),
                Level.valueOf(jsonLog.getString("level")), jsonLog.getString("namespace"), jsonLog.getLong("timeStamp") );
        return log;
    }
}
