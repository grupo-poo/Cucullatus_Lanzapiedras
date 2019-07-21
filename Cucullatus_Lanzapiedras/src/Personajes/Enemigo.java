/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Escenario.ObjetoInerte;
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
    private boolean direccion; // Si es true el jugador mira a la derecha.
    private boolean animacion1;
    
    private int secuencia = 1;//Numero de imagenes, empieza por 1
    private int cuenta= 0;//Ayuda a controlar la cantidad de veces que se pintan las imágenes

    public Enemigo(int anchoPantalla, int altoPantalla) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.imagen=new Image("Nucleo/Recursos/Enemigo/Enemigo.png");
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
    }
    
    public void actualizar(Jugador jugador, ArrayList<ObjetoInerte> obstaculos) {
        if (jugador.isDistanciaCritica()) {
            x -= jugador.getVelocidadHorizontal();
        }
        movimiento(obstaculos);
    }
    
    public void dibujar(GraphicsContext lapiz, Jugador jugador) {
        if(x < anchoPantalla && !muerto){
            lapiz.drawImage(imagen, x, y, ancho, alto);
        }
    }
    
    private void movimiento(ArrayList<ObjetoInerte> obstaculos) {
        animacion1(obstaculos);
        //animacion2
        //animacion3...
    }
    
    private void animacion1(ArrayList<ObjetoInerte> obstaculos) {
        if (animacion1) {
            if (!muerto) {
                if (direccion) {
                    direccion = desplazarseIzquierda(obstaculos, 3);
                } else {
                    direccion = !desplazarseDerecha(obstaculos, 3);
                }
            }
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
                }
            }
        }
        if (viaLibre) { y += velocidad; }
        return viaLibre;
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
    
}
