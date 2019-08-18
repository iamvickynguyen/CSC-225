/* PixelVertex.java
   CSC 225 - Summer 2019


   B. Bird - 04/28/2019
   (Vicky Nguyen/V00906571/07-25-2019)
*/
import java.awt.Color;

public class PixelVertex{

	/* Add a constructor here (if necessary) */
	private int x;
	private int y;
	private PixelVertex[] Neighbours = new PixelVertex[4];
	private int size;
	private boolean visited;
	

	public PixelVertex(int x, int y) { 
		this.x = x;
		this.y = y;
	}

	public void Mark(boolean visited){
		this.visited = visited;
	}

	public boolean isVisited(){
		return visited;
	}

	/* getX()
	   Return the x-coordinate of the pixel corresponding to this vertex.
	*/
	public int getX(){
		return x;
	}
	
	/* getY()
	   Return the y-coordinate of the pixel corresponding to this vertex.
	*/
	public int getY(){
		return y;
	}
	
	/* getNeighbours()
	   Return an array containing references to all neighbours of this vertex.
	   The size of the array must be equal to the degree of this vertex (and
	   the array may therefore contain no duplicates).
	*/
	public PixelVertex[] getNeighbours(){
		PixelVertex[] temp = new PixelVertex[size];
		for (int i = 0; i < size ; i++)
			temp[i] = Neighbours[i];
		Neighbours = temp;
		return Neighbours;
	}
	
	/* addNeighbour(newNeighbour)
	   Add the provided vertex as a neighbour of this vertex.
	*/
	public void addNeighbour(PixelVertex newNeighbour){
		Neighbours[size] = newNeighbour;
		size += 1;
	}
	
	/* removeNeighbour(neighbour)
	   If the provided vertex object is a neighbour of this vertex,
	   remove it from the list of neighbours.
	*/
	public void removeNeighbour(PixelVertex neighbour){
		PixelVertex[] temp = new PixelVertex[size-1];
		int i = 0;
		for(PixelVertex v : Neighbours){
			if(!v.equals(neighbour)){
				temp[i] = v;
				i += 1;
			}
		}
		size -= 1;
		Neighbours = temp;		
	}
	
	/* getDegree()
	   Return the degree of this vertex. Since the graph is simple, 
	   the degree is equal to the number of distinct neighbours of this vertex.
	*/
	public int getDegree(){
		return size;
	}
	
	/* isNeighbour(otherVertex)
	   Return true if the provided PixelVertex object is a neighbour of this
	   vertex and false otherwise.
	*/
	public boolean isNeighbour(PixelVertex otherVertex){
		for(PixelVertex v : Neighbours){
			if(v.equals(otherVertex))
				return true;
		}
		return false;
	}
	
}