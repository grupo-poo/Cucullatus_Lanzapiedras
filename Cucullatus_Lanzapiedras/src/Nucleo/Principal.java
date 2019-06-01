package Nucleo;

import javax.swing.JOptionPane;

/**
 * @author Nicolas Hoyos
 * @author Nicolas Mejia
 * @author Milton Lenis
 * @author Diego Carvajal
 */
public class Principal {
    
    public static void main(String[] args) throws Exception {
        try {
            new Ventana();
            
        } catch (Exception ex) {
            if (ex.getMessage() == null) {
                JOptionPane.showMessageDialog(null,"A ocurrido un error");
            } else {
                JOptionPane.showMessageDialog(null,ex.getMessage());
            }
        }
    }
}
