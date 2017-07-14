package service;

import Utils.Constants;
import Utils.JsonDataCreator;
import models.Log;

import com.rabbitmq.client.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RabbitMQ
{
    private final String _queueName;
    private Connection _connection;
    private List<Channel> _channels;
    private int _concurrency;

    public RabbitMQ(int concurrency, String queueName)
    {
        _concurrency = concurrency;
        _channels = new ArrayList<Channel>();
        _queueName = queueName;
        init();
    }

    public void connect()
    {
        ConnectionFactory factory = new ConnectionFactory();
        //Connection connection = null;
        try
        {
            factory.setUri(Constants.RabbitMQUrl );
            factory.setRequestedHeartbeat(60);
            factory.setConnectionTimeout(30000);
            _connection = factory.newConnection();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean init()
    {
        connect();
        if (_connection != null)
        {
            for (int i = 0; i < _concurrency; i++)
            {
                try
                {
                    initChannel();
                } catch (IOException e)
                {
                    e.printStackTrace();
                    //TODO shutdown connection
                    return false;
                }
            }
        }
        return true;
    }

    public String addTask(Log log)
    {
        JSONObject logJson = JsonDataCreator.getJsonLog(log);
        try
        {
            _channels.get(0).basicPublish("", _queueName, null, logJson.toString().getBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void initChannel() throws IOException
    {
        Channel channel = _connection.createChannel();
        _channels.add(channel);
    }
    private static RabbitMQ rabbitMQ = null;
    public static RabbitMQ getInstance()
    {
        if( rabbitMQ == null )
        {
            rabbitMQ = new RabbitMQ(3, Constants.queue);
        }
        return rabbitMQ;
    }
}
