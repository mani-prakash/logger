package server;

import models.Log;
import service.IQueueConsumer;

public interface ITaskHandler
{
    public void taskReceived(Log log);
}
