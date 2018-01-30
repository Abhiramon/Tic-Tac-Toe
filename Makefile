all:
	javac src/GUI/Game.java src/GUI/mulGUI.java -d bin
	javac -classpath bin/ src/Singleplayer/GameTree.java -d bin
	javac -classpath bin/ src/Multiplayer/GameMultiplayer.java src/Multiplayer/GameNetworkingInterface.java -d bin
	javac -classpath bin/ src/Init.java -d bin/