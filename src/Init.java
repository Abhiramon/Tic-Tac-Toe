import Multiplayer.GameNetworkingInterface;
import Singleplayer.GameTree;
import GUI.Game;


public class Init{

	  public static void main(String[] args) {
		
	    GameNetworkingInterface network=new GameNetworkingInterface();
	    GameTree g = new GameTree();
	    g.setPlayingFirst(true);
	   Game gui=new Game(new int[9]);
	   gui.chooseMode();
	    int mode=gui.getOption();
	    while(mode==0)
	    	{mode=gui.getOption();
	    	try {
	 	       Thread.sleep(20);
	 	    } catch(InterruptedException e) {
	 	    	}
	    	
	    	}
	    if (mode==1){
	    	g.main(args);
	    	}
	    
	if (mode==2){
		network.main(args);
		}
	  }

	}
