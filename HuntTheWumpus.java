/*
 * Maan Qraitem
 * Project 9
 * CS 231 
 * Hunt The Wumpus class: 
 * Runs the game after loading its components.
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.IOException;
import javax.swing.*;



	public class HuntTheWumpus extends JFrame {
	
			private static final long serialVersionUID = 1L; 
			private Random random; // For assigning the position of the Wumpus 
			private int width; // the width of the screen. 
			private int height; // the height of the screen. 
			private int x0; // the shift of the width. 
			private int y0; // the shift of the height. 
			private int scale; // the scale of the drawn objects. 
			private boolean isRunning; // the game loop boolean. 
			private Graph graph; // the graph of the game. 
			private Hunter hunter; //  
			private BufferedImage backBuffer;
			private Wumpus wumpus; // holds the Wumpus instance. 
			private int Wumx; // the Wumpus row position. 
			private int Wumy; // the Wumpus column position.  
			private boolean loaded; // boolean for tracking if the arrow is loaded or not. 
			private boolean attempted; // boolean for tracking if a shot was attempted. 
			private boolean win; // tracks the win state. 
			private boolean endGame; // tracks if the game has ended or not. 
			private String direction; // specifies the direction in which the archer in shooting position is. 
			private boolean dead; // tells us if the archer is dead or not. 
			private BasicPanel canvas; 
			private boolean replay; // tells us if the player wants to replay or not. 
			private int range; // the range for which near vertices turn red. 
			private boolean freeze;
			
			/* 
			 * initialize all the variables. 
			 */
			public HuntTheWumpus(int width, int height, int range){ 
				this.replay = false;
				this.freeze = false; 
				this.width = width;
				this.height = height;
				this.direction = "Right";
				this.dead = false;
				this.loaded = false;  
				this.attempted = false; 
				this.win = false; 
				this.endGame = false; 
				this.random = new Random(); 
				this.x0 = 10;
				this.y0 = 30;		
				this.isRunning = true; 
				this.graph = new Graph(8, 8);
				this.scale = 96;
				this.range = range;
				/*
				 * this part is responsible for making the graphs. 
				 * it basically adds vertices in random locations in the graph. 
				 * using addEdge() method. Then it adds the wumpus in a random location 
				 * making sure that it's in non null location. Then it adds the hunter in the last 
				 * vertex in the map. Finally it checks if there is a path between the wumpus and the hunter. 
				 * if there is, then it breaks, if not, it clears the 2D array and goes one more time through the 
				 * loop. 
				 */
				while (true){
					graph.addEdge();
					while (true){
						this.Wumx = random.nextInt(6);
						this.Wumy = random.nextInt(6);
						if (this.graph.getVertex(this.Wumx, this.Wumy) != null){
							this.wumpus = new Wumpus(this.graph.getVertex(this.Wumx, this.Wumy));
							break;
						}
					}
					this.hunter = new Hunter(this.graph.getVertex(7, 7));
					this.graph.getVertex(7,7).setVisible(); 
					if (graph.detectConnection(this.hunter.getVertex(), this.wumpus.getVertex())){
						break; 
					}
					
					else {
						graph.clearGraph();
					}
				}
				
				/*
				 * managing the flickering. 
				 */
				backBuffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB); 				
				

				/*
				 * it creates a canvas to hold the basic panel. 
				 * adds the proper layouts. 
				 * add all the buttons. 
				 * and then add them to the panel.
				 */
				this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
				this.canvas = new BasicPanel( height, width );
				this.add( this.canvas, BorderLayout.CENTER );
				this.setVisible( true );
				JButton color = new JButton("Exit");
				JButton quit = new JButton("replay");
				JButton Easy = new JButton("Easy");
				JButton Medium = new JButton("Medium");
				JButton Hard = new JButton("Hard");
				JPanel panel = new JPanel( new FlowLayout(FlowLayout.RIGHT));
				panel.add( color );
				panel.add( quit );
				panel.add(Easy); 
				panel.add(Medium);
				panel.add(Hard);
				this.add( panel, BorderLayout.SOUTH);
				this.pack();
				
				/*
				 * creates an instance of the control class. 
				 * and then pass it in to the action listner of 
				 * each button. 
				 */

				Control control = new Control();
				this.addKeyListener(control);
				this.setFocusable(true);
				this.requestFocus();

				color.addActionListener( control );
				quit.addActionListener( control );
				Easy.addActionListener( control );
				Medium.addActionListener( control );
				Hard.addActionListener( control );

			}
			

			private class BasicPanel extends JPanel {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				/**
				 * Creates the drawing canvas
				 * @param height the height of the panel in pixels
				 * @param width the width of the panel in pixels
				 **/
				public BasicPanel(int height, int width) {
					super();
					this.setPreferredSize( new Dimension( width, height ) );
					this.setBackground(Color.white);
				}
			}
			
			
			/*
			 * The game logic: 
			 * checks if the hunter entered the wumpus room. 
			 * checks if the shot was successful or not after attempt. 
			 */
			public String UpdateWin(){ 
				
				if (this.hunter.getRow() == this.wumpus.getRow() && 
						this.hunter.getCol() == this.wumpus.getCol()){ 
					this.endGame = true; 
					return "Lost"; 
				}
				
				if (this.attempted){ 
					if (this.win){ 
						this.endGame = true; 
						return "Win"; 
					}
					else{ 
						this.dead = true;
						this.endGame = true; 
						return "Lost";
					}
				}
				
				return null; 
			}
	
			/*
			 * the draw method of the game. 
			 * Initialize the graphics object. 
			 * Then back buffer to deal with flickering. 
			 * Then draws all the components. 
			 */
			public void draw() throws IOException{ 
				Graphics g = getGraphics();
				g.drawImage(backBuffer, 0, 0, null); 
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, 0, this.width, this.height);
				this.graph.draw(g, this.scale, this.x0, this.y0, this.range);
				this.hunter.draw(g, this.scale, this.x0, this.y0, this.loaded, this.direction, this.dead);
				this.wumpus.draw(g, this.scale, this.x0, this.y0, this.UpdateWin());
				
			}
			
			/*
			 * returns the range. 
			 */
			
			public int getRange(){ 
				return this.range;
			}
			
			/* 
			 * runs the game. 
			 * contains simple game while loop. 
			 * draws and calculate the shortest path. 
			 * end the game when end game is true. 
			 */
			public boolean run() throws InterruptedException, IOException{ 	
				while(this.isRunning){ 	
					draw();
					this.graph.shortestPath(this.wumpus.getVertex());
					this.UpdateWin();
					if (this.endGame == true){ 
						this.graph.visibleAll();
						this.freeze = true;
					}
					Thread.sleep(80);
				}
				
				return this.replay;
			}
			
	
			/* 
			 * The control class. 
			 * contains the several actions in case a special key was hit. 
			 */
		    public class Control extends KeyAdapter implements ActionListener {
	
		        public void keyTyped(KeyEvent e) {
	            	 
	            	
		            /*
		             * The basic logic for each key: 
		             * If the hunter in arrow loaded state: 
		             * 		checks if the arrival position matches the wumpus place. 
		             * 		update the win boolean. 
		             * If not: 
		             * 		moves the hunter to the correct place. 
		             */
		            if( ("" + e.getKeyChar()).equalsIgnoreCase("q") ) {
		                isRunning = false; 
		            }
		            
		            if (freeze) return; 
		            
		            if( ("" + e.getKeyChar()).equalsIgnoreCase(" ") ){ 
		            	loaded = (!loaded); 
		            }
		            
		            if( ("" + e.getKeyChar()).equalsIgnoreCase("l") ){ 
		            	if (loaded == true){
		            		if (direction == "Right"){ 
			            		attempted = true; 
		            			Vertex vx = hunter.getVertex().getNeighbor(Vertex.Direction.EAST);
		            			if (vx.getCol() == wumpus.getCol() && vx.getRow() == wumpus.getRow()){ 
		            				win = true; 
		            			}
		            		}
		            		if (direction == "Left"){ 
			            		attempted = true;  
		            			Vertex vx = hunter.getVertex().getNeighbor(Vertex.Direction.WEST);
		            			if (vx.getCol() == wumpus.getCol() && vx.getRow() == wumpus.getRow()){ 
		            				win = true; 
		            			}
		            		}
		            		if (direction == "Up"){
			            		attempted = true; 
		            			Vertex vx = hunter.getVertex().getNeighbor(Vertex.Direction.NORTH);
		            			if (vx.getCol() == wumpus.getCol() && vx.getRow() == wumpus.getRow()){ 
		            				win = true; 
		            			}
		            		}
		            		if (direction == "Down"){ 
			            		attempted = true; 
		            			Vertex vx = hunter.getVertex().getNeighbor(Vertex.Direction.SOUTH);
		            			if (vx.getCol() == wumpus.getCol() && vx.getRow() == wumpus.getRow()){ 
		            				win = true; 
		            			}
		            		}
		            	}
		            }
		            
		            if( ("" + e.getKeyChar()).equalsIgnoreCase("w") ) {
		            	direction = "Up";
		            	if (loaded == false){ 
		            		if (hunter.getVertex().getNeighbor(Vertex.Direction.NORTH) != null){
			            		hunter.getVertex().getNeighbor(Vertex.Direction.NORTH).setVisible();
			                	hunter.move(hunter.getVertex().getNeighbor(Vertex.Direction.NORTH));
		            		}
		            	}
		            }
		            if( ("" + e.getKeyChar()).equalsIgnoreCase("s") ) {
		            	direction = "Down";
		            	if (loaded == false){ 
		            		if (hunter.getVertex().getNeighbor(Vertex.Direction.SOUTH) != null){
			            		hunter.getVertex().getNeighbor(Vertex.Direction.SOUTH).setVisible();
			                	hunter.move(hunter.getVertex().getNeighbor(Vertex.Direction.SOUTH));
		            		}
		            	}
		            }
		            if( ("" + e.getKeyChar()).equalsIgnoreCase("d") ) {
		            	direction = "Right";
		            	if (loaded == false){ 
		            		if (hunter.getVertex().getNeighbor(Vertex.Direction.EAST) != null){
			            		hunter.getVertex().getNeighbor(Vertex.Direction.EAST).setVisible();
			                	hunter.move(hunter.getVertex().getNeighbor(Vertex.Direction.EAST));
		            		}
		            	}
		            }
		            
		            if( ("" + e.getKeyChar()).equalsIgnoreCase("a") ) {
		            	direction = "Left";
		            	if (loaded == false){ 
		            		if (hunter.getVertex().getNeighbor(Vertex.Direction.WEST) != null){
			            		hunter.getVertex().getNeighbor(Vertex.Direction.WEST).setVisible();
			                	hunter.move(hunter.getVertex().getNeighbor(Vertex.Direction.WEST));
		            		}
		            	}
		            }
		        }
		    	
		        /*
		         * (non-Javadoc)
		         * perform the actions after pressing the buttons. 
		         */
		        public void actionPerformed(ActionEvent event) {
		            if( event.getActionCommand().equalsIgnoreCase("replay") ) {
		            	replay = true;
		            	isRunning = false;
		            	dispose();
		            }
		            else if( event.getActionCommand().equalsIgnoreCase("Exit") ) {
		            	isRunning = false;
		                dispose(); 
		            }
		            else if( event.getActionCommand().equalsIgnoreCase("Easy") ) {
		            	range = 2; 
		            	replay = true;
		            	isRunning = false;
		            	dispose();
		            }
		            else if( event.getActionCommand().equalsIgnoreCase("Medium") ) {
		            	range = 3;
		            	replay = true;
		            	isRunning = false;
		            	dispose();
		            }
		            else if( event.getActionCommand().equalsIgnoreCase("Hard") ) {
		            	range = 4;
		            	replay = true;
		            	isRunning = false;
		            	dispose();
		            }
		        }
		    } // end class Control
		    
		    /*
		     * Runs the game. 
		     */
			public static void main(String[] args) throws InterruptedException, IOException{ 
				HuntTheWumpus thing = new HuntTheWumpus(800, 800, 2);
				/*
				 * runs another game as long as the player hits replay.
				 * or changes the difficulty passing the the future range. 
				 */
				while (thing.run()){ 
					thing = new HuntTheWumpus(800, 800, thing.getRange());
				}
	
			}
		}