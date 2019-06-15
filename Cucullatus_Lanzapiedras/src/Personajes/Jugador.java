package Personajes;

import Control.Teclado;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * @author Milton Lenis
 */
public class Jugador {
    
    private byte pasos;   // Número de pasos.
    private int ancho, alto;    // Dimensiones de la imagen del Jugador.
    private int x, y;   // Posiciones.
    private int desplazamiento;
    private boolean distanciaCritica; // está en este punto de no avance ¿?
    private Image imagen;
    
    public Jugador(Image imagen) {
        this.imagen = imagen;
        
        // Por defecto el jugador tiene las dimensiones de la imagen original.
        ancho = (int) imagen.getWidth();
        alto  = (int) imagen.getHeight();
        
        // Posiciones por defecto
        x = 40;     y = 550;
        
        desplazamiento = x;
        
        // Número de pixeles que se mueven con cada paso
        pasos = 3;
    }
    
    public void dibujar(GraphicsContext lapiz) {
        lapiz.drawImage(imagen, x, y, ancho, alto);
        
        /* Esto de aquí sirve para depurar comentando lo de arriba y
           dejando lo de abajo
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.red);
        g2.fill(new Rectangle2D.Double(x, y, ancho, alto)); */
        
    }
    
    public void actualizar(int anchoDePantalla) {
        
        /**
         * Cuando el jugador esté a una determinada distancia ya no podrá
         * seguir avanzando hacia los lados en la pantalla.
         */
        int distCrit = ((anchoDePantalla / 2) - 2*ancho);
        
        if (desplazamiento >= distCrit) {
            
            /**
             * Superar esta "distancia critica" nos pone en una zona en la que
             * el jugador ya no se puede desplazar hacia los lados, pero
             * tratar de salir de esta zona regresando al punto donde
             * comenzamos genera errores que se evitan si cuando hayamos
             * superardo la distancia critica pero aún estemos en su borde
             * habilitamos únicamente el movimiento hacia la izquierda.
             */
            if (desplazamiento < (distCrit + pasos) && Teclado.isIZQUIERDA()) {
                
                distanciaCritica = false;
            ////
            } else {
            distanciaCritica = true;
            }
        } else {
            distanciaCritica = false;
        }
        
        mover();
    }
    
    private void mover() {
        // El jugador se moverá mientras no haya superado la distancia critica.
        
        if (!distanciaCritica) {
            if (Teclado.isIZQUIERDA()) {
                x -= pasos;
                desplazamiento -= pasos;
            }else if (Teclado.isDERECHA()) {
                x += pasos;
                desplazamiento += pasos;
            }if (Teclado.isARRIBA()) {
                y -= pasos;
            }if (Teclado.isABAJO()) {
                y += pasos;
            }
        } else {
            if (Teclado.isIZQUIERDA()) {
                desplazamiento -= pasos;
            }else if (Teclado.isDERECHA()) {
                desplazamiento += pasos;
            }if (Teclado.isARRIBA()) {
                y -= pasos;
            }if (Teclado.isABAJO()) {
                y += pasos;
            }
        }
    }
    
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, ancho, alto);
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public boolean isDistanciaCritica() {
        return distanciaCritica;
    }

    public void setDistanciaCritica(boolean distanciaCritica) {
        this.distanciaCritica = distanciaCritica;
    }
    
    public int getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(int desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

    public byte getPasos() {
        return pasos;
    }

    public void setPasos(byte pasos) {
        this.pasos = pasos;
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
