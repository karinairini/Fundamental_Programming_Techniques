package ro.tuc.tp.logic;

import ro.tuc.tp.gui.View;
import ro.tuc.tp.model.Task;
import ro.tuc.tp.model.Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable {
    public int timeLimit = 20;
    public int maxProcessingTime = 10;
    public int minProcessingTime = 2;
    public int maxArrivalTime = 5;
    public int minArrivalTime = 1;
    public int numberOfServers = 5;
    public int numberOfClients = 6;
    public SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    private final List<Task> generatedTasks = new ArrayList<>();
    private FileWriter fileWriter;
    public View view;
    public Thread thread;
    public SimulationManager() {
        view = new View();
        this.thread = new Thread(this);
        view.addActionListener(new ControlButton());
    }
    private void generateNRandomTasks(int n) {
        for (int i = 0; i < n; i++) {
            AtomicInteger processingTime = new AtomicInteger((int) (Math.random() * (maxProcessingTime - minProcessingTime)) + minProcessingTime);
            int arrivalTime = (int) (Math.random() * (maxArrivalTime - minArrivalTime + 1)) + minArrivalTime;
            Task task = new Task(i + 1, arrivalTime, processingTime);
            generatedTasks.add(task);
        }
        generatedTasks.sort(null);
    }
    public void sleepThread() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        String result = "";
        int currentTime = 0, peakHour = Integer.MIN_VALUE, peakHourTime = 0, nbClientsDispatched = 0;
        float averageWaitingTime = 0;
        try {
            fileWriter = new FileWriter("ThirdTestQueueStrategy.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (currentTime < timeLimit) {
            if (!generatedTasks.isEmpty() || !scheduler.isClosed()) {
                ArrayList<Task> removableTasks = new ArrayList<>();
                for (Task task : generatedTasks) {
                    if (task.getArrivalTime() == currentTime) {
                        scheduler.dispatchTask(task);
                        nbClientsDispatched++;
                        removableTasks.add(task);
                    }
                }
                for(Server server: scheduler.getServers()){
                    averageWaitingTime += server.getSize();
                }
                generatedTasks.removeAll(removableTasks);
                result = printResult(currentTime, fileWriter, result);
                view.setTextArea(result);
                if(peakHour() >= peakHour) {
                    peakHour = peakHour();
                    peakHourTime = currentTime;
                }
                currentTime++;
                this.sleepThread();
            }
            else break;
        }
        float averageServiceTime = averageServiceTime();
        averageWaitingTime /= nbClientsDispatched;
        result = printResult(currentTime, fileWriter, result);
        view.setTextArea(result);
        printStatistics(averageServiceTime, peakHourTime, averageWaitingTime);
        closeFileWriter(fileWriter);
    }
    public void closeFileWriter(FileWriter fileWriter){
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void printStatistics(float averageServiceTime, int peakHourTime, float averageWaitingTime) {
        try {
            fileWriter.write("Average Service Time: " + averageServiceTime + "\n");
            fileWriter.write("Peak Hour: " + peakHourTime + "\n");
            fileWriter.write("Average Waiting Time: " + averageWaitingTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Average Service Time: " + averageServiceTime);
        System.out.println("Peak Hour: " + peakHourTime);
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }
    public String printResult(int currentTime, FileWriter fileWriter, String result) {
        try {
            fileWriter.write("Time " + currentTime + "\n");
            result = result + "Time " + currentTime + "\n";
            fileWriter.write("Waiting clients: ");
            result = result + "Waiting clients: ";
            for (Task generatedTask : generatedTasks) {
                fileWriter.write(generatedTask.toString() + "; ");
                result = result + generatedTask + "; ";
            }
            fileWriter.write("\n");
            result += "\n";
            int queueNumber = 0;
            for (Server server : scheduler.getServers()) {
                fileWriter.write("Queue " + (queueNumber + 1) + ": ");
                result = result + "Queue " + (queueNumber + 1) + ": ";
                fileWriter.write(server.printServer() + "\n");
                result = result + server.printServer() + "\n";
                queueNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public float averageServiceTime() {
        float averageTime = 0;
        for(Server server: scheduler.getServers()) {
            if(server.getNbOfClientsProcessed() != 0) {
                averageTime += (float)server.getTime() / server.getNbOfClientsProcessed();
            }
        }
        return averageTime / scheduler.getServers().size();
    }
    public int peakHour() {
        int tasksSum = 0;
        for(Server server: scheduler.getServers()) {
            tasksSum += server.getTasks().size();
        }
        return tasksSum;
    }
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
    public void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }
    public void setMinProcessingTime(int minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }
    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }
    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }
    public void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }
    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }
    public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
        this.selectionPolicy = selectionPolicy;
    }
    public static void main(String[] args) {
        new SimulationManager();
    }
    class ControlButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("START")) {
                view.setTextArea("");
                setTimeLimit(view.getTextField(view.getTimeLimitTextField()));
                setMaxProcessingTime(view.getTextField(view.getMaxProcessingTimeTextField()));
                setMinProcessingTime(view.getTextField(view.getMinProcessingTimeTextField()));
                setMaxArrivalTime(view.getTextField(view.getMaxArrivalTimeTextField()));
                setMinArrivalTime(view.getTextField(view.getMinArrivalTimeTextField()));
                setNumberOfServers(view.getTextField(view.getNumberOfServersTextField()));
                setNumberOfClients(view.getTextField(view.getNumberOfClientsTextField()));
                if (view.getComboBox().equals("ConcreteStrategyTime"))
                    setSelectionPolicy(SelectionPolicy.SHORTEST_TIME);
                if(view.getComboBox().equals("ConcreteStrategyQueue"))
                    setSelectionPolicy(SelectionPolicy.SHORTEST_QUEUE);
                scheduler = new Scheduler(numberOfServers, numberOfClients);
                scheduler.changeStrategy(selectionPolicy);
                generateNRandomTasks(numberOfClients);
                thread.start();
            }
        }
    }
}