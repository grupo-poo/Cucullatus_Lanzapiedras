package Personajes;

import Escenario.ObjetoInerte;
import Escenario.ObjetoRecogible;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * @author diegocarvajal
 * @author Milton Lenis
 */
public class Enemigo extends Personaje{
    
    private final int anchoPantalla;
    
    private boolean moverse;
    private boolean animacion1;
    private boolean animacion2;
    
    private boolean lanzarPiedra;

    public Enemigo(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.imagen=new Image("Nucleo/Recursos/Enemigo/Enemigo.png");
        this.ancho = 45;
        this.alto = 85;
    }
    
    public void actualizar(Jugador jugador, ObjetoInerte[] obstaculos) {
        if (jugador.isDistanciaCritica()) {
            x -= jugador.getVelocidadHorizontal();
        }
        controlAnimacion();
        movimiento(obstaculos);
        if (lanzarPiedra) {
            lanzarPiedra(obstaculos, jugador);
            abatir(jugador);
        }
    }
    
   @Override
    public void dibujar(GraphicsContext lapiz) {
        if(isVisible()){
            lapiz.drawImage(imagen, x, y, ancho, alto);
        }
        if (piedra != null) {
            piedra.dibujar(lapiz);
        }
    }
    
    private void controlAnimacion() {
        moverse = !(x > anchoPantalla + 190 || x + ancho < -190);
    }
    
    private void movimiento(ObjetoInerte[] obstaculos) {
        animacion1(obstaculos);
        animacion2(obstaculos);
        //animacion3...
    }
    
    private void abatir(Jugador jugador) {
        if (piedra != null) {
            if (jugador.getRectangulo().intersects(piedra.getRectangulo().getBoundsInLocal())) {
                jugador.setMuerto(true);
                piedra = null;
                abortarLanzamiento();
            }
        }
    }
    
    private void animacion1(ObjetoInerte[] obstaculos) {
        if (animacion1 && moverse && !muerto) {
            if (direccion) {
                direccion = desplazarseDerecha(obstaculos, 3);
            } else {
                direccion = !desplazarseIzquierda(obstaculos, 3);
            }
        }
    }
    
    private void animacion2(ObjetoInerte[] obstaculos) {
        if (animacion2 && moverse && !muerto) {
            if (gravedad(obstaculos)) {
                aceleracion = 0;
            }
            saltar(obstaculos, 5);
        }
    }
    
