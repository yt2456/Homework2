/*
 Program To find a breadth-first path in a maze with obstacles
 Uses a queue to expand each cell 1 unit distance
 each cell has 4 neighbors: North, East, South, West.
 Input is 8x8 ascii matrix file, with 0 for empty cell, 1 for obstacle,
 S for Start, G for Goal, and spaces between entries.
 Example Maze File:

0 0 0 0 0 0 0 0  
0 1 1 0 0 0 0 0  
0 1 1 0 1 G 0 0  
0 0 0 0 1 1 1 0  
0 0 0 0 0 1 0 0  
0 S 0 0 0 0 0 0  
0 0 0 0 0 1 0 0 
0 0 0 0 0 0 0 0  

program has 1 argument: Maze File Name

    java maze_file_name
*/

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.*;

public class MazeShell implements ActionListener{

// Fields
  private JFrame frame;
  private DrawingCanvas canvas;
  private JTextArea messageArea;
  private Color color;
  private final int CELL_SIZE=50;
  private int startingX;
  private int startingY;
  private int endingX;
  private int endingY;
  private int totalDistance;
  private String filename;
  private BufferedReader diskInput;
  private boolean pathfound=false;
  private int[][] mazeGrid = new int[8][8];
  private int[][] distances = new int[8][8];
  private int[][] visited = new int[8][8];
  private boolean atGoal = false;
  final int WIDTH=8;
  final int HEIGHT=WIDTH;

  public MazeShell(String file)
  {
    filename=file;
    frame = new JFrame("Maze");
    frame.setSize(500, 500);

    //The graphics area
    canvas = new DrawingCanvas();
    frame.getContentPane().add(canvas, BorderLayout.CENTER);

    //The message area, mainly for debugging.
    messageArea = new JTextArea(1, 80);     //one line text area for messages.
    frame.getContentPane().add(messageArea, BorderLayout.SOUTH);

    JPanel buttonPanel = new JPanel(new java.awt.GridLayout(2,0));
    frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

    addButton(buttonPanel, "Draw Initial Grid").setForeground(Color.black);
    addButton(buttonPanel, "Calculate Distances").setForeground(Color.black);
    addButton(buttonPanel, "Show Path").setForeground(Color.black);
    addButton(buttonPanel, "Quit").setForeground(Color.black);

    frame.setVisible(true);
  }

  /** Helper method for adding buttons */
  private JButton addButton(JPanel panel, String name){
    JButton button = new JButton(name);
    button.addActionListener(this);
    panel.add(button);
    return button;
  }

  /** Respond to button presses */

  public void actionPerformed(ActionEvent e){
    String cmd = e.getActionCommand();
    if (cmd.equals("Draw Initial Grid") ){
       initialize();
       return;
    }  else 
    if (cmd.equals("Calculate Distances") ){
       pathfound = calcDistances();
       return;
    }  else 
    if(cmd.equals("Show Path")) {
        if (pathfound) 
        	outputPath();
        else 
        	messageArea.insert("no Path found!",0);
        return;
    } else
    if (cmd.equals("Quit") ){
       frame.dispose();
       return;
    } else
	throw new RuntimeException("No such button: "+cmd);
  }
 
    public void initialize(){

       canvas.clear();
       drawGrid();

       //now read from the diskInput...
       try{
          diskInput = new BufferedReader(new InputStreamReader(
	          new FileInputStream(
	          new File(filename))));

          int temp = 0;
          
          for(int y = 0; y < 8; y++)
          {	 
       	   		for(int x = 0; x < 8; x++)
       	   		{
       	   			temp = diskInput.read();
       	   			diskInput.skip(1);
       	   	
       	   			if(temp == 48)
       	   			{
       	   				mazeGrid[x][y] = 0;
       	   			}
       	   			if(temp == 49)
       	   			{
       	   				mazeGrid[x][y] = 1;
       	   			}  
       	   			if(temp == 83)
       	   			{
       	   				mazeGrid[x][y] = 2;
       	   			}
       	   			if(temp == 84)
       	   			{
       	   				mazeGrid[x][y] = 3;
       	   			}  
                    if( temp == 10 || temp == 32)
                    {
                    	x--;
                    }
       	   		 }
       	   		    	   	
          }
         
          messageArea.insert("Maze File Name: " + filename,0);
       }
       catch (IOException e){
            System.out.println("io exception!");
       }
       
       
//some sample draw commands...

       for(int x = 0; x < 8; x++)
       {
    	   	for(int y = 0; y < 8; y++)
    	   	{
    	   
    	   	   if(mazeGrid[x][y] == 1)
    	   	   {
 	   		       fillRectangle(x, y, CELL_SIZE,CELL_SIZE,Color.blue);
    	   	   }  
    	       if(mazeGrid[x][y] == 2)
 	   	   	   {
    	    	   drawText("S",x,y,Color.black);
    	    	   startingX = x;
    	    	   startingY = y;
 	   	       }
 	   	   	   if(mazeGrid[x][y] == 3)
	   	       {
 	   	   		   drawText("G",x,y,Color.black);
 	   	   		   endingX = x;
 	   	   		   endingY = y;
	   	       }  
    	   	}
       }
      
       // read in maze file, display "S" for start, "G" for GOAL
       // and fill rectangles for obstacles
       // commands above show how to write text and colored rectangles
             
       canvas.display();
    }

    public void drawText(String s,int i, int j,Color color) {
       Color col=color;
       canvas.setForeground(col);
       canvas.drawString(s,(i)*CELL_SIZE+10,(j+1)*CELL_SIZE-10,false);
    }

    public void fillRectangle(int i,int j,int height,int width,Color color){

       Color col=color;
       canvas.setForeground(col);
       canvas.fillRect(i*CELL_SIZE,j*CELL_SIZE,height,width,false);
    }


    public void drawGrid(){

      int k=0;
      int m=0;
      color=Color.black;
      canvas.setForeground(color);
    //draw horizontal grid lines
      for(int i=0;i<9;i++) {
        canvas.drawLine(0,i*CELL_SIZE,WIDTH*CELL_SIZE,i*CELL_SIZE,false);
    }
    //draw vertical grid lines
      for(int i=0;i<9;i++) {
        canvas.drawLine(i*CELL_SIZE,0,i*CELL_SIZE,HEIGHT*CELL_SIZE,false);
    }
  }


    public boolean calcDistances() 
    {
    	int currentDistance = 0;
    	easyQueue currentLoc = new easyQueue();
    	easyQueue prevLoc = new easyQueue();
    	Location cLoc;
    	
    	currentLoc.enQueue(new Location(startingX, startingY));
    	distances[startingX][startingY] = -1;
    	
    	while( atGoal == false && currentLoc.isEmpty() == false)
    	{
    		currentDistance += 1;
    		
    		while(currentLoc.isEmpty() == false)
    		{
    			prevLoc.enQueue(currentLoc.deQueue());
    		}
    		
    		while(prevLoc.isEmpty() == false)
    		{
    			cLoc = (Location) prevLoc.deQueue();
    			if(isValid(cLoc.getX() - 1,cLoc.getY()) == true)
    			{
    				distances[cLoc.getX() - 1][cLoc.getY()] = currentDistance;
    				currentLoc.enQueue(new Location(cLoc.getX() - 1,cLoc.getY()));
    				drawText(Integer.toString(currentDistance),cLoc.getX() - 1,cLoc.getY(),Color.black);
    			}
    			if(isValid(cLoc.getX(),cLoc.getY() - 1) == true)
    			{
    				distances[cLoc.getX()][cLoc.getY() - 1] = currentDistance;
    				currentLoc.enQueue(new Location(cLoc.getX(),cLoc.getY() - 1));
    				drawText(Integer.toString(currentDistance),cLoc.getX(),cLoc.getY() - 1,Color.black);
    			}
    			if(isValid(cLoc.getX(),cLoc.getY() + 1) == true)
    			{
    				distances[cLoc.getX()][cLoc.getY() + 1] = currentDistance;
    				currentLoc.enQueue(new Location(cLoc.getX(),cLoc.getY() + 1));
    				drawText(Integer.toString(currentDistance),cLoc.getX(),cLoc.getY() + 1,Color.black);
    			}
    			if(isValid(cLoc.getX() + 1,cLoc.getY()) == true)
    			{
    				distances[cLoc.getX() + 1][cLoc.getY()] = currentDistance;
    				currentLoc.enQueue(new Location(cLoc.getX() + 1,cLoc.getY()));
    				drawText(Integer.toString(currentDistance),cLoc.getX() + 1,cLoc.getY(),Color.black);
    			}
    		}
    	}
    	 
    	totalDistance = currentDistance;
		canvas.display();
		
    	if( atGoal == true)
    	{
    		
    		return true;
    	}

       return false;
    }
  
    public boolean isValid(int x, int y)
    {
    	if( x < 0 || x > 7 || y < 0 || y > 7)
    	{
    		return false;
    	}
    	
    	if(mazeGrid[x][y] == 0 && distances[x][y] == 0)
    	{
    		return true;
    	}
    	
    	if(mazeGrid[x][y] == 3)
    	{
    		atGoal = true;
    		return false;
    	}
    	return false;
    }
        
    public boolean inBounds(int x, int y)
    {
    	if( x < 0 || x > 7 || y < 0 || y > 7)
    	{
    		return false;
    	}
    	
    	return true;
    }
    
    public void outputPath()
    { 
	   int currentX = endingX;
	   int currentY = endingY;
	   
       for(int x = totalDistance - 1; x > 0; x--)
       {
    	   messageArea.insert(Integer.toString(x),0);
    	   
    	   if(inBounds(currentX - 1, currentY) == true)
    	   {
    		   if(distances[currentX - 1][currentY] == x)
    		   {
    			   messageArea.insert(Integer.toString(x),0);
    			   fillRectangle(currentX - 1, currentY, CELL_SIZE,CELL_SIZE,Color.red);
    			   currentX -= 1;
    			   continue;
    		   }
    	   }
    	   
    	   if(inBounds(currentX, currentY - 1) == true)
    	   {
    		   if(distances[currentX][currentY - 1] == x)
    		   {
    			   messageArea.insert(Integer.toString(x),0);
    			   fillRectangle(currentX, currentY - 1, CELL_SIZE,CELL_SIZE,Color.red);
    			   currentY -= 1;
    			   continue;
    		   }
    	   }
    	   
    	   if(inBounds(currentX, currentY + 1) == true)
    	   {
    		   if(distances[currentX][currentY + 1] == x)
    		   {
    			  messageArea.insert(Integer.toString(x),0);
    			  fillRectangle(currentX, currentY + 1, CELL_SIZE,CELL_SIZE,Color.red);
    			  currentY += 1;
    			  continue;
    		   }
    	   }
    	   
    	   if(inBounds(currentX + 1, currentY) == true)
    	   {
    		   if(distances[currentX + 1][currentY] == x)
    		   {
    			   messageArea.insert(Integer.toString(x),0);
    			   fillRectangle(currentX + 1, currentY, CELL_SIZE,CELL_SIZE,Color.red);
    			   currentX += 1;
    			   continue;
    		   }
    	   }
    		   
    	   
       }
       canvas.display();
    } 
    

public static void main(String[] args){

    //  args[0] is maze file name from command line
    MazeShell P =new MazeShell(args[0]);


}

}
























