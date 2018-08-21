package day2.webscrapprodcon;

public class DocumentCounter
{

    private int consumed = 0;
    private int produced = 0;


    public synchronized int incrementConsumed()
    {
        return ++consumed;
    }

    public synchronized int readConsumed()
    {
        return consumed;
    }

    public synchronized int incrementProduced()
    {
        return ++produced;
    }

    public synchronized int readProduced()
    {
        return produced;
    }
}
