
package ventana;

public class UsuarioB extends javax.swing.JFrame {

    public UsuarioB() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitulo = new javax.swing.JLabel();
        jButtonClose = new javax.swing.JButton();
        jLabelJugador = new javax.swing.JLabel();
        jLabelColor = new javax.swing.JLabel();
        jLabelNombre = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jButtonContinuar = new javax.swing.JButton();
        jLabelColorFicha = new javax.swing.JLabel();
        jLabelFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitulo.setText("Ingresando datos");
        getContentPane().add(jLabelTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, -1, 40));

        jButtonClose.setBackground(new java.awt.Color(255, 204, 102));
        jButtonClose.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonClose.setForeground(new java.awt.Color(255, 255, 255));
        jButtonClose.setText("Salir");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, -1, -1));

        jLabelJugador.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelJugador.setForeground(new java.awt.Color(255, 255, 255));
        jLabelJugador.setText("Jugador P1");
        getContentPane().add(jLabelJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, -1, 40));

        jLabelColor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelColor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/userBlanco.png"))); // NOI18N
        getContentPane().add(jLabelColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 150, 150));

        jLabelNombre.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelNombre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre.setText("Nombre : ");
        getContentPane().add(jLabelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        jTextFieldNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        getContentPane().add(jTextFieldNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 302, 200, 30));

        jButtonContinuar.setBackground(new java.awt.Color(255, 204, 102));
        jButtonContinuar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonContinuar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonContinuar.setText("Continuar");
        jButtonContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonContinuarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonContinuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 380, -1, -1));

        jLabelColorFicha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelColorFicha.setForeground(new java.awt.Color(255, 255, 255));
        jLabelColorFicha.setText("Color de ficha: Blanco");
        getContentPane().add(jLabelColorFicha, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 150, 20));

        jLabelFondo.setBackground(new java.awt.Color(255, 0, 0));
        jLabelFondo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/FondoBeige.png"))); // NOI18N
        getContentPane().add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 526, 419));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonContinuarActionPerformed
        UsuarioN usuario2 = new UsuarioN();
        usuario2.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButtonContinuarActionPerformed

    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UsuarioB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsuarioB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsuarioB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsuarioB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UsuarioB().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonContinuar;
    private javax.swing.JLabel jLabelColor;
    private javax.swing.JLabel jLabelColorFicha;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelJugador;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JTextField jTextFieldNombre;
    // End of variables declaration//GEN-END:variables
}
