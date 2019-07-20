/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Escenario;

import Personajes.Jugador;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author diegocarvajal
 */
public class Piedra {
    private int x;
    private int y;
    private int ancho;
    private int alto;
    private Image imagen;
    private boolean visible = true;
    private int desplazamiento; // Cambia cada vez que el jugador se desplaaza.
    
    
    public Piedra(int x, int y, int ancho, int alto){
        this.x=x;
        this.y=y;
        this.ancho=ancho;
        this.alto=alto;
        this.imagen=new Image("Nucleo/Recursos/Piedra/Piedra.png");
    }
    
    public Piedra(int x, int y){
        this.x=x;
        this.y=y;
        this.imagen=new Image("Nucleo/Recursos/Piedra/Piedra.png");
    }
    
    public void dibujar(GraphicsContext lapiz){
        if(visible){
            lapiz.drawImage(imagen, x, y,ancho,alto);
        }
    }
    
    public void actualizar(Jugador jugador) {
        if (jugador.isDistanciaCritica()) {
            this.x -= jugador.getVelocidadHorizontal();
        }
    }
    
    public boolean desplazarseDerecha(ArrayList<ObjetoInerte> obstaculos) {
        int fuerza = 9;
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setX(x + fuerza);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    x = obs.getX() - ancho;
                }
            }
        }
        if (viaLibre) { x += fuerza; }
        return viaLibre;
    }
    
    public boolean desplazarseIzquierda(ArrayList<ObjetoInerte> obstaculos) {
        int fuerza = 9;
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setX(x - fuerza);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    x = obs.getX() + obs.getAncho();
                }
            }
        }
        if (viaLibre) { x -= fuerza; }
        return viaLibre;
    }
    
    public boolean desplazarseArriba(ArrayList<ObjetoInerte> obstaculos) {
        int fuerza = 9;
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setY(y - fuerza);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() + obs.getAlto();
                }
            }
        }
        if (viaLibre) { y -= fuerza; }
        return viaLibre;
    }
    
    public boolean desplazarseAbajo(ArrayList<ObjetoInerte> obstaculos) {
        int fuerza = 9;
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setY(y + fuerza);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() - alto;
                }
            }
        }
        if (viaLibre) { y += fuerza; }
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
    
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, ancho, alto);
    }
    
    public void setCoordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setDimensiones(int ancho, int alto) {
        this.ancho = ancho;
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

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    
}
