**README for Fish**

Fish is a project of which the concept is making the game "Hey, That's My Fish"
playable online in a tournament-based system in order to win money, at the cost
of an entry fee. Players submit their own automatic players according to
a protocol to be published.

In this project:
    
    Fish/
        
        Makefile
            - Used to build the project with "make", and
            "make clean" removes the output directories generated that contain the .class files.
            
        Admin/
            - Contains the files relevant to all administrative components of the Fish game.
            
            src/
                - contains source code for all administrative components of the Fish game. For now, this only includes the referee.
                
                referee/
                    - the package for all source code for the referee component of Fish games
            
            test/
                - contains unit tests for all administrative components of the Fish game.
                
                referee/
                     - the package for all unit tests for the referee component of Fish games.
        
        Common/
            - Contains the files relevant to programming the common components of Fish, which includes 
            but is not limited to the game board, game state, game tree, and the lower level data
            representations that compose them.
            
            resources/
                 - contains resources, such as images of penguins and fish, used by the Fish game. The source fish image is found at https://www.cleanpng.com/png-computer-icons-fish-clip-art-pour-vector-1633443/, and the source penguin image is found at https://www.hiclipart.com/free-transparent-background-png-clipart-qmoau/download
            
            src/
                 - contains the code for the common software components of Fish, contained within
                 their respective directories.
                
                controller/
                    - contains source code for class used to bring together model and view components to display a game of Fish
                
                model/
                     - contains source code for common software components of Fish
                    - Files related to the pieces include
                    Tile.java, Hole.java, Board.java, IBoard.java, BoardSpace.java, BoardPosition.java, PixelPosition.java, etc.
                    Files related to the game state include GameState.java, Penguin.java, Player.java, IState.java, etc.
                    Files related to the game tree include GameTree.java, Action.java, Move.java, and Pass.java.
                
                view/
                    - contains source code for displaying a game of Fish
            
            test/
                - contains unit tests for all common software components of Fish
            
            run.sh
                - After running the Makefile, running this displays a random test board of the Fish game.
        
        Planning/
            - contains documents describing the underlying structure of the Fish game
            in broader terms as conceptualized before implementing the game. It also contains
            descriptions of software components of Fish and their interfaces.
        
        Player/
            - contains the files relevant to external player components, including a representation of a house player.
            
            src/
                - contains all the source code for external player components
                
                player/
                     - the package for all source code relevant to the representation of a house player
            
            test/
                - contains all unit tests for external player components
                
                player/
                     - the package for all unit tests for the representation of a house player

Testing:
Assuming the current working directory is Fish/:
- "make clean" to clear project artifacts
- "make" to generate a fresh build from the source code
- "./xtest" to run the test script (this will cd Common, run the tests, cd back, cd to Player, run
the tests for that directory, cd back, cd to Admin, run the tests, then cd back.)
- Test source code can be found within Common/test/, with further subdirectories
depending on the software component the tests were written for. Player/test contains unit tests for
the player component. Admin/test contains unit tests for administrative components.
- Individual tests for Common can be run from inside Common/ with
"java -cp /usr/share/java/junit4.jar:out/:out-test/:resources/ org.junit.runner.JUnitCore <classname>"
where <classname> is the test class to run, properly prepended with the package it belongs to.
- Individual tests for Player can be run from inside Player/ with
"java -cp /usr/share/java/junit4.jar:../Common/out/:../Common/out-test/:../Common/resources/ org.junit.runner.JUnitCore <classname>"
where <classname> is the test class to run, properly prepended with the package it belongs to.
- Individual tests for Admin can be run from inside Admin with
"java -cp /usr/share/java/junit4.jar:../Common/out/:../Common/out-test/:../Common/resources/ org.junit.runner.JUnitCore <classname>"
where <classname> is the test class to run, properly prepended with the package it belongs to.