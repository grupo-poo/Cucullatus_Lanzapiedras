package Nucleo;

import Control.Teclado;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Milton Lenis
 */
public class Ventana extends JFrame implements Runnable{
    
    private PanelJuego juego;
    private boolean ejecutandose;
    
    public Ventana() throws Exception{
         /*Cuando cerramos una ventana el programa continua ejecutandose,
         * para detenerlo tenemos que hacerlo manualmente. Con el metodo
         * setDefaultCloseOperation el programa se detiene cuando se
         * cierra la ventana.
         */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false); // Eliminamos los botones ( - [] X ).
        setExtendedState(MAXIMIZED_BOTH); // Pantalla completa.
        juego = new PanelJuego(getAltoPantalla(), getAnchoPantalla());
        ejecutandose = juego.isEjecutandose();
        add(juego);
        addKeyListener(new Teclado());
        setVisible(true);
        
        // Creamos nuestro hilo.
        Thread animacion = new Thread(this);
        animacion.start();
    }
    
    public int getAltoPantalla() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        return (int) dim.getHeight();
    }
    
    public int getAnchoPantalla() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        return (int) dim.getWidth();
    }

    @Override
    public void run() {
        while(ejecutandose) {
            try {
                juego.repaint();
                Thread.sleep(30);
                
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null,"Error con "
                                                 + "la velocidad del hilo");
            }
        }
    }
    
}