package day2.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

class DeadLockDetector extends Thread
{

    private boolean run = true;
    ThreadMXBean bean = ManagementFactory.getThreadMXBean();

    public void run()
    {
        while (run) {
            long[] threadIds = bean.findDeadlockedThreads();
            if (threadIds != null)
                throw new IllegalStateException("Deadlock");
        }
    }

    public void end()
    {
        this.run = false;
    }
}

