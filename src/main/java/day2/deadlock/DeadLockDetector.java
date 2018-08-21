package day2.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

class DeadLockDetector extends Thread
{

    ThreadMXBean bean = ManagementFactory.getThreadMXBean();

    public void run()
    {
        while (true) {
            long[] threadIds = bean.findDeadlockedThreads();
            if (threadIds != null)
                System.out.println("Deadlock");
        }
    }
}

