package Escenario;

import Control.Teclado;
import Personajes.Jugador;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Milton Lenis
 */
public class Mensaje {
    
    private int ancho, alto, x, y;
    private byte avance;
    private BufferedImage imagen;
    
    public Mensaje(BufferedImage imagen, byte avance) {
        this.imagen = imagen;
        this.avance = avance; // Número de pixeles que se mueven con cada paso
        
        // Por defecto el mensaje tiene las dimensiones de la imagen original.
        ancho = imagen.getWidth();
        alto = imagen.getHeight();
    }
    
    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ancho, alto, null);
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
    
    public BufferedImage getImagen() {
        return imagen;
    }
    
    public void setImagen(BufferedImage imagen) {
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