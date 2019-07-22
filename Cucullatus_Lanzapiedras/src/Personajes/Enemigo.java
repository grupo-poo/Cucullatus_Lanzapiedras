/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Escenario.ObjetoInerte;
import Escenario.Piedra;
import Nucleo.ObjetoEscenario;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author diegocarvajal
 */
public class Enemigo extends ObjetoEscenario{
   private byte pasos;   // Número de pixeles que debe recorrer por frame.
   
    private final int altoPantalla;
    private final int anchoPantalla;
    private int ancho, alto; // Dimensiones de la imagen del Jugador.
    private int x, y; // Coordenadas del jugador en la pantalla.
    
    private Image imagen;
    private boolean muerto;
    private boolean moverse;
    private boolean direccion; // Si es true el jugador mira a la derecha.
    private boolean animacion1;
    private boolean animacion2;
    
    private Piedra piedra;
    private boolean lanzarPiedra;
    
    // Direcciones a las que la piedra es lanzada.
    private boolean piedraDerecha = false;
    private boolean piedraIzquierda = false;
    
    // Auxiliares para la gravedad.
    private int countAuxForGravity = 0; // Contador auxiliar para la gravedad.
    private int aceleracion = -1; // cambio de la velocidad con respecto a cada frame.
    private int velocidadVertical; // pixeles que recorre verticalmente en tiempo real
    
    private int secuencia = 1;//Numero de imagenes, empieza por 1
    private int cuenta= 0;//Ayuda a controlar la cantidad de veces que se pintan las imágenes

    public Enemigo(int anchoPantalla, int altoPantalla) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.imagen=new Image("Nucleo/Recursos/Enemigo/Enemigo.png");
        this.ancho = 45;
        this.alto = 85;
    }
    
    public Enemigo(int x, int y, int ancho, int alto, int vidaInicial, int anchoPantalla, int altoPantalla) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.imagen=new Image("Nucleo/Recursos/Enemigo/Enemigo.png");
        this.ancho = ancho;
        this.alto = alto;
        this.x = x;
        this.y = y;
        direccion = true;
        velocidadVertical = 1;
    }
    
    public void actualizar(Jugador jugador, ArrayList<ObjetoInerte> obstaculos) {
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
    
    public void dibujar(GraphicsContext lapiz, Jugador jugador) {
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
    
    private void movimiento(ArrayList<ObjetoInerte> obstaculos) {
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
    
    private void animacion1(ArrayList<ObjetoInerte> obstaculos) {
        if (animacion1 && moverse && !muerto) {
            if (direccion) {
                direccion = desplazarseDerecha(obstaculos, 3);
            } else {
                direccion = !desplazarseIzquierda(obstaculos, 3);
            }
        }
    }
    
    private void animacion2(ArrayList<ObjetoInerte> obstaculos) {
        if (animacion2 && moverse && !muerto) {
            if (gravedad(obstaculos)) {
                aceleracion = 0;
            }
            saltar(obstaculos, 5);
        }
    }
    
    public boolean desplazarseDerecha(ArrayList<ObjetoInerte> obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setX(x + velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    x = obs.getX() - ancho;
                    break;
                }
            }
        }
        if (viaLibre) { x += velocidad; }
        return viaLibre;
    }
    
    public boolean desplazarseIzquierda(ArrayList<ObjetoInerte> obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setX(x - velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    x = obs.getX() + obs.getAncho();
                    break;
                }
            }
        }
        if (viaLibre) { x -= velocidad; }
        return viaLibre;
    }
    
    public boolean desplazarseArriba(ArrayList<ObjetoInerte> obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setY(y - velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() + obs.getAlto();
                    break;
                }
            }
        }
        if (viaLibre) { y -= velocidad; }
        return viaLibre;
    }
    
    public boolean desplazarseAbajo(ArrayList<ObjetoInerte> obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setY(y + velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() - alto;
                    break;
                }
            }
        }
        if (viaLibre) { y += velocidad; }
        return viaLibre;
    }
    
    private void lanzarPiedra(ArrayList<ObjetoInerte> obstaculos, Jugador jugador) {
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
    
    private void crearPiedra() {
        if (piedra == null) {
            Image imagen = new Image("Nucleo/Recursos/Piedra/Piedra.png");
            piedra = new Piedra(imagen, x, y + 20);
        }
    }
    
    private void desplazarPiedraDerecha(ArrayList<ObjetoInerte> obstaculos, Jugador jugador) {
        if (piedraDerecha) {
            piedra.actualizar(jugador);
            if (!piedra.desplazarseDerecha(obstaculos, 9)) {
                piedraDerecha = false;
                piedra = null;
            }
        }
    }
    
    private void desplazarPiedraIzquierda(ArrayList<ObjetoInerte> obstaculos, Jugador jugador) {
        if (piedraIzquierda) {
            piedra.actualizar(jugador);
            if (!piedra.desplazarseIzquierda(obstaculos, 9)) {
                piedraIzquierda = false;
                piedra = null;
            }
        }
    }
    
    public void eliminarPiedraSiSaleDeEscenario() {
        if (piedra != null) {
            if (isFueraDeEscenario(piedra)) {
                abortarLanzamiento();
                piedra = null;
            }
        }
    }
    
    private void abortarLanzamiento() {
        piedraDerecha = false;
        piedraIzquierda = false;
    }
    
    private boolean isFueraDeEscenario(Piedra piedra) {
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
    private boolean gravedad(ArrayList<ObjetoInerte> obstaculos) {
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
    private void saltar(ArrayList<ObjetoInerte> obstaculos, int alturaDeSalto) {
        if (aceleracion >= 0) {
            boolean hayAlgoArriba = !desplazarseArriba(obstaculos, alturaDeSalto*3);
            aceleracion = alturaDeSalto - velocidadVertical;
            if (aceleracion < 0 || hayAlgoArriba) {
                velocidadVertical = 1;
                aceleracion = -1;
            }
        }
    }
    
   @Override
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, ancho, alto);
    }

    public boolean isMuerto() {
        return muerto;
    }

    public void setMuerto(boolean muerto) {
        this.muerto = muerto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
    
    public void setCoordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setDimensiones(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    public boolean isDireccion() {
        return direccion;
    }

    public void setDireccion(boolean direccion) {
        this.direccion = direccion;
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
