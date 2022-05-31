package engine;

import java.util.ArrayList;

import java.util.Random;

import board.Board;
import board.Cell;

public class EngineAI {
	private Engine engine;
	private final String[][] millString = {{"a1", "d1", "g1"}, {"b2", "d2", "f2"}, {"c3", "d3", "e3"}, {"a4", "b4", "c4"}, 
			{"e4", "f4", "g4"}, {"c5", "d5", "e5"}, {"b6", "d6", "f6"}, {"a7", "d7", "g7"},
			{"a1", "a4", "a7"}, {"b2", "b4", "b6"}, {"c3", "c4", "c5"}, {"d1", "d2", "d3"}, 
			{"d5", "d6", "d7"}, {"e3", "e4", "e5"}, {"f2", "f4", "f6"}, {"g1", "g4", "g7"}};

	public EngineAI(Engine engine) {
		this.engine = engine;
	}

	public String placeRandom() {
		String[] dstString = engine.cBoard.getVacantCells().split(", ");
		Random rand = new Random();
		int n = rand.nextInt(dstString.length);
		//engine.place(dstString[n]);
		return dstString[n];
	}
	
	public String[] moveRandom() {
		String[] movString = {" ", " "};
		Board curBoard = engine.cBoard;
		String[] srcString = engine.activePlayer.getOwnedCells().split(", ");
		int n = srcString.length;
		Random randgen = new Random();
		int randstart = randgen.nextInt(n);
		int j;
		
		//moveLoop:
		for (int k = randstart; k < randstart + n; k++){
			if (k < n) { 
				j = k;
			} else { 
				j = k - n;
			}

			Cell temCell = curBoard.getCell(srcString[j]);
			
			if (temCell.hasOpenNeighbor()){
				int randDir = randgen.nextInt(4);
				for (int t = 0; t < 2; t++){
					switch (randDir) {
					case 0:
					if (temCell.left != null){
						if (temCell.left.isEmpty()){
							movString[0] = srcString[j];
							movString[1] = temCell.left.label;
							return  movString;
							//engine.move(srcString[j],temCell.left.label);
							//break moveLoop;
							}
					}
					case 1:
					if (temCell.right != null) {
						if (temCell.right.isEmpty()){
							movString[0] = srcString[j];
							movString[1] = temCell.right.label;
							return  movString;
						
							//break moveLoop;
							}
					}
					case 2:
					if (temCell.top != null) {
						if (temCell.top.isEmpty()){
							movString[0] = srcString[j];
							movString[1] = temCell.top.label;
							return  movString;
							//engine.move(srcString[j],temCell.top.label);
							//break moveLoop;
							}
					}
					case 3:
					if (temCell.bottom != null) {
						if (temCell.bottom.isEmpty()){
							movString[0] = srcString[j];
							movString[1] = temCell.bottom.label;
							return  movString;
							
							//break moveLoop;
							}
					}
					}
					randDir = 0;
				}
			}
		}
		return movString;	
	}
	public String evalPlace() {
		final String ownString = engine.activePlayer.getOwnedCells();
		final String oppString = engine.activePlayer.opponent.getOwnedCells();
		String dstCell = null;
		int count = 0;
		
		for(String[] mills : millString) {
			count = 0;
			dstCell = null;
			for(String cell : mills) {
				if (ownString.contains(cell)) {
					count = count + 1;
				} else if (!oppString.contains(cell)){
					dstCell = cell;
				}
			}
			
			if (count == 2 && dstCell != null) {
				//System.out.println(dstCell + "*****1*****");
				return dstCell;
			}
		}
		
		dstCell = null;
		for(String[] mills : millString) {
			count = 0;
			dstCell = null;
			for(String cell : mills) {
				if (oppString.contains(cell)) {
					count = count + 1;
					//System.out.println(count);
				} else if (!ownString.contains(cell)){
					//System.out.println(cell);
					dstCell = cell;
				}
			}
			
			if (count == 2 && dstCell !=null) {
				//System.out.println(dstCell + "*****2*****");
				return dstCell;
			}
		}
		
		dstCell = placeRandom();
		//System.out.println(dstCell + "*****3*****");
		return dstCell;	
	}
	
	public String evalRemove() {
		ArrayList<String> rmString = engine.activePlayer.opponent.getRemovableCells();
		final String ownString = engine.activePlayer.getOwnedCells();
		final String oppString = engine.activePlayer.opponent.getOwnedCells();
		String rmCell = null;
		int count = 0;
		int empty = 0;
		
		for(String[] mills : millString) {
			count = 0;
			empty = 0;
			rmCell = null;
			for(String cell : mills) {
				if (oppString.contains(cell) && rmString.contains(rmCell)) {
					count = count + 1;
					rmCell = cell;
					//System.out.println(count);
				} else if (!ownString.contains(cell)){
					empty++;
				}
			}
			
			if (count == 2 && empty == 1 && rmCell !=null) {
				return rmCell;
				
			}
		}
		//rmCell = removeRandom();
		return rmCell;
	}
	
	public String[] evalMove() {
		Board curBoard = engine.cBoard;
		final String ownString = engine.activePlayer.getOwnedCells();
		final String oppString = engine.activePlayer.opponent.getOwnedCells();
		String dstCell = null;
		String srcCell = null;
		String[] movString = {" ", " "};
		int count = 0;
		int loop = 0;
		for(String[] mills : millString) {
			count = 0;
			dstCell = null;
			for(String cell : mills) {
				if (ownString.contains(cell)) {
					count = count + 1;
				} else if (!oppString.contains(cell)){
					dstCell = cell;
				}
			}
			
			if (count == 2 && dstCell != null) {
				Cell temCell = curBoard.getCell(dstCell);
				if (loop < 8) {

					if (temCell.top != null) {
						if (ownString.contains(temCell.top.label)) {
							srcCell = temCell.top.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
					if (temCell.bottom != null) {
						if (ownString.contains(temCell.bottom.label)) {
							srcCell = temCell.bottom.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}	
				} else {
					
					if (temCell.left != null) {
						if (ownString.contains(temCell.left.label)) {
							srcCell = temCell.left.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
					if (temCell.right != null) {
						if (ownString.contains(temCell.right.label)) {
							srcCell = temCell.right.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
				}
			}
			loop++;
		}
		
		count = 0;
		loop = 0;
		dstCell = null;
		for(String[] mills : millString) {
			count = 0;
			dstCell = null;
			for(String cell : mills) {
				if (oppString.contains(cell)) {
					count = count + 1;
				} else if (!ownString.contains(cell)){
					dstCell = cell;
				}
			}
			
			if (count == 2 && dstCell != null) {
				Cell temCell = curBoard.getCell(dstCell);
				if (loop < 8) {

					if (temCell.top != null) {
						if (ownString.contains(temCell.top.label)) {
							srcCell = temCell.top.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
					if (temCell.bottom != null) {
						if (ownString.contains(temCell.bottom.label)) {
							srcCell = temCell.bottom.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}	
				} else {
					
					if (temCell.left != null) {
						if (ownString.contains(temCell.left.label)) {
							srcCell = temCell.left.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
					if (temCell.right != null) {
						if (ownString.contains(temCell.right.label)) {
							srcCell = temCell.right.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
				}
			}
			loop++;
		}
		
		movString = moveRandom();
		return movString;	
	}
	
	public String[] evalMoveInverse() {
		Board curBoard = engine.cBoard;
		final String ownString = engine.activePlayer.opponent.getOwnedCells();
		final String oppString = engine.activePlayer.getOwnedCells();
		String dstCell = null;
		String srcCell = null;
		String[] movString = {" ", " "};
		int count = 0;
		int loop = 0;
		for(String[] mills : millString) {
			count = 0;
			dstCell = null;
			for(String cell : mills) {
				if (ownString.contains(cell)) {
					count = count + 1;
				} else if (!oppString.contains(cell)){
					dstCell = cell;
				}
			}
			
			if (count == 2 && dstCell != null) {
				Cell temCell = curBoard.getCell(dstCell);
				if (loop < 8) {

					if (temCell.top != null) {
						if (ownString.contains(temCell.top.label)) {
							srcCell = temCell.top.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
					if (temCell.bottom != null) {
						if (ownString.contains(temCell.bottom.label)) {
							srcCell = temCell.bottom.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}	
				} else {
					
					if (temCell.left != null) {
						if (ownString.contains(temCell.left.label)) {
							srcCell = temCell.left.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
					if (temCell.right != null) {
						if (ownString.contains(temCell.right.label)) {
							srcCell = temCell.right.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
				}
			}
			loop++;
		}
		
		count = 0;
		loop = 0;
		dstCell = null;
		for(String[] mills : millString) {
			count = 0;
			dstCell = null;
			for(String cell : mills) {
				if (oppString.contains(cell)) {
					count = count + 1;
				} else if (!ownString.contains(cell)){
					dstCell = cell;
				}
			}
			
			if (count == 2 && dstCell != null) {
				Cell temCell = curBoard.getCell(dstCell);
				if (loop < 8) {

					if (temCell.top != null) {
						if (ownString.contains(temCell.top.label)) {
							srcCell = temCell.top.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
					if (temCell.bottom != null) {
						if (ownString.contains(temCell.bottom.label)) {
							srcCell = temCell.bottom.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}	
				} else {
					
					if (temCell.left != null) {
						if (ownString.contains(temCell.left.label)) {
							srcCell = temCell.left.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
					if (temCell.right != null) {
						if (ownString.contains(temCell.right.label)) {
							srcCell = temCell.right.label;
							movString[0] = srcCell;
							movString[1] = dstCell;
							return movString;
						}
					}
					
				}
			}
			loop++;
		}
		
		movString = moveRandom();
		return movString;	
	}
	
	public String[] evalFly() {
		final String ownString = engine.activePlayer.getOwnedCells();
		final String oppString = engine.activePlayer.opponent.getOwnedCells();
		String[] flyString = {" ", " "};
		String dstCell = null;
		String srcCell = null;
		int count = 0;
		
		// forming own mill check
		for(String[] mills : millString) {
			count = 0;
			dstCell = null;
			srcCell = ownString;
			for(String cell : mills) {
				if (ownString.contains(cell)) {
					count = count + 1;
					srcCell = srcCell.replace(cell, "");
				} else if (!oppString.contains(cell)){
					dstCell = cell;
				}
			}
			
			if (count == 2 && dstCell != null) {
				//System.out.println(dstCell + "*****1*****");
				flyString[1] = dstCell;
				srcCell = srcCell.replaceAll(",", "");
				srcCell = srcCell.replaceAll(" ", "");
				flyString[0] = srcCell;
				return flyString;
			}
		}
		
		dstCell = null;
		count = 0;
		srcCell = ownString;
		
		for(String[] mills : millString) {
			count = 0;
			dstCell = null;

			for(String cell : mills) {
				if (oppString.contains(cell)) {
					count = count + 1;
					//System.out.println(count);
				} else if (ownString.contains(cell)){
					//System.out.println(cell);
					dstCell = cell;
				}
			}
			
			if (count == 2 && dstCell !=null) {
				//System.out.println(dstCell + "*****2*****");
				srcCell = srcCell.replace(dstCell, "");
			}
		}
		
		srcCell = srcCell.replaceAll(",", "");
		srcCell = srcCell.replaceAll(" ", "");
		if (!srcCell.equals("")) {
			srcCell = srcCell.substring(0, 2);
			if (engine.activePlayer.opponent.isMoving()) {
				String[] guessOppMov = evalMoveInverse();
				dstCell = guessOppMov[1];
			} else if (engine.activePlayer.opponent.isFlying()) {
				String[] guessOppMov = evalFlyInverse();
				dstCell = guessOppMov[1];
			}
			
			flyString[0] = srcCell;
			flyString[1] = dstCell;
			return flyString;
		}
		
		
		return flyString;	
	}
	
	public String[] evalFlyInverse() {
		final String ownString = engine.activePlayer.opponent.getOwnedCells();
		final String oppString = engine.activePlayer.getOwnedCells();
		String[] flyString = {" ", " "};
		String dstCell = null;
		String srcCell = null;
		int count = 0;
		
		for(String[] mills : millString) {
			count = 0;
			dstCell = null;
			srcCell = ownString;
			for(String cell : mills) {
				if (ownString.contains(cell)) {
					count = count + 1;
					srcCell = srcCell.replace(cell, "");
				} else if (!oppString.contains(cell)){
					dstCell = cell;
				}
			}
			
			if (count == 2 && dstCell != null) {
				//System.out.println(dstCell + "*****1*****");
				flyString[1] = dstCell;
				srcCell = srcCell.replaceAll(",", "");
				srcCell = srcCell.replaceAll(" ", "");
				flyString[0] = srcCell;
				return flyString;
			}
		}
		
		
		return flyString;	
	}
	
}
