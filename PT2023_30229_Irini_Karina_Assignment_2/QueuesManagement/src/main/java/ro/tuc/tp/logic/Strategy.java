package ro.tuc.tp.logic;

import ro.tuc.tp.model.Task;
import ro.tuc.tp.model.Server;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task task);
}
