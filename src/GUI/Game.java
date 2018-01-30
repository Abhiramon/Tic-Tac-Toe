package GUI;



/*import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;




/*
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;*/


public class Game
{
    private JFrame menuframe = new JFrame("Tic Tac Toe");
    private int gameOption;
   // private int[] array = new int[9];
    public mulGUI client;
    


    public Game(int[] temp)
	    {
	        //array=temp;
	        client = new mulGUI(temp);

    	}
  
    public void chooseMode()
    {
        
        menuframe.setSize(480, 480);
        menuframe.setLocationRelativeTo(null);
        menuframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        ImageIcon sPlayer = new ImageIcon("resources/single.png");
        ImageIcon mPlayer = new ImageIcon("resources/multi.gif");


    
        JButton button = new JButton("Single Player",sPlayer);
        button.setPreferredSize(new Dimension(100,50));
        button.addActionListener (new Action2());
        JButton button2 = new JButton("Multi Player ",mPlayer);
        button2.setPreferredSize(new Dimension(100,50));
        button2.addActionListener (new Action1());
        button.setVerticalTextPosition(SwingConstants.TOP);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button2.setVerticalTextPosition(SwingConstants.TOP);
        button2.setHorizontalTextPosition(SwingConstants.CENTER);


        panel.setLayout(new GridLayout());
        panel.add(button);
        panel.add(button2);
        

        menuframe.add(panel);
        //menuframe.pack();
        menuframe.revalidate();
        menuframe.repaint();
        menuframe.setVisible(true);

    }  

    class Action1 implements ActionListener 
    {        
        public void actionPerformed (ActionEvent e) 
        {     
                        
            gameOption = 2;
            menuframe.dispose(); 
           
        }
    }   

    class Action2 implements ActionListener 
    {        
        public void actionPerformed (ActionEvent e) 
        {     
            
            gameOption =1;
            menuframe.dispose(); 
            
        }
    }
    
    public void buildFrame(int go) 
    { 	gameOption=go;
    	if(gameOption==1)
    	{
    		client.buildGUI2();
    		client.display();
    	}
    	else if(gameOption == 2)
    	{
    		client.buildGUI();
    		client.display();
    	}
    }

    public mulGUI getClient(){return client;}
    public int getOption(){return gameOption;}
    
    public void printDialog(String msg)
    {
      JOptionPane.showMessageDialog(new Frame(), msg);
    }
}



