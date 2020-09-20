package xgui;

/**
 * Class to provide the functionality of xgui. 
 */
public class xgui {

    /**
     * Main method of xgui - draws a hexagon based on argument into the window
     * and closes window & shuts down upon clicking inside it.
     * @args the arguments called at the command line.
     */
    public static void main(String[] args) {
        inputCheck(args);
        
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
}

