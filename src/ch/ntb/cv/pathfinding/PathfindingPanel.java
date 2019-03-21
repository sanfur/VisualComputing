/*
 * NTB - Interstaatliche Hochschule für Technik Buchs
 * Schoenauweg 4, 9000 St. Gallen
 * All rights reserved
 */
package ch.ntb.cv.pathfinding;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;



@SuppressWarnings("serial")
public class PathfindingPanel extends JPanel implements ActionListener{

    final int vGap = 5;
    final int hGap = 10;
    final int gridsize = 4;
    final int mapWidth = 200;
    final int mapHeight = 80;
    
    
    private JButton bStart;
    private JButton bGoal;
    private JButton bObstacles;
    private JButton bExecute;
    private JButton bDiagonal;
    private JButton bClearAll;
            
    private PathfindingCanvas drawingArea;
    private JPanel navigation;

    
    public PathfindingPanel() {
            setName("A* Pathfinding");
            
            layoutButtons();

            drawingArea = new PathfindingCanvas(mapWidth, mapHeight, gridsize);

            this.setLayout(new BorderLayout(hGap, vGap));		
            this.add(navigation, BorderLayout.WEST);
            this.add(drawingArea, BorderLayout.CENTER);

    }
    
    private void layoutButtons() {
		bStart = new JButton("Set start");
                bGoal = new JButton("Set goal");
                bObstacles  = new JButton("Set|Unset obstacles");
                bExecute  = new JButton("Execute");
                bDiagonal  = new JButton("Diagonal movement");
		bClearAll = new JButton("Clear all");
		
		navigation = new JPanel();
		navigation.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(vGap, hGap, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		navigation.add(bStart, gbc);
		bStart.addActionListener(this);	
                
                gbc.gridy++;
                navigation.add(bGoal, gbc);
		bGoal.addActionListener(this);
                
                gbc.gridy++;
                navigation.add(bObstacles, gbc);
		bObstacles.addActionListener(this);
                
                gbc.gridy++;
                navigation.add(bDiagonal, gbc);
		bDiagonal.addActionListener(this);
                
                gbc.gridy++;
                navigation.add(bExecute, gbc);
		bExecute.addActionListener(this);
		
                gbc.gridy++;
                navigation.add(bClearAll, gbc);
		bClearAll.addActionListener(this);

		// Add empty space
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.gridy++;
		navigation.add(new JPanel(), gbc);
	}
    
    private void execute(){
        drawingArea.pc.execute();
        drawingArea.setImage();
    }
    
    private void reset(){
        drawingArea.pc.setPredefinedMap();
        drawingArea.setImage();
    }



    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
		if(source == bStart){
                    drawingArea.setType(PathfindingCanvas.Type.start); 
                }else if(source == bGoal){
                    drawingArea.setType(PathfindingCanvas.Type.goal); 	
		}else if(source == bObstacles){
                    drawingArea.setType(PathfindingCanvas.Type.blocked); 	
		}else if(source == bDiagonal){
                    if(drawingArea.pc.switchVertical()){
                        bDiagonal.setText("Diagonal movement"); 
                    }else{
                        bDiagonal.setText("Straight movement"); 
                    }
                    
		}else if(source == bExecute){
                        execute();
		}else if(source == bClearAll){
                        reset();
		}else{
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
    }
}
