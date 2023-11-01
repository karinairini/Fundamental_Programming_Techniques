package ro.tuc.tp.logic;

import ro.tuc.tp.model.Server;
import ro.tuc.tp.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private final List<Server> servers;
    private final int maxNoOfServers;
    private final int maxTasksPerServer;
    private Strategy strategy;
    public Scheduler(int maxNoOfServers, int maxTasksPerServer)
    {
        this.maxNoOfServers = maxNoOfServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers = new ArrayList<>();
        for(int i = 0; i < maxNoOfServers; i++)
        {
            Server server = new Server();
            Thread thread = new Thread(server);
            servers.add(server);
            thread.start();
        }
    }
    public void changeStrategy(SelectionPolicy policy)
    {
        if(policy == SelectionPolicy.SHORTEST_QUEUE)
        {
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME)
        {
            strategy = new ConcreteStrategyTime();
        }
    }
    public void dispatchTask(Task task)
    {
        strategy.addTask(servers, task);
    }
    public List<Server> getServers() {
        return servers;
    }
    public boolean isClosed()
    {
        boolean serversAreClosed = true;
        for(Server server: servers){
            if (!server.getTasks().isEmpty()) {
                serversAreClosed = false;
                break;
            }
        }
        return serversAreClosed;
    }
}