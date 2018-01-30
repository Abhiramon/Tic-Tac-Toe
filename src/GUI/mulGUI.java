package GUI;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import GUI.mulGUI.Square;
import GUI.mulGUI.resetAction;

import java.io.*;
import java.util.*;
import java.net.*;

public class mulGUI {

    protected JFrame frame = new JFrame("Tic Tac Toe");
    protected JFrame userframe = new JFrame("UserDetails");
    
    
    protected JLabel messageLabel = new JLabel("");
    protected ImageIcon icon;
    protected ImageIcon opponentIcon;

    protected Square[] board = new Square[9];
    protected int[] array = new int[9];
    protected String userId = "";
    protected String oppId = "";
    protected Square currentSquare;
    protected int hostingChoice =-1;
    protected int hostChoice;
    protected String hostIP ="";

    protected int lastClicked=-1;
    protected boolean gameReset;
    protected boolean PlayerTurn;
    protected boolean initializationDone;


    /**
     * Constructs the client by connecting to a server, laying out the
     * GUI and registering GUI listeners.
     */
    public mulGUI(int[] temp)
    {
    	array=temp;
    	gameReset = false;
        PlayerTurn = true;
        initializationDone=false;
    
    }

    public void buildGUI2()
    {   //USER INPUT FRAME
    	
    	frame.getContentPane().removeAll();
    	userframe.getContentPane().removeAll();

        JButton nextButton = new JButton("Next >>");
       
        final JTextField txtInput = new JTextField(30); 
        final JTextField txtInput2 = new JTextField(30); 
        
                    
        
        userframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userframe.setSize(480, 480);
        userframe.setLocationRelativeTo(null);
        userframe.setLayout(new GridBagLayout());
        
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);

        gc.gridx = 0;
        gc.gridy = 0;
        userframe.add(new JLabel("Name:"), gc);

        gc.gridx = 0;
        gc.gridy = 1;
        userframe.add(txtInput, gc);  

        gc.gridx = 0;
        gc.gridy = 5;
        userframe.add(nextButton, gc);




        userframe.pack();
        userframe.revalidate();
        userframe.repaint();
        //userframe.setLocationRelativeTo(null);
        userframe.setVisible(true);


        
        

        nextButton.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent e)
            {
                userId = (txtInput.getText()); 
                hostChoice=1;
                //System.out.println("from nextclick -> "+hostingChoice+" "+userId);
                userframe.dispose();
                frame.revalidate();
                frame.repaint();
                frame.pack();
                display();
                frame.setVisible(true);
            }
        });

        
        
        

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, "South");

        
        // TIC TAC TOE FRAME
        
        final AudioClip click;
        final URL urlClick = Game.class.getResource("../resources/click.wav");
        click = Applet.newAudioClip(urlClick);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 525);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        

        for (int i = 0; i < array.length; i++)
            board[i] = new Square();

        


        for (int i = 0; i < board.length; i++)
        {

            final int j = i;
            board[i].addMouseListener(new MouseAdapter()
            {   public void mousePressed(MouseEvent e)
                {
                    currentSquare = board[j];
                    if (PlayerTurn==true){
                    int playerSymbol = (hostChoice ==1) ? 1 : 2;
                    if(array[j]==playerSymbol || array[j]==0)
                        array[j]=playerSymbol;
                    //System.out.print(j);
                    ImageIcon icon = (playerSymbol==1)?new ImageIcon("resources/X.png"):new ImageIcon("O.png");
                    if(array[j]==playerSymbol)
                    {
                        currentSquare.setIcon(icon);
                        click.play();
                        lastClicked = j;
                       

                    }

                  

                    display();
                    }
                }

            });
            //boardPanel.add(board[i]);
        }
       
    }

    public void buildGUI()
    {
        
        
        

        ItemListener joinListener = new ItemListener() 
        {       
            
                public void itemStateChanged(ItemEvent itemEvent) 
                {

                
                JButton nextButton2 = new JButton("Next >>");
                final JFrame ipframe = new JFrame("IP Configuration");
                final JTextField txtInput2 = new JTextField(30); 
                final ButtonGroup group2 = new ButtonGroup();
                ipframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ipframe.setSize(480, 480);
                ipframe.setLocationRelativeTo(null);
                ipframe.setLayout(new GridBagLayout());
                
                AbstractButton aButton = (AbstractButton)itemEvent.getSource();
                int state = itemEvent.getStateChange();
                String label = aButton.getText();
                if (state == ItemEvent.SELECTED) 
                {
                    GridBagConstraints gc2 = new GridBagConstraints();
                    gc2.fill = GridBagConstraints.HORIZONTAL;
                    gc2.insets = new Insets(10, 10, 10, 10);
                    gc2.gridx = 0;
                    gc2.gridy = 0;
                    ipframe.add(new JLabel("Please enter the Host's IP:"), gc2);

                    gc2.gridx = 0;
                    gc2.gridy = 1;
                    ipframe.add(txtInput2, gc2);

                    gc2.gridx = 0;
                    gc2.gridy = 3;
                    ipframe.add(nextButton2, gc2);

                    ipframe.setVisible(true);

                    nextButton2.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {   
                            
                                hostIP = (txtInput2.getText());
                                ipframe.dispose();
                                
                        }
                    });
                    
                  
                }
            }
        };

        ItemListener hostListener = new ItemListener() 
        {   




            public void itemStateChanged(ItemEvent itemEvent) 
            {
                String hostIP = "";
                JLabel IPString = new JLabel();
                try
                {
                    
                    hostIP = "Host IP Address "+InetAddress.getLocalHost().getHostAddress();
                    IPString.setText(hostIP);
                }
                catch(UnknownHostException e){e.printStackTrace();}

                JButton nextButton2 = new JButton("Next >>");
                final JFrame ipframe = new JFrame("IP Configuration");
                final JTextField txtInput2 = new JTextField(30); 
                final ButtonGroup group2 = new ButtonGroup();
                ipframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ipframe.setSize(480, 480);
                ipframe.setLocationRelativeTo(null);
                ipframe.setLayout(new GridBagLayout());
                
                AbstractButton aButton = (AbstractButton)itemEvent.getSource();
                int state = itemEvent.getStateChange();
                String label = aButton.getText();
                if (state == ItemEvent.SELECTED) 	
                {   
                	

                    
                    GridBagConstraints gc2 = new GridBagConstraints();
                    gc2.fill = GridBagConstraints.HORIZONTAL;
                    gc2.insets = new Insets(10, 10, 10, 10);
                    gc2.gridx = 0;

                    gc2.gridy =0;
                    ipframe.add(IPString, gc2);

                    

                   
                    gc2.gridx = 0;
                    gc2.gridy = 3;
                    ipframe.add(nextButton2, gc2);

                    ipframe.setVisible(true);

                    nextButton2.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {   
                            
                               
                				hostChoice = 1;
                                ipframe.dispose();
                                
                        }
                    });
                    
                }
                }    
            
            
        };


        //USER INPUT FRAME

        JButton nextButton = new JButton("Next >>");
       
        final JTextField txtInput = new JTextField(30);
        JRadioButton option1 = new JRadioButton("Host");
        JRadioButton option2 = new JRadioButton("Join");
        option1.setActionCommand("Host");
        option2.setActionCommand("Join");
        option1.addItemListener(hostListener);
        option2.addItemListener(joinListener);
        final ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);



        
        
        userframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userframe.setSize(480, 480);
        userframe.setLocationRelativeTo(null);
        userframe.setLayout(new GridBagLayout());
        
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);

        gc.gridx = 0;
        gc.gridy = 0;
        userframe.add(new JLabel("Name:"), gc);

        gc.gridx = 0;
        gc.gridy = 1;
        userframe.add(txtInput, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        userframe.add(new JLabel("Would you like to Host or Join the game? "), gc);

        gc.gridx = 0;
        gc.gridy = 3;
        userframe.add(option1, gc);


        gc.gridx = 1;
        gc.gridy = 3;
        userframe.add(option2, gc);

        gc.gridx = 0;
        gc.gridy = 4;
        userframe.add(nextButton, gc);




        userframe.pack();
        userframe.revalidate();
        userframe.repaint();
        //userframe.setLocationRelativeTo(null);
        userframe.setVisible(true);


        
        

        nextButton.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent e)
            {
                userId = (txtInput.getText()); 
                String entry = group.getSelection().getActionCommand();
                hostingChoice = (entry=="Host") ? 1 : 0;
                //System.out.println("from nextclick -> "+hostingChoice+" "+userId);
                initializationDone=true;
                userframe.dispose();
                frame.revalidate();
                frame.repaint();
                frame.pack();
                display();
                frame.setVisible(true);
            }
        });

        
        
        

        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, "South");

        
        // TIC TAC TOE FRAME
        
        final AudioClip click;
        final URL urlClick = Game.class.getResource("../resources/click.wav");
        click = Applet.newAudioClip(urlClick);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 525);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        

        for (int i = 0; i < array.length; i++)
            board[i] = new Square();

        


        
        
        for (int i = 0; i < board.length; i++)
        {

            final int j = i;
            board[i].addMouseListener(new MouseAdapter()
            {   public void mousePressed(MouseEvent e)
                {
            	currentSquare = board[j];
                int playerSymbol = (hostChoice ==1) ? 1 : 2;
                if(array[j]==playerSymbol || array[j]==0)
                    array[j]=playerSymbol;
                //System.out.print(j);
                ImageIcon icon = (playerSymbol==1)?new ImageIcon("resources/X.png"):new ImageIcon("src/GUI/O.png");
                if(array[j]==playerSymbol)
                {
                    currentSquare.setIcon(icon);
                    click.play();
                    lastClicked = j;
                   

                }


                    display();  
                }

            });
            //boardPanel.add(board[i]);
        }
       
    }

    public  void display()
    {
        frame.getContentPane().removeAll();
        
       
        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);
        boardPanel.setPreferredSize(new Dimension(480,480));
        boardPanel.setLayout(new GridLayout(3, 3, 2, 2));
        for (int i = 0; i < array.length; i++)
        {
           
            if(array[i]==2)
            {
            ImageIcon icon = new ImageIcon("resources/O.png");
            board[i].setIcon(icon);
            }

            else if(array[i]==1)
            {
            ImageIcon icon = new ImageIcon("resources/X.png");
            board[i].setIcon(icon);
            }

            else if(array[i]==0)
            {
            ImageIcon icon = new ImageIcon("resources/null.png");
            board[i].setIcon(icon);
            }


        }


    

      	for (int i = 0;i< board.length;i++)
            	boardPanel.add(board[i]);

        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(Color.white);
        infoPanel.setPreferredSize(new Dimension(300,480));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);

        
       
       
        JLabel vsID = new JLabel();
        vsID.setText(getUserID()+" V/S "+getOppID());
        JLabel playerTurn = new JLabel();
        vsID.setFont(new Font("Serif", Font.PLAIN, 20));
        String turn = (PlayerTurn)?getUserID():getOppID();
        playerTurn.setText("TURN : "+turn);
        playerTurn.setFont(new Font("Serif", Font.PLAIN, 20));
        
        

        gc.gridx = 0;
       
        gc.gridy = 3;
        infoPanel.add(vsID,gc);
        gc.gridy = 4;
        infoPanel.add(playerTurn,gc);
       
        
        frame.add(boardPanel);
        frame.add(infoPanel);
        frame.revalidate();
        frame.repaint();
        frame.pack();
        //frame.add(new JLabel("Center"));

        
        


        

    }

    static class Square extends JPanel 
    {
        JLabel label = new JLabel((Icon)null);

        private int occupancy;

        public Square() {
            setBackground(Color.white);
            setSize(160,160);
            add(label);
        }

        public void setIcon(Icon icon) {
            label.setIcon(icon);
            occupancy=1;
        }

        public int getOccupancy(){return occupancy;}
    }    

    public int[] getArray(){return array;}
    public JFrame getFrame(){return frame;}
    public String getUserID(){return userId;}
    public String getOppID(){return oppId;}
    public int getHostingChoice(){return hostingChoice;}
    public int getPlayingFirst(){return hostChoice;}
    public String getIP(){return hostIP;}
    public boolean getPlayerTurn(){return PlayerTurn;}


    
    public void setArray(int pos,int symbol)
    {
      array[pos]=symbol;
      //display();
      
      
    }

    public int getMove()
    {
    	return lastClicked;
    }
    
    public boolean getReset()
    {
    	return gameReset;
    }
    
    public boolean getInit()
    {
    	return initializationDone;
    }
    
    public void setReset(boolean value)
    {
    	gameReset=value;
    }
    
    public void setPlayerTurn(boolean value)
    {
    	PlayerTurn=value;
    }

    public void setOppID(String val)
    {
    	oppId = val;
    }
    

    class resetAction implements ActionListener 
    {        
        public void actionPerformed (ActionEvent e) 
        {   frame.dispose();
        	array = new int[9];
        	gameReset=true;
        	buildGUI2();
            display();
        }
    }   

}

