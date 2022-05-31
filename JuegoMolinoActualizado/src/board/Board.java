package board;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int DIMENSION = 7;
    private Cell [][] board;

 // enumeraci�n p�blica PlaceOrMoveStates { NO INICIALIZADO, FALLIDO, �XITO, FORMEDMILL }
     //placeOrMoveStates privado boardStatus = PlaceOrMoveStates.UNINITIALIZED;
    
    /* Se imprime las vareiables desde la consola */
    boolean colIsNumeric = false;
    boolean rowIsNumeric = !colIsNumeric;
    private boolean ROWADDRFIRST = false;
    private String LABELS = "ABCDEFG1234567abcdefg";
    
    private ArrayList<String> vacantCells = null;
    
    
    /*
     * Inicializacion del tablero,inicializa la celda X-by-Y
     * Luego se configura los enlaces de celdad basados en la matriz de adyacencia adjunto
     */
    public Board() {
        board = new Cell[DIMENSION][DIMENSION];
        vacantCells = new ArrayList<String>();
        setNeighbors();
    }
    
    /*
     *Se configura los cadilleros adjuntos :izquierdo ,derecho ,superior e inferior de cada celda 
     en un taablero de juego de Nine_Men-Morris - se observa la matriz de aduyacencia para las celdas.
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
            currCell.label = cellMap[index];    //  la cadena
            
            currCell.left = createCell(adjacency[index][1][0], adjacency[index][1][1]);
            currCell.right = createCell(adjacency[index][2][0], adjacency[index][2][1]);
            currCell.top = createCell(adjacency[index][3][0], adjacency[index][3][1]);
            currCell.bottom = createCell(adjacency[index][4][0], adjacency[index][4][1]);
            
            vacantCells.add(cellMap[index]);    // Actualiza celdas vacantes
        }
    }
    
    /*
     * Al ingresar una direccion de una celda con tipo A1,A2,D4,E5,rtc,determina
     * la posicion de fila y columna del tablero de la direccion de la celda
     * @param cellAddress: direccion de celda de cadena de 2 caracteres
     * @param rowOrCol: cadenas : fila o columna
     * @return integer: tablero matriz de celda  fila/columna
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
     *Dada una direccion de celda de tipo A1,A2,D4,E5,etc
     *determina y devuelve la posicion fila del tablero de la direccion de celda.en el segundo caracter
     * @param cellAddress: direccion de la celda
     * @return integer: tablero matriz de celdas fila
     */
    private int getRow(String cellAddress) { return getIntIndex(cellAddress, "row"); }
    
    /*
     * 
          Dada una direcci�n de celda de tipo A1, A2, D4, E5, etc., determine y
     * devolver la posici�n de la columna del tablero de la direcci�n de la celda (1er car�cter)

     * @param cellAddress: direccion de la celda
     * @return integer: tablero matriz de celda columna
     */
    private int getCol(String cellAddress) { return getIntIndex(cellAddress, "col"); }
    
    /* Adjust an row-/col-index para una indexaci�n base-0 adecuada en la matriz del tablero */
    private int adjustIndex(int index) { return index - 1; }
    
    /*Crear una celda en la fila y columna del tablero
     * @param boardRow:tipo entero en la fila row# (base-1)
     * @param boardCol: tipo columna en la columna (base-1)
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
    
    /* comprobar si las coordenadas de fila y columna son validas */
    private boolean isValidCellAddr(int boardRow, int boardCol) {
        return (boardRow > 0) && (boardCol > 0);
    }

    /* Imprime los encabezados de la columna del tablero */
    public void printColumnHeader(int spacer) {
        String str = " ";
        int chOffset = colIsNumeric ? DIMENSION : 0;
        
        // Imprime encabezados de columna
        for (int i = 0; i < DIMENSION; i++) {
            str += String.format("%s%s", repeatChar(' ', spacer), 
                        Character.toString(LABELS.charAt(i + chOffset)));
        }
        System.out.println(str);
    }
    
    /*Impresi�n de placa basada en consola para depuraci�n sin interfaz gr�fica de usuario */
    public void printBoard() {
        int numSpacer = 1;
        int rowChOffset = rowIsNumeric ? DIMENSION : 0;
        printColumnHeader(numSpacer);
        
        String str = "";
        //Imprime envabezados de las columnas
        for (int i = 0; i < DIMENSION; i++) {
            str += String.format("%s%s", Character.toString(LABELS.charAt(i + rowChOffset)), 
                        repeatChar(' ', numSpacer));
            
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
     * Dada una direcci�n de fila y columna basada en 1, obtenga la celda del tablero
     * referenciado por los par�metros de fila y columna
     * @param boardRow: fila tipo fila row# (base-1)
     * @param boardCol: columna tipo entero column# (base-1)
     * @return: referencia de una celda del tablero
     */
    public Cell getCell(int boardRow, int boardCol) {
        Cell cell = null;
        
        if (isValidCellAddr(boardRow, boardCol)) {
            cell = board[adjustIndex(boardRow)][adjustIndex(boardCol)];
        }
        return cell;
    }
    
    public Cell getCell(String cellAddr) { return getCell(getRow(cellAddr), getCol(cellAddr));}
    
    /* �til para la impresi�n en consola de un personaje un cierto n�mero de veces */
    private static final String repeatChar(char c, int length) {
        char[] data = new char[length];
        Arrays.fill(data, c);
        return new String(data);
    }
    

    /*
     *Busque en las celdas la direcci�n izquierda/derecha/arriba/abajo y devuelva el
     * celda de borde en la direcci�n de b�squeda
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

    /*Borra la celda deFrom edge cell to right/bottom, clear Mill status */
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
     * mover la marca de un jugador de una celda a otra
     * @param player: jugador de clase jugador
     * @param srcCellAddr: coordenada de la celda de origen, e.g. A1, C3, F4, etc.
     * @param dstCellAddr: coordenada de la celda de destino, e.g. A1, C3, F4, etc.
     * @return booleano de verdad o falso para movimiento exitoso o fallido
     */
    public String moveMark(Player p, String srcAddress, String dstAddress) {
        Cell dstCell = getCell(getRow(dstAddress), getCol(dstAddress));
        Cell srcCell = getCell(getRow(srcAddress), getCol(srcAddress));
        String status = "FAILED";
        
        if ((srcCell != null) && (dstCell != null)) {
            if (p.isOwner(srcCell) && !dstCell.isOccupied()) {
                clearMillFormation(srcCell);    // 
                vacateCell(srcCell);
                
                occupyCell(dstCell, p);         //Actualiza lista de celdas vacantes
                clearMillFormation(dstCell);    // Limpia la formacion del molino para el destino
                
                status = millStatusCheck(dstCell, p);       // vefificando el estado de formacion del molino
                
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
    
    /* elimina una celda se�alada que pertenece al jugador */
    public boolean removeMark(Player p, String cellAddr) {
        Cell c = getCell(getRow(cellAddr), getCol(cellAddr));
        
        if (c.isOccupied() && p.isOwner(c)) {
            clearMillFormation(c);     //Limpia las formaciones de molinos
            //c.setEmpty();
            vacateCell(c);
            p.killMan();           
            setOwnedCellsGroup(p);
            return true;
        }
        return false;
    }
    
    /* Limpia restos de la formacio de molino para una celda dada */
    private void clearMillFormation(Cell c) {
        Cell edgeCell = null;
        String millCells = "";
        
        //chequea si se formo molino en una fila
        if (millCells.length() == 0) {
            // marca de izquierda a derecha si se form� molino en la fila
            edgeCell = getEdgeCell(c, "left");
            millCells = checkMillFormation(edgeCell, c.owner, "right");
            if (millCells.length() > 0) { clearFromMill(edgeCell, "right"); }
        }
        
        //comprobar si no se formo molino en la columna,al no detectarse en la fila
        if (millCells.length() == 0) {
            //Verifica de arriba a abajo si se formo molino en la columna
            edgeCell = getEdgeCell(c, "top");
            millCells = checkMillFormation(edgeCell, c.owner, "bottom");
            if (millCells.length() > 0) { clearFromMill(edgeCell, "bottom"); }
        }
    }

    /*averigua si un jugador P consigue todas las fichas que pertenecen al oponente de p */
    public String getOpponentCells(Player p) {
        String cellLabels = "";
        Cell c;
        
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                c = board[i][j];
                
                if ((c == null) || c.isEmpty()) {
                    // omitir todas las iteraciones de celdas no validas
                    continue;
                } else if (!p.isOwner(c) && !c.isInMill()) {
                    // aun no vuela
                    // a1,b1,c3,etc
                    cellLabels = (cellLabels.length() == 0) ? c.label : cellLabels + "," + c.label;
                } else {
                    // volador
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
     * Obtenga todas las celdas vac�as / vacantes a bordo
   
     * - se puede usar para determinar posibles ubicaciones cuando un jugador puede volar
     */
    public String getVacantCells() { return String.join(", ", vacantCells); }

    /* Eliminar una celda con direcci�n de la lista de celdas vacantes */
    public void occupyCell(Cell c, Player p) { c.setOccupied(p); vacantCells.remove(c.label); }
    /* vuelva a agregar una celda con direccion a la lista de celda vacante*/
    public void vacateCell(Cell c) { c.setEmpty(); vacantCells.add(c.label); }
    
    
    
    /* consigue todas las celdas que pertenecen a la jugadora. p */
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

    /* obtenga ce */
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
    
    /*obtener celdas por molinos alcanzados por el jugador */
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
    
    
  
    
    private String millStatusCheck(Cell c, Player p) {
        String status = "";
        
        // comprobar si la accion mas reciente formo un Molino
        status = getMillCells(c, p);
        // Non-empty delimited string == cells that formed mill;
        if (status.length() == 0){ status = "SUCCESS"; }
        
        return status;
    }
    /* Establezca una direccion de celda dada como perteneciente a un jugador
     * @param player:Jugadora de clase
     * @param dstCellAddr: coordenada de celda, e.g. A1, C3, F4, etc.
     * @return None
     */
    public String placeMark(Player p, String cellAddr) {
        Cell c = getCell(getRow(cellAddr), getCol(cellAddr));
        String status = "FAILED";
        
        if ( (c != null) && !c.isOccupied() ) {
            occupyCell(c, p);       // actualizar la lista de celdas vancantes
            p.doPlace();            // Actualizar recuento de lugares
            status = millStatusCheck(c, p);     // Check Mill formation status
            
        } else if (c == null) { System.out.println("Cell " + cellAddr + " is invalid!");
        } else if (c.isOccupied()) { System.out.println("Cell " + cellAddr + " is occupied!");
        } else { System.out.println("PLACE:: Unhandled placeMark() error #1");
        }
        
        return status;
    }
    
    /* Comprobar si una celda es parte de un molino :return cadena de celdas pertenecientes a molino */
    private String checkMillFormation(Cell edgeCell, Player p, String direction) {
        Cell cell = edgeCell;
        ArrayList<String> millCells = new ArrayList<String>();
        
        while((cell != null) && cell.isOccupied() && p.isOwner(cell)) {
            millCells.add(cell.label);
            
            if (direction.equals("right")) {
                //Busqueda de izquierda a derecha
                cell = cell.right;
            } else if (direction.equals("bottom")) {
                // busqueda de arriba a abajo
                cell = cell.bottom;
            }
        }

        String millCellsString = "";
        if (millCells.size() < 3) {
            // No formo un molino ;borrar el estado del molino de las celdad contiguas
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
    
    /*Compruebe si 3 celdas de tablero en la misma fila forman un molino, de lo contrario
     * Compruebe si 3 celdas de tablero en la misma columna forman un molino
     * @param cell: celda colocada m�s recientemente
     */
    private String getMillCells(Cell cell, Player p) {
        Cell edgeCell = null;
        String millCells = "";
        
        // compruebe si hay molino en una fila
        edgeCell = getEdgeCell(cell, "left");
        // Marque de izquierda a derecha si se form� MILL en la fila
        millCells = checkMillFormation(edgeCell, p, "right");
        if (millCells.length() > 0) {
            //System.out.println("Row mill check result = \"" + millCells + "\"");
            return millCells;
        }
        
        // chequeando molino fia
        edgeCell = getEdgeCell(cell, "top");
        //
       // Verifique de arriba a abajo si se form� MILL en la columna
        millCells = checkMillFormation(edgeCell, p, "bottom");
            
        if (millCells.length() > 0) {
            //System.out.println("chequeando el resultado de molino en fila = \"" + millCells + "\"");
            return millCells;
        }
        
        return "";
    }
    
    /* */
    private void setToMill(Cell edgeCell, String direction) {
        Cell cell = edgeCell;
        
        while (cell != null) {
            if (direction.equals("right")) {
                //ir de izquierda a derecha
                cell.setRowMill();
                cell = cell.right;
            } else if (direction.equals("bottom")) {
                //ir de arriba abajo
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
