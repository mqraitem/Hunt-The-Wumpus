
/*
 * Maan Qraitem
 * Graph class
 */

import java.awt.Graphics;



public class Agent{


	//Stores the coordinates of the agent.
	int x;
	int y;


	public Agent( int r, int c){
		this.x = r;
		this.y = c;
	}

	//returns the row index.
	public int getRow(){
		return this.x;
	}

	//returns the column index.
	public int getCol(){
		return this.y;
	}

	//sets the row component to a new row.
	public void setRow( int newRow ){
		this.x = newRow;
	}

	//sets the col component to a new col.
	public void setCol( int newCol ) {
		this.y = newCol;
	}

	//returns a string with the template (row, col)
	public String toString(){
		String thing = "(";
		thing = thing + this.x + "," + this.y + ")";
		return thing;
	}

	//public void updateState( Landscape scape ){}
	//public void updateState(Landscape scape){}
	public void draw(Graphics g){}


	public static void main(String[] args){
		Agent agent = new Agent(3, 4);
		agent.setCol(33);
		System.out.println(agent.toString());

	}




}
