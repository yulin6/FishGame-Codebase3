README for Fish
Fish is a project of which the concept is making the game "Hey, That's My Fish"
playable online in a tournament-based system in order to win money, at the cost
of an entry fee. Players submit their own automatic players according to
a protocol to be published.

In this project:

Common/ contains the files relevant to programming Fish, which includes but
is not limited to the source code and scripts used to build and test the project.
Common/src contains the code for the software components of Fish, contained within
their respective directories. At present, the only component is /gamestate.

The Makefile within Common/ can be used to build the project with "make", and
"make clean" removes the output directories generated that contain the .class files.

The resources/ folder within Common/ contains some files that are not source
code that are associated with the project; currently it contains an image of a Fish icon
we used
(obtained from https://www.cleanpng.com/png-computer-icons-fish-clip-art-pour-vector-1633443/
as well as the resized version of it that was used in our rendering.

There are currently two .sh files, run.sh and all-tests.sh. run.sh will bring up
a rendering of a game board, while all-tests.sh is part of the test harness for
this project. See the later "Testing" section for more details.

Roadmap:
Common/src/ contains the plain Java source code written for this project
divided by software component. The game state component is contained within
/gamestate/, which has three subdirectories /model, /view, and /controller,
which contain the respective aspects of the gamestate modelled with the MVC
approach.

Planning/ contains documents describing the underlying structure of the Fish game
in broader terms as conceptualized before implementing the game. It also contains
descriptions of software components of Fish and their interfaces.

Testing:
Assuming the current working directory is Common/:
- "make clean" to clear project artifacts
- "make" to generate a fresh build from the source code
- "sh all-tests.sh" to run all tests for milestone 2 - "The Game Pieces"
  which may later be renamed for clarity
- Individual tests for milestone 2 may be run with
"java -cp /usr/share/java/junit4.jar:out/:out-test/ org.junit.runner.JUnitCore gamestate.model.<CLASSNAME>", where <CLASSNAME> is one of BoardPositionTest, PixelPositionTest, TileTest, or BoardTest
- Test source code can be found within Common/test/, with further subdirectories
depending on the software component the tests were written for.
