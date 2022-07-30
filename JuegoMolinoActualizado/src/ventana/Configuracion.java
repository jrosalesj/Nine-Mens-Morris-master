package ventana;

import java.awt.Image;
import java.awt.Toolkit;

public class Configuracion extends javax.swing.JFrame {
    public int optionGame;
    public Configuracion() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    //Cambiando el icono del programa 
    @Override
    public Image getIconImage(){
        Image img = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagenes/logoNMM.png"));
        return img;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonClose = new javax.swing.JButton();
        jLabelTitle = new javax.swing.JLabel();
        jButtonPlayerPc = new javax.swing.JButton();
        jButtonPlayerPlayer = new javax.swing.JButton();
        jLabelPlayerPlayer = new javax.swing.JLabel();
        jLabelPlayerPc = new javax.swing.JLabel();
        jLabelFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFont(new java.awt.Font("Agency FB", 1, 14)); // NOI18N
        setForeground(new java.awt.Color(51, 51, 255));
        setIconImage(getIconImage());
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonClose.setBackground(new java.awt.Color(255, 204, 102));
        jButtonClose.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonClose.setForeground(new java.awt.Color(255, 255, 255));
        jButtonClose.setText("Salir");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 60, 20));

        jLabelTitle.setFont(new java.awt.Font("Viner Hand ITC", 1, 48)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setText("Nine Men's Morris");
        getContentPane().add(jLabelTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        jButtonPlayerPc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/playerVsPc.png"))); // NOI18N
        jButtonPlayerPc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlayerPcActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonPlayerPc, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 220, 180));

        jButtonPlayerPlayer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/playerVsPlayer.png"))); // NOI18N
        jButtonPlayerPlayer.setText("jButton1");
        jButtonPlayerPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlayerPlayerActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonPlayerPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 220, 180));

        jLabelPlayerPlayer.setBackground(new java.awt.Color(255, 51, 51));
        jLabelPlayerPlayer.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelPlayerPlayer.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPlayerPlayer.setText("Player VS Player");
        getContentPane().add(jLabelPlayerPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

        jLabelPlayerPc.setBackground(new java.awt.Color(255, 255, 255));
        jLabelPlayerPc.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelPlayerPc.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPlayerPc.setText("Player VS PC");
        getContentPane().add(jLabelPlayerPc, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, -1, -1));

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/FondoBeige.png"))); // NOI18N
        getContentPane().add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 510, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonPlayerPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlayerPlayerActionPerformed
        optionGame=0;
        UsuarioB usuario1 = new UsuarioB();
        usuario1.setOptionGame(optionGame);
        usuario1.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButtonPlayerPlayerActionPerformed

    private void jButtonPlayerPcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlayerPcActionPerformed
        optionGame=1;
        UsuarioB usuario1 = new UsuarioB();
        usuario1.setOptionGame(optionGame);
        usuario1.setVisible(true);
        this.setVisible(false);// TODO add your handling code here:
    }//GEN-LAST:event_jButtonPlayerPcActionPerformed

    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
      
        java.awt.EventQueue.invokeLater(() -> {
            new Configuracion().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonPlayerPc;
    private javax.swing.JButton jButtonPlayerPlayer;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelPlayerPc;
    private javax.swing.JLabel jLabelPlayerPlayer;
    private javax.swing.JLabel jLabelTitle;
    // End of variables declaration//GEN-END:variables
}
