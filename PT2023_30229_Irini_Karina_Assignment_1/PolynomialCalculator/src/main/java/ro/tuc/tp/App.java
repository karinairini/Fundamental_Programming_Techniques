package ro.tuc.tp;

import ro.tuc.tp.gui.View;
import javax.swing.*;

public class App
{
    public static void main( String[] args )
    {
        JFrame frame = new View("POLYNOMIAL CALCULATOR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}