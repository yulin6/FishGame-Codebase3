OUTDIR = -d Common/out
TESTDIR = -d Common/out-test
TESTDEP = -cp /usr/share/java/junit4.jar:Common/out/:Common/resources
JAVAC = javac

JAVAFILES = \
	Common/src/game/view/*.java \
	Common/src/game/model/*.java \
	Common/src/game/controller/*.java \
	Player/src/player/*.java \
    Admin/src/referee/*.java \
	Admin/src/tmanager/*.java

TESTFILES = \
	Common/test/game/model/*.java \
	Player/test/player/*.java \
    Admin/test/referee/*.java \
	Admin/test/tmanager/*.java

default: clean sources testsources

sources:
	mkdir Common/out
	$(JAVAC) $(OUTDIR) $(TESTDEP) $(JAVAFILES)

testsources:
	mkdir Common/out-test
	$(JAVAC) $(TESTDIR) $(TESTDEP) $(TESTFILES)

clean:
	$(RM) -r Common/out
	$(RM) -r Common/out-test
