package Control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author diegocarvajal
 * @author Milton Lenis
 */
public class Teclado implements KeyListener{
    
    public static boolean ARRIBA;
    public static boolean ABAJO;
    public static boolean DERECHA;
    public static boolean IZQUIERDA;
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int codTecla = e.getKeyCode();
        
        if (codTecla == KeyEvent.VK_UP) {
            ARRIBA = true;
        }else if (codTecla == KeyEvent.VK_DOWN) {
            ABAJO = true;
        }else if (codTecla == KeyEvent.VK_RIGHT) {
            DERECHA = true;
        }else if (codTecla == KeyEvent.VK_LEFT) {
            IZQUIERDA = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int codTecla = e.getKeyCode();
     
        if (codTecla == KeyEvent.VK_UP) {
            ARRIBA = false;
        }else if (codTecla == KeyEvent.VK_DOWN) {
            ABAJO = false;
        }else if (codTecla == KeyEvent.VK_RIGHT) {
            DERECHA = false;
        }else if (codTecla == KeyEvent.VK_LEFT) {
            IZQUIERDA = false;
        }
    }
    
}
