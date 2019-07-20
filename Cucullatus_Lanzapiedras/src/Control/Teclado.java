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
    private static boolean VANDALIZAR;
    private static boolean PAUSA;
    private static boolean RECOGERPIEDRA;
    private static boolean LANZARFRONTAL;
    private static boolean LANZARARRIBA;
    private static boolean LANZARABAJO;

    public static void keyPressed(KeyCode tecla) {
        
        if (tecla == KeyCode.UP) {
            ARRIBA = true;
        }else if (tecla == KeyCode.DOWN) {
            ABAJO = true;
        }else if (tecla == KeyCode.RIGHT) {
            DERECHA = true;
        }else if (tecla == KeyCode.LEFT) {
            IZQUIERDA = true;
        }else if(tecla == KeyCode.P) {
            PAUSA = true;
        }else if (tecla == KeyCode.SPACE) {
            VANDALIZAR=true;
        }else if(tecla == KeyCode.R) {
            RECOGERPIEDRA = true;
        }else if(tecla == KeyCode.E) {
            LANZARFRONTAL = true;
        }else if(tecla == KeyCode.W) {
            LANZARARRIBA = true;
        }else if(tecla == KeyCode.S) {
            LANZARABAJO = true;
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
        }else if (tecla== KeyCode.SPACE){
            VANDALIZAR=false;
        }else if(tecla==KeyCode.R){
            RECOGERPIEDRA = false;
        }else if(tecla == KeyCode.E) {
            LANZARFRONTAL = false;
        }else if(tecla == KeyCode.W) {
            LANZARARRIBA = false;
        }else if(tecla == KeyCode.S) {
            LANZARABAJO = false;
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
    
    public static boolean isPAUSA() {
        return PAUSA;
    }

    public static void setPAUSA(boolean PAUSA) {
        Teclado.PAUSA = PAUSA;
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

    public static boolean isVANDALIZAR() {
        return VANDALIZAR;
    }

    public static void setVANDALIZAR(boolean VANDALIZAR) {
        Teclado.VANDALIZAR = VANDALIZAR;
    }

    public static boolean isRECOGERPIEDRA() {
        return RECOGERPIEDRA;
    }

    public static void setRECOGERPIEDRA(boolean RECOGERPIEDRA) {
        Teclado.RECOGERPIEDRA = RECOGERPIEDRA;
    }

    public static boolean isLANZARFRONTAL() {
        return LANZARFRONTAL;
    }

    public static void setLANZARFRONTAL(boolean LANZARFRONTAL) {
        Teclado.LANZARFRONTAL = LANZARFRONTAL;
    }

    public static boolean isLANZARARRIBA() {
        return LANZARARRIBA;
    }

    public static void setLANZARARRIBA(boolean LANZARARRIBA) {
        Teclado.LANZARARRIBA = LANZARARRIBA;
    }

    public static boolean isLANZARABAJO() {
        return LANZARABAJO;
    }

    public static void setLANZARABAJO(boolean LANZARABAJO) {
        Teclado.LANZARABAJO = LANZARABAJO;
    }
    
}
