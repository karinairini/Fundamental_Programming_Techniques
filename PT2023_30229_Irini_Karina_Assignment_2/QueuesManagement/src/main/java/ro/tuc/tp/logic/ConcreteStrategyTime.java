package ro.tuc.tp.logic;

import ro.tuc.tp.model.Task;
import ro.tuc.tp.model.Server;

import java.util.List;

public class ConcreteStrategyTime implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        Server suitableServer = new Server();
        int minimum = Integer.MAX_VALUE;
        for(Server server: servers) {
            if(server.getWaitingPeriod().get() < minimum) {
                minimum = server.getWaitingPeriod().get();
                suitableServer = server;
            }
        }
        suitableServer.addTask(task);
    }
}