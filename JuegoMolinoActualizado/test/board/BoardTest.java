package board;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import board.Board;
import board.Player;

public class BoardTest {
    final int MEN = 9;   /*cambio MEN por Ficha*/
	
	
	// Test método para verificar la ubicación válida
	@Test
	public void testColocacionValida() {
		Board testBoard = new Board();
		testBoard.printBoard();
		
		Player p1 = new Player('1', 'W', MEN);
		String position = "c5";
		testBoard.placeMark(p1, position);
		assertTrue(testBoard.getCell(position).isOccupied());
	}
	
	
	//clearMillFormation
	// Test método para comprobar la propiedad de los marcadores
        //Marcando de izquierda a derecha si se formó MILL en la fila o de arriba a abajo si se formo la columna
	@Test
	public void testOwnership(){
        Board testboard = new Board();
        
        Player p1 = new Player('1','W', MEN);
        Player p2 = new Player('2','B', MEN);
        
        testboard.placeMark(p1, "c5"); 
        testboard.placeMark(p2, "c5"); 
        
        assertEquals(p1, testboard.getCell("c5").owner);

	}
	
	/// Test método para verificar si se forma un molino a través de una fila
        //clearMillFormation
	@Test
	public void testFilaMillFormation() {
		Board testBoard = new Board();
		
		Player p1 = new Player('1', 'W', MEN);
		
		testBoard.placeMark(p1, "a1");
		testBoard.placeMark(p1, "d1");
		
		assertEquals("a1-d1-g1", testBoard.placeMark(p1, "g1"));
	}
	
	// Test método para comprobar si se forma un molino bajo una columna
	@Test
	public void testColumnaMillFormation() {
		Board testBoard = new Board();
		testBoard.printBoard();
		
		Player p1 = new Player('1', 'W', MEN);
		
		testBoard.placeMark(p1, "a1");
		testBoard.placeMark(p1, "a4");
		
		assertEquals("a1|a4|a7", testBoard.placeMark(p1, "a7"));
	}
	

	//TestprintBoard pinta las posiciones del tablero con W
	@Test
	public void testprintBoard() {
	    String colLabels = "abcdefg";
	    String rowLabels = "1234567";
	    String pos;
	    
		Board testboard;
		
		testboard = new Board();
		testboard.printBoard();
        
        Player p1 = new Player('1','W', MEN);
        
        
	    for(int i=0;i<colLabels.length();i++){
	    	for(int j=0;j<colLabels.length();j++){
	    		StringBuilder sb = new StringBuilder();
	    		pos =  sb.append(colLabels.charAt(i)).append(rowLabels.charAt(j)).toString();
	    		System.out.println(pos);
	    		
	    		testboard.placeMark(p1, pos);
	    		
	    		if(testboard.getCell(pos) == null){
	    			assertTrue(testboard.getCell(pos) == null);
	    		} else{
	    			assertTrue(testboard.getCell(pos).isOccupied());
	    		}	
	    	}
	    } 
        testboard.printBoard();  
	}
}