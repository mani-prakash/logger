import client.ClientLogger;

public class ClientMain
{
    public static void main(String args[])
    {
        ClientLogger clientLogger = new ClientLogger("testClass");
        clientLogger.debug("debug client");
        clientLogger.error("debug client error");
        clientLogger.info("client info 1");
        clientLogger.info("client info 2 ");
        clientLogger.info("client info 3");
        clientLogger.fatal("fatal issue");
        clientLogger.warning("warning from client");
        System.out.println("done");
    }
}
