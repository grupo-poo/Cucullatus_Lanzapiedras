package Personajes;

import Control.Teclado;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Milton Lenis
 */
public class Jugador {
    
    private byte pasos;   // Número de pasos.
    private int ancho, alto;    // Dimensiones de la imagen del Jugador.
    private int x, y;   // Posiciones.
    private int desplazamiento;
    private boolean distanciaCritica; // está en este punto de no avance ¿?
    private BufferedImage imagen;
    
    public Jugador(BufferedImage imagen) {
        this.imagen = imagen;
        
        // Por defecto el jugador tiene las dimensiones de la imagen original.
        ancho = imagen.getWidth();
        alto = imagen.getHeight();
        
        // Posiciones por defecto
        x = 40;     y = 590;
        
        desplazamiento = x;
        
        // Número de pixeles que se mueven con cada paso
        pasos = 3;
    }
    
    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, ancho, alto, null);
        
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
            if (desplazamiento < (distCrit + pasos) && Teclado.IZQUIERDA) {
                
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
            if (Teclado.IZQUIERDA) {
                x -= pasos;
                desplazamiento -= pasos;
            }else if (Teclado.DERECHA) {
                x += pasos;
                desplazamiento += pasos;
            }if (Teclado.ARRIBA) {
                y -= pasos;
            }if (Teclado.ABAJO) {
                y += pasos;
            }
        } else {
            if (Teclado.IZQUIERDA) {
                desplazamiento -= pasos;
            }else if (Teclado.DERECHA) {
                desplazamiento += pasos;
            }if (Teclado.ARRIBA) {
                y -= pasos;
            }if (Teclado.ABAJO) {
                y += pasos;
            }
        }
    }

    public BufferedImage getImagen() {
        return imagen;
    }
    
    public void setImagen(BufferedImage imagen) {
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
