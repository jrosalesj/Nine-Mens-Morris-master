package test;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import engine.Engine;

public class EngineTest {

	@Test
	public void testLaunchModePlace() {
		Engine engine = new Engine();
		
		assertTrue(engine.inPlaceMode());
	}
	
	@Test
	public void testLaunchModeMove() {
		Engine engine = new Engine();
		
		assertFalse(engine.inMoveMode());
	}
	
	@Test
	public void testLaunchModeRemove() {
		Engine engine = new Engine();
		
		assertFalse(engine.inRemoveMode());
	}
	
	@Test
	public void testLaunchModePlayerStatus() {
		Engine engine = new Engine();
		
		assertThat(engine.activePlayer, CoreMatchers.is(engine.p1));
		assertThat(engine.inActivePlayer, CoreMatchers.is(engine.p2));
	}
	
	@Test
	public void testSetNextPlayer() {
		Engine engine = new Engine();
		
		engine.setNextPlayer();
		
		assertThat(engine.activePlayer, CoreMatchers.is(engine.p2));
		assertThat(engine.inActivePlayer, CoreMatchers.is(engine.p1));
	}

}
