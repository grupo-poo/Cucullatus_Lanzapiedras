package Nucleo;

import javafx.scene.shape.Rectangle;

/**
 * @author Milton Lenis
 */
public class ObjetoEscenario {
    private int ancho, alto; // Dimensiones de la imagen del Jugador.
    private int x, y; // Coordenadas del jugador en la pantalla.
    
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, ancho, alto);
    }
}
