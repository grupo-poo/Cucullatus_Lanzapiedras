package Escenario;

import Control.Teclado;
import Personajes.Jugador;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * @author Milton Lenis
 */
public class Obstaculo {
    
    private int ancho, alto, x, y;
    private byte avance;
    private BufferedImage imagen;
    private boolean atravesable;
    
    public Obstaculo(BufferedImage imagen, byte avance) {
        this.imagen = imagen;
        this.avance = avance; // Número de pixeles que se mueven con cada paso
        
        // Por defecto el fondo tiene las dimensiones de la imagen original.
        ancho = imagen.getWidth();
        alto = imagen.getHeight();
        
        
        // Por defecto el obstaculo se podrá atravesar.
        atravesable = true;
    }
    
    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ancho, alto, null);
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
     * Resitua al jugador en un posicion en la que no intercepta con
     * el obstaculo.
     */
    private void atravesable(boolean atravesable, Jugador jug) {
        if (!atravesable) {
            
            /*
            * Creamos un rectangulo con las dimensiones y posicion
            * de nuestro obstaculo, esto es necesario para usar el
            * metodo intersects()
            */
            Rectangle area = getRectangulo();
            
            if (area.intersects(jug.getRectangulo())) {
                
                int xIncial = jug.getX();
                
                // Creamos un rectangulo auxiliar (/r/ectangulo de /Jug/ador).
                Rectangle rJug;
                
                /**
                 * Ahora le damos las mismas dimensiones y posicion
                 * que el rectangulo del jugador.
                 */
                rJug = jug.getRectangulo();
                
                /**
                 * Lo movemos un pixel a la izquierda. Esto es para saber si
                 * este intercepta con el rectangulo de nuestro obstaculo.
                 */
                rJug.setLocation(jug.getX() - 1 , jug.getY());
                
                if ( Teclado.isIZQUIERDA() && area.intersects(rJug) ) {
                    int nuevaPosicion = (x + ancho);
                    
                    /**
                     * Mientras la posición X de nuestro jugador NO tenga
                     * un cambio muy drastico, entonces está bien que esta
                     * cambie para que no colisione con nuestro obstaculo.
                     */
                    if (!((nuevaPosicion - jug.getX()) > (avance+1))) {
                        jug.setX(nuevaPosicion);
                    }
                }
                
                /**
                 * Ahora haremos lo mismo pero desplazando el rectangulo
                 * auxiliar un pixel a la derecha de la posicion original
                 * del cuadro del jugador. Como ya le habíamos restado 1
                 * entonces ahora hay que sumarle 2.
                 */
                rJug.setLocation(jug.getX() + 2 , jug.getY());
                
                if ( Teclado.isDERECHA() && area.intersects(rJug) ) {
                    int nuevaPosicion = (x - jug.getAncho());
                    if (!((jug.getX() - nuevaPosicion) > (avance+1))) {
                        jug.setX(nuevaPosicion);
                    }
                }
                
                /**
                 * Restamos 2 a la posición X para que regrese a su posición
                 * X original y ahora lo movemos un pixel hacía arriba.
                 */
                rJug.setLocation(jug.getX() - 2 , jug.getY() - 1);

                 if ( Teclado.isARRIBA() && area.intersects(rJug) ) {
                    int nuevaPosicion = (y + alto);
                    if (!((nuevaPosicion - jug.getY()) > (avance+1))) {
                        jug.setY(nuevaPosicion);
                    }
                }
                 
                rJug.setLocation(jug.getX() , jug.getY() + 2);
                
                if ( Teclado.isABAJO() && area.intersects(rJug) ) {
                    int nuevaPosicion = (y - jug.getAlto());
                    if (!((jug.getY() - nuevaPosicion) > (avance+1))) {
                        jug.setY(nuevaPosicion);
                    }
                }
                
                /**
                 * Si cambiamos la posicion del jugador, también debemos
                 * cambiar su desplazamiento.
                 */
                jug.setDesplazamiento(
                        ( jug.getDesplazamiento() - (xIncial - jug.getX()) ) );
            }
        } 
    }
  
    
/**
 * @author Milton Lenis
 * 
 * Esta fue la primera version del metodo "atravesable(..)"
 * funcionaba pero no tan bien como el actual.
 * 
 * Basicamente desactivaba las teclas que te permitian moverte hacia un sprite
 * pero eso no impedía que lo atravesara un poquito. Cuando lo terminé
 * se me ocurrió una mejor forma de hacerlo que es la que está en el metodo
 * "atravesble(..)" actual.
 */    
//    public void Atravesable(boolean atravesable, Jugador jugador) {
//        if (!atravesable) {
//            
//            /*
//             * Creamos un rectangulo con las dimensiones y posicion
//             * de nuestro obstaculo, esto es necesario para usar el
//             * metodo intersects()
//             */
//            
//            Rectangle area = getRectangulo();
//            
//            if (area.intersects(jugador.getRectangulo())) {
//                
//                Rectangle rLeft;
//                Rectangle rRight;
//                Rectangle rUp;
//                Rectangle rDown;
//                
//                rLeft = new Rectangle(  jugador.getX() - avance,
//                                        jugador.getY(),
//                                        jugador.getAncho(),
//                                        jugador.getAlto());
//                
//                rRight = new Rectangle( jugador.getX() + avance,
//                                        jugador.getY(),
//                                        jugador.getAncho(),
//                                        jugador.getAlto());
//                
//                rUp = new Rectangle(    jugador.getX(),
//                                        jugador.getY() - avance,
//                                        jugador.getAncho(),
//                                        jugador.getAlto());
//                
//                rDown = new Rectangle(  jugador.getX(),
//                                        jugador.getY() + avance,
//                                        jugador.getAncho(),
//                                        jugador.getAlto());
//                
//                if ( Teclado.isIZQUIERDA() && area.intersects(rLeft) ) {
//                    if (area.intersects(rUp) && area.intersects(rDown) ) {
//                            Teclado.setIZQUIERDA(false);
//                    }
//                }
//                if ( Teclado.isDERECHA() && area.intersects(rRight) ) {
//                    if (area.intersects(rUp) && area.intersects(rDown) ) {
//                            Teclado.setDERECHA(false);
//                    }
//                }
//                if ( Teclado.isARRIBA() && area.intersects(rUp) ) {
//                    if (area.intersects(rLeft) && area.intersects(rRight) ) {
//                            Teclado.setARRIBA(false);
//                    }
//                }
//                if ( Teclado.isABAJO() && area.intersects(rDown) ) {
//                    if (area.intersects(rLeft) && area.intersects(rRight) ) {
//                            Teclado.setABAJO(false);
//                    }
//                }
//            }
//        } 
//    }
    
    
    
    private Rectangle getRectangulo() {
        return new Rectangle(x, y, ancho, alto);
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
