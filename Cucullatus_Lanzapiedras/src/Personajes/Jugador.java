package Personajes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
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
        if (RIGHT) {
            x+=3;
        }else if (LEFT) {
            x-=3;
        }else if (UP) {
            y-=3;
        }else if (DOWN) {
            y+=3;
        }
    }
    
    public void teclaPresionada(KeyEvent e) {
        //Obtenemos el codigo de tecla el objeto KeyEvent
        int codigoTecla = e.getKeyCode();
        
        if (codigoTecla == KeyEvent.VK_RIGHT) {
            RIGHT = true;
        }else if (codigoTecla == KeyEvent.VK_LEFT) {
            LEFT = true;
        }else if (codigoTecla == KeyEvent.VK_UP) {
            UP = true;
        }else if (codigoTecla == KeyEvent.VK_DOWN) {
            DOWN = true;
        }
    }
    
    public void teclaSoltada(KeyEvent e) {
        //Obtenemos el codigo de tecla el objeto KeyEvent
        int codigoTecla = e.getKeyCode();
        
        if (codigoTecla == KeyEvent.VK_RIGHT) {
            RIGHT = false;
        }else if (codigoTecla == KeyEvent.VK_LEFT) {
            LEFT = false;
        }else if (codigoTecla == KeyEvent.VK_UP) {
            UP = false;
        }else if (codigoTecla == KeyEvent.VK_DOWN) {
            DOWN = false;
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
