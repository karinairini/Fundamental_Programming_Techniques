package ro.tuc.tp.start;

import java.lang.reflect.Field;

/**
 * Aceasta clasa utilizeaza tehnica Reflection.
 * Scopul metodei este să extragă valorile proprietăților (câmpurilor) obiectului dat și să le stocheze
 * în array-ul rowData, folosit pentru a genera tablele din interfata grafica.
 */

public class Reflection {
    public static void retrieveProperties(Object object, Object[] rowData) {
        int i = 0;
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                rowData[i] = value;
                i++;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
