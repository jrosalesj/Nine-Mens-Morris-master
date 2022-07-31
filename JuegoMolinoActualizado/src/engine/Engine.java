package engine;

import java.util.ArrayList;

import board.Board;
import board.Player;
import board.Player.PlayState;

public class Engine {
    public int MEN = 9;
    public Board cBoard;
    public Player p1, p2, activePlayer, inActivePlayer;
    String cellsToRemove = "";
    String placeCells = "";
    String newMillCells = "";
    
    /* Constructor class for game engine
     * Create a new game board and associated players
     */
    public Engine() {
        cBoard = new Board();
        
        p1 = new Player('1', 'X', MEN);
        p2 = new Player('2', 'O', MEN);
        p1.opponent = p2;
        p2.opponent = p1;
        
        printGameInfo("Unplayed");
        setNextPlayer();
    }
    
    /* Print current game information */
    public void printGameInfo(String str) {
        System.out.println("== " + str);
        cBoard.printBoard();
        
        if (activePlayer != null) { activePlayer.printInfo(); System.out.println(); }
        if (inActivePlayer != null) { inActivePlayer.printInfo(); System.out.println();}
        
        System.out.println("Board.getVacantCells():: " + cBoard.getVacantCells());
    }
    
    /* Toggle player at each turn, such that:
     *   p2 gets turn after p1, and
     *   p1 gets turn after p2
     */
    public void setNextPlayer() {
        activePlayer = (activePlayer == null) ? p1 : activePlayer.opponent;
        inActivePlayer = activePlayer.opponent;
        
        if (activePlayer.getCurrentPlayState() == PlayState.NOTSTARTED ) { activePlayer.setNextPlayState(); }
        System.out.println("\n>> Player-" + activePlayer.getName() + " is next\n\n");
    }
    
    /* Place a Man/Mark for activePlayer at destination cell address;
     * - toggle turn if place was successful but does not result in a Mill
     * - set board to removeMode() if Mill was formed
     * @param cellAddress: board cell destination address place Man/Mark
     */
    public void place(String cellAddress) {
        String msg = String.format("Player-%s @ %s", activePlayer.getName(), cellAddress);
        String result = "";
        String removableCells = "";
        
        if ( activePlayer.isPlacing() ) {
            result = cBoard.placeMark(activePlayer, cellAddress);
            cBoard.setOwnedCellsGroup(activePlayer);
            
            if ( result.equals("SUCCESS") ) {
                // PLACING was successful but did not result in a Mill for activePlayer
                printGameInfo("PLACE:: " + msg + "; " + result);
                activePlayer.setNextPlayState();
                setNextPlayer();
            } else if ( result.contains("-") || result.contains("|")) {
                // PLACING was successful and resulted in a Mill for activePlayer
                //      <>-<>-<> -> Row Mill was formed
                //      <>|<>|<> -> Column Mill was formed
                removableCells = inActivePlayer.getNonMillCells();
                if (removableCells.length() == 0) { removableCells = inActivePlayer.getMillCells(); }
                if (removableCells.length() > 0) {
                    System.out.println(">> Select Player-" + inActivePlayer.getName() + 
                            " Man/Mark to remove: \"" + removableCells + "\"");
                    activePlayer.opponent.setRemovableCells(removableCells);
                    
                    printGameInfo("PLACE:: " + msg + "; SUCCESS!");
                }
                activePlayer.setNextPlayState();
            } else {
                // Failed placeMark
                System.out.println("PLACE:: " + msg + "; " + result);
            }
        } else if ( activePlayer.removePending() ) {
            System.out.println("\n\nPLACE:: " + msg + "; ABORTED -- Remove is pending!\n");
        } else if ( activePlayer.isMoving() ) {
            System.out.println("\n\nPLACE:: " + msg + "; ABORTED -- Done placing marks!\n");
        } else {
            System.out.println("\n\nPLACE:: " + msg + "; NOT ALLOWED!\n");
        }
    }
    
