/*
 * Maan Qraitem
 * Vertex class:
 * Vertex in the graph.
 */



import java.awt.Color;
import java.awt.Graphics;


import java.util.*;


public class Vertex extends Agent implements Comparable<Vertex>  {
	public enum Direction {NORTH, SOUTH, EAST, WEST, NONE} // Direction for the vertex.
	private HashMap<Direction, Vertex> edges; // holds all the edges connected to the vertex.
	private int cost; // holds the cost of the vertex.
	private boolean marked; // boolean required for calculating the shortest path.
	private String label; // holds the label of the vertex.
	private boolean visible; // boolean for holding if the vertex is visible or not.
	private boolean marked2; // boolean for keeping check if the vertex has been visited when detecting a path between
	// the wumpus and the hunter.

	// returns the opposite direction of a given direction.
	public static Direction opposite (Direction d){
		if (d == Direction.NORTH){
			return Direction.SOUTH;
		}

		else if (d == Direction.NORTH){
			return Direction.NORTH;
		}

		else if (d == Direction.EAST){
			return Direction.WEST;
		}

		else if (d == Direction.WEST){
			return Direction.EAST;
		}

		else {
			return null;
		}
	}

	/*
	 * initialize all the required values.
	 */
	public Vertex (String label, int r, int c) {
		super(r, c);
		this.edges = new HashMap<Direction, Vertex>();
		this.cost = 0;
		this.marked = false;
		this.label = label;
		this.visible = false;
		this.marked2 = false;

	}

	public void disconnect(){
		this.edges = null;
	}

	/*
	 * connect a vertex to another by
	 * putting in the hast table.
	 */
	public void connect(Vertex other, Direction dir){
		this.edges.put(dir, other);
	}

	/*
	 * makes the vertex visible.
	 */
	public void setVisible(){
		this.visible = true;
	}

	public boolean containsneighbor(Direction dir){
		if (this.edges.containsKey(dir)){
			return true;
		}

		return false;
	}

	/*
	 * gets the neighbor in a specific direction.
	 */
	public Vertex getNeighbor(Direction dir){
		if (this.edges.containsKey(dir)){
			return this.edges.get(dir);
		}
		else {
			return null;
		}
	}

	/*
	 * returns the label of the vertex.
	 */
	public String getLabel(){
		return this.label;
	}

	/*
	 * returns all the neighbors of the vertex.
	 */
	public Collection<Vertex> getNeighbors() {
		return this.edges.values();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo (Vertex other){
		return other.cost - this.cost;
	}

	/*
	 * returns the cost of the vertex.
	 */
	public int getCost(){
		return this.cost;
	}

	/*
	 * marks the vertex.
	 */
	public void mark(){
		this.marked = true;
	}

	/*
	 * unmark it.
	 */

	/*
	 * returns if the vertex is marked or not.
	 */
	public boolean getMarked(){
		return this.marked;
	}

	public void unmark(){
		this.marked = false;
	}

	/*
	 * sets the cost.
	 */
	public void setCost( int cost){
		this.cost = cost;
	}

	/*
	 * resets the mark 2.
	 */
	public void mark2(){
		this.marked2 = true;
	}
	/*
	 * returns the mark 2.
	 */
	public boolean getMarked2(){
		return this.marked2;
	}

	/*
	 * draws the vertex.
	 */
	public void draw(Graphics g, int x0, int y0, int scale, int range) {
		if (!this.visible) return;

		int xpos = x0 + this.x*scale;
		int ypos = y0 + this.y*scale;
		int border = 2;
		int half = scale / 2;
		int eighth = scale / 8;
		int sixteenth = scale / 16;

		// draw rectangle for the walls of the cave
		if (this.cost <= range)
			// wumpus is nearby
			g.setColor(Color.red);
		else
			// wumpus is not nearby
			g.setColor(Color.GRAY);

		g.drawRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
		g.fillRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);

		// draw doorways as boxes
		g.setColor(Color.black);
		if (this.edges.containsKey(Direction.NORTH))
			g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
		if (this.edges.containsKey(Direction.SOUTH))
			g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth),
				  eighth, eighth + sixteenth);
		if (this.edges.containsKey(Direction.WEST))
			g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
		if (this.edges.containsKey(Direction.EAST))
			g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth,
				  eighth + sixteenth, eighth);
	}

	/*
	 * (non-Javadoc)
	 * @see project.Agent#toString()
	 */
	public String toString(){
		String thing = "";
		thing = thing + this.label + "\n";
		thing = thing + "Number of edges: " + this.edges.size() + "\n";
		thing = thing + "Cost: " + this.cost;
		return thing;
	}

	/*
	 * tests the class.
	 */
	public static void main(String[] args){
		Vertex ver = new Vertex("Point",2, 3);
		ver.connect(new Vertex("Point2", 3, 4), Direction.SOUTH);
		System.out.println(ver.toString());
	}

}
