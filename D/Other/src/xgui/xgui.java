package xgui;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Class to provide the functionality of xgui. 
 */
public class xgui extends JFrame implements MouseListener {
    private int width;
    private int height;
    private Polygon hex;

    public xgui(int size) {
        // Enough space to fit the hexagon
        width = 3 * size;
        height = 2 * size;

        addMouseListener(this);
        setSize(size * 6, size * 4);
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

        if (x <= 0) {
            System.out.println("usage: ./xgui positive-integer");
            System.exit(-3);
        }
    }

    public void paint(Graphics g) {
        int w = this.width;
        int h = this.height;
        int bottom = this.getSize().height - 1;
        hex = new Polygon();
        hex.addPoint(0, bottom - h/2);
        hex.addPoint(w/3, bottom);
        hex.addPoint(2 * (w/3), bottom);
        hex.addPoint(w, bottom - h/2);
        hex.addPoint(2 * (w/3), bottom - h);
        hex.addPoint(w/3, bottom - h);
        g.drawPolygon(hex);
    }

    @Override public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (hex.contains(new Point(x, y))) {
            System.exit(0);   
        }
    }
    
    // Leave these overrides empty; we need implementations to build, but only clicked is needed
    @Override public void mouseEntered(MouseEvent e) { }

    @Override public void mouseExited(MouseEvent e) { }

    @Override public void mousePressed(MouseEvent e) { }

    @Override public void mouseReleased(MouseEvent arg0) { }

}

