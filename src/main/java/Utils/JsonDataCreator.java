package Utils;

import models.Log;
import org.json.JSONObject;

public class JsonDataCreator
{
    public static JSONObject getJsonLog(Log log)
    {
        JSONObject jsonLog = new JSONObject();
        jsonLog.put("content", log.getContent());
        jsonLog.put("namespace", log.getNamespace());
        jsonLog.put("timeStamp", log.getTimeStamp());
        jsonLog.put("level", log.getLevel());
        return jsonLog;
    }
}
