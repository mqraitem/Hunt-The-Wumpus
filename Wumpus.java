/*
 * Maan Qraitem
 * Project 9
 * CS 231 
 * Wumpus class. 
 */



import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException; 



public class Wumpus extends Agent{ 

	/*
	 * reference to the vertex the wumpus is currently on. 
	 * boolean the holds if the vertex is visible or not.
	 */
	Vertex current; 
	boolean visible; 

	/*
	 * initialize the values. 
	 */
	public Wumpus(Vertex current){
		super(current.getRow(), current.getCol()); 
		this.current = current; 
	}
	
	public Vertex getVertex(){ 
		return this.current;
	}
	

	/*
	 * draws the Wumpus depending on its state. 
	 * doesn't draw anything if nothing is happenning.  
	 * if the game was lost or won, the appropriate image is drawn. 
	 * the image is loaded using buffered image. 
	 */
	public void draw(Graphics g, int gridScale, int x0, int y0, String state) throws IOException{
		int xpos = this.getRow()*gridScale + x0;
		int ypos = this.getCol()*gridScale + y0;
		xpos += 15; 
		ypos += 15;
		if (state != null){ 
			if (state.equals("Lost")) {
	            String path = "Images/MonsterWin.png";
	            File file = new File(path);
	            BufferedImage image = ImageIO.read(file);
	            g.drawImage(image, xpos, ypos, null);
			}
			
			else if (state.equals("Win")){ 
	            String path = "Images/MonsterDeath.png";
	            File file = new File(path);
	            BufferedImage image = ImageIO.read(file);
	            g.drawImage(image, xpos, ypos, null);
			}
		}
	}
	
	public static void main(String[] args){ 
	}
}