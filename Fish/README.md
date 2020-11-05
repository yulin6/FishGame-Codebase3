README for Fish
Fish is a project of which the concept is making the game "Hey, That's My Fish"
playable online in a tournament-based system in order to win money, at the cost
of an entry fee. Players submit their own automatic players according to
a protocol to be published.

In this project:

The Makefile at the top-level directory (Fish/) can be used to build the project with "make", and
"make clean" removes the output directories generated that contain the .class files.

Common/ contains the files relevant to programming Fish, which includes but
is not limited to the source code and scripts used to build and test the project.
Common/src contains the code for the software components of Fish, contained within
their respective directories. At present, the only component is /game. Common/test contains
unit tests for the classes of Fish.

The resources/ folder within Common/ contains some files that are not source
code that are associated with the project; currently it contains an image of a Fish icon
we used
(obtained from https://www.cleanpng.com/png-computer-icons-fish-clip-art-pour-vector-1633443/
as well as the resized version of it that was used in our rendering.
It also contains some images relating to penguins. The base penguin image was retrieved from
https://www.hiclipart.com/free-transparent-background-png-clipart-qmoau/download
and resized/recolored for use in this project.

There are currently other additional files in Common/, including run.sh, which will bring up
a rendering of a game board. Additionally, other directories will be generated as output
when running make on the project.

Player/ contains the files relevant to programming the player component of the Fish game, which
includes but is not limited to its source code and tests, including files such as Strategy.java and
its interface.

Roadmap:
Common/src/ contains the plain Java source code written for this project
divided by software component. The game state component is contained within
game/, which has three subdirectories /model, /view, and /controller,
which contain the respective aspects of the game modelled with the MVC
approach.

Currently, most files are contained within /model. Files related to the pieces include
Tile.java, Hole.java, Board.java, IBoard.java, BoardSpace.java, BoardPosition.java, PixelPosition.java, etc.
Files related to the game state include GameState.java, Penguin.java, Player.java, IState.java, etc.
Files related to the game tree include GameTree.java, Action.java, Move.java, and Pass.java.

Player/src also contains source code for the player component; it contains files including Strategy
.java and its interface, IStrategy.java.

Player/test contains unit tests for the source code in the Player/src folder.

Planning/ contains documents describing the underlying structure of the Fish game
in broader terms as conceptualized before implementing the game. It also contains
descriptions of software components of Fish and their interfaces.

Making the project:
"make" in the same directory this README.txt is originally located in, assuming
no changes have been made to the Makefile provided. It will remove any output
folders/classes if they exist, then build the project. 

Testing:
Assuming the current working directory is Common/:
- "make clean" to clear project artifacts
- "make" to generate a fresh build from the source code
- "cd ../" to move up a directory to the top-level Fish/ directory
- "./xtest" to run the test script (this will cd Common, run the tests, cd back, cd to Player, run
the tests for that directory, then cd back again)
- Test source code can be found within Common/test/, with further subdirectories
depending on the software component the tests were written for. Player/test contains unit tests for
the player component.
- Individual tests for Common can be run from inside Common/ with
"java -cp /usr/share/java/junit4.jar:out/:out-test/:resources/ org.junit.runner.JUnitCore <classname>"
where <classname> is the test class to run, properly prepended with the package it belongs to.
- Individual tests for Player can be run from inside Player/ with
"java -cp /usr/share/java/junit4.jar:../Common/out/:../Common/out-test/:../Common/resources/ org.junit.runner.JUnitCore <classname>"
where <classname> is the test class to run, properly prepended with the package it belongs to.