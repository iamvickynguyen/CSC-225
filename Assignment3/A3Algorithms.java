/* A3Algorithms.java
   CSC 225 - Summer 2019


   B. Bird - 04/28/2019
   (Vicky Nguyen/V00906571/07-29-2019)
*/ 

import java.awt.Color;
import java.util.*;


public class A3Algorithms{

	public static void FloodFillDFS(PixelVertex v, PixelWriter writer, Color fillColour){
		DFS(v, writer, fillColour, 1);
	}

	public static void FloodFillBFS(PixelVertex v, PixelWriter writer, Color fillColour){
		BFS(v, writer, fillColour, 1);
	}
	
	public static void OutlineRegionDFS(PixelVertex v, PixelWriter writer, Color outlineColour){
		DFS(v, writer, outlineColour, 2);
	}
	
	public static void OutlineRegionBFS(PixelVertex v, PixelWriter writer, Color outlineColour){
		BFS(v, writer, outlineColour, 2);
	}

	//=========== HELPER FUNCTIONS ====================

	private static void DFS(PixelVertex v, PixelWriter writer, Color outlineColour, int mode){
		v.Mark(true);
		if ((mode==1) || ((mode==2)&&(v.getDegree() < 4)))
			writer.setPixel(v.getX(), v.getY(), outlineColour);
		for(PixelVertex w : v.getNeighbours()){
			if (!w.isVisited())
				DFS(w, writer, outlineColour, mode);		
		}
	}

	private static void BFS(PixelVertex v, PixelWriter writer, Color outlineColour, int mode){
		Queue<PixelVertex> queue = new LinkedList<PixelVertex>();
		v.Mark(true);
		queue.add(v);

		while(!queue.isEmpty()){
			PixelVertex vertex = queue.remove();
			if ((mode==1) || (mode==2 && vertex.getDegree() < 4))
				writer.setPixel(vertex.getX(), vertex.getY(), outlineColour);
			for(PixelVertex p : vertex.getNeighbours()){
				if(!p.isVisited()){
					p.Mark(true);
					queue.add(p);
				}
			}
		}
	}
	//=================================

	/* CountComponents(G)
	   Count the number of connected components in the provided PixelGraph 
	   object.
	*/
	public static int CountComponents(PixelGraph G){
		int count = 0;
		for(int i = 0; i < G.getHeight(); i++){
			for(int j = 0; j < G.getWidth(); j++){
				if(!G.adjList[i][j].isVisited()){
					count += 1;
					DFS(G.adjList[i][j]);
				}	
			}
		}	
		
		return count;
	}

	private static void DFS(PixelVertex v){
		v.Mark(true);
		for(PixelVertex w : v.getNeighbours()){
			if(!w.isVisited()){
				DFS(w);
			}
		}
	}

}