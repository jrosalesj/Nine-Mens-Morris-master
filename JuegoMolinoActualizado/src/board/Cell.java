package board;

public class Cell {
    public String label = "xx";

    public boolean rowMill;
    public boolean columnMill;
    
    public Player owner = null;
    
    public Cell left = null;
    public Cell right = null;
    public Cell top = null;
    public Cell bottom = null;
    
    /* Constructor */
    //cell----celda
    public Cell() { setEmpty(); }
    
    /* Se utiliza en la impresión a la consola */
    public String getStateChar() {
        String stateStr;
        
        if (isEmpty()) { stateStr = "-";
        } else if (isOccupied()) { stateStr = owner.getMark();
        } else { stateStr = " ";
        }
        
        return stateStr;
    }
    
    public void clearRowMill() { rowMill = false; }
    public void clearColumnMill() { columnMill = false; }
    public void setRowMill() { rowMill = true; }
    public void setColumnMill() { columnMill = true; }
    public boolean isInMill() { return (rowMill || columnMill); }
    
    /*Verifica si la celda no está ocupada por ninguna jugadora */
    public boolean isEmpty() { return (owner == null); }
    /* borrar la propiedad de una celda */
    public void setEmpty() { if (!isEmpty()) { owner.removeOwnedCell(label); owner = null; } }
    
    /*Comprobar si la celda está ocupada por algún jugador */
    public boolean isOccupied() { return (!isEmpty()); }
    /* establecer la celda como ocupada por el jugador */
    public void setOccupied(Player p) { if (isEmpty()) { p.setOwner(this); owner = p; } }
    

    public boolean matchOwner(Player p) {
        return ((p != null) && (owner != null) && 
                (owner.getName().equals(p.getName()))
                );
    }
    
    /* Compruebe si Cell tiene algún vecino abierto (incluidos los vecinos del molino) */
    public boolean hasOpenNeighbor() {
        boolean status = false;
        
        status |= (left != null) && left.isEmpty();
        status |= (right != null) && right.isEmpty();
        status |= (top != null) && top.isEmpty();
        status |= (bottom != null) && bottom.isEmpty();
        
        return status;
    }
    
    /* compruebe si la celda tiene una celda vacia que no es de molino */
    public boolean hasOpenNonMillNeighbor() {
        boolean status = false;
        
        status |= (left != null) && left.isEmpty() && !left.isInMill();
        status |= (right != null) && right.isEmpty() && !right.isInMill();
        status |= (top != null) && top.isEmpty() && !top.isInMill();
        status |= (bottom != null) && bottom.isEmpty() && !bottom.isInMill();
        
        return status;
    }
}