    /* Remove a cell's occupant from Board */
    public void remove(String dstCellAddr) {
        String msg = String.format("Player-%s removed Player-%s cell %s", activePlayer.getName(), inActivePlayer.getName(), dstCellAddr);
        ArrayList<String> removableCells = activePlayer.opponent.getRemovableCells();
        if (removableCells.contains(dstCellAddr)) {
            cBoard.removeMark(activePlayer.opponent, dstCellAddr);
            activePlayer.opponent.clearRemovableCells();
            //cBoard.setOwnedCellsGroup(activePlayer);
            
            printGameInfo("REMOVE:: " + msg);
            activePlayer.setNextPlayState();
            activePlayer.opponent.setNextPlayState();
            setNextPlayer();
        } else {
            System.out.println("\nRemove FAILED; " + dstCellAddr + " not part of valid cells to remove");
            System.out.println("-- can only remove from " + String.join(", ", removableCells));
        }
    }
    
    public Player getActivePlayer() { return activePlayer; }
    
    public Board getBoard(){ return cBoard; }

    public boolean gameOver() { return (p1.hasLost() || p2.hasLost()); }
    
    public boolean inRemoveMode() { return !gameOver() && (cellsToRemove.length() != 0); }
    public boolean inPlaceMode() { return (activePlayer.isPlacing() || inActivePlayer.isPlacing()); }
    public boolean inMoveMode() { return (activePlayer.isMoving() || inActivePlayer.isMoving()); }
    
    /*
     * For the player with active turn, move a Man from a source-cell
     * to destination cell on the 9-Men's Morris board
     * @param srcCell: source cell address
     * @param dstCell: destination cell address
     */
    public void move(String srcCellAddr, String dstCellAddr) {
        String msg = String.format("Player-%s @ %s -> %s", activePlayer.getName(), srcCellAddr, dstCellAddr);;
        String result = "";
        String removableCells = "";
        String vacantCells = "";
        
        if ( activePlayer.isMoving() || activePlayer.isFlying() ) {
            if (activePlayer.canFly()) {
                vacantCells = cBoard.getVacantCells();
            } else {
                vacantCells = cBoard.getVacantNeighbors(srcCellAddr);
            }
            
            if (!vacantCells.contains(dstCellAddr)) {
                System.out.println("Cell-" + dstCellAddr + " not in possible move set");
                System.out.println("MOVE:: " + msg + "; " + " FAILED");
            } else {
                result = cBoard.moveMark(activePlayer, srcCellAddr, dstCellAddr);
                cBoard.setOwnedCellsGroup(activePlayer);
                
                if ( result.equals("SUCCESS") ) {
                    // PLACING was successful but did not result in a Mill for activePlayer
                    printGameInfo("MOVE:: " + msg + "; " + result);
                    activePlayer.setNextPlayState();
                    setNextPlayer();
                } else if ( result.contains("-") || result.contains("|")) {
                    // PLACING was successful and resulted in a Mill for activePlayer
                    //      <>-<>-<> -> Row Mill was formed
                    //      <>|<>|<> -> Column Mill was formed
                    removableCells = inActivePlayer.getNonMillCells();
                    if (removableCells.length() == 0) { removableCells = inActivePlayer.getMillCells(); }
                    if (removableCells.length() > 0) {
                        System.out.println(">> Select Player-" + inActivePlayer.getName() + " Man/Mark to remove: \"" + removableCells + "\"");
                        activePlayer.opponent.setRemovableCells(removableCells);
                        printGameInfo("MOVE:: " + msg + "; SUCCESS!");
                    }
                    activePlayer.setNextPlayState();
                } else {
                    // Failed moveMark
                    System.out.println("PLACE:: " + msg + "; " + result);
                }            
            }
        } else { System.out.println("Board not in MOVE state ->> MOVE " + msg + " FAILED!");
        }
    }
    /* Set string value to newMillCells */
    public void setNewMillCells(String commaSeparatedString) { newMillCells = commaSeparatedString; }
}