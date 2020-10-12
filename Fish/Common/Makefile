OUTDIR = -d out 
TESTDIR = -d out-test
TESTDEP = -cp /usr/share/java/junit4.jar:out/
JAVAC = javac

JAVAFILES = \
	src/game/view/*.java \
	src/game/model/*.java \
	src/game/controller/*.java \

TESTFILES = \
	test/game/model/*.java \

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
