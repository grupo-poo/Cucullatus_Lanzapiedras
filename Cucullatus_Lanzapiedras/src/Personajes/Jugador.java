package Personajes;

import Control.Teclado;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Milton Lenis
 */
public class Jugador {
    
    int ancho, alto;    // Dimensiones
    int x, y;   // Posiciones
    boolean UP, DOWN, LEFT, RIGHT;  // Teclas
    
    BufferedImage imagen;
    
    public Jugador(BufferedImage imagen) {
        this.imagen = imagen;
        
        // Por defecto el jugador tiene las dimensiones de la imagen original.
        ancho = imagen.getWidth();
        alto = imagen.getHeight();
        
        UP = false; DOWN = false; LEFT = false; RIGHT = false;
        
        // Posiciones por defecto
        x = 40;
        y = 590;
    }
    
    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ancho, alto, null);
    }
    
    public void mover() {
        if (Teclado.derecha) {
            x+=3;
        }else if (Teclado.izquierda) {
            x-=3;
        }else if (Teclado.arriba) {
            y-=3;
        }else if (Teclado.abajo) {
            y+=3;
        }
    }
    
    public BufferedImage getImagen() {
        return imagen;
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
