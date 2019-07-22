package Escenario;

import Personajes.Jugador;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Milton Lenis
 */
public class ObjetoInerte extends ObjetoEscenario{
    
    public ObjetoInerte(Image imagen) {
        this.imagen = imagen;
        this.ancho = (int) imagen.getWidth();
        this.alto = (int) imagen.getHeight();
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
    @Override
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
            this.x -= jugador.getVelocidadHorizontal();
        }
    }
    
}
