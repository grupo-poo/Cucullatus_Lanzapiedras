package Escenario;

import Personajes.Jugador;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Milton Lenis
 */
public class Portal extends ObjetoInerte{
    
    private boolean abrirPortal;
    private boolean transportar;
    
    public Portal(Image imagen) {
        super(imagen);
    }
    
    @Override
    public void dibujar(GraphicsContext lapiz) {
        if (abrirPortal) {
            lapiz.drawImage(imagen, imagen.getWidth()/2, 0, imagen.getWidth()/2, imagen.getHeight(), x, y, ancho, alto);
        } else {
            lapiz.drawImage(imagen, 0, 0, imagen.getWidth()/2, imagen.getHeight(), x, y, ancho, alto);
        }
    }
    
    public void actualizar(Jugador jugador, Pared[] paredes) {        
        if (jugador.isDistanciaCritica()) {
            x -= jugador.getVelocidadHorizontal();
        }
        abrirPortal = estadoTransportacion(paredes, (byte)2);
        transportar = abrirPortal && intersectJugador(jugador);
    }
    
    private boolean estadoTransportacion(Pared[] paredes, byte arraySize) {
        if (arraySize < 0) return true;
        boolean p = paredes[arraySize].isIsGraffiteada();
        return p && estadoTransportacion(paredes, (byte)(arraySize - 1));
    }
    
    private boolean intersectJugador(Jugador jugador) {
        return getRectangulo().intersects(jugador.getRectangulo().getBoundsInLocal());
    }

    public boolean isTransportar() {
        return transportar;
    }

    public void setTransportar(boolean transportar) {
        this.transportar = transportar;
    }
    
}
