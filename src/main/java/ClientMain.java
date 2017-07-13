import client.ClientLogger;

public class ClientMain
{
    public static void main(String args[])
    {
        ClientLogger clientLogger = new ClientLogger("testClass");
        clientLogger.debug("debug client");
        clientLogger.error("debug client");
        clientLogger.info("client info");
        clientLogger.fatal("fatal issue");
        clientLogger.warning("warning from client");
    }
}
