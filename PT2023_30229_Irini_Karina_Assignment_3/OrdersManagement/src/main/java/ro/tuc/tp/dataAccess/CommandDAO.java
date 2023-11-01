package ro.tuc.tp.dataAccess;

import ro.tuc.tp.model.Command;

import java.util.List;

/**
 * Aceasta clasa extinde clasa AbstractDAO pe tipul de obiect Command si implementeaza actiunile
 * corespunzatoare acestuia.
 */

public class CommandDAO extends AbstractDAO<Command> {
    public Command findCommandById(int commandId) {
        return super.findById(commandId);
    }
    public Command insertCommand(Command command) {
        return super.insert(command);
    }
    public void deleteCommand(Command command) {
        super.delete(command.getId());
    }
    public Command updateCommand(Command command, String field, Object valueChanged) {
        return super.update(command, command.getId(), field, valueChanged);
    }
    public List<Command> findAll() {
        return super.findAll();
    }
}
