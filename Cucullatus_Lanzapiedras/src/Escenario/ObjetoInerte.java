package Escenario;

import Personajes.Jugador;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * @author Milton Lenis
 */
public class ObjetoInerte {
    
    private int ancho, alto; // Dimensiones de la imagen del objeto.
    private int x, y; // Coordenadas del Objeto en la pantalla.
    private int xInit; // Coordemada x inicial (no cambia)
    private Image imagen;
    
    public ObjetoInerte(Image imagen) {
        this.imagen = imagen;
        ancho = (int) imagen.getWidth();
        alto = (int) imagen.getHeight();
    }
    
    public ObjetoInerte(Image imagen, int x, int y) {
        this.imagen = imagen;
        ancho = (int) imagen.getWidth();
        alto = (int) imagen.getHeight();
        this.x = x;
        this.xInit = x;
        this.y = y;
    }
    
    public ObjetoInerte(Image imagen, int x, int y, int ancho, int alto) {
        this.imagen = imagen;
        this.x = x;
        this.xInit = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
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
    
    public void setCoordenadas(int x, int y) {
        this.x = x;
        this.xInit = x;
        this.y = y;
    }
    
    public void setDimensiones(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
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

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
    
    
    
}
