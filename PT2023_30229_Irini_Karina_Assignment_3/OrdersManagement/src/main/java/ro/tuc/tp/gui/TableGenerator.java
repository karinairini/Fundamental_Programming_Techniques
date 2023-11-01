package ro.tuc.tp.gui;

import ro.tuc.tp.start.Reflection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Am ales sa implementez o clasa suplimentara de generare a tabelului avand in vedere utilizarea multipla
 * a metodei implementate.
 * @param <T>
 */

public class TableGenerator<T> {
    public ArrayList<Object> generateTable(ArrayList<T> list, Color colorTable, Color colorBackground, String tableName) {
        ArrayList<Object> arrayList = new ArrayList<>();
        JPanel tablePanel = new JPanel();
        tablePanel.setName(tableName);
        tablePanel.setLayout(new BorderLayout());
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        Field[] fields = list.get(0).getClass().getDeclaredFields();
        for (Field field : fields) {
            tableModel.addColumn(field.getName());
        }
        for (Object obj : list) {
            Object[] rowData = new Object[fields.length];
            Reflection.retrieveProperties(obj, rowData);
            tableModel.addRow(rowData);
        }
        JTable table = new JTable(tableModel);
        table.setBackground(colorTable);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Calibre", Font.BOLD, 18));
        table.setRowHeight(25);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setGridColor(Color.BLACK);
        table.getTableHeader().setFont(new Font("TimesNewRoman", Font.BOLD, 20));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setCellSelectionEnabled(true);
        JScrollPane scrollPane = new JScrollPane(table);
        JViewport viewport = scrollPane.getViewport();
        viewport.setBackground(colorBackground);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(scrollPane);
        arrayList.add(tablePanel);
        arrayList.add(table);
        return arrayList;
    }
}