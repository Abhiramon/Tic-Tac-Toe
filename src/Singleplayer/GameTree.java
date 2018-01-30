package Singleplayer;
import java.util.ArrayList;
import java.util.List;
import GUI.Game;


//import A.TicTacToeClient;

public class GameTree{
public static List<Node> nodeList = new ArrayList<>();    //first element of list is root
private Node nodeSelected;
private int playerSymbol;
private int compSymbol;
private int board;
private boolean playingFirst;
private boolean createdTree;
private static Game gui=new Game(new int[9]);


public GameTree(){
	gui.getClient().setOppID("Computer");
	setPlayerSymbol(1);
	setPlayingFirst(true);
	board=0;
	createdTree=false;
}

public void setPlayingFirst(boolean b){
	playingFirst=b;
	}

public void setPlayerSymbol(int sym){
	playerSymbol=sym;
	if (playerSymbol==1)
			compSymbol=2;
	if (playerSymbol==2)
			compSymbol=1;
	}

private class Node{
	public int number;
	public int score;
	public ArrayList<Integer> childList; 

    private Node() {
        this.childList = new ArrayList<Integer>();
    }
		
}

private void updateBoard(int number){     //GUI send String array
	board=number;
	String s=Integer.toString(number);
	int l=s.length();
    int i;
    for (i=0;i<(9-l);i++)
        {s="0"+s;
        
        }
    for (i=0;i<9;i++)
    	{
    	gui.getClient().setArray(i,s.charAt(i)-'0');
    	}
    
	}    

private void printBoard(){     //calls display of GUI
 	gui.getClient().display();
    }    


public void makeCompMove(){
	int i;
	int max=0;
	if (nodeSelected.childList.size()>0)
		max=nodeSelected.childList.get(0);
	for(i=1;i<nodeSelected.childList.size();i++)
		{
		if (nodeList.get(nodeSelected.childList.get(i)).score>nodeList.get(max).score)
			{
			max=nodeSelected.childList.get(i);
			}
		}
	
	updateBoard(nodeList.get(max).number);
	gui.getClient().setPlayerTurn(true);
	printBoard();
	nodeSelected=nodeList.get(max);
	}

private int getPlayerMove(){
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
    }   //gets position the player plays at from GUI 

private int elementAt(int index,int num){ //gives an integer indicating the symbol at index
	String s=Integer.toString(num);
            int l=s.length();
            int i;
            for (i=0;i<(9-l);i++)
                {s="0"+s;
                
                }
	return s.charAt(index)-'0';
	}

private int putElement(int element,int pos,int num){
	num-=Math.pow(10, 8-pos)*elementAt(pos,num);
	num+=element*Math.pow(10, 8-pos);
            return num;
	}


public void makePlayerMove(){
	int pos=getPlayerMove();
	
	while(elementAt(pos,board)!=0){
			gui.printDialog("The box is already occupied");//  GUI display "The tile is already filled"
			pos=getPlayerMove();
			}
	//System.out.println("Got move as "+pos);
	board=putElement(playerSymbol,pos,board);
	updateBoard(board);

	int i=0;
	for (i=0;i<nodeSelected.childList.size();i++)
	{
		if (nodeList.get(nodeSelected.childList.get(i)).number==board)
                        { nodeSelected=nodeList.get(nodeSelected.childList.get(i));
                        
                        }
		}
	gui.getClient().setPlayerTurn(false);       
	printBoard();
    
    }

private int startGame(){
	nodeSelected=nodeList.get(nodeList.size()-1);
	int i=0;
	if (playingFirst==true){
		makePlayerMove();
		for (i=0;i<4;i++)
			{
			
			makeCompMove();
			if (checkWin(board)==1) break;
			
			makePlayerMove();
			if (checkWin(board)==-1) break;
			}
		checkDraw(board);
		}
	else if (playingFirst==false){
		makeCompMove();
		for (i=0;i<4;i++)
			{
			
			makePlayerMove();
			
			if (checkWin(board)==-1) break;
			makeCompMove();
			if (checkWin(board)==1) break;
			}
		checkDraw(board);
		
		}
	return 1;
	}

    private boolean CheckAllSame(int bo,int val,int pos1,int pos2,int pos3){
        if (elementAt(pos1,bo)==val && elementAt(pos2,bo)==val && elementAt(pos3,bo)==val)
              return true;
        return false;
    }

    private boolean check_for_val_win(int val,int bo){
        if (CheckAllSame(bo,val,0,1,2) || CheckAllSame(bo,val,0,3,6) || CheckAllSame(bo,val,3,4,5) || CheckAllSame(bo,val,1,4,7) || CheckAllSame(bo,val,6,7,8) || CheckAllSame(bo,val,2,5,8) || CheckAllSame(bo,val,0,4,8) || CheckAllSame(bo,val,2,4,6))
            return true;
    return false;
    }
    private int checkWin(int bo){
        if(check_for_val_win(compSymbol,bo)){
            if (createdTree)
            	{gui.printDialog("Computer wins");  //GUI
            	}
            return 1;
        }
        else if(check_for_val_win(playerSymbol,bo)){
            if (createdTree){
            	gui.printDialog("You win");	      //GUI
            }
        	return -1;
        }
        return 0;
    }
    private boolean CheckFull(int bo){
        for(int i=0;i<9;i++){
            if(elementAt(i,bo)==0){
                return false;
            }
        }
        return true;
    }
    private boolean checkDraw(int bo){
        if(CheckFull(bo)){
        	if (createdTree)
        		{gui.printDialog("The game is a draw");
        		}
            if(checkWin(bo)==0){
                return true;
                }
            else{
                return false;
            }
        }
        return false;    
    } 
    public void createTree(){
        int p;
       
        if(playingFirst){
            p = -1;
        }
        else{
            p=1;
        }
        nodeList.add(createNode(000000000,p,0));
        createdTree=true;
    }

public Node createNode(int num,int t,int c){

        Node n = new Node();
        n.number = num;
        n.score = 0;
        if(checkWin(num)==1){ 
            n.score = 1;
            return n;
        }
        else if(checkWin(num)==-1){
            n.score = -1;
            return n;
        }
        if(checkDraw(num)){
            n.score = 0;
            return n;
        }
        int max = -1;
        int min = 1;
        int tb;
        Node tn = new Node();
        for(int i=0;i<9;i++){
            if(elementAt(i,num)==0){
                tb = num;
                if(t==1){
                    tb = putElement(compSymbol,i,num);
                }
                else{
                    tb = putElement(playerSymbol,i,num);
                }
                tn = createNode(tb,(t*(-1)),c+1);
                nodeList.add(tn);
                n.childList.add(nodeList.size()-1);
                if(t==1){
                    if(tn.score>max){
                        max = tn.score;
                    }
                }
                else{
                    if(tn.score<min){
                        min = tn.score;
                    }
                }                    
            }
        }
        if(t==1){
            n.score = max;
        }
        else{
            n.score = min;
        }
        
        return n;
    }

public static void main(String args[]){
    GameTree g = new GameTree();
    g.setPlayingFirst(true);
    //System.out.println(g.putElement(1, 0, 12022100));
   

    gui.buildFrame(1);
    g.createTree();
    g.startGame();
	}
}
