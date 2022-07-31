package board;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private final int MEN = 9;
    public boolean lost = false;
    private String mark = "";
    private String name = "";
    private int placeCount = MEN;
    private int manCount = placeCount;
    public Player opponent = null;
    public boolean killPending = false;
    private ArrayList<String> ownedCells = null;
    private ArrayList<String> millCells = null;
    private ArrayList<String> nonMillCells = null;
    private ArrayList<String> removableCells = null;
    
    public enum PlayState { NOTSTARTED, PLACING, MOVING, REMOVING, FLYING, GAMEOVER };
    private PlayState currState = PlayState.NOTSTARTED;
    private PlayState prevState = PlayState.NOTSTARTED;
    
    /* Constructor for Player class
     * @param pName: player name character
     * @param pMark: player mark character
     */
    public Player(char pName, char pMark, int numMen) {
        name = Character.toString(pName);
        mark = Character.toString(pMark);
        placeCount = numMen;
        ownedCells = new ArrayList<String>();
        millCells = new ArrayList<String>();
        nonMillCells = new ArrayList<String>();
    }
    
    public boolean isOwner(Cell c) {
        return (c != null) && 
               (c.owner != null) && 
               (getName().equals(c.owner.getName()));
    }
    public void setOwner(Cell c) { if (c.isEmpty()) {addOwnedCell(c.label); } }
    
    public String getMark() { return mark; }
    public String getName() { return name; }

    public void doPlace() { placeCount--; }
    public boolean canPlace() { return (placeCount > 0); }
    public boolean isPlacing() { return getCurrentPlayState() == PlayState.PLACING; }
    public int getPlaceCount() { return placeCount; }
    
    public boolean canMove() { return (manCount >= 3); }
    public boolean isMoving() { return getCurrentPlayState() == PlayState.MOVING; }
    
    public boolean canFly() { return (manCount == 3); }
    public boolean isFlying() { return getCurrentPlayState() == PlayState.FLYING; }
    
    public boolean removePending() { return getCurrentPlayState() == PlayState.REMOVING; }
    
    
    public boolean hasLost() { return (manCount < 3 || lost ); }
    public void killMan() { manCount--; }
    
    /* Print player info to console */
    public void printInfo() {
        System.out.println("Player-" + getName() + "::");
        
        if ( isPlacing() ) {
            if ( canPlace() ) {
                System.out.println("- Placing, Placed [" + (MEN - placeCount) + "] & CanPlace [" + placeCount + "]");
            } else {
                System.out.println("- Placing, Placed [" + (MEN - placeCount) + "] & CanPlace [" + placeCount + "]");
            }
        } else if ( isMoving() ) {
            if ( canMove() ) {
                System.out.println("- Moving CanMove [" + manCount + "]");
            } else {
                System.out.println("- Will MOVE in next turn");
            }
        } else if ( isFlying() && canFly() ) {
            System.out.println("- Flying CanFly [" + manCount + "]");
        } else if ( removePending() ) {
            System.out.println("- Removed " + opponent.getName() +"'s mark");
        }
        
        System.out.println("- Owns cells: \"" + getOwnedCells() + "\"");
        System.out.println("- Non-mill cells: \"" + getNonMillCells() + "\"");
        System.out.println("- Mill cells: \"" + getMillCells() + "\"");
    }


    /* Clear cells belonging to player than can be removed */
    public void clearRemovableCells() { removableCells = new ArrayList<String>(); killPending = false; }
    /* Set Cells belonging to player than can be removed */
    public void setRemovableCells(String cellsToRemove) {
        killPending = true;
        removableCells = new ArrayList<String>(Arrays.asList(cellsToRemove.split(", ")));
        System.out.println("Setting removable cells = \"" + cellsToRemove + "\"");
    }
    /* Get cells belonging to player than can be removed */
    public ArrayList<String> getRemovableCells() { return removableCells; }
    
    
    /* Cell ownership methods */
    public void addOwnedCell(String cellAddress) { ownedCells.add(cellAddress); }
    public void removeOwnedCell(String cellAddress) {
        ownedCells.remove(cellAddress);
        //millCells.remove(millCells.indexOf(cellAddress));
        //nonMillCells.remove(nonMillCells.indexOf(cellAddress));
    }
    public String getOwnedCells() { return String.join(", ", ownedCells); }
    public String getMillCells() { return String.join(", ", millCells); }
    public void setMillCells(ArrayList<String> cells) { millCells = cells; }
    public String getNonMillCells() { return String.join(", ", nonMillCells); }
    public void setNonMillCells(ArrayList<String> cells) { nonMillCells = cells; }
    

    /* Finite State Machine for driving player game state */
    public void setPlayState(PlayState state) {
        PlayState nextState = state;
        
        if ( (state == PlayState.NOTSTARTED) || (placeCount == MEN) ) {
            // START -- game just initialized or yet to place
            nextState = PlayState.PLACING;
            
        } else if (state == PlayState.PLACING) {
            if (opponent.killPending) {
                // Formed a MILL, remove opponent's man
                nextState = PlayState.REMOVING;
            } else if ( canPlace() ) {
                // Still have men left to place
                nextState = PlayState.PLACING;
            } else {
                // PLACING completed, advance to MOVING
                nextState = PlayState.MOVING;
            }
            
        } else if (state == PlayState.MOVING) {
            if (hasLost()) {
            	nextState = PlayState.GAMEOVER;
            } else if (opponent.killPending) {
                // Formed a MILL, remove opponent's man
                nextState = PlayState.REMOVING;
            } else if ( canFly() ) {
                // Able to FLY
                nextState = PlayState.FLYING;
            } else {
                // Remain in MOVE state
                nextState = PlayState.MOVING;
            }
        } else if (state == PlayState.REMOVING) {
            if ( opponent.hasLost() ) {
                // Check that game is not yet over
                nextState = PlayState.GAMEOVER;
            } else {
                if (prevState == PlayState.PLACING && !canPlace()) {
                	nextState = PlayState.MOVING;// Go back to previous state
                } else if (prevState == PlayState.MOVING && canFly()) {
                	nextState = PlayState.FLYING;
                } else {
                	nextState = prevState;
                }
            }
        } else if (state == PlayState.FLYING) {
            if (opponent.killPending) {
                // Formed a MILL, remove opponent's man
                nextState = PlayState.REMOVING;
            } else if (hasLost()){
            	nextState = PlayState.GAMEOVER;
            } else {
                // Remain in MOVE state
                nextState = PlayState.FLYING;
            }
        }
        
        prevState = state;          // Save previous state
        currState = nextState;      // Save next state
    }
    public void setNextPlayState() { setPlayState(currState); }
    public PlayState getCurrentPlayState() { return currState; }
}
