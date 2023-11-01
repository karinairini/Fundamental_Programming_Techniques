package ro.tuc.tp.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Task implements Comparable<Task>{
    private final int ID;
    private final int arrivalTime;
    private AtomicInteger serviceTime;
    public Task(int ID, int arrivalTime, AtomicInteger serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
    public Integer getArrivalTime() {
        return arrivalTime;
    }
    public AtomicInteger getServiceTime() {
        return serviceTime;
    }
    public void setServiceTime(AtomicInteger serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public int compareTo(Task o) {
        return this.getArrivalTime().compareTo(o.getArrivalTime());
    }

    @Override
    public String toString() {
        return '(' + "ID=" + ID + ", arrivalTime=" + arrivalTime + ", serviceTime=" + serviceTime.get() + ')';
    }
}