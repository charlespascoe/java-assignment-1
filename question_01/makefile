OUT=imperative_vs_declarative.exe
COMPILE=mcs -out:bin/$(OUT) -pkg:dotnet -recurse:'src/*.cs'

all:
	mkdir -p bin/
	$(COMPILE)

debug:
	$(COMPILE) -debug
	mono --debug bin/$(OUT)

run: all
	mono bin/$(OUT)
