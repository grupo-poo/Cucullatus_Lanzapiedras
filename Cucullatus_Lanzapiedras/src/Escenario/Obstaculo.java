package Escenario;

import Control.Teclado;
import Personajes.Jugador;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * @author Milton Lenis
 */
public class Obstaculo {
    
    private int ancho, alto, x, y;
    private byte avance;
    private Image imagen;
    private boolean atravesable;
    
    public Obstaculo(Image imagen, byte avance) {
        this.imagen = imagen;
        this.avance = avance; // Número de pixeles que se mueven con cada paso
        
        // Por defecto el fondo tiene las dimensiones de la imagen original.
        ancho = (int) imagen.getWidth();
        alto = (int) imagen.getHeight();
        
        // Por defecto el obstaculo se podrá atravesar.
        atravesable = true;
    }
    
    public void dibujar(GraphicsContext lapiz) {
        lapiz.drawImage(imagen, x, y, ancho, alto);
    }
    
    public void actualizar(Jugador jugador) {
        
        atravesable(atravesable,jugador);
        
        if (jugador.isDistanciaCritica()) {
            if (Teclado.isIZQUIERDA()) {
                x += avance;
            }else if (Teclado.isDERECHA()) {
                x -= avance;
            }
        }
    }
    
    /**
     * Resitua al jugador en un posicion en la que no intercepta con el 
     * obstaculo.
     */
    private void atravesable(boolean atravesable, Jugador jug) {
        if (!atravesable) {
            Rectangle area = getRectangulo();
            
            if (area.intersects(jug.getRectangulo().getBoundsInLocal())) {
                
                int xIncial = jug.getX();
                Rectangle rJug = jug.getRectangulo();
                
                rJug.relocate(jug.getX() - 1 , jug.getY());
                if ( Teclado.isIZQUIERDA() && area.intersects(rJug.getBoundsInLocal())) {
                    int nuevaPosicion = (x + ancho);
                    if (!((nuevaPosicion - jug.getX()) > (avance+1))) {
                        jug.setX(nuevaPosicion);
                    }
                }
                rJug.relocate(jug.getX() + 2 , jug.getY());
                if ( Teclado.isDERECHA() && area.intersects(rJug.getBoundsInLocal()) ) {
                    int nuevaPosicion = (x - jug.getAncho());
                    if (!((jug.getX() - nuevaPosicion) > (avance+1))) {
                        jug.setX(nuevaPosicion);
                    }
                }
                rJug.relocate(jug.getX() - 2 , jug.getY() - 1);
                 if ( Teclado.isARRIBA() && area.intersects(rJug.getBoundsInLocal()) ) {
                    int nuevaPosicion = (y + alto);
                    if (!((nuevaPosicion - jug.getY()) > (avance+1))) {
                        jug.setY(nuevaPosicion);
                    }
                }
                rJug.relocate(jug.getX() , jug.getY() + 2);
                if ( Teclado.isABAJO() && area.intersects(rJug.getBoundsInLocal()) ) {
                    int nuevaPosicion = (y - jug.getAlto());
                    if (!((jug.getY() - nuevaPosicion) > (avance+1))) {
                        jug.setY(nuevaPosicion);
                    }
                }
                
                jug.setDesplazamiento(
                        jug.getDesplazamiento() - (xIncial - jug.getX()) );
            }
        } 
    }
    
    private Rectangle getRectangulo() {
        return new Rectangle(x, y, ancho, alto);
    }
    
    public Image getImagen() {
        return imagen;
    }
    
    public void setImagen(Image imagen) {
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

    public byte getAvance() {
        return avance;
    }

    public void setAvance(byte avance) {
        this.avance = avance;
    }

    public boolean isAtravesable() {
        return atravesable;
    }

    public void setAtravesable(boolean atravesable) {
        this.atravesable = atravesable;
    }
    
}
