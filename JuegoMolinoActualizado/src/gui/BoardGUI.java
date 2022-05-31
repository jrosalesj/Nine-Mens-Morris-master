package gui;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.JPanel;

import engine.Engine;
import engine.EngineAI;


public class BoardGUI extends JPanel {
	

	private final int[] boardPoint = {9,0,1,2,14,23,22,21,9,10,3,4,1,4,5,13,14,13,20,19,22,19,18,10,11,6,7,4,7,8,12,13,12,17,16,19,16,15,11};
	
	
	private String[] guiToBoardMap = {
            "a1", "d1", "g1",
            "b2", "d2", "f2",
            "c3", "d3", "e3",
            "a4", "b4", "c4", 
            "e4", "f4", "g4",
            "c5", "d5", "e5",
            "b6", "d6", "f6",
            "a7", "d7", "g7"
        };
	
	public Engine gameEngine;
	public int isAIMode = 0;
	private int selectedMoveMakerIndex = -1;

	public BoardGUI() {
	    gameEngine = new Engine();
		addMouseListener(new Controller());
		
	}
	
	public void setEngine(Engine gameEngine){
		this.gameEngine = gameEngine;
		this.selectedMoveMakerIndex = -1;
		repaint();
	}

	
	Point getPositionCoords(int position) {
		Point result = new Point();

		int margin = getSize().width/15;
		int width = getSize().width - 2 * margin;
		int height = getSize().height - 2 * margin;
		int squareSide = Math.min(width, height);
		int positionSpace = squareSide / 6;
		
		int row = position / 3;
		if (row < 3) {
			result.x = row * positionSpace + (position % 3) * (squareSide - 2 * row * positionSpace) / 2;
			result.y = row * positionSpace;
		} else if (row == 3) {
			result.x = (position % 3) * positionSpace;
			result.y = row * positionSpace;
		} else {
			Point point = getPositionCoords(23 - position);
			point.x -= margin;
			point.y -= margin;
			result.x = squareSide - point.x;
			result.y = squareSide - point.y;
		}
		
		result.x += margin;
		result.y += margin;
		
		return result;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Image img = new ImageIcon(getClass().getResource("cuzco.jpg")).getImage();
		g.drawImage(img,0,0,null);
		
		// draw the board line
		for (int i = 0; i < boardPoint.length-1; i++) {
			    
				Point start = getPositionCoords(boardPoint[i]);
				Point end = getPositionCoords(boardPoint[i+1]);
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(getSize().height/200));
				g2.setColor(new Color(130, 82, 1));
				g2.drawLine(start.x, start.y, end.x, end.y);
			
		}
		// line intersection point
		for (int i = 0; i < 24; i++) {
			Point coords = getPositionCoords(i);
			g.setColor(Color.BLACK);
			g.fillOval(coords.x - getSize().height/100, coords.y - getSize().height/100, getSize().height/50, getSize().height/50);
		}
		
