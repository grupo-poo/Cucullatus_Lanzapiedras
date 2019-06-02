package Control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author diegocarvajal
 * @author Milton Lenis
 */
public class Teclado implements KeyListener{
    
    public static boolean arriba;
    public static boolean abajo;
    public static boolean derecha;
    public static boolean izquierda;
    
    @Override
    public void keyTyped(KeyEvent e) {  
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int codTecla = e.getKeyCode();
        
        if (codTecla == KeyEvent.VK_UP) {
            arriba = true;
        }else if (codTecla == KeyEvent.VK_DOWN) {
            abajo = true;
        }else if (codTecla == KeyEvent.VK_RIGHT) {
            derecha = true;
        }else if (codTecla == KeyEvent.VK_LEFT) {
            izquierda = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int codTecla = e.getKeyCode();
     
        if (codTecla == KeyEvent.VK_UP) {
            arriba = false;
        }else if (codTecla == KeyEvent.VK_DOWN) {
            abajo = false;
        }else if (codTecla == KeyEvent.VK_RIGHT) {
            derecha = false;
        }else if (codTecla == KeyEvent.VK_LEFT) {
            izquierda = false;
        }
    }
    
}
