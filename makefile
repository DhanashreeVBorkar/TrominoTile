JC = javac
JVM = java
SRC = TrominoTile.java
BIN = TrominoTile

all: $(BIN)

$(BIN): $(SRC)
	$(JC) $(SRC)

run:
	$(JVM) $(BIN) 
	
clean:
	$(RM) $(BIN).class