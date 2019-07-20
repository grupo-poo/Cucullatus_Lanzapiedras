package Escenario;

import Personajes.Jugador;
import java.util.ArrayList;
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
    private boolean Alargamiento;
    private int desplazamiento; // Cambia cada vez que el jugador se desplaaza.
    
    public ObjetoInerte(Image imagen) {
        this.imagen = imagen;
        ancho = (int) imagen.getWidth();
        alto = (int) imagen.getHeight();
    }
    
    public ObjetoInerte(Image imagen, int x, int y) {
        this.imagen = imagen;
        this.ancho = (int) imagen.getWidth();
        this.alto = (int) imagen.getHeight();
        this.x = x;
        this.desplazamiento = x;
        this.xInit = x;
        this.y = y;
    }
    
    public ObjetoInerte(Image imagen, int x, int y, int ancho, int alto) {
        this.imagen = imagen;
        this.x = x;
        this.desplazamiento = x;
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
        lapiz.drawImage(imagen, x, y, ancho, alto);
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
    public void actualizar(Jugador jugador) {        
        if (jugador.isDistanciaCritica()) {
            x -= jugador.getVelocidadHorizontal();
            if(Alargamiento)
            ancho+=jugador.getVelocidadHorizontal();
        }
    }
    
    private boolean desplazarseAbajo(ArrayList<ObjetoInerte> obstaculos) {
        int fuerza = 6;
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setY(y + fuerza);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() - alto;
                }
            }
        }
        if (viaLibre) { y += fuerza; }
        return viaLibre;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * @param clon rectangulo con las dimensiones del jugador
     * @param obstaculo rectangulo con las dimensiones del obstaculo con el que colisiona.
     * @return true si la colision con un obstaculo es por la derecha o por la izquierda.
     * @author Milton Lenis
     */
    private boolean ObstaculoDirHorizontal(Rectangle clon, Rectangle obstaculo) {
        return !(clon.getBoundsInLocal().getMinY() == obstaculo.getBoundsInLocal().getMaxY()
                || clon.getBoundsInLocal().getMaxY() == obstaculo.getBoundsInLocal().getMinY());
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * @param clon rectangulo con las dimensiones del jugador
     * @param obstaculo rectangulo con las dimensiones del obstaculo con el que colisiona.
     * @return true si la colision con un obstaculo es por la derecha o por la izquierda.
     * @author Milton Lenis
     */
    private boolean ObstaculoDirVertical(Rectangle clon, Rectangle obstaculo) {
        return !(clon.getBoundsInLocal().getMaxX() == obstaculo.getBoundsInLocal().getMinX()
                || clon.getBoundsInLocal().getMinX() == obstaculo.getBoundsInLocal().getMaxX());
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

    public boolean isAlargamiento() {
        return Alargamiento;
    }

    public void setAlargamiento(boolean Alargamiento) {
        this.Alargamiento = Alargamiento;
    }
    
    
   
    
    
    
}
