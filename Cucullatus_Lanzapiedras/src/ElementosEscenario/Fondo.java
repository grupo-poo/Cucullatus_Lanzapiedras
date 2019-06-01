package ElementosEscenario;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * @author Milton Lenis
 */
public class Fondo {
    
    int ancho, alto, x, y;
    BufferedImage imagen;
    
    public Fondo(String dirImagen) throws IOException {
        
        // Incorporamos nuestra imagen para el fondo.
        URL url = this.getClass().getResource(dirImagen);
        imagen = ImageIO.read(url);
                
        
        // Por defecto el fondo tiene las dimensiones de la imagen original.
        ancho = imagen.getWidth();
        alto = imagen.getHeight();
    }
    
    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ancho, alto, null);
    }
    
    public void actualizar() {
        ////////////////////
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
