package day2.deadlock;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Tester
{
    public static void main(String[] args)
    {

        try {

            //            DeadLockDetector dead = new DeadLockDetector();
            //            dead.start();

            ResourceContainer resources = new ResourceContainer();
            ResourceUser1     t1        = new ResourceUser1(resources);
            ResourceUser2     t2        = new ResourceUser2(resources);
            t1.start();
            t2.start();

            t1.join();
            t2.join();

            System.out.println("Done");
            System.out.println("Words incrementProduced: " + resources.getResourceWords().size());
            System.out.println("Numbers incrementProduced: " + resources.getResourceNumbers().size());
        } catch (InterruptedException ex) {
            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