    public boolean desplazarseDerecha(ObjetoInerte[] obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setX(x + velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    x = obs.getX() - ancho;
                }
            }
        }
        if (viaLibre) { x += velocidad; }
        return viaLibre;
    }
    
    public boolean desplazarseIzquierda(ObjetoInerte[] obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setX(x - velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    x = obs.getX() + obs.getAncho();
                }
            }
        }
        if (viaLibre) { x -= velocidad; }
        return viaLibre;
    }
    
    public boolean desplazarseArriba(ObjetoInerte[] obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setY(y - velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() + obs.getAlto();
                }
            }
        }
        if (viaLibre) { y -= velocidad; }
        return viaLibre;
    }
    
    public boolean desplazarseAbajo(ObjetoInerte[] obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setY(y + velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() - alto;
                }
            }
        }
        if (viaLibre) { y += velocidad; }
        return viaLibre;
    }
    
    private void lanzarPiedra(ObjetoInerte[] obstaculos, Jugador jugador) {
        if ((!muerto && isVisible()) || piedra != null) {
            if (direccion) {
                if (!(piedraIzquierda)) {
                    piedraDerecha = true;
                    crearPiedra();
                }
            } else {
                if (!(piedraDerecha)) {
                    piedraIzquierda = true;
                    crearPiedra();
                }
            }
            desplazarPiedraDerecha(obstaculos, jugador);
            desplazarPiedraIzquierda(obstaculos, jugador);
            eliminarPiedraSiSaleDeEscenario();
        }
    }
    
    private void desplazarPiedraDerecha(ObjetoInerte[] obstaculos, Jugador jugador) {
        if (piedraDerecha) {
            piedra.actualizar(jugador);
            if (!piedra.desplazarseDerecha(obstaculos, 9)) {
                piedraDerecha = false;
                piedra = null;
            }
        }
    }
    
    private void desplazarPiedraIzquierda(ObjetoInerte[] obstaculos, Jugador jugador) {
        if (piedraIzquierda) {
            piedra.actualizar(jugador);
            if (!piedra.desplazarseIzquierda(obstaculos, 9)) {
                piedraIzquierda = false;
                piedra = null;
            }
        }
    }
    
    @Override
    protected boolean isFueraDeEscenario(ObjetoRecogible piedra) {
        return piedra.getX() > anchoPantalla || piedra.getX() + piedra.getAncho() < 0;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * @param clon rectangulo con las dimensiones del jugador
     * @param obstaculo rectangulo con las dimensiones del obstaculo con el que colisiona.
     * @return true si la colision con un obstaculo es por la derecha o por la izquierda.
     * @author Milton Lenis
     */
    private boolean ObstaculoDirHorizontal(Rectangle clon, Rectangle obstaculo) {
        return !(clon.getBoundsInLocal().getMinY() == obstaculo.getBoundsInLocal().getMaxY()
                || clon.getBoundsInLocal().getMaxY() == obstaculo.getBoundsInLocal().getMinY());
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * @param clon rectangulo con las dimensiones del jugador
     * @param obstaculo rectangulo con las dimensiones del obstaculo con el que colisiona.
     * @return true si la colision con un obstaculo es por la derecha o por la izquierda.
     * @author Milton Lenis
     */
    private boolean ObstaculoDirVertical(Rectangle clon, Rectangle obstaculo) {
        return !(clon.getBoundsInLocal().getMaxX() == obstaculo.getBoundsInLocal().getMinX()
                || clon.getBoundsInLocal().getMinX() == obstaculo.getBoundsInLocal().getMaxX());
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * 
     * Este metodo cambia los siguientes atributos:
     * - countAuxForGravity
     * - velocidadVertical
     * 
     * Hace que el jugador caiga con aceleración hasta posarse sobre alguna
     * base.
     * 
     * No se puede invocar este metodo más de una vez dentro de
     * ningún metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @return Devuelve true si el jugador intercepta con algo debajo de él.
     * @author Milton Lenis
     */
   @Override
    protected boolean gravedad(ObjetoInerte[] obstaculos) {
        if(countAuxForGravity == 5) {
            countAuxForGravity = 0;
            velocidadVertical++;
        } else {
            countAuxForGravity++;
        }
        boolean hayAlgoAbajo = !desplazarseAbajo(obstaculos, velocidadVertical*3);
        if (hayAlgoAbajo && aceleracion < 0) {
            velocidadVertical = 1;
            countAuxForGravity = 0;
        }
        return hayAlgoAbajo;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * 
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
     * @author Milton Lenis
     */
   @Override
    protected void saltar(ObjetoInerte[] obstaculos, int alturaDeSalto) {
        if (aceleracion >= 0) {
            boolean hayAlgoArriba = !desplazarseArriba(obstaculos, alturaDeSalto*3);
            aceleracion = alturaDeSalto - velocidadVertical;
            if (aceleracion < 0 || hayAlgoArriba) {
                velocidadVertical = 1;
                aceleracion = -1;
            }
        }
    }

    public boolean isAnimacion1() {
        return animacion1;
    }

    public void setAnimacion1(boolean animacion1) {
        this.animacion1 = animacion1;
    }

    public boolean isAnimacion2() {
        return animacion2;
    }

    public void setAnimacion2(boolean animacion2) {
        this.animacion2 = animacion2;
    }

    public boolean isLanzarPiedra() {
        return lanzarPiedra;
    }

    public void setLanzarPiedra(boolean lanzarPiedra) {
        this.lanzarPiedra = lanzarPiedra;
    }
    
    public boolean isVisible () {
        return x < anchoPantalla && !muerto && x + ancho > 0;
    }
    
}
