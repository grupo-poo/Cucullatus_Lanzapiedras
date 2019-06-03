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
    
    public Ventana() throws Exception{
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false); // Eliminamos los botones ( - [] X ).
        setExtendedState(MAXIMIZED_BOTH); // Pantalla completa.
        juego = new PanelJuego(getAltoPantalla(), getAnchoPantalla());
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
        while(juego.isEjecutandose()) {
            try {
                juego.repaint();
                Thread.sleep(11);
                
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null,"Error al dormir el hilo");
                Thread.currentThread().interrupt();
            }
        }
    }
    
}