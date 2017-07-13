package service;

import Utils.Constants;
import Utils.ObjectCreator;
import com.google.common.eventbus.EventBus;
import com.rabbitmq.client.*;
import models.Log;
import server.ITaskHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RabbitMQQueueConsumer implements IQueueConsumer
{
    private Connection _connection;
    private List<Channel> _channels;
    private int _concurrency;
    private ITaskHandler _taskHandler;
    private String _queue = "";

    public RabbitMQQueueConsumer(int concurrency, String queue)
    {
        _concurrency = concurrency;
        _channels = new ArrayList();
        _queue = queue;
        init();
    }

    public void connect()
    {
        ConnectionFactory factory = new ConnectionFactory();
        try
        {
            factory.setUri( Constants.RabbitMQUrl );
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
                    //TODO shutdown connection
                    return false;
                }
            }
        }
        return true;
    }

    private void initChannel() throws IOException
    {
        Channel channel = _connection.createChannel();
        _channels.add(channel);
    }

    public void startListening(ITaskHandler taskHandler)
    {
        _taskHandler = taskHandler;
        for (Channel channel : _channels)
        {
            start(channel);
        }
    }

    private void start(final Channel channel)
    {
        final Consumer consumer = new DefaultConsumer(channel)
        {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                String message = new String(body, "UTF-8");
                try
                {
                    final Log log = ObjectCreator.getLogObject(message);
                    _taskHandler.taskReceived(log);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e)
                {
                    rejectTask(channel, envelope.getDeliveryTag());
                }
            }
        };
        try
        {
            channel.basicQos(8);
            channel.basicConsume(_queue, false, consumer);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void rejectTask(Channel channel, long deliveryTag)
    {
        try
        {
            channel.basicReject(deliveryTag, false);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

