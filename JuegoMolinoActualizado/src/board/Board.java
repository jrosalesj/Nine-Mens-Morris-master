package board;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int DIMENSION = 7;
    private final Cell [][] board;
    //public enum PlaceOrMoveStates { UNINITIALIZED, FAILED, SUCCESS, FORMEDMILL }
    //private PlaceOrMoveStates boardStatus = PlaceOrMoveStates.UNINITIALIZED;
    
    /* Console print variables */
    boolean colIsNumeric = false;
    boolean rowIsNumeric = !colIsNumeric;
    private boolean ROWADDRFIRST = false;
    private String LABELS = "ABCDEFG1234567abcdefg";
    
    private ArrayList<String> vacantCells = null;
    
    /*
     * Board constructor: initialize board X-by-X grid of Cells and
     * then setup Cell links based on adjacency/neighbor matrix
     */
    public Board() {
        board = new Cell[DIMENSION][DIMENSION];
        vacantCells = new ArrayList<String>();
        setNeighbors();
    }
    
    /*
     * Setup left, right, top and bottom neighbors of each cell on
     * a 9-Men's-Morris game board (adjacency matrix for all 24-cells)
     */
    private void setNeighbors() {
        String[] cellMap = "a1,d1,g1,b2,d2,f2,c3,d3,e3,a4,b4,c4,e4,f4,g4,c5,d5,e5,b6,d6,f6,a7,d7,g7".split(",");
        int [][][] adjacency =  {
            // cell, lft,  rht,  top,  bot
            { {1,1}, {0,0}, {1,4}, {0,0}, {4,1} },
            { {1,4}, {1,1}, {1,7}, {0,0}, {2,4} },
            { {1,7}, {1,4}, {0,0}, {0,0}, {4,7} },
            { {2,2}, {0,0}, {2,4}, {0,0}, {4,2} },
            { {2,4}, {2,2}, {2,6}, {1,4}, {3,4} },
            { {2,6}, {2,4}, {0,0}, {0,0}, {4,6} },
            { {3,3}, {0,0}, {3,4}, {0,0}, {4,3} },
            { {3,4}, {3,3}, {3,5}, {2,4}, {0,0} },
            { {3,5}, {3,4}, {0,0}, {0,0}, {4,5} },
            { {4,1}, {0,0}, {4,2}, {1,1}, {7,1} },
            { {4,2}, {4,1}, {4,3}, {2,2}, {6,2} },
            { {4,3}, {4,2}, {0,0}, {3,3}, {5,3} },
            { {4,5}, {0,0}, {4,6}, {3,5}, {5,5} },
            { {4,6}, {4,5}, {4,7}, {2,6}, {6,6} },
            { {4,7}, {4,6}, {0,0}, {1,7}, {7,7} },
            { {5,3}, {0,0}, {5,4}, {4,3}, {0,0} },
            { {5,4}, {5,3}, {5,5}, {0,0}, {6,4} },
            { {5,5}, {5,4}, {0,0}, {4,5}, {0,0} },
            { {6,2}, {0,0}, {6,4}, {4,2}, {0,0} },
            { {6,4}, {6,2}, {6,6}, {5,4}, {7,4} },
            { {6,6}, {6,4}, {0,0}, {4,6}, {0,0} },
            { {7,1}, {0,0}, {7,4}, {4,1}, {0,0} },
            { {7,4}, {7,1}, {7,7}, {6,4}, {0,0} },
            { {7,7}, {7,4}, {0,0}, {4,7}, {0,0} }
        };
        
        Cell currCell = null;
        for (int index = 0; index < adjacency.length; index++) {
            currCell = createCell(adjacency[index][0][0], adjacency[index][0][1]);
            currCell.label = cellMap[index];    // string label
            
            currCell.left = createCell(adjacency[index][1][0], adjacency[index][1][1]);
            currCell.right = createCell(adjacency[index][2][0], adjacency[index][2][1]);
            currCell.top = createCell(adjacency[index][3][0], adjacency[index][3][1]);
            currCell.bottom = createCell(adjacency[index][4][0], adjacency[index][4][1]);
            
            vacantCells.add(cellMap[index]);    // Update vacant cells
        }
    }
    
    /*
     * Given a cell address of type A1, A2, D4, E5, etc, determine and 
     * return the cell address's board row/col position
     * @param cellAddress: 2-character string cell address
     * @param rowOrCol: "row" or "col" strings
     * @return integer: board Cell array row/col
     */
    private int getIntIndex(String cellAddress, String rowOrCol) {
        int addrIndex = -1;
        
        if (ROWADDRFIRST) {
            addrIndex = rowOrCol.equals("row") ? 0 : 1;
        } else {
            addrIndex = rowOrCol.equals("row") ? 1 : 0;
        }
        
        int cRow = LABELS.indexOf(cellAddress.charAt(addrIndex));
        cRow = (cRow >= 0) ? (cRow % DIMENSION) : cRow;
        
        return cRow + 1;
    }
    
    /*
     * Given a cell address of type A1, A2, D4, E5, etc, determine and 
     * return the cell address's board row position (2nd character)
     * @param cellAddress: cell address
     * @return integer: board Cell array row
     */
    private int getRow(String cellAddress) { return getIntIndex(cellAddress, "row"); }
    
    /*
     * Given a cell address of type A1, A2, D4, E5, etc, determine and 
     * return the cell address's board column position (1st character)
     * @param cellAddress: cell address
     * @return integer: board Cell array column
     */
    private int getCol(String cellAddress) { return getIntIndex(cellAddress, "col"); }
    
    /* Adjust an row-/col-index for proper base-0 indexing into board array */
    private int adjustIndex(int index) { return index - 1; }
    
    /*
     * Create a cell at board's row and column 
     * @param boardRow: integer type row# (base-1)
     * @param boardCol: integer type column# (base-1)
     */
    private Cell createCell(int boardRow, int boardCol) {
        Cell cell = null;
        
        if (isValidCellAddr(boardRow, boardCol)) {
            cell = getCell(boardRow, boardCol);
        
            if (cell == null) { cell = new Cell(); }
        
            board[adjustIndex(boardRow)][adjustIndex(boardCol)] = cell;
        }
        return cell;
    }
    
    /* Check if row and column coordinates are valid */
    private boolean isValidCellAddr(int boardRow, int boardCol) {
        return (boardRow > 0) && (boardCol > 0);
    }

    /* Print game board column header */
    public void printColumnHeader(int spacer) {
        String str = " ";
        int chOffset = colIsNumeric ? DIMENSION : 0;
        
        // print column headers
        for (int i = 0; i < DIMENSION; i++) {
            str += String.format("%s%s", repeatChar(' ', spacer), 
                        Character.toString(LABELS.charAt(i + chOffset)));
        }
        System.out.println(str);
    }
    
    /* Console-based board printing for non-gui debug */
    public void printBoard() {
        int numSpacer = 1;
        int rowChOffset = rowIsNumeric ? DIMENSION : 0;
        printColumnHeader(numSpacer);
        
        String str = "";
        // print column headers
        for (int i = 0; i < DIMENSION; i++) {
            str += String.format("%s%s", Character.toString(LABELS.charAt(i + rowChOffset)), repeatChar(' ', numSpacer));
            
            for (int j = 0; j < DIMENSION; j++) {
                if (board[i][j] != null) { str += String.format("%s", board[i][j].getStateChar());
                } else { str += " ";
                }
                
                if (j < DIMENSION - 1) { str += repeatChar(' ', numSpacer); }
            }
            System.out.println(str);
            str = "";
        }
        System.out.println();
    }
    
    /*
     * Given a 1-based row and column address, get the board cell
     * referenced by the row and column parameters
     * @param boardRow: integer type row# (base-1)
     * @param boardCol: integer type column# (base-1)
     * @return: referenced board cell 
     */
    public Cell getCell(int boardRow, int boardCol) {
        Cell cell = null;
        
        if (isValidCellAddr(boardRow, boardCol)) {
            cell = board[adjustIndex(boardRow)][adjustIndex(boardCol)];
        }
        return cell;
    }
    
    public Cell getCell(String cellAddr) { return getCell(getRow(cellAddr), getCol(cellAddr));}
    
    /* Useful for console printing of a character a certain number of times */
    private static final String repeatChar(char c, int length) {
        char[] data = new char[length];
        Arrays.fill(data, c);
        return new String(data);
    }
    
    /*
     * Search cells left/right/top/bottom direction and return the
     * edge cell in the search direction
     */
    private Cell getEdgeCell(Cell cell, String direction) {
        Cell edgeCell = cell;
        Cell nextCell = edgeCell;
        
        while(nextCell != null) {
            if (direction.equals("left")) { nextCell = nextCell.left;
            } else if (direction.equals("right")) { nextCell = nextCell.right;
            } else if (direction.equals("top")) { nextCell = nextCell.top;
            } else if (direction.equals("bottom")) { nextCell = nextCell.bottom;
            }
            
            edgeCell = (nextCell != null) ? nextCell : edgeCell;
        }
        
        return edgeCell;
    }
    
    /* From edge cell to right/bottom, clear Mill status */
    private void clearFromMill(Cell edgeCell, String direction) {
        Cell cell = edgeCell;
        
        while (cell != null) {
            if (direction.equals("right")) {
                // Go left-to-right
                cell.clearRowMill();
                cell = cell.right;
            } else if (direction.equals("bottom")) {
                // Go top-to-bottom
                cell.clearColumnMill();
                cell = cell.bottom;
            }
        }
    }

    /*
     * Move a player's mark from one cell to another
     * @param player: player of class Player
     * @param srcCellAddr: source cell coordinate, e.g. A1, C3, F4, etc.
     * @param dstCellAddr: destination cell coordinate, e.g. A1, C3, F4, etc.
     * @return boolean true/false for successful/failed move
     */
    public String moveMark(Player p, String srcAddress, String dstAddress) {
        Cell dstCell = getCell(getRow(dstAddress), getCol(dstAddress));
        Cell srcCell = getCell(getRow(srcAddress), getCol(srcAddress));
        String status = "FAILED";
        
        if ((srcCell != null) && (dstCell != null)) {
            if (p.isOwner(srcCell) && !dstCell.isOccupied()) {
                clearMillFormation(srcCell);    // Clear Mill formation for source
                vacateCell(srcCell);
                
                occupyCell(dstCell, p);         // Update vacant cell list
                clearMillFormation(dstCell);    // Clear Mill formation for destination
                
                status = millStatusCheck(dstCell, p);       // Check Mill formation status
                
            } else if (!p.isOwner(srcCell)) {
                System.out.println("Wrong owner for cell " + srcAddress + "!");
            } else if (dstCell.isOccupied()) {
                System.out.println("Cell " + dstAddress + " is not empty!");
            }
        } else {
            if (srcCell == null) { System.out.println("Cell " + srcAddress + " is invalid!");
            } else if (dstCell == null) { System.out.println("Cell " + dstAddress + " is invalid!");
            } else { System.out.println("MOVE:: Unhandled moveMark() error #1");
            }
        }
        
        return status;
    }
    
    /* Remove referenced cell belonging to player */
    public boolean removeMark(Player p, String cellAddr) {
        Cell c = getCell(getRow(cellAddr), getCol(cellAddr));
        
        if (c.isOccupied() && p.isOwner(c)) {
            clearMillFormation(c);     // Clear mill formations
            //c.setEmpty();
            vacateCell(c);
            p.killMan();           
            setOwnedCellsGroup(p);
            return true;
        }
        return false;
    }
    
    /* Clear mill formation details for a given cell */
    private void clearMillFormation(Cell c) {
        Cell edgeCell = null;
        String millCells = "";
        
        // Check for Row-Mill
        if (millCells.length() == 0) {
            // Check left-to-right if MILL formed on row
            edgeCell = getEdgeCell(c, "left");
            millCells = checkMillFormation(edgeCell, c.owner, "right");
            if (millCells.length() > 0) { clearFromMill(edgeCell, "right"); }
        }
        
        // Check for Column-Mill if Row-Mill is not detected
        if (millCells.length() == 0) {
            // Check top-to-bottom if MILL formed in column
            edgeCell = getEdgeCell(c, "top");
            millCells = checkMillFormation(edgeCell, c.owner, "bottom");
            if (millCells.length() > 0) { clearFromMill(edgeCell, "bottom"); }
        }
    }

    /* Given a player p, get all cells belonging p's opponent */
    public String getOpponentCells(Player p) {
        String cellLabels = "";
        Cell c;
        
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                c = board[i][j];
                
                if ((c == null) || c.isEmpty()) {
                    // Skip all invalid cell iterations
                    continue;
                } else if (!p.isOwner(c) && !c.isInMill()) {
                    // Not yet flying
                    // a1,b1,c3,etc
                    cellLabels = (cellLabels.length() == 0) ? c.label : cellLabels + "," + c.label;
                } else {
                    // Flying
                    //System.out.println("Player-" + p.getName() + " is flying!");
                }
            }
        }
        
        return cellLabels;
    }
    
    /*
     * Given a cell address, get comma-separated list of available neighboring cells
     * into which player's mark at cell-address can move
     */
    public String getVacantNeighbors(String cellAddr) {
        String neighbors = "";
        Cell c = getCell(cellAddr);
        
        if (c != null) {
            if ((c.left != null) && !c.left.isOccupied()) {
                neighbors = (neighbors.length() == 0) ? c.left.label : neighbors + "," + c.left.label;
            }
            if ((c.right != null) && !c.right.isOccupied()) {
                neighbors = (neighbors.length() == 0) ? c.right.label : neighbors + "," + c.right.label;
            }
            if ((c.top != null) && !c.top.isOccupied()) {
                neighbors = (neighbors.length() == 0) ? c.top.label : neighbors + "," + c.top.label;
            }
            if ((c.bottom != null) && !c.bottom.isOccupied()) {
                neighbors = (neighbors.length() == 0) ? c.bottom.label : neighbors + "," + c.bottom.label;
            }
            System.out.println("Board.getVacantNeighbors():: Cell-" + c.label + " has neighbor(s) \"" 
                    + neighbors.replace(",", ", ") + "\"");
        } else {
            System.out.println("Board.getVacantNeighbors():: Cell-? is NULL");
        }
        
        return neighbors;
    }
    
    /*
     * Get all empty/vacant cells on Board
     * - can be used in determining possible Place locations
     * - can be used in determining possible Move locations when a player can fly
     */
    public String getVacantCells() { return String.join(", ", vacantCells); }

    /* Remove an addressed cell from vacant Cells list */
    public void occupyCell(Cell c, Player p) { c.setOccupied(p); vacantCells.remove(c.label); }
    /* Add back an addressed cell to vacant Cells list */
    public void vacateCell(Cell c) { c.setEmpty(); vacantCells.add(c.label); }
    
    
    /* Get all cells belonging to player p */
    public String getOwnedCells(Player p, boolean doPrint) {
        String cellLabels = "";
        Cell c;
        
        if (p != null) {
            cellLabels = p.getOwnedCells();
            
            if (doPrint) System.out.println("Board.getOwnedCells():: Player-" + p.getName() + " owns cell(s): \"" + cellLabels.replace(",", ", ") + "\"");
        } else {
            if (doPrint) System.out.println("Board.getOwnedCells():: Player-? is NULL");
        }
        return cellLabels;
    }

    /* Get non-Mill-formed cells belonging to Player */
    public String getNonMillOwnedCells(Player p, boolean doPrint) {
        String[] cellAddrs = getOwnedCells(p, false).split(",");
        String cellLabels = "";
        Cell c;
        if (p != null ) {
            for (int i = 0; i < cellAddrs.length; i++) {
                if (cellAddrs[i].length() > 0) {
                    c = getCell(cellAddrs[i]);
                    
                    if ((c != null) && !c.isInMill()) {
                        cellLabels = (cellLabels.length() == 0) ? c.label : cellLabels + "," + c.label;
                    }
                }
            }
            if (doPrint) System.out.println("Board.getNonMillOwnedCells():: Player-" + p.getName() + 
                    " has non-Mill cell(s): \"" + cellLabels.replace(",", ", ")+ "\"");
        } else {
            if (doPrint) System.out.println("Board.getNonMillOwnedCells():: Player-? is NULL");
        }
        
        return cellLabels;
    }
    
    /* Get Mill-formed cells belonging to Player */
    public String getMillOwnedCells(Player p, boolean doPrint) {
        String[] ownedCellAddrs = getOwnedCells(p, false).split(",");
        String nonMillCellAddrs = getNonMillOwnedCells(p, false);
        String cellLabels = "";
        
        if (p != null) {
            for (int i = 0; i < ownedCellAddrs.length; i++) {
                if (!nonMillCellAddrs.contains(ownedCellAddrs[i])) {
                    cellLabels = (cellLabels.length() == 0) ? ownedCellAddrs[i] : cellLabels + "," + ownedCellAddrs[i];
                }
            }
            if (doPrint) System.out.println("Board.getMillOwnedCells():: Player-" + p.getName() + 
                    " has Mill cell(s): \"" + cellLabels.replace(",", ", ")+ "\"");
        } else {
            if (doPrint) System.out.println("Board.getMillOwnedCells():: Player-? is NULL");
        }
        return cellLabels;
    }
    
    
    /*************************************************************************************/
    // REFACTORED METHODS
    /*************************************************************************************/
    private String millStatusCheck(Cell c, Player p) {
        String status = "";
        
        // Check if most recent action formed a Mill
        status = getMillCells(c, p);
        // Non-empty delimited string == cells that formed mill;
        if (status.length() == 0){ status = "SUCCESS"; }
        
        return status;
    }
    /* Set a given cell address as belonging to a player
     * @param player: player of class Player
     * @param dstCellAddr: cell coordinate, e.g. A1, C3, F4, etc.
     * @return None
     */
    public String placeMark(Player p, String cellAddr) {
        Cell c = getCell(getRow(cellAddr), getCol(cellAddr));
        String status = "FAILED";
        
        if ( (c != null) && !c.isOccupied() ) {
            occupyCell(c, p);       // Update vacant cell list
            p.doPlace();            // Update Place count
            status = millStatusCheck(c, p);     // Check Mill formation status
            
        } else if (c == null) { System.out.println("Cell " + cellAddr + " is invalid!");
        } else if (c.isOccupied()) { System.out.println("Cell " + cellAddr + " is occupied!");
        } else { System.out.println("PLACE:: Unhandled placeMark() error #1");
        }
        
        return status;
    }
    
    /* Check if a cell is part of a mill; return String of cells belonging to Mill */
    private String checkMillFormation(Cell edgeCell, Player p, String direction) {
        Cell cell = edgeCell;
        ArrayList<String> millCells = new ArrayList<String>();
        
        while((cell != null) && cell.isOccupied() && p.isOwner(cell)) {
            millCells.add(cell.label);
            
            if (direction.equals("right")) {
                // Left-to-right search
                cell = cell.right;
            } else if (direction.equals("bottom")) {
                // Top-to-bottom search
                cell = cell.bottom;
            }
        }

        String millCellsString = "";
        if (millCells.size() < 3) {
            // Didn't form a mill; clear mill status of adjoining cells 
            clearFromMill(edgeCell, direction);
            millCells = new ArrayList<String>();
        } else if (millCells.size() == 3) {
            if (direction.equals("right")) {
                millCellsString = String.join("-", millCells);
                System.out.println("Row MILL was formed with cells \"" + millCellsString + "\"");
            } else if (direction.equals("bottom")) {
                millCellsString = String.join("|", millCells);
                System.out.println("Column MILL was formed with cells \"" + millCellsString + "\"");
            }
            setToMill(edgeCell, direction);
        }
        
        return millCellsString;
    }
    
    /* Check if 3 board cells on the same row form a mill, else
     * Check if 3 board cells in the same column form a mill
     * @param cell: most recently placed cell 
     */
    private String getMillCells(Cell cell, Player p) {
        Cell edgeCell = null;
        String millCells = "";
        
        // Check for Row-Mill
        edgeCell = getEdgeCell(cell, "left");
        // Check left-to-right if MILL formed on row
        millCells = checkMillFormation(edgeCell, p, "right");
        if (millCells.length() > 0) {
            //System.out.println("Row mill check result = \"" + millCells + "\"");
            return millCells;
        }
        
        // Check for Column-Mill
        edgeCell = getEdgeCell(cell, "top");
        // Check top-to-bottom if MILL formed in column
        millCells = checkMillFormation(edgeCell, p, "bottom");
            
        if (millCells.length() > 0) {
            //System.out.println("Column mill check result = \"" + millCells + "\"");
            return millCells;
        }
        
        return "";
    }
    
    /* */
    private void setToMill(Cell edgeCell, String direction) {
        Cell cell = edgeCell;
        
        while (cell != null) {
            if (direction.equals("right")) {
                // Go left-to-right
                cell.setRowMill();
                cell = cell.right;
            } else if (direction.equals("bottom")) {
                // Go top-to-bottom
                cell.setColumnMill();
                cell = cell.bottom;
            }
        }
    }

    public String setOwnedCellsGroup(Player p) {
        Cell c = null;
        String[] ownedCells = p.getOwnedCells().split(", ");
        ArrayList<String> millCells = new ArrayList<String>(); 
        ArrayList<String> nonMillCells = new ArrayList<String>(); 
        
        for (int i = 0; i < ownedCells.length; i++) {
            c = getCell(getRow(ownedCells[i]), getCol(ownedCells[i]));
            
            if (c != null) {
                if (c.isInMill()) { millCells.add(c.label);
                } else { nonMillCells.add(c.label);
                }
            }
        }
        
        p.setMillCells(millCells);
        p.setNonMillCells(nonMillCells);
        return String.join(",", nonMillCells) + "*" + String.join(",", millCells);
    }
    
}
