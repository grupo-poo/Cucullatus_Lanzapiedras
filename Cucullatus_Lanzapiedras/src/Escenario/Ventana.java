package Escenario;

import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * Crea la ventana del juego.
 * @author Milton Lenis
 */
public class Ventana extends JFrame{
    
    //private TableroJuego lienzo;
    
    public Ventana() throws IOException{
         /*Cuando cerramos una ventana el programa continua ejecutandose,
         * para detenerlo tenemos que hacerlo manualmente. Con el metodo
         * setDefaultCloseOperation el programa se detiene cuando se
         * cierra la ventana.
         */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false); // Eliminamos los botones ( - [] X ).
        setExtendedState(MAXIMIZED_BOTH); // Pantalla completa.
        setBackground(Color.getHSBColor(0.541f , 0.40f, 0.85f));
        setVisible(true);
    }
    

    

}
