/*
 * Maan Qraitem
 * Hunter class.
 */




import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hunter extends Agent{

		/*
		 * a reference to the the vertex that the
		 * hunter is on.
		 */
		Vertex current;

		/*
		 * initialize the values.
		 */
		public Hunter(Vertex current){
			super(current.getRow(), current.getCol());
			this.current = current;
		}

		/*
		 * moves the hunter by changing its current refrence to the vector
		 * and editing its x and y values.
		 */
		public void move (Vertex newVertex){
			this.x = newVertex.getRow();
			this.y = newVertex.getCol();
			this.current = newVertex;
		}

		/*
		 * returns the vertex the hunter is on.
		 */
		public Vertex getVertex(){
			return this.current;
		}

		/*
		 * draws the hunter using previously loaded hunter images.
		 * it depends on the state of the archer:
		 * 		loaded:
		 * 			*South
		 * 			*North
		 * 			*East
		 * 			*West
		 * 		alive not loaded.
		 * 		dead.
		 */
		public void draw(Graphics g, int gridScale, int x0, int y0, boolean loaded, String dir, boolean lost) throws IOException{
			int xpos = this.getRow()*gridScale + x0;
			int ypos = this.getCol()*gridScale + y0;

			xpos += 15;
			ypos += 15;

			if (lost){
	            String path = "Images/ArcherDeath.png";
	            File file = new File(path);
	            BufferedImage image = ImageIO.read(file);
	            g.drawImage(image, xpos, ypos, null);
	            return;
			}

			if (loaded == false){
	            String path = "Images/Archer.png";
	            File file = new File(path);
	            BufferedImage image = ImageIO.read(file);
	            g.drawImage(image, xpos, ypos, null);
			}

			else{
				if (dir.equals("Up")){
		            String path = "Images/Archerup.png";
		            File file = new File(path);
		            BufferedImage image = ImageIO.read(file);
		            g.drawImage(image, xpos, ypos, null);
				}
				if (dir.equals("Down")){
		            String path = "Images/Archerdown.png";
		            File file = new File(path);
		            BufferedImage image = ImageIO.read(file);
		            g.drawImage(image, xpos, ypos, null);
				}
				if (dir.equals("Right")){
		            String path = "Images/Archerright.png";
		            File file = new File(path);
		            BufferedImage image = ImageIO.read(file);
		            g.drawImage(image, xpos, ypos, null);
				}
				if (dir.equals("Left")){
		            String path = "Images/Archerleft.png";
		            File file = new File(path);
		            BufferedImage image = ImageIO.read(file);
		            g.drawImage(image, xpos, ypos, null);
				}
			}
		}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
