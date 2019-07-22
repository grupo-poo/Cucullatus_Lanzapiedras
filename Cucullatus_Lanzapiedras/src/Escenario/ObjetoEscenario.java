package Escenario;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * @author Milton Lenis
 */
public abstract class ObjetoEscenario {
    protected int ancho, alto; // Dimensiones de la imagen del objeto.
    protected int x, y; // Coordenadas del Objeto en la pantalla.
    protected int xInit; // Coordemada x inicial (no cambia)
    protected Image imagen;
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * 
     * Este metodo dibuja al objeto.
     * 
     * Se le pueden añadir nuevos metodos si así se requiere o cualquier
     * otra alteración de su estructura.
     * 
     * @param lapiz Objeto de graficos
     */
    public abstract void dibujar(GraphicsContext lapiz);

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
    
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, ancho, alto);
    }
    
}
