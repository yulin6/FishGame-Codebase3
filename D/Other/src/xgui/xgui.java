package xgui;

import javax.swing.*;
import java.awt.*;

/**
 * Class to provide the functionality of xgui. 
 */
public class xgui extends JFrame {

    public xgui(int size) {
        //Enough space to fit the hexagon
        setSize(size * 3, size * 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    
    }
    /**
     * Main method of xgui - draws a hexagon based on argument into the window
     * and closes window & shuts down upon clicking inside it.
     * @args the arguments called at the command line.
     */
    public static void main(String[] args) {
        inputCheck(args);
        
        new xgui(Integer.parseInt(args[0])); 
        // If this point is reached, our input (singular) is valid
        System.out.println("Debug: input value is: " + args[0]);        
        
    }
    
    /**
     * Helper function for quick input checking.
     * @inputs the command line arguments to the program.
     */
    private static void inputCheck(String[] inputs) {
        if (inputs.length != 1) {
            System.out.println("usage: ./xgui positive-integer");
            System.exit(-1);
        }

        int x = -1;

        try {
            x = Integer.parseInt(inputs[0]);
        } catch (NumberFormatException e) {
            System.out.println("usage: ./xgui positive-integer");
            System.exit(-2);
        }

        if (x < 0) {
            System.out.println("usage: ./xgui positive-integer");
            System.exit(-3);
        }
    }

    public void paint(Graphics g) {
        int w = this.getSize().width - 1;
        int h = this.getSize().height - 1;
        Polygon p = new Polygon();
        p.addPoint(0, h/2);
        p.addPoint(w/3, h);
        p.addPoint(2 * (w/3), h);
        p.addPoint(w, h/2);
        p.addPoint(2 * (w/3), 0);
        p.addPoint(w/3, 0);
        g.drawPolygon(p);
    }
}

