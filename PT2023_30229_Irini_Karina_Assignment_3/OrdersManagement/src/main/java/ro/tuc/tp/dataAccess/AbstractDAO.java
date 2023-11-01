package ro.tuc.tp.dataAccess;

import ro.tuc.tp.connection.ConnectionFactory;
import ro.tuc.tp.start.Reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Aceasta clasa implementeaza actiunile pe baza de date generic pe tipul de obiect T:
 * findAll - returneaza toate informatiile din baza de date
 * findById - returneaza o anumita inregistrare din baza de date, conform Id-ului furnizat ca si argument
 * insert - returneaza obiectul de tipul T pe care il inregistreaza in baza de date
 * update - editeaza campul field cu valoarea value a obiectului t de tipul T
 * delete - sterge inregistrarea cu Id-ul furnizat ca parametru
 * @param <T>
 */

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return new ArrayList<>();
    }
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } catch(IndexOutOfBoundsException e) {
            return null;
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
    private String createSelectQuery(String field) {
        return "SELECT " + " * " + " FROM " + type.getSimpleName() + " WHERE " + field + " = ?";
    }
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        if(type.getSimpleName().equals("Bill")) {
            try {
                while (resultSet.next()) {
                    Field[] fields = type.getDeclaredFields();
                    Object[] values = new Object[fields.length];
                    for (int i = 0; i < fields.length; i++) {
                        Field field = fields[i];
                        String fieldName = field.getName();
                        Object value = resultSet.getObject(fieldName);
                        values[i] = value;
                    }
                    Class<?>[] fieldTypes = new Class<?>[fields.length];
                    for (int i = 0; i < fields.length; i++) {
                        fieldTypes[i] = fields[i].getType();
                    }
                    Constructor<T> constructor = type.getDeclaredConstructor(fieldTypes);
                    constructor.setAccessible(true);
                    T instance = constructor.newInstance(values);
                    list.add(instance);
                }
            } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            Constructor[] constructors = type.getDeclaredConstructors();
            Constructor constructor = null;
            for (Constructor item : constructors) {
                constructor = item;
                if (constructor.getGenericParameterTypes().length == 0) {
                    break;
                }
            }
            try {
                while (resultSet.next()) {
                    if (constructor != null) {
                        constructor.setAccessible(true);
                    }
                    @SuppressWarnings("unchecked")
                    T instance = (T) (constructor != null ? constructor.newInstance() : null);
                    for (Field field : type.getDeclaredFields()) {
                        String fieldName = field.getName();
                        Object value = resultSet.getObject(fieldName);
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                        Method method = propertyDescriptor.getWriteMethod();
                        method.invoke(instance, value);
                    }
                    list.add(instance);
                }
            } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                     InvocationTargetException | SQLException | IntrospectionException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            Object[] rowData = new Object[type.getDeclaredFields().length];
            Reflection.retrieveProperties(t, rowData);
            for (int i = 1; i < rowData.length; i++) {
                preparedStatement.setObject(i, rowData[i]);
            }
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new " + type.getSimpleName() + " was inserted successfully!");
                return t;
            }
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return t;
    }
    private String createInsertQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" (");
        Field[] fields = type.getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            stringBuilder.append(fields[i].getName());
            if (i < fields.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(") VALUES (");
        for (int i = 1; i < fields.length; i++) {
            stringBuilder.append("?");
            if (i < fields.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
    public T update(T t, int id, String field, Object value) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = createUpdateQuery(field);
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            Object[] rowData = new Object[type.getDeclaredFields().length];
            Reflection.retrieveProperties(t, rowData);
            preparedStatement.setObject(1, value);
            preparedStatement.setInt(2, id);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("The " + type.getSimpleName() + " was updated successfully!");
                return t;
            }
        } catch(SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return t;
    }
    private String createUpdateQuery(String field) {
        return "UPDATE " + type.getSimpleName() + " SET " + field + " = ?" + " WHERE id = ?";
    }
    public boolean delete(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("The " + type.getSimpleName() + " with ID " + id + " was deleted successfully!");
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return false;
    }
}