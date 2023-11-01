package ro.tuc.tp.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private final BlockingQueue<Task> tasks;
    private final AtomicInteger waitingPeriod;
    private int nbOfClientsProcessed = 0;
    private int time = 0;
    public Server() {
        this.tasks = new LinkedBlockingDeque<>();
        this.waitingPeriod = new AtomicInteger(0);
    }
    public BlockingQueue<Task> getTasks() {
        return tasks;
    }
    public AtomicInteger getWaitingPeriod() { return waitingPeriod;}
    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime().get());
    }
    public int getNbOfClientsProcessed() {
        return nbOfClientsProcessed;
    }
    public int getTime() {
        return time;
    }
    public int getSize()
    {
        if(tasks.size() > 0) {
            return tasks.size() - 1;
        }
        return 0;
    }
    public void run() {
        while(true) {
            if(!tasks.isEmpty()) {
                Task task = tasks.peek();
                try {
                    nbOfClientsProcessed++;
                    while (task.getServiceTime().get() > 0) {
                        time++;
                        Thread.sleep(1000);
                        if(waitingPeriod.get() > 0)
                            waitingPeriod.decrementAndGet();
                        task.setServiceTime(new AtomicInteger(task.getServiceTime().decrementAndGet()));
                        if (task.getServiceTime().get() == 0) {
                            tasks.remove(task);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String printServer() {
        if(tasks.isEmpty())
            return "closed";
        StringBuilder string = new StringBuilder();
        for(Task task: tasks)
            string.append(task.toString()).append("; ");
        return string.toString();
    }
}