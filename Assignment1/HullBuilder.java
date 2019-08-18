/* HullBuilder.java
   CSC 225 - Summer 2019

   Starter code for Convex Hull Builder. Do not change the signatures
   of any of the methods below (you may add other methods as needed).

   B. Bird - 03/18/2019
   (Vicky Nguyen/V00906571/16-06-2019)
*/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.*;

public class HullBuilder{

    /* Add constructors as needed */
    ArrayList<Point2d> Upper = new ArrayList<Point2d>();
    ArrayList<Point2d> Lower = new ArrayList<Point2d>();

    /* addPoint(P)
       Add the point P to the internal point set for this object.
       Note that there is no facility to delete points (other than
       destroying the HullBuilder and creating a new one). 
    */
    public void addPoint(Point2d P){
        if(Upper.isEmpty()) {
            Upper.add(P);
            Lower.add(P);
        }else{
            int UpperPos = toIndex(binarySearchIndex(Upper, P));
            if (((UpperPos>0)&&(UpperPos<Upper.size())&&(Point2d.chirality(Upper.get(UpperPos-1), Upper.get(UpperPos), P) < 0)) || (UpperPos<=0)|| (UpperPos>=Upper.size())){
                Upper.add(UpperPos, P);
                Upper = buildHull(Upper, 1);
            }
                
            int LowerPos = toIndex(binarySearchIndex(Lower, P));
            if (((LowerPos>0)&&(LowerPos<Lower.size())&&(Point2d.chirality(Lower.get(LowerPos-1), Lower.get(LowerPos), P) > 0)) || (LowerPos<=0) || (LowerPos>=Lower.size())){
                Lower.add(LowerPos, P);
                Lower = buildHull(Lower, 2);
            }      
        }
    }
 
    public int binarySearchIndex (ArrayList<Point2d> arr, Point2d P){
        return Collections.binarySearch(arr, P, (Point2d a, Point2d b)->{
            int result = Double.compare(a.x, b.x);
            if (result==0) result = Double.compare(a.y, b.y);
            return result;
        } );
    }

    //to real position
    public int toIndex(int x){
        if(x<0) return (-x-1);
        return x;
    }

    //Reference: B. Bird's lecture 8
    //build upper and lower hulls
    public ArrayList<Point2d> buildHull (ArrayList<Point2d> original, int x){
        ArrayList<Point2d> temp = new ArrayList<Point2d>(original.size());

        if(original.size()>2){
            temp.add(original.get(0));
            temp.add(original.get(1));
        }else return original;

        for(int i = 1; i<original.size(); i++){
            while(temp.size()>=2){
                Point2d a = temp.get(temp.size()-2), b = temp.get(temp.size()-1);
                if (((x==1)&&(Point2d.chirality(a,b, original.get(i)) > 0)) || ((x==2)&&(Point2d.chirality(a,b, original.get(i)) < 0))) break;
                    temp.remove(temp.size()-1);
                }
                temp.add(original.get(i));
        }
        return temp;  
    }

    /* getHull()
       Return a java.util.LinkedList object containing the points
       in the convex hull, in order (such that iterating over the list
       will produce the same ordering of vertices as walking around the 
       polygon).
    */
    public LinkedList<Point2d> getHull(){
        LinkedList<Point2d> hull = new LinkedList<Point2d>();

        for(int u=0; u<Upper.size(); u++)
            hull.add(Upper.get(u));
        
        for(int lo = Lower.size()-2; lo > 0; lo--)
            hull.add(Lower.get(lo));

        return hull;
    }

    /* isInsideHull(P)
       Given an point P, return true if P lies inside the convex hull
       of the current point set (note that P may not be part of the
       current point set). Return false otherwise.
     */
    public boolean isInsideHull(Point2d P){
        int UpperPos = toIndex(binarySearchIndex(Upper, P));
        int LowerPos = toIndex(binarySearchIndex(Lower, P));
		if ((UpperPos>0)&&(UpperPos<Upper.size())&&(Point2d.chirality(Upper.get(UpperPos-1), Upper.get(UpperPos), P) >= 0) && 
		(LowerPos>0)&&(LowerPos<Lower.size())&&(Point2d.chirality(Lower.get(LowerPos-1), Lower.get(LowerPos), P) <= 0))
            return true;
		return false;   
    }
}