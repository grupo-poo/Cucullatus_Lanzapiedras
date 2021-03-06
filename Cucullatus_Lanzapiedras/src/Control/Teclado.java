package Control;

import javafx.scene.input.KeyCode;

/**
 * @author diegocarvajal
 * @author Milton Lenis
 */
public class Teclado {
    
    private static boolean ARRIBA;
    private static boolean ABAJO;
    private static boolean DERECHA;
    private static boolean IZQUIERDA;

    public static void keyPressed(KeyCode tecla) {
        
        if (tecla == KeyCode.UP) {
            ARRIBA = true;
        }else if (tecla == KeyCode.DOWN) {
            ABAJO = true;
        }else if (tecla == KeyCode.RIGHT) {
            DERECHA = true;
        }else if (tecla == KeyCode.LEFT) {
            IZQUIERDA = true;
        }
    }

    public static void keyReleased(KeyCode tecla) {
     
        if (tecla == KeyCode.UP) {
            ARRIBA = false;
        }else if (tecla == KeyCode.DOWN) {
            ABAJO = false;
        }else if (tecla == KeyCode.RIGHT) {
            DERECHA = false;
        }else if (tecla == KeyCode.LEFT) {
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
