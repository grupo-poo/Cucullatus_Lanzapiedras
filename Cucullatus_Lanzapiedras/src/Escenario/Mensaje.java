package Escenario;

import Control.Teclado;
import Personajes.Jugador;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Milton Lenis
 */
public class Mensaje {
    
    private int ancho, alto, x, y;
    private byte avance;
    private Image imagen;
    
    public Mensaje(Image imagen, byte avance) {
        this.imagen = imagen;
        this.avance = avance; // Número de pixeles que se mueven con cada paso
        
        // Por defecto el mensaje tiene las dimensiones de la imagen original.
        ancho = (int) imagen.getWidth();
        alto = (int) imagen.getHeight();
    }
    
    public void dibujar(GraphicsContext lapiz) {
        lapiz.drawImage(imagen, x, y, ancho, alto);
    }
    
    public void actualizar(Jugador jugador) {
        if (jugador.isDistanciaCritica()) {
            if (Teclado.isIZQUIERDA()) {
                x += avance;
            }else if (Teclado.isDERECHA()) {
                x -= avance;
            }
        }
    }
    
    public Image getImagen() {
        return imagen;
    }
    
    public void setImagen(Image imagen) {
        this.imagen = imagen;
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
    
}