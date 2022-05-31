package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;
import javax.swing.JPanel;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;


import engine.Engine;

public class MainGUI extends JFrame {
    private static final long serialVersionUID = -8557749141800673771L;
    private BoardGUI boardPanel;
    private JPanel bottomBar;
    private JButton startGameButton;
    private JLabel turnsStatus;
    private JLabel modeStatus;
    private final String[] messageStrings = {"Jugador"};

    JComboBox cmbMessageList = new JComboBox(messageStrings);
    
    @SuppressWarnings("serial")
	public MainGUI() {
        super("Nine Men's Morris");
        this.setExtendedState(MAXIMIZED_BOTH);
        boardPanel = new BoardGUI();
        add(boardPanel, BorderLayout.CENTER);
        
        bottomBar = new JPanel(){
        	@Override
        	protected void paintComponent(Graphics g){
        		super.paintComponent(g);

        		switch (boardPanel.gameEngine.activePlayer.getCurrentPlayState()) {
        		case GAMEOVER: 
        			if (boardPanel.gameEngine.p2.hasLost()) {
        				modeStatus.setForeground(Color.GREEN);
        				modeStatus.setText("Juego Terminado - El blanco gana!!!");
        				turnsStatus.setText("");
        			} else {
        				modeStatus.setForeground(Color.BLUE);
        				modeStatus.setText("Juego terminado - El negro gana!!!");
        				turnsStatus.setText("");
        			} 
        			break;
        		case FLYING: 
        			modeStatus.setForeground(Color.GREEN);
        			modeStatus.setText("Volando!!!!!");
        			break;
        		case MOVING:
        			modeStatus.setForeground(Color.RED);
        			modeStatus.setText("Modo: Movimiento");
        			break;
        		case REMOVING:
        			modeStatus.setForeground(Color.BLACK);
        			modeStatus.setText("Molino formado! Eliminar ficha");
        			break;
        		case PLACING:
        			modeStatus.setForeground(Color.BLUE);
        			modeStatus.setText("Modo: Colocacion");
        			break;
        		case NOTSTARTED:
        			break;
        		default:
        		}
        		
        		if ( !boardPanel.gameEngine.gameOver() && boardPanel.gameEngine.getActivePlayer() == boardPanel.gameEngine.p1) {
        			turnsStatus.setText("Turno: Blanco");
        		} else if ( !boardPanel.gameEngine.gameOver() ) {
        			turnsStatus.setText("Turno: Negro");
        		}
        		
        		repaint();
        	}
        	
        };
        
        bottomBar.setLayout(new FlowLayout(FlowLayout.LEADING, 30, 1));
        
        
        startGameButton = new JButton("Juego Nuevo");
        startGameButton.setPreferredSize(new Dimension(250,40));
        startGameButton.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                boardPanel.setEngine(new Engine());
            }
        });
        cmbMessageList.setPreferredSize(new Dimension(190, 30));
        cmbMessageList.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 25));
        cmbMessageList.addActionListener(new ActionListener() {
        	

			@Override
        	public void actionPerformed(ActionEvent e) {
        		if (e.getSource() == cmbMessageList) {
        			JComboBox cb = (JComboBox) e.getSource();
        			String msg = (String) cb.getSelectedItem();
        			switch (msg) {
        			
        			case "Jugador": boardPanel.isAIMode = 0;
        			break;
        			default: boardPanel.isAIMode = 0;
        			}
        		}
        	}
        });

        turnsStatus = new JLabel("");
        modeStatus = new JLabel("");
		turnsStatus.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
		modeStatus.setFont(new Font(modeStatus.getFont().getName(), Font.PLAIN, 30));
		turnsStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		modeStatus.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		bottomBar.add(startGameButton);
		bottomBar.add(turnsStatus);
		bottomBar.add(modeStatus);
		bottomBar.add(cmbMessageList); 
        add(bottomBar, BorderLayout.SOUTH);
    }
    

    public static void main(String[] args) {
        JFrame game = new MainGUI();
        game.setSize(1100, 700);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);
        game.setLocationRelativeTo(null);
 
    }
}
