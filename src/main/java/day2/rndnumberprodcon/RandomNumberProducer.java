package day2.rndnumberprodcon;

import java.util.concurrent.ArrayBlockingQueue;


public class RandomNumberProducer implements Runnable
{

    public static final int MAX_NUMBERS_TO_PRODUCE = 100;
    public static final int MAX_RANDOM             = 100;

    ArrayBlockingQueue<Integer> numbersProduced;

    public RandomNumberProducer(ArrayBlockingQueue<Integer> numbersProduced)
    {
        this.numbersProduced = numbersProduced;
    }

    @Override
    public void run()
    {
        for (int x = 1; x <= MAX_NUMBERS_TO_PRODUCE; x++) {
            int number = (int) ((Math.random() * MAX_RANDOM + 1));
            try {
                numbersProduced.add(number);
            } catch (IllegalStateException e) {
                x--;
            }
        }
    }
}
