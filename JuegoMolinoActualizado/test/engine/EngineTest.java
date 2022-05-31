
package engine;


import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

public class EngineTest {

    
        //testinPlaceMode metodo para saber si hay un lugar para colocar
	@Test
	public void testinPlaceMode() {
		Engine engine = new Engine();
		
		assertTrue(engine.inPlaceMode());
	}
	//testinMoveMode metodo para mover la ficha
	@Test
	public void testinMoveMode() {
		Engine engine = new Engine();
		
		assertFalse(engine.inMoveMode());
	}
	//testinRemoveMode metodo para eliminar la ficha
	@Test
	public void testinRemoveMode() {
		Engine engine = new Engine();
		
		assertFalse(engine.inRemoveMode());
	}
	

}