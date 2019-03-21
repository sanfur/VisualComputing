/*
 * NTB - Interstaatliche Hochschule für Technik Buchs
 * Schoenauweg 4, 9000 St. Gallen
 * All rights reserved
 */
package ch.ntb.cv.pathfinding;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author scholz
 */
public class PathfindingController {
    
    static int rgb_black=new Color(0,0,0).getRGB();
    static int rgb_white=new Color(255,255,255).getRGB();
    static int rgb_gray=new Color(127,127,127).getRGB();
    static int rgb_red=new Color(255,30,30).getRGB();
    static int rgb_blue=new Color(30,160,255).getRGB();
    static int rgb_green=new Color(35,200,35).getRGB();
    static int rgb_violett=new Color(200,80,215).getRGB();
    static int rgb_lightgray=new Color(220,220,220).getRGB();
    static int rgb_lightviolett=new Color(208,150,215).getRGB();
    static int rgb_brightyellow=new Color(255,255,160).getRGB();
    
    private final int width;                   //! dimension in x-direction
    private final int height;                  //! dimension in y-direction
    private final int scale;                   //! factor the image is resized
    
    private boolean moveDiagonal;
    
    private boolean[][] occupancy_map;
    private Point start;
    private Point goal;
    
    PathfindingAStar astar;
    
    
    public PathfindingController(int w, int h, int s){
        start  = new Point();
         goal  = new Point();
         
        width  = Math.max(100, w);
        height = Math.max( 50, h);
        scale  = s;
        moveDiagonal = true;
        
        occupancy_map = new boolean[w][h];
        
        astar = new PathfindingAStar();
        
        clearMap();
    }
    
    public void clearMap(){
        
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                occupancy_map[x][y] = true;
            }
        }
    }
    
    
    public void setPredefinedMap(){
        clearMap();
        
        start.x = width-5;
        start.y = 5;
        
         goal.x = 10;
         goal.y = height-10;
         
         
        for(int y = 15; y < height-15; ++y){
            occupancy_map[50][y] = false;
        }
        
        for(int x = 50; x < width/2; ++x){
            occupancy_map[x][height-15] = false;
        }
        
        for(int y = 0; y < 25; ++y){
            occupancy_map[width-(width/4)][y] = false;
        }
        
        astar = new PathfindingAStar();
        
    }

    public void setStart(Point start) {
        if(isOuside(start.x, start.y)) return;
        this.start = start;
    }

    public void setGoal(Point goal) {
        if(isOuside(goal.x, goal.y)) return;
        this.goal = goal;
    }

    public Point getStart() {
        return start;
    }

    public Point getGoal() {
        return goal;
    }
    
    public void setOccupancy(int x, int y){
        if(isOuside(x,y)) return;
        occupancy_map[x][y] = !occupancy_map[x][y];
    }
    
    public boolean isOuside(int x, int y){
        return x < 0 || x > width-1 || y < 0 || y > height-1;
    }
    
    public boolean switchVertical(){
       moveDiagonal = !moveDiagonal;
       return moveDiagonal;
    }
    
    
    public void execute(){
        
        astar.initialize(occupancy_map, moveDiagonal, start, goal);
        astar.search();
    }
    
    
    public BufferedImage getGeneratedMap(){
        BufferedImage finalMap = new BufferedImage(width*scale, height*scale, BufferedImage.TYPE_INT_RGB);
        
        boolean[][] pmap = astar.getProcessedMap();
        ArrayList<Point> path = astar.getFinalPath();
        
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {             
                if(occupancy_map[x][y]){
                    if(pmap != null && !pmap[x][y] ){
                        // aufgedeckte ( besuchte) Felder
                        setArea(x, y, rgb_brightyellow, finalMap);
                    }else{
                        // alle nicht blockierten Felder
                        setArea(x, y, rgb_white, finalMap);
                    }
                }else{
                    setArea(x, y, rgb_black, finalMap);
                }
            }
        }
        
        // gefundener Pfad
        if(path != null){
            for(int index = 0; index < path.size(); ++index){
                // mache nachtraeglich blockierte Zellen ueber pfad sichtbar
                if(finalMap.getRGB(path.get(index).x*scale, path.get(index).y*scale) != rgb_black){
                    setArea(path.get(index).x, path.get(index).y, rgb_blue, finalMap);
                }
            }
        }
        
        setArea(start.x, start.y, rgb_green, finalMap);
        setArea(goal.x, goal.y, rgb_red, finalMap);
        
        
        return finalMap;
    }
    
    private void setArea(int x, int y, int color, BufferedImage map){
        for(int u = 0; u < scale; ++u){
            for(int v = 0; v < scale; ++v){
                map.setRGB((x*scale)+u, (y*scale)+v, color);
            }
        }
    }
    
    
    
}
