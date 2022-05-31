package engine;

import java.util.Scanner;

public class Debug {
    static Engine game;
    static Scanner scanner;;
    
    public static void main(String[] args) {
        game = new Engine();
        scanner = new Scanner(System.in);
        
        interractive();
    }
    
    
    public static void fillAll() {
        String [] plays = "a1,d1,g1,b2,d2,f2,c3,d3,e3,a4,b4,c4,e4,f4,g4,c5,d5,e5,b6,d6,f6,a7,d7,g7".split(",");
        for (int i = 0; i < plays.length; i++) {
            game.place(plays[i]);
        }
    }
    
    public static void interractive() {
        fillAll();
        
        /*
        String[] plays = "a1,b2,a4,d2".split(",");
        for (int i = 0; i < plays.length; i++) {
            game.place(plays[i]);
        }
        */
        
        while (game.activePlayer.isPlacing() || game.inActivePlayer.isPlacing()) {
            place();
            if (game.activePlayer.removePending()) { remove(); }
        }
        
        while (game.activePlayer.isMoving() || game.inActivePlayer.isMoving()) {
            move();
            if (game.activePlayer.removePending()) { remove(); }
        }
    }
    
    public static void remove() {
        System.out.print("\n\nREMOVE:: P" + game.activePlayer.getName() + 
                " -> P" + game.activePlayer.opponent.getName() + " (" + String.join(", ",
                game.inActivePlayer.getRemovableCells()) + "): ");
        game.remove(scanner.nextLine());
    }
    
    public static void place() {
        System.out.print("PLACE[P" + game.activePlayer.getName() + "], e.g. a1, b2, e4: ");
        game.place(scanner.nextLine());
    }
    
    public static void move() {
        System.out.print("MOVE [P" + game.activePlayer.getName() + "], e.g. a1-d1, g4-f4: ");
        String input = scanner.nextLine();
        input = input.replaceAll(" ", "").trim();
        String[] addr = input.split("-");
        game.move(addr[0],  addr[1]);
    }
}
