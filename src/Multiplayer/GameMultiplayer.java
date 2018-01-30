package Multiplayer;
import java.io.*;
import GUI.Game;

class GameMultiplayer{
    private int[] array;
    private int playerSymbol;
    private int opponentSymbol;
	private Game gui=new Game(new int[9]);

	
	public Game getGui(){
		return gui;
		}
    public GameMultiplayer(){
      array=new int[9];
  
      }

    private int getPlayerMove(){    //GUI use getMove()
    	int m1=gui.getClient().getMove();
        int m2=m1;
        while(m2==m1){
        	m2=gui.getClient().getMove();
        	 try {
        	       Thread.sleep(20);
        	    } catch(InterruptedException e) {
        	    	}
        	    }
        return m2;
      }

    private int getOpponentMove(DataInputStream in){
      int move=0;
      try{
      move= Integer.parseInt(in.readUTF());
      }catch(IOException e) {
           e.printStackTrace();
          }
        return move;
      }

    private void sendPlayerMove(DataOutputStream out,int position){
      try{
      out.writeUTF(Integer.toString(position));
        }catch(IOException e) {
             e.printStackTrace();
          }
      }


    private void getAndSetOpponentSymbol(DataInputStream in){
      try{
        setOppSymbol(Integer.parseInt(in.readUTF()));
          }catch(IOException e) {
               e.printStackTrace();
            }
        }

    private void sendPlayerSymbol(DataOutputStream out){
      try{
        out.writeUTF(Integer.toString(playerSymbol));
        }catch(IOException e) {
             e.printStackTrace();
          }

      }

    private void setOppSymbol(int sym){
      opponentSymbol=sym;
      if (opponentSymbol==1)
          playerSymbol=2;
      else if (opponentSymbol==2)
          playerSymbol=1;
      }

    private boolean checkAllSame(int val,int pos1,int pos2,int pos3){
        if (array[pos1]==val && array[pos2]==val && array[pos3]==val)
              return true;
        return false;
        }

    private boolean checkForThreeInRow(int val){
      if (checkAllSame(val,0,1,2) || checkAllSame(val,0,3,6) || checkAllSame(val,3,4,5) || checkAllSame(val,1,4,7) || checkAllSame(val,6,7,8) || checkAllSame(val,2,5,8) || checkAllSame(val,0,4,8) || checkAllSame(val,2,4,6))
          return true;
      return false;
      }
    private boolean checkVictory(){    //GUI dialog box
      if (checkForThreeInRow(playerSymbol))
          {gui.printDialog("You win");
          return true;
          }
      else if (checkForThreeInRow(opponentSymbol))
          {gui.printDialog("You lose");
          return true;
          }
      return false;
      }

    private boolean checkFull(){    
        for(int i=0;i<9;i++){
            if(array[i]==0)
                return false;
              }
        return true;
        }

    public int runGame(int playingFirst,DataInputStream in,DataOutputStream out)	{
      int move;
      boolean madeMove;
      printBoard();      
      if (playingFirst==1)
          {setOppSymbol(2);   /////////setting player symbol as 1,opponentSymbol as 2
          sendPlayerSymbol(out);
          System.out.println("Your turn");                                    //GUI display on top
          move=getPlayerMove();
          madeMove=makeMove(playerSymbol,move);
	        while(!madeMove)
		        { move=getPlayerMove();
              madeMove=makeMove(playerSymbol,move);
		          }
          sendPlayerMove(out,move);
          gui.getClient().setPlayerTurn(false);
          printBoard();
          if (checkVictory()) return 1;
          while (!checkFull())
              {System.out.println("Opponent's turn");   //....
              madeMove=makeMove(opponentSymbol,getOpponentMove(in));
              gui.getClient().setPlayerTurn(true);
              printBoard();
              if (checkVictory()) return 1;

              System.out.println("Your turn");        //....
              move=getPlayerMove();
              madeMove=makeMove(playerSymbol,move);
    	        while(!madeMove)
    		        { move=getPlayerMove();
                  madeMove=makeMove(playerSymbol,move);
    		          }
              sendPlayerMove(out,move);
              gui.getClient().setPlayerTurn(false);
              printBoard();
              if (checkVictory()) return 1;
              }
          if (checkFull())  gui.printDialog("The game is a draw");
          return 1;
          }
      else if (playingFirst==0){
          getAndSetOpponentSymbol(in);
          System.out.println("Opponent's turn");          //gui   
          madeMove=makeMove(opponentSymbol,getOpponentMove(in));
          gui.getClient().setPlayerTurn(true);
          printBoard();
          if (checkVictory()) return 1;
          while (!checkFull()){

          System.out.println("Your turn");            //gui...
            move=getPlayerMove();
            madeMove=makeMove(playerSymbol,move);
  	        while(!madeMove)
  		        { move=getPlayerMove();
                madeMove=makeMove(playerSymbol,move);
  		          }
            sendPlayerMove(out,move);
            gui.getClient().setPlayerTurn(false);
            printBoard();
            if (checkVictory()) return 1;

            System.out.println("Opponent's turn");    //gui...
            madeMove=makeMove(opponentSymbol,getOpponentMove(in));
            gui.getClient().setPlayerTurn(true);
            printBoard();
            if (checkVictory()) return 1;
                }
          if (checkFull()) gui.printDialog("The game is a draw");   
          return 1;
          }
        return 0;
      }


    public boolean makeMove(int symbol,int position){    
      if (array[position]==0)
        {array[position]=symbol;
        gui.getClient().setArray(position, symbol);
        return true;
        }
      else {  gui.printDialog("The position is occupied");                  
		return false;
		}
      }

    public void printBoard(){   //GUI display
        gui.getClient().display();
      }

}
