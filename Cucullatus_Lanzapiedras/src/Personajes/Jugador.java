package Personajes;

import Control.Teclado;
import Escenario.ObjetoInerte;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * @author Milton Lenis
 */
public class Jugador {
    
    private byte pasos;   // Número de pixeles que debe recorrer por frame.
    private int velocidad; // pixeles que recorre en tiempo real.
    private int ancho, alto;    // Dimensiones de la imagen del Jugador.
    private int x, y;   // Posiciones.
    private int desplazamiento;
    private boolean distanciaCritica; // está en este punto de no avance ¿?
    private Image imagen;
    
    public Jugador(Image imagen) {
        this.imagen = imagen;
        ancho = (int) imagen.getWidth();
        alto  = (int) imagen.getHeight();
        x = 40;     y = 550;
        desplazamiento = x;
        pasos = 3;
    }
    
    public void dibujar(GraphicsContext lapiz) {
        lapiz.drawImage(imagen, x, y, ancho, alto);
    }
    
    public void actualizar(int anchoDePantalla, ArrayList<ObjetoInerte> obstaculos) {
        // Cuando el jugador esté en un cierto punto el escenario se mueve.
        movimientoDeEscenario(anchoDePantalla);
        mover(obstaculos); // Aquí movemos al jugador.
    }
    
    private void mover(ArrayList<ObjetoInerte> obstaculos) {
        velocidad = desplazamiento;
        if (Teclado.isIZQUIERDA()) {
            desplazarseIzquierda(obstaculos);
        }
        if (Teclado.isDERECHA()) {
            desplazarseDerecha(obstaculos);
        }
        if (Teclado.isARRIBA()) {
            desplazarseArriba(obstaculos);
        }
        if (Teclado.isABAJO()) {
            desplazarseAbajo(obstaculos);
        }
        velocidad = desplazamiento - velocidad;
    }
    
    private void desplazarseIzquierda(ArrayList<ObjetoInerte> obstaculos) {
        boolean hayInterseccion = false;
        Rectangle Ju = getRectangulo();
        Ju.setX(x - pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (!(esqInfDer_esqSupIzq(obs.getRectangulo(), Ju)
                        || esqSupDer_esqInfIzq(obs.getRectangulo(), Ju))) {
                    Ju.setX(x + 1);
                    if (!Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                        hayInterseccion = true;
                        desplazamiento = obs.getxInit() + obs.getAncho();
                        if (!distanciaCritica) { x = desplazamiento; }
                    }
                    Ju.setX(x - 1);
                }
            } 
        }
        if (!hayInterseccion) {
            if ((desplazamiento - x) % pasos != 0) {
                while ((desplazamiento - x) % pasos != 0) { desplazamiento--; }
            } else {
                desplazamiento -= pasos;
                if (!distanciaCritica) { x = desplazamiento; }
            }
        }
    }
        
    private void desplazarseDerecha(ArrayList<ObjetoInerte> obstaculos) {
        boolean hayInterseccion = false;
        Rectangle Ju = getRectangulo();
        Ju.setX(x + pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (!(esqSupDer_esqInfIzq(Ju, obs.getRectangulo()) 
                        || esqInfDer_esqSupIzq(Ju, obs.getRectangulo()))) {
                    Ju.setX(x - 1);
                    if (!Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                        hayInterseccion = true;
                        desplazamiento = obs.getxInit() - ancho;
                        if (!distanciaCritica) { x = desplazamiento; }
                    }
                    Ju.setX(x + 1);
                }
            }
        }
        if (!hayInterseccion) {
            if ((desplazamiento - x) % pasos != 0) {
                while ((desplazamiento - x) % pasos != 0) { desplazamiento++; }
            } else {
                desplazamiento += pasos;
                if (!distanciaCritica) { x = desplazamiento; }
            }
        }
    }
    
    private void desplazarseArriba(ArrayList<ObjetoInerte> obstaculos) {
        boolean hayInterseccion = false;
        Rectangle Ju = getRectangulo();
        Ju.setY(y - pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (!(esqSupDer_esqInfIzq(Ju, obs.getRectangulo())
                        || esqInfDer_esqSupIzq(obs.getRectangulo(), Ju))) {
                    Ju.setY(y + 1);
                    if (!Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                        hayInterseccion = true;
                        y = obs.getY() + obs.getAlto();
                    }
                    Ju.setY(y - 1);
                }
            }
        }
        if (!hayInterseccion) { y -= pasos; }
    }
    
    private void desplazarseAbajo(ArrayList<ObjetoInerte> obstaculos) {
        boolean hayInterseccion = false;
        Rectangle Ju = getRectangulo();
        Ju.setY(y + pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (!(esqInfDer_esqSupIzq(Ju, obs.getRectangulo())
                        || esqSupDer_esqInfIzq(obs.getRectangulo(), Ju))) {
                    Ju.setY(y - 1);
                    if (!Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                        hayInterseccion = true;
                        y = obs.getY() - alto;
                    }
                    Ju.setY(y + 1);
                }
            }
        }
        if (!hayInterseccion) { y += pasos; }
    }
    
    private void movimientoDeEscenario(int anchoDePantalla) {
        int distCrit = ((anchoDePantalla / 2) - 2*ancho);
        if (desplazamiento >= distCrit) {
            distanciaCritica = !(desplazamiento < (distCrit + pasos) 
                                                && Teclado.isIZQUIERDA());
        } else distanciaCritica = false;
    }
    
    private boolean esqSupDer_esqInfIzq(Rectangle rect1, Rectangle rect2) {
        short rect1MinY = (short) rect1.getBoundsInLocal().getMinY();
        short rect2MaxY = (short) rect2.getBoundsInLocal().getMaxY();
        short rect1MaxX = (short) rect1.getBoundsInLocal().getMaxX();
        short rect2MinX = (short) rect2.getBoundsInLocal().getMinX();
        boolean bool = false;
        if (rect1MinY == rect2MaxY) {
            bool = (rect1MaxX - rect2MinX) <= pasos;
        } else 
        if (rect1MaxX == rect2MinX) {
            bool = (rect2MaxY - rect1MinY) <= pasos;
        }
        return bool;
    }
    
    private boolean esqInfDer_esqSupIzq(Rectangle rect1, Rectangle rect2) {
        short rect1MaxY = (short) rect1.getBoundsInLocal().getMaxY();
        short rect2MinY = (short) rect2.getBoundsInLocal().getMinY();
        short rect1MaxX = (short) rect1.getBoundsInLocal().getMaxX();
        short rect2MinX = (short) rect2.getBoundsInLocal().getMinX();
        boolean bool = false;
        if (rect1MaxY == rect2MinY) {
            bool = (rect1MaxX - rect2MinX) <= pasos;
        } else 
        if (rect1MaxX == rect2MinX) {
            bool = (rect1MaxY - rect2MinY) <= pasos;
        }
        return bool;
    }
    
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, ancho, alto);
    }

    public byte getPasos() {
        return pasos;
    }

    public void setPasos(byte pasos) {
        this.pasos = pasos;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
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

    public int getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(int desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

    public boolean isDistanciaCritica() {
        return distanciaCritica;
    }

    public void setDistanciaCritica(boolean distanciaCritica) {
        this.distanciaCritica = distanciaCritica;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    
    
}