			for (int i = 0; i<24;i++) {
				if (selectedMoveMakerIndex != -1) {
					if ( gameEngine.cBoard.getVacantCells().contains(guiToBoardMap[i]) && gameEngine.activePlayer.isMoving() && gameEngine.cBoard.getVacantNeighbors(guiToBoardMap[selectedMoveMakerIndex]).contains(guiToBoardMap[i])) {
    					Point coords = getPositionCoords(i);
    			        Graphics2D g2d = (Graphics2D) g.create();
    			        //set the stroke of the copy, not the original 
    			        Stroke dashed = new BasicStroke(getSize().height/400, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    			        g2d.setStroke(dashed);
    					//Graphics2D g2 = (Graphics2D) g;
    					// describir las piezas
    					//g2d.setStroke(new BasicStroke(getSize().height/400));
    					g2d.setColor(Color.RED);
    					g2d.drawOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
    					g2d.dispose();	
					}
					
					if ( gameEngine.activePlayer.isFlying() && gameEngine.cBoard.getVacantCells().contains(guiToBoardMap[i])) {
    					Point coords = getPositionCoords(i);
    					
    			        Graphics2D g2d = (Graphics2D) g.create();

    			        // establecer el trazo de la copia, no el original
    			        Stroke dashed = new BasicStroke(getSize().height/400, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    			        g2d.setStroke(dashed);
    					//Graphics2D g2 = (Graphics2D) g;
    					//  Dibujar l�mite de piezas
    					//g2.setStroke(new BasicStroke(getSize().height/400));
    					g2d.setColor(Color.RED);
    					g2d.drawOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
    					g2d.dispose();
					}
				}
				
				if (selectedMoveMakerIndex == i){
					g.setColor(Color.GRAY);
					Point coords = getPositionCoords(i);					
					g.fillOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
					Graphics2D g2 = (Graphics2D) g;
					// Draw pieces boundary
					g2.setStroke(new BasicStroke(getSize().height/400));
					g2.setColor(Color.gray);
					g2.drawOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
				} else {
				    
				    if (gameEngine.p1.getOwnedCells().contains(guiToBoardMap[i])) {
    					
    					g.setColor(Color.WHITE);
    					
    					Point coords = getPositionCoords(i);
    					
    					g.fillOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
    					Graphics2D g2 = (Graphics2D) g;
    					//Describir piezas
    					g2.setStroke(new BasicStroke(getSize().height/400));
    					g2.setColor(Color.gray);

    					g2.drawOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
    				} else if( gameEngine.p2.getOwnedCells().contains(guiToBoardMap[i]) ) {
    					g.setColor(Color.BLACK);
    					Point coords = getPositionCoords(i);
    					
    					//g.fillOval(coords.x - 20, coords.y - 20, 40, 40);
    					g.fillOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
    					Graphics2D g2 = (Graphics2D) g;
    					// Draw pieces boundary
    					g2.setStroke(new BasicStroke(getSize().height/400));
    					g2.setColor(Color.gray);
    					//g2.drawOval(coords.x - 20, coords.y - 20, 40, 40);
    					g2.drawOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
    				} 
                }
			
			if (gameEngine.activePlayer.removePending() && gameEngine.activePlayer.opponent.getRemovableCells().contains(guiToBoardMap[i])) {
				Point coords = getPositionCoords(i);
		        Graphics2D g2d = (Graphics2D) g.create();
		        //establecer el trazo de la copia, no el original
		        Stroke dashed = new BasicStroke(getSize().height/200, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		        g2d.setStroke(dashed);
				//Graphics2D g2 = (Graphics2D) g;
				// 
				//g2d.setStroke(new BasicStroke(getSize().height/400));
				g2d.setColor(Color.RED);
				g2d.drawOval(coords.x - getSize().height/30, coords.y - getSize().height/30, getSize().height/15, getSize().height/15);
				g2d.dispose();	
			}
			
			}	
		
		
		for(int i = gameEngine.p1.getPlaceCount(); i > 0; i--) {
			Point coords = getPositionCoords(2);
			g.setColor(Color.WHITE);
			int x = coords.x + getSize().height/10 - getSize().height/30 + (9-i)%3*getSize().height/15;
			int y = coords.y + getSize().height/10 - getSize().height/30 + ((9 - i)/3)*getSize().height/15;
			g.fillOval(x, y, getSize().height/15, getSize().height/15);
		}
		
		for(int i = gameEngine.p2.getPlaceCount() ; i > 0; i--) {
				Point coords = getPositionCoords(14);
				g.setColor(Color.BLACK);
				int x = coords.x + getSize().height/10 - getSize().height/30 + (9-i)%3*getSize().height/15;
				int y = coords.y + getSize().height/10 - getSize().height/30 + ((9 - i)/3)*getSize().height/15;
				g.fillOval(x, y, getSize().height/15, getSize().height/15);
			}

	}
	
	private class Controller extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (isAIMode == 1 && gameEngine.activePlayer == gameEngine.p2){
			return;
		}
			
			int x = e.getX();
			int y = e.getY();
			
			for (int i = 0; i < 24; i++) {
				Point coords = getPositionCoords(i);
				
				if (coords.x - getSize().height/30 <= x && x <= coords.x + getSize().height/30
						&& coords.y - getSize().height/30 <= y && y <= coords.y + getSize().height/30) {
					
					if (isAIMode == 0){
						if (gameEngine.activePlayer.isPlacing()) {
							gameEngine.place(guiToBoardMap[i]);
							repaint();
							if (gameEngine.activePlayer.isMoving()) {
							    EngineAI evalendAI = new EngineAI(gameEngine);
								String[] evalendMov = evalendAI.evalMove();
								if (evalendMov[0].equals(" ") && evalendMov[1].equals(" ")) {
									gameEngine.activePlayer.lost = true;
									gameEngine.activePlayer.setNextPlayState();
									
								}
							}
				
						} else if (gameEngine.activePlayer.removePending()) { 
							gameEngine.remove(guiToBoardMap[i]);
							repaint();
							if (gameEngine.activePlayer.isMoving()) {
							    EngineAI evalendAI = new EngineAI(gameEngine);
								String[] evalendMov = evalendAI.evalMove();
								if (evalendMov[0].equals(" ") && evalendMov[1].equals(" ")) {
									gameEngine.activePlayer.lost = true;
									gameEngine.activePlayer.setNextPlayState();
									
								}
							}
	
						} else if (gameEngine.activePlayer.isMoving() || gameEngine.activePlayer.isFlying()) {
							
							
						    // evaluate if movable 
							if (gameEngine.activePlayer.isMoving()) {
							    EngineAI evalendAI = new EngineAI(gameEngine);
								String[] evalendMov = evalendAI.evalMove();
								if (evalendMov[0] == " " && evalendMov[1] == " ") {
									gameEngine.activePlayer.lost = true;
									gameEngine.activePlayer.setNextPlayState();
									return;
								}
							}
							
							if (gameEngine.activePlayer.getOwnedCells().contains(guiToBoardMap[i])) {
								selectedMoveMakerIndex = i;

							} else if (selectedMoveMakerIndex != -1) { 
								
								gameEngine.move(guiToBoardMap[selectedMoveMakerIndex], guiToBoardMap[i]);
							    selectedMoveMakerIndex = -1;
							    repaint();
								
							    if (gameEngine.activePlayer.isMoving()) {
								    EngineAI evalendAI = new EngineAI(gameEngine);
									String[] evalendMov = evalendAI.evalMove();
									if (evalendMov[0].equals(" ") && evalendMov[1].equals(" ")) {
										gameEngine.activePlayer.lost = true;
										gameEngine.activePlayer.setNextPlayState();
										return;										
									}
								}

							}
						} 
						repaint();
						
					}
					
					if (isAIMode == 1) {
						if (gameEngine.activePlayer == gameEngine.p1){
							if (gameEngine.activePlayer.isPlacing()) {
								gameEngine.place(guiToBoardMap[i]);

							} else if (gameEngine.activePlayer.removePending()) { 
								gameEngine.remove(guiToBoardMap[i]);
							    EngineAI evalendAI = new EngineAI(gameEngine);
								String[] evalendMov = evalendAI.evalMove();
								if (evalendMov[0].equals(" ") && evalendMov[1].equals(" ")) {
									gameEngine.activePlayer.lost = true;
									gameEngine.activePlayer.setNextPlayState();
								}

							} else if (gameEngine.activePlayer.isMoving() || gameEngine.activePlayer.isFlying()) {
								
								if (gameEngine.activePlayer.isMoving()) {
								    // evaluate if movable 
								    EngineAI evalendAI = new EngineAI(gameEngine);
									String[] evalendMov = evalendAI.evalMove();
									if (evalendMov[0] == " " && evalendMov[1] == " ") {
										gameEngine.activePlayer.lost = true;
										gameEngine.activePlayer.setNextPlayState();
										return;
									}
								}
								
								if ( gameEngine.activePlayer.getOwnedCells().contains(guiToBoardMap[i]) ) {
									selectedMoveMakerIndex = i;

								} else if (selectedMoveMakerIndex != -1) { 
									
									gameEngine.move(guiToBoardMap[selectedMoveMakerIndex], guiToBoardMap[i]);
								    selectedMoveMakerIndex = -1;								

								}
							}
							repaint();
						}
						
						if (gameEngine.activePlayer == gameEngine.p2) {
							if ( gameEngine.activePlayer.isPlacing() ) {

								EngineAI currAI = new EngineAI(gameEngine);
								String evalDst = currAI.evalPlace();
								gameEngine.place(evalDst);

							} else if ( gameEngine.activePlayer.isMoving() ) {

							    EngineAI evalendAI = new EngineAI(gameEngine);
								String[] evalendMov = evalendAI.evalMove();
								if (evalendMov[0].equals(" ") && evalendMov[1].equals(" ")) {
									gameEngine.activePlayer.lost = true;
									gameEngine.activePlayer.setNextPlayState();
						
								}

								gameEngine.move(evalendMov[0], evalendMov[1]);

								
							} else if ( gameEngine.activePlayer.isFlying() ) {
								
								EngineAI currAI = new EngineAI(gameEngine);
								//String[] evalFly = currAI.flyRandom();
								String[] evalFly = currAI.evalFly();
								gameEngine.move(evalFly[0], evalFly[1]);
							}
							repaint();
							
							if (gameEngine.activePlayer.removePending()) {

								EngineAI currAI = new EngineAI(gameEngine);
								//String evalRv = currAI.removeRandom();
								String evalRv = currAI.evalRemove();
								gameEngine.remove(evalRv);
								repaint();
							    if (gameEngine.activePlayer.isMoving()) {
									EngineAI evalendAI = new EngineAI(gameEngine);
									String[] evalendMov = evalendAI.evalMove();
									if (evalendMov[0].equals(" ") && evalendMov[1].equals(" ")) {
										gameEngine.activePlayer.lost = true;
										gameEngine.activePlayer.setNextPlayState();
										return;
									
									}
							    }
							}	
						}
					}
					break;
				}
			}
		}
	}
}
