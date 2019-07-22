package Escenario;

import Nucleo.Debug;
import Personajes.Jugador;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * @author Milton Lenis
 */
public class Portal {
    
    private int ancho, alto; // Dimensiones de la imagen del objeto.
    private int x, y; // Coordenadas del Objeto en la pantalla.
    private int xInit; // Coordemada x inicial (no cambia)
    private Image imagen;
    
    private boolean abrirPortal;
    private boolean transportar;
    
    public Portal(Image imagen) {
        this.imagen = imagen;
        ancho = (int) imagen.getWidth();
        alto = (int) imagen.getHeight();
    }
    
    public Portal(Image imagen, int x, int y) {
        this.imagen = imagen;
        this.ancho = (int) imagen.getWidth();
        this.alto = (int) imagen.getHeight();
        this.x = x;
        this.xInit = x;
        this.y = y;
    }
    
    public Portal(Image imagen, int x, int y, int ancho, int alto) {
        this.imagen = imagen;
        this.x = x;
        this.xInit = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }
    
    /**
     * 
     * *************************** METODO ALTERABLE ***************************
     * 
     * Este metodo dibuja al objeto inerte.
     * 
     * Se le pueden añadir nuevos metodos si así se requiere o cualquier
     * otra alteración de su estructura.
     * 
     * @param lapiz Objeto de graficos
     */
    public void dibujar(GraphicsContext lapiz) {
        if (abrirPortal) {
            lapiz.drawImage(imagen, imagen.getWidth()/2, 0, imagen.getWidth()/2, imagen.getHeight(), x, y, ancho, alto);
        } else {
            lapiz.drawImage(imagen, 0, 0, imagen.getWidth()/2, imagen.getHeight(), x, y, ancho, alto);
        }
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * 
     * Este metodo cambia los atributos que los metodos
     * que contiene cambian por cada frame.
     * 
     * Se le pueden añadir nuevos metodos si así se requiere o cualquier
     * otra alteración de su estructura.
     * 
     * @param jugador 
     */
    public void actualizar(Jugador jugador, ArrayList<Pared> paredes) {        
        if (jugador.isDistanciaCritica()) {
            x -= jugador.getVelocidadHorizontal();
        }
        abrirPortal = estadoTransportacion(paredes, (byte)2);
        transportar = abrirPortal && intersectJugador(jugador);
    }
    
    private boolean estadoTransportacion(ArrayList<Pared> paredes, byte arraySize) {
        if (arraySize < 0) return true;
        boolean p = paredes.get(arraySize).isIsGraffiteada();
        return p && estadoTransportacion(paredes, (byte)(arraySize - 1));
    }
    
    private boolean intersectJugador(Jugador jugador) {
        return getRectangulo().intersects(jugador.getRectangulo().getBoundsInLocal());
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

    public boolean isTransportar() {
        return transportar;
    }

    public void setTransportar(boolean transportar) {
        this.transportar = transportar;
    }
    
}
