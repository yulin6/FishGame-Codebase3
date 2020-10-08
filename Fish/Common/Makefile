OUTDIR = -d out 
TESTDIR = -d out-test
TESTDEP = -cp /usr/share/java/junit4.jar:out/
JAVAC = javac

JAVAFILES = \
	src/gamestate/view/*.java \
	src/gamestate/model/*.java \
	src/gamestate/controller/*.java \

TESTFILES = \
	test/gamestate/model/*.java \

default: sources testsources

sources:
	mkdir out
	$(JAVAC) $(OUTDIR) $(JAVAFILES) 

testsources:
	mkdir out-test
	$(JAVAC) $(TESTDIR) $(TESTDEP) $(TESTFILES)

clean:
	$(RM) -r out
	$(RM) -r out-test
