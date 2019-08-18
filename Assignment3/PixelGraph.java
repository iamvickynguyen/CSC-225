/* PixelGraph.java
   CSC 225 - Summer 2019

   B. Bird - 04/28/2019
   (Vicky Nguyen/V00906571/07-29-2019)
*/ 

import java.awt.Color;
import java.util.ArrayList;

public class PixelGraph{

	/* PixelGraph constructor
	   Given a 2d array of colour values (where element [x][y] is the colour 
	   of the pixel at position (x,y) in the image), initialize the data
	   structure to contain the pixel graph of the image. 
	*/
	public PixelVertex[][] adjList;
	private int width;
	private int height;


	public PixelGraph(Color[][] imagePixels){
		width = imagePixels[0].length;
		height = imagePixels.length;

		adjList = new PixelVertex[height][width];

		//add vertices
		for(int row = 0; row < height; row++){
			for(int column = 0; column < width; column++){
				adjList[row][column] = new PixelVertex(row, column);
			}
		}

		//add edges
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				PixelVertex v = adjList[i][j];
				if ((i-1>=0) && imagePixels[i][j].equals(imagePixels[i-1][j])) { //up
					v.addNeighbour(adjList[i-1][j]);
				}
				if ((i+1 < height) && imagePixels[i][j].equals(imagePixels[i+1][j])) { //down
					v.addNeighbour(adjList[i+1][j]);
				}
				if ((j-1>=0) && imagePixels[i][j].equals(imagePixels[i][j-1])) { //left
					v.addNeighbour(adjList[i][j-1]);
				}
				if ((j+1 < width) && imagePixels[i][j].equals(imagePixels[i][j+1])) { //right
					v.addNeighbour(adjList[i][j+1]);
				}
			}
		}
	}

	
	/* getPixelVertex(x,y)
	   Given an (x,y) coordinate pair, return the PixelVertex object 
	   corresponding to the pixel at the provided coordinates.
	   This method is not required to perform any error checking (and you may
	   assume that the provided (x,y) pair is always a valid point in the 
	   image).
	*/
	public PixelVertex getPixelVertex(int x, int y){
		//width x row + column = index
		return adjList[x][y];
	}
	
	/* getWidth()
	   Return the width of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getWidth(){
		return width;
	}
	
	/* getHeight()
	   Return the height of the image corresponding to this PixelGraph 
	   object.
	*/
	public int getHeight(){
		return height;
	}
	
}