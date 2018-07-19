/*
 * Maan Qraitem
 * Graph class
 */




import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class Graph{


	/*
	 * 2D array to hold the vertices.
	 * rows and columns in the array.
	 */
	private Vertex[][] vertices;
	private int rows;
	private int cols;

	/*
	 * initialize the values.
	 */
	public Graph(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		vertices = new Vertex[rows][cols];
	}

	/*
	 * detects if there is a viable path between the hunter and the wumpus
	 * so if not, I construct the graph again until there will be a path.
	 * The basic logic is similar to the breadth first algorithm.
	 * Neighbors are checked and added to a FIFO structure. A Queue.
	 * Visited vertices get marked as visited.
	 */

	public boolean detectConnection(Vertex v1, Vertex v2){

		MyQueue<Vertex> que = new MyQueue<Vertex>(5);
		Vertex current = v1;
		que.offer(current);

		while (que.getSize() > 0){

			current = que.peek();

			if (current.getNeighbor(Vertex.Direction.EAST) != null && !current.getNeighbor(Vertex.Direction.EAST).getMarked2()){
				que.offer(current.getNeighbor(Vertex.Direction.EAST));
				current.getNeighbor(Vertex.Direction.EAST).mark2();
			}
			if (current.getNeighbor(Vertex.Direction.WEST) != null && !current.getNeighbor(Vertex.Direction.WEST).getMarked2()){
				que.offer(current.getNeighbor(Vertex.Direction.WEST));
				current.getNeighbor(Vertex.Direction.WEST).mark2();
			}
			if (current.getNeighbor(Vertex.Direction.SOUTH) != null && !current.getNeighbor(Vertex.Direction.SOUTH).getMarked2()){
				que.offer(current.getNeighbor(Vertex.Direction.SOUTH));
				current.getNeighbor(Vertex.Direction.SOUTH).mark2();
			}
			if (current.getNeighbor(Vertex.Direction.NORTH) != null && !current.getNeighbor(Vertex.Direction.NORTH).getMarked2()){
				que.offer(current.getNeighbor(Vertex.Direction.NORTH));
				current.getNeighbor(Vertex.Direction.NORTH).mark2();
			}


			if (current.getNeighbor(Vertex.Direction.EAST) == v2){
				return true;
			}

			if (current.getNeighbor(Vertex.Direction.NORTH) == v2){
				return true;
			}

			if (current.getNeighbor(Vertex.Direction.SOUTH) == v2){
				return true;
			}

			if (current.getNeighbor(Vertex.Direction.WEST) == v2){
				return true;
			}

			current = que.poll();

		}

		return false;


	}
	/*
	 * clears the graph by setting all the places in the grid to null.
	 */
	public void clearGraph(){
		for (int a = 0; a < this.rows; a ++){
			for (int b = 0; b < this.cols; b ++){
				this.vertices[a][b] = null;
			}
		}
	}


	/*
	 * returns the vertex count.
	 */
	public int vertexCount(){
		return vertices.length;
	}



	/*
	 * returns if a gives position is accessible or not.
	 * checks mainly if  it's out or bound.
	 */
	public boolean accessible(int test_row, int test_col){


		if (this.vertices[test_col][test_row] == null){
			return false;
		}

		return true;
	}

	/*
	 * connects all the vertices in the array with their existed neighbors.
	 * checks for South, North, West, East.
	 */
	public void refresh(){
		for (int a = 0; a < this.rows; a ++){
			for (int b = 0; b < this.cols; b ++){
				if  (this.vertices[a][b] != null){
					if (a + 1 < this.rows){
						if (this.vertices[a + 1][b] != null){
							this.vertices[a][b].connect(this.vertices[a + 1][b], Vertex.Direction.EAST );
						}
					}

					if (b + 1 < this.cols){
						if (this.vertices[a][b + 1] != null){
							this.vertices[a][b].connect(this.vertices[a][b + 1], Vertex.Direction.SOUTH);
						}
					}
					if (a - 1 >= 0){
						if (this.vertices[a - 1][b] != null){
							this.vertices[a][b].connect(this.vertices[a - 1][b], Vertex.Direction.WEST );
						}
					}

					if (b - 1 >= 0){
						if (this.vertices[a][b - 1] != null){
							this.vertices[a][b].connect(this.vertices[a][b - 1], Vertex.Direction.NORTH);
						}
					}


				}
			}
		}
	}

	/*
	 * adds all the vertices to the game by simply inserting a vertex each time in the next
	 * location in the 2D array after conducting a possibility check.
	 * it makes sure that each row has no more than two missing vertices.
	 * refreshed after adding each vertex.
	 */
	public void addEdge() {
		Random rand = new Random();
		for (int a = 0; a < this.rows; a ++){
			int count = 0;
			for (int b = 0; b < this.cols; b ++){
				Vertex v = new Vertex("(" + Integer.toString(a) + "," +  Integer.toString(b)+ ")",a , b);
				if ((rand.nextFloat() < 0.8) || (a == this.rows - 1 && b == this.cols - 1)){
					if (count <= this.rows - 2 || (a == this.rows - 1 && b == this.cols - 1)){
						if (this.vertices[a][b] == null){
							count += 1;
							this.vertices[a][b] = v;
							this.refresh();
						}
					}
				}
			}
		}
	}

	/*
	 * makes all the vertices visible.
	 */
	public void visibleAll(){
		for (int a = 0; a < this.rows; a ++){
			for (int b = 0; b < this.cols; b ++){
				if (this.vertices[a][b] == null) continue;
				this.vertices[a][b].setVisible();
			}
		}
	}

	/*
	 * unmarks all vertices.
	 */
	public void unmarkAll(){
		for (int a = 0; a < this.rows; a ++){
			for (int b = 0; b < this.cols; b ++){
				if (this.vertices[a][b] == null) continue;
				this.vertices[a][b].unmark();
			}
		}
	}

	/*
	 * set all costs to maximum.
	 */
	public void setCostHighAll () {
		int highest = Integer.MAX_VALUE;
		for (int a = 0; a < this.rows; a ++){
			for (int b = 0; b < this.cols; b ++){
				if (this.vertices[a][b] == null) continue;
				this.vertices[a][b].setCost(highest);
			}
		}
	}

	/*
	 * returns a specific vertex.
	 */
	public Vertex getVertex(int row, int col){
			return this.vertices[row][col];
	}

	// returns the 2D array.
	public Vertex[][] getVertices() {
		return vertices;
	}

	/*
	 * calculates the shortest path by using
	 * a special algorithm.
	 */
	public void shortestPath (Vertex v0) {

		this.unmarkAll();
		this.setCostHighAll();
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>(10, Collections.reverseOrder());
		v0.setCost(0);
		q.add(v0);

		while (!q.isEmpty()){
			Vertex v = q.remove();
			v.mark();
			for (Vertex w : v.getNeighbors()){
				if (!w.getMarked() && v.compareTo(w) > 1 ){
					w.setCost(v.getCost() + 1);
					q.remove(w);
					q.add(w);
				}
			}
		}
	}


	/*
	 * draws all the vertices in the 2D array.
	 */
	public void draw(Graphics g, int scale, int x0, int y0, int range){
		for (int a = 0; a < this.rows; a ++){
			for (int b = 0; b < this.cols; b ++){
				if (this.vertices[a][b] != null){
					this.vertices[a][b].draw(g, x0, y0, scale, range);
				}

				else{
					int xpos = x0 + a*scale;
					int ypos = y0 + b*scale;
					int border = 2;

					g.setColor(Color.BLACK);

					g.drawRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
					g.fillRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String thing = "";
		for (int a = 0; a < this.rows; a ++){
			for (int b = 0; b < this.cols; b ++){
				if (this.vertices[a][b] != null)
				thing = thing + this.vertices[a][b].toString() + "\n";
			}
		}
		return thing;
	}

	/*
	 * tests the graph.
	 */
	public static void main(String[] args) {
		Graph gf = new Graph(5, 5);
		for (int i = 0; i < 25; i ++){
			gf.addEdge();
		}
		System.out.println(gf.toString());
	}
}
