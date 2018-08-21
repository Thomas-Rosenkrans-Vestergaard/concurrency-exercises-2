package day2.rndnumberprodcon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RandomNumberConsumer implements Runnable
{

    ArrayBlockingQueue<Integer> numbersProduced;

    public RandomNumberConsumer(ArrayBlockingQueue<Integer> numbersProduced)
    {
        this.numbersProduced = numbersProduced;
    }

    int           sumTotal  = 0;
    List<Integer> below50   = new ArrayList();
    List<Integer> aboveOr50 = new ArrayList();

    @Override
    public void run()
    {
        for (int i = 0; i < 400; i++) {
            try {
                Integer number = numbersProduced.poll(100, TimeUnit.MILLISECONDS);
                if (number == null) {
                    i--;
                    continue;
                }

                sumTotal += number;
                if (number < 50)
                    below50.add(number);
                else
                    aboveOr50.add(number);
            } catch (InterruptedException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }


    public int getSumTotal()
    {
        return sumTotal;
    }

    public List<Integer> getBelow50()
    {
        return below50;
    }

    public List<Integer> getAboveOr50()
    {
        return aboveOr50;
    }
}
