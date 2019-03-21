/*
 * NTB - Interstaatliche Hochschule für Technik Buchs
 * Schoenauweg 4, 9000 St. Gallen
 * All rights reserved
 */
package ch.ntb.cv.pathfinding;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashSet;



/**
 * Calculation of a shortest path by using A* 
 * 
 * Inputs an two-dimensional boolean array with passable/impassable cells, start and goal information
 * 
 * @see https://en.wikipedia.org/wiki/A*_search_algorithm
 * @result getProcessedMap() returns a 2 dimensional boolean array with processed cells being false
 * @result getFinalPath() returns a List of 2-int arrays [x,y] with the shortest path from goal to start
 * 
 * @author Andreas Scholz
 */
public class PathfindingAStar{
    
    // TODO: Optional: Implementieren Sie eine Datenstruktur, die Ihnen bei der Verwaltung Ihrer Knoten hilft
    protected class Node{

    }
    
    // TODO: Implementieren Sie einen Comperator, der zwei Knoten o.ä. vergleicht
    protected class ValueComparator implements Comparator<Node>
    {
        @Override
        public int compare(Node x, Node y){
            
            // HIER muss ein vergleich stattfinden
            System.err.println("Comparator is not yet implemented");
            
            return 0;
        }
    }
    
    
    
    // primary class ----------------------------------------------------------
    private final float STRAIGHT = (float)1.0;              // length of straight step
    private final float DIAGONAL = (float)Math.sqrt(2);     // length of diagonal step
        
    private boolean[][] map;
    private boolean moveDiagonal;
    private ValueComparator comparator;
    
    // TODO: Wählen und implementieren Sie eine geeignete Datenstruktur fuer die 
    // openlist
    // closedlist

    
    private boolean[][] closed_map;         // map of evaluated cells
    private ArrayList<Point> path;          // list of consecutive nodes as [x,y], no further information

    //protected GlobalProperties prop;
    private int WIDTH;                      //! modified (scaled) width  of the map
    private int HEIGHT;                     //! modified (scaled) height of the map
    
    private Node start;                     // node to start at
    private Node goal;                      // pre-set with target, overwritten by result node (incl. costs and parent)
    
    // constructor
    /**
    * @param map expects a 2 dimensional boolean array with passable cells to be true
    * @param moveDiagonal if true, diagonal steps are allowed and euclidian distance used
    * */
    public PathfindingAStar(){
        
        this.comparator = new ValueComparator(); 
    }

    
    
    public void initialize(boolean[][] map, boolean moveDiagonal, Point s, Point g){
        this.moveDiagonal = moveDiagonal;
        
        this.map = map;

        WIDTH = map.length;
        HEIGHT = map[0].length;
        
        // TODO: Passen Sie die beiden Zuweisungen an Ihre implementierte Datenstruktur an
        this.start = new Node();
        this.goal = new Node();
        System.err.println("Knoten-Datenstruktur is not yet initialized");
        
        // TODO: initialisieren sie Ihre beiden Datenstrukturen für die 
        // openlist
        // closelist
        System.err.println("Openlist/Closelist is not yet initialized");
        

        this.closed_map = new boolean[WIDTH][HEIGHT];
        this.path = new ArrayList<>();
        
        // copy the occupancy map. Visited and impassable cells will be 'false'
        if(this.map != null){
            for(int x = 0; x < WIDTH; ++x){
                for(int y = 0; y < HEIGHT; ++y){
                    this.closed_map[x][y] = map[x][y];
                }
            }
        }
    }

    
    public boolean search(){
        // TODO: Dies ist der primaere Aufruf die Suche durchzufuehren. Implementieren Sie einen Algorithmus, der die Suche initialisiert und 
        // dann solange Knoten durchsucht, bis das Ziel gefunden wurde (return true) oder die Suche fehlschlug ( return false)
        
        
        // initialize

        // search
        System.err.println("Search is not yet implemented");
        return false;
    }
    
    public void printResult(){
       // Optional: Implementieren Sie eine funktionen, die Ihnen das Ergebnis als Liste von Wegpunkte ausgibt.
    }
    

    
    
    
    // --- Controller access to get final result back to View - not subject to change
    
    public boolean[][] getProcessedMap(){
        if(closed_map == null) return null;
        
        return closed_map;
    }
    

    public ArrayList<Point> getFinalPath(){
        if(path == null) return null;
        
        ArrayList<Point> tmp = new ArrayList<>();
        for(int i = 0; i < path.size(); ++i){
            tmp.add(new Point(path.get(i).x,path.get(i).y));
        }
        return tmp;
    }
    
    
    
    
    // --- PRIVATE METHODS
    
         
    //! calculates the Manhattan (rectilinear) distance between two points
    private int getEdgeDistMHT(int x1, int y1){
        System.err.println("getEdgeDistMHT is not yet implemented");
        return 0;
    }
    
    //! calculates the euclidian distance between two points
    private float getEdgeDistEUC(int x1, int y1){
        System.err.println("getEdgeDistEUC is not yet implemented");
        return 0.0f;
    }
    
    private void expandNode(Node n){
        
        // TODO: Implementieren Sie hier den Algorithmus, eine Zelle zu evaluieren und gem. Heuristic in die OpenList zu sortieren
        System.err.println("expandNode is not yet implemented");

    }

}
