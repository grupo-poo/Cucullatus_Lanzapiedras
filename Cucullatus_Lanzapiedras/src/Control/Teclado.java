package Control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author diegocarvajal
 * @author Milton Lenis
 */
public class Teclado implements KeyListener{
    
    private static boolean ARRIBA;
    private static boolean ABAJO;
    private static boolean DERECHA;
    private static boolean IZQUIERDA;
    
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

    public static boolean isARRIBA() {
        return ARRIBA;
    }

    public static void setARRIBA(boolean ARRIBA) {
        Teclado.ARRIBA = ARRIBA;
    }

    public static boolean isABAJO() {
        return ABAJO;
    }

    public static void setABAJO(boolean ABAJO) {
        Teclado.ABAJO = ABAJO;
    }

    public static boolean isDERECHA() {
        return DERECHA;
    }

    public static void setDERECHA(boolean DERECHA) {
        Teclado.DERECHA = DERECHA;
    }

    public static boolean isIZQUIERDA() {
        return IZQUIERDA;
    }

    public static void setIZQUIERDA(boolean IZQUIERDA) {
        Teclado.IZQUIERDA = IZQUIERDA;
    }
}
