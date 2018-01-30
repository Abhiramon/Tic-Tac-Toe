package Multiplayer;

import java.io.*;
import java.net.*;


public class GameNetworkingInterface{
	  private int port=12345;
	  private int hostingChoice;
	  private String hostIp;
	  private static GameMultiplayer game=new GameMultiplayer();
	  
	  
	  public GameNetworkingInterface()
	  {
	      
	  }

	  public void chooseHosting()
	  {
	    hostingChoice=game.getGui().getClient().getHostingChoice();
	   	hostIp = game.getGui().getClient().getIP();

	  }

	    public void startGame(){
	        if (hostingChoice==1)
	            {
	            try {
	                hostGame();
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	            }
	        else if (hostingChoice==0)
	            {
	              connectGame(hostIp);
	            }

	        }

	    private void hostGame() throws IOException{
	    	
	        ServerSocket sersoc = new ServerSocket(port);
	        sersoc.setSoTimeout(100000);
	        while(true){
	            try {
	                //System.out.println(sersoc.getLocalPort());    
	                Socket server = sersoc.accept();
	                game.getGui().printDialog("Connected to Opponent");
	                //System.out.println(server.getRemoteSocketAddress());
	                DataInputStream in = new DataInputStream(server.getInputStream());

	           //GUI take input
	                String userName=game.getGui().getClient().getUserID();
					game.getGui().getClient().setOppID(in.readUTF());
	                DataOutputStream out = new DataOutputStream(server.getOutputStream());
	                out.writeUTF(userName);
	                
	                int resp = game.getGui().getClient().getPlayingFirst();
	                out.writeUTF(Integer.toString(resp));
	                
	                game.runGame(resp,in,out);
	                sersoc.close();
	                server.close();
	            }catch(SocketTimeoutException s) {
	                System.out.println("Time out");  //GUI display
	                break;
	            }catch(IOException e){
	                e.printStackTrace();
	                break;
	            }
	        }
	    }


	    private void connectGame(String ip){
	        try {
	           

	           Socket client = new Socket(ip, port);
	           game.getGui().printDialog("Connected to " + client.getRemoteSocketAddress());
	           OutputStream outToServer = client.getOutputStream();
	           DataOutputStream out = new DataOutputStream(outToServer);

	           InputStream inFromServer = client.getInputStream();
	           DataInputStream in = new DataInputStream(inFromServer);
	           //System.out.println("Enter user name");
	           String userName=game.getGui().getClient().getUserID();
	  //Sending username to server
	            out.writeUTF(userName);
	  //Recieving username from server
	            String opponentName=in.readUTF();
	            game.getGui().getClient().setOppID(opponentName);
	            //System.out.println("Game: "+userName+" vs "+opponentName); GUI PART
	            
	            int playingFirstOpp=Integer.parseInt(in.readUTF());
	            int playingFirst=0;
	            if (playingFirstOpp==1)
	                playingFirst=0;
	            else if (playingFirstOpp==0)
	                playingFirst=1;
	            
	            game.runGame(playingFirst,in,out);
	              client.close();
	          }catch(IOException e) {
	               e.printStackTrace();
	            }

	        }
	    public static void main(String[] args) {
			
		    GameNetworkingInterface network=new GameNetworkingInterface();
		    game.getGui().buildFrame(2);
		    //System.out.println("Done");
		    boolean ready=game.getGui().getClient().getInit();
		    while(ready==false){
		    	ready=game.getGui().getClient().getInit();
		    	try {
	        	       Thread.sleep(20);
	        	    } catch(InterruptedException e) {
	        	    	}
		    	}
		    network.chooseHosting();
		    //System.out.println("done 2");
		    network.startGame();
		
		  }

	  }