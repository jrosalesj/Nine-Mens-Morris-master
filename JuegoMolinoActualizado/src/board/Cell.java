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
    public Cell() { setEmpty(); }
    
    /* Used in printing to console */
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
    
    /* Check if cell is not occupied by any player */
    public boolean isEmpty() { return (owner == null); }
    /* Clear ownership of a cell */
    public void setEmpty() { if (!isEmpty()) { owner.removeOwnedCell(label); owner = null; } }
    
    /* Check if cell is occupied by any player */
    public boolean isOccupied() { return (!isEmpty()); }
    /* Set cell as occupied by Player */
    public void setOccupied(Player p) { if (isEmpty()) { p.setOwner(this); owner = p; } }
    

    public boolean matchOwner(Player p) {
        return ((p != null) && (owner != null) && (owner.getName().equals(p.getName())));
    }
    
    /* Check if Cell has any open neighbor (including mill neighbors) */
    public boolean hasOpenNeighbor() {
        boolean status = false;
        
        status |= (left != null) && left.isEmpty();
        status |= (right != null) && right.isEmpty();
        status |= (top != null) && top.isEmpty();
        status |= (bottom != null) && bottom.isEmpty();
        
        return status;
    }
    
    /* Check if Cell has an open non-mill neighbor */
    public boolean hasOpenNonMillNeighbor() {
        boolean status = false;
        
        status |= (left != null) && left.isEmpty() && !left.isInMill();
        status |= (right != null) && right.isEmpty() && !right.isInMill();
        status |= (top != null) && top.isEmpty() && !top.isInMill();
        status |= (bottom != null) && bottom.isEmpty() && !bottom.isInMill();
        
        return status;
    }
}
