package service;

import com.google.common.eventbus.EventBus;
import models.Log;
import server.ITaskHandler;

public interface IQueueConsumer
{
    public void startListening(ITaskHandler taskHandler);
}
