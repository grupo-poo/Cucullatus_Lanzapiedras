package Personajes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * @author Milton Lenis
 */
public class Jugador {
    
    int ancho, alto, x, y;
    BufferedImage imagen;
    
    public Jugador(String dirImagen) throws IOException {
        
        // Incorporamos nuestra imagen para el jugador
        URL url = this.getClass().getResource(dirImagen);
        imagen = ImageIO.read(url);
        
        // Por defecto el jugador tiene las dimensiones de la imagen original.
        ancho = imagen.getWidth();
        alto = imagen.getHeight();
        
        // Posiciones por defecto
        x = 4;
        y = 590;
    }
    
    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ancho, alto, null);
    }
    
    public void actualizar() {
        mover();
    }
    
    int dx = 3;
    public void mover() {
        /**
         * Esto es solo un simple programa de ejemplo para mostrar
         * el lugar que tenemos que modificar para que nuestro personaje
         * se mueva como queremos.
         */
        ///////////////////////////////////////
        if (x < 200 && x > 2) {
            x+=dx;
        } else {
            dx = -dx;
            x+=dx;
        }
        ///////////////////////////////////////
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
