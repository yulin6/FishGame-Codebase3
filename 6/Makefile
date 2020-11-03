OUTDIR = -d Other/out
DEP = -cp Other/dep/gson-2.8.6.jar:Other/out:../Fish/Common/resources
JAVAC = javac

JAVAFILES = \
	Other/src/*.java \
    ../Fish/Common/src/game/model/*.java \
    ../Fish/Common/src/game/controller/*.java \
    ../Fish/Common/src/game/view/*.java \
    ../4/Other/src/*.java

default: sources

sources:
	mkdir Other/out
	$(JAVAC) $(OUTDIR) $(DEP) $(JAVAFILES)

clean:
	$(RM) -r Other/out
