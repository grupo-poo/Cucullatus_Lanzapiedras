package Escenario;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * @author diegocarvajal
 * @author Milton Lenis
 */
public class ObjetoRecogible extends ObjetoInerte {
    
    private boolean visible;
    
    public ObjetoRecogible(Image imagen){
        super(imagen);
        this.ancho = 40;
        this.alto = 30;
        this.visible = true;
    }
    
    @Override
    public void dibujar(GraphicsContext lapiz){
        if(visible){
            lapiz.drawImage(imagen, x, y,ancho,alto);
        }
    }
    
    public boolean desplazarseDerecha(ArrayList<ObjetoInerte> obstaculos , int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setX(x + velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    x = obs.getX() - ancho;
                    break;
                }
            }
        }
        if (viaLibre) { x += velocidad; }
        return viaLibre;
    }
    
    public boolean desplazarseIzquierda(ArrayList<ObjetoInerte> obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setX(x - velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    x = obs.getX() + obs.getAncho();
                    break;
                }
            }
        }
        if (viaLibre) { x -= velocidad; }
        return viaLibre;
    }
    
    public boolean desplazarseArriba(ArrayList<ObjetoInerte> obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setY(y - velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() + obs.getAlto();
                    break;
                }
            }
        }
        if (viaLibre) { y -= velocidad; }
        return viaLibre;
    }
    
    public boolean desplazarseAbajo(ArrayList<ObjetoInerte> obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Clon = getRectangulo();
        Clon.setY(y + velocidad);
        for (ObjetoInerte obs : obstaculos) {
            if (Clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Clon, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() - alto;
                    break;
                }
            }
        }
        if (viaLibre) { y += velocidad; }
        return viaLibre;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * @param clon rectangulo con las dimensiones del jugador
     * @param obstaculo rectangulo con las dimensiones del obstaculo con el que colisiona.
     * @return true si la colision con un obstaculo es por la derecha o por la izquierda.
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
     */
    private boolean ObstaculoDirVertical(Rectangle clon, Rectangle obstaculo) {
        return !(clon.getBoundsInLocal().getMaxX() == obstaculo.getBoundsInLocal().getMinX()
                || clon.getBoundsInLocal().getMinX() == obstaculo.getBoundsInLocal().getMaxX());
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
}
