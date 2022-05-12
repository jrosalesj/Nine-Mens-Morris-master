
package juego;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ventana extends JFrame{
    public Ventana(){
        this.setSize(720, 740);         //Establecemos el tamaño de la ventana
        setTitle("JUEGO DEL MOLINO");   //Establecemos el titulo
        setLocationRelativeTo(null);    //Establecemos la ventana en la posicion central
        setResizable(false);            //Establecemos que la ventana no pueda cambiar de tamaño
        
        iniciarComponentes();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void iniciarComponentes(){
        JPanel panel = new JPanel();        //Creamos un panel
        panel.setLayout(null);              //Desactivamos el diseño por defecto
        this.getContentPane().add(panel);   //Agregamos el panel a la ventana
        
        //Etiqueta 1
        ImageIcon imagen1 = new ImageIcon("Tablero.png");
        JLabel etiqueta1= new JLabel();
        etiqueta1.setBounds(0, 0, 700, 700);
        etiqueta1.setIcon(new ImageIcon(imagen1.getImage().getScaledInstance(etiqueta1.getWidth(), etiqueta1.getHeight(), WIDTH)));
        
        panel.add(etiqueta1);
        
        //Etiqueta 2
        ImageIcon imagen2 = new ImageIcon("Madera.png");
        JLabel etiqueta2 = new JLabel();
        etiqueta2.setBounds(0, 0, 700, 700);
        etiqueta2.setIcon(new ImageIcon(imagen2.getImage().getScaledInstance(etiqueta2.getWidth(), etiqueta2.getHeight(), WIDTH)));
        
        panel.add(etiqueta2);
        
        
    }
}
