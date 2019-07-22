package Personajes;

import Escenario.ObjetoEscenario;
import Escenario.ObjetoInerte;
import Escenario.ObjetoRecogible;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Milton Lenis
 */
public abstract class Personaje extends ObjetoEscenario{
    
    protected boolean muerto; // Estado de permanencia en el juego del personaje.
    
    protected ObjetoRecogible piedra; // Elemento que el personaje puede lanzar.
    
    protected boolean direccion; // Si es true el jugador mira a la derecha.
    
    // Direcciones a las que la piedra es lanzada.
    protected boolean piedraArriba;
    protected boolean piedraAbajo;
    protected boolean piedraDerecha;
    protected boolean piedraIzquierda;
    
    // Auxiliares para la gravedad.
    protected int countAuxForGravity = 0; // Contador auxiliar para la gravedad.
    protected int aceleracion = -1; // cambio de la velocidad con respecto a cada frame.
    protected int velocidadVertical; // pixeles que recorre verticalmente en tiempo real
    
    @Override
    public abstract void dibujar(GraphicsContext lapiz);
    
    /**
     * Este metodo cambia los siguientes atributos:
     * - countAuxForGravity
     * - velocidadVertical
     * 
     * Hace que el personaje caiga con aceleración hasta posarse sobre alguna
     * base.
     * 
     * No se puede invocar este metodo más de una vez dentro de
     * ningún metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @return Devuelve true si el jugador intercepta con algo debajo de él.
     */
    protected abstract boolean gravedad(ObjetoInerte[] obstaculos);
    
    /**
     * Este metodo cambia los siguientes atributos:
     * - aceleracion
     * - velocidadVertical
     * 
     * Hace que el jugador salte con desaceleracion hasta cierto punto.
     * Si choca con algún objeto arriba de él la desaceleración es total.
     * 
     * No se puede invocar este metodo más de una vez dentro de
     * ningún metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @param alturaDeSalto que tan alto subirá el personaje al saltar.
     */
    protected abstract void saltar(ObjetoInerte[] obstaculos, int alturaDeSalto);
    
    /**
     * @param piedra
     * @return true si la piedra se ha salido del escenario
     */
    protected abstract boolean isFueraDeEscenario(ObjetoRecogible piedra);
    
    protected void crearPiedra() {
        if (piedra == null) {
            Image imagen = new Image("Nucleo/Recursos/Piedra/Piedra.png");
            piedra = new ObjetoRecogible(imagen);
            piedra.setX(x);
            piedra.setY(y + 20);
        }
    }
    
    protected void eliminarPiedraSiSaleDeEscenario() {
        if (piedra != null) {
            if (isFueraDeEscenario(piedra)) {
                abortarLanzamiento();
                piedra = null;
            }
        }
    }
    
    protected void abortarLanzamiento() {
        piedraDerecha = false;
        piedraIzquierda = false;
    }

    public boolean isMuerto() {
        return muerto;
    }

    public void setMuerto(boolean muerto) {
        this.muerto = muerto;
    }

    public boolean isDireccion() {
        return direccion;
    }

    public void setDireccion(boolean direccion) {
        this.direccion = direccion;
    }
    
}
