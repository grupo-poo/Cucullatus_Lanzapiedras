package Escenario;

import ElementosEscenario.Fondo;
import Personajes.Jugador;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * @author Nicolas Hoyos
 * @author Nicolas Mejia
 * @author Milton Lenis
 * @author Diego Carvajal
 */
public class Principal {
    
    private static Ventana ventana;
    
    public static void main(String[] args) throws IOException {
        ventana = new Ventana();
        new Principal().ejecutar();
    }
    
    private Jugador jugador;
    private Fondo fondo1;
    
    public void ejecutar() throws IOException {
        Graphics g = ventana.getGraphics();
        
        prepararEscenario();
        dibujar(g);
    }
    
    public void prepararEscenario() throws IOException {
        //Se cargan las imagenes
        URL url = this.getClass().getResource("Fondos/fondo.png");
        BufferedImage imagen = ImageIO.read(url);
        
        //Se crean el fondo
        fondo1 = new Fondo(imagen);
        
        //Se establecen las dimensiones para el fondo
        fondo1.setAlto(ventana.getHeight());
        fondo1.setAncho(ventana.getWidth());
        
        //se prepara el jugador
        url = this.getClass().getResource("Sprites/Cucullatus.png");
        imagen = ImageIO.read(url);
        jugador = new Jugador(imagen);
    }
    
    public void dibujar(Graphics g) {
        fondo1.dibujar(g);
        jugador.dibujar(g);
    }
}
