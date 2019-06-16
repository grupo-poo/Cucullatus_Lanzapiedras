package Escenario;

import Personajes.Jugador;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * @author Milton Lenis
 */
public class ObjetoInerte {
    
    private int ancho, alto, x, y, xInit;
    private Image imagen;
    
    public ObjetoInerte(Image imagen) {
        this.imagen = imagen;
        // Por defecto el fondo tiene las dimensiones de la imagen original.
        ancho = (int) imagen.getWidth();
        alto = (int) imagen.getHeight();
    }
    
    public void dibujar(GraphicsContext lapiz) {
        lapiz.drawImage(imagen, x, y, ancho, alto);
    }
    
    public void actualizar(Jugador jugador) {
        if (jugador.isDistanciaCritica()) {
            x -= jugador.getVelocidad();
        }
    }
    
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, ancho, alto);
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
        this.xInit = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxInit() {
        return xInit;
    }

    public void setxInit(int xInit) {
        this.xInit = xInit;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
    
    
    
}
