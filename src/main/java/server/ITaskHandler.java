package server;

import models.Log;

public interface ITaskHandler
{
    public void taskReceived(Log log);
}
