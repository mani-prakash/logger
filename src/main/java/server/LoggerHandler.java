package server;

import Utils.Constants;
import sinks.ConsoleSink;
import sinks.FileSink;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/*
* ts_format
* class_name
*
* */
public class LoggerHandler
{
    private Logger logger;
    public LoggerHandler(String fileLocation, Logger logger)
    {
        this.logger = logger;
        this.logger.sinks.put("console", new ConsoleSink(new HashMap<String, String>()));
        this.logger.setDefaultSink("console");
        setConfig(fileLocation);
    }

    public Sink createSync(Map<String, String> config){
        Sink sink = null;
        try
        {
            System.out.println(config.get(Constants.class_name));
            sink = (Sink) Class.forName(config.get(Constants.class_name)).getConstructor(HashMap.class).newInstance(config);
            return sink;
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return sink;
    }

    private String getFileData(String fileLocation)
    {
        StringBuffer stringBuffer = new StringBuffer();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileLocation)));
            int i = -1;
            while ( (i = bufferedReader.read()) != -1 )
            {
                stringBuffer.append((char) i);
            }
            return stringBuffer.toString();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public void startLogger(){
        try
        {
            logger.start();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void setConfig(String fileLocation)
    {
        String configData = getFileData(fileLocation);
        String[] configLines = configData.split("\n");
        Map<String, String> configProps = new HashMap<String, String>();
        Set<String> sinks = new HashSet<String>();
        for( int i = 0 ; i < configLines.length; i++ )
        {
            String line = configLines[i];
            if(line.contains("ts_format"))
            {
                if( configProps.size() != 0 )
                {
                    if( !sinks.contains(configProps.get(Constants.sink_type)) )
                    {
                        Sink sink = createSync(configProps);
                        logger.addSink(configProps.get(Constants.sink_type) , sink);
                    }
                    boolean threadModel = false;
                    if(configProps.get(Constants.thread_model).equalsIgnoreCase("MULTI"))
                    {
                        threadModel = true;
                    }
                    logger.setLevelSink( Level.valueOf(configProps.get(Constants.log_level)),
                            configProps.get(Constants.sink_type), threadModel);
                }
                configProps = new HashMap<String, String>();
            }
            String[] prop = line.split(":");
            configProps.put(prop[0], prop[1]);
        }
    }
}
