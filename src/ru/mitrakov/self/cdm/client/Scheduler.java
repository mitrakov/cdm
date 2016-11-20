package ru.mitrakov.self.cdm.client;

import java.util.concurrent.*;

/**
 * @author Tommy
 */
public class Scheduler {
    private static Scheduler self = new Scheduler();
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    
    private Scheduler() {}
    
    public static Scheduler getInstance() {return self;}
    
    public void run(Runnable task, int delayMsec) {
        service.schedule(task, delayMsec, TimeUnit.MILLISECONDS);
    }
}
