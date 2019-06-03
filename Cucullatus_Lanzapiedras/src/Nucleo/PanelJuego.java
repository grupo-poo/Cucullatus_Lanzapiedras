package Nucleo;

import Escenario.Fondo;
import Escenario.Mensaje;
import Personajes.Jugador;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * Dibujamos todo en el panel que va sobre la ventana del juego.
 * @author Milton Lenis
 * @author diegocarvajal
 */
public class PanelJuego extends JPanel  {
    
    private Jugador jugador;
    private Fondo fondo1;
    private Mensaje mensaje1;
    
    private final int altoPantalla;
    private final int anchoPantalla;
    
    private boolean ejecutandose;
    
    public PanelJuego(int altoPantalla, int anchoPantalla) throws Exception {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        inicializar();
    }
    
    private void inicializar() throws IOException{
        ejecutandose = true;
        //prepararMenu(); PRÓXIMAMENTE
        prepararJuego();
    }
    
    private void prepararJuego() throws IOException {
        cargarJugador();
        cargarEscenario();
        //cargarEnemigos(); PRÓXIMAMENTE
    }
    
    private void cargarJugador() throws IOException {
        // Se cargan la imagen del jugador
        URL url = this.getClass().getResource("Sprites/Cucullatus.png");
        BufferedImage imagen = ImageIO.read(url);
        jugador = new Jugador(imagen);
    }
    
    private void cargarEscenario() throws IOException {
        // Se cargan la imagen del fondo
        URL url = this.getClass().getResource("Fondos/fondo.png");
        BufferedImage imagen = ImageIO.read(url);

        // Se crea el fondo y se le añade la imagen
        fondo1 = new Fondo(imagen, jugador.getPasos());
        fondo1.setAlto(altoPantalla);
        
        // Se preparan los elementos del escenario
        url = this.getClass().getResource("Fondos/Mensaje.png");
        imagen = ImageIO.read(url);
        mensaje1 = new Mensaje(imagen, jugador.getPasos());
        mensaje1.setX(100);     mensaje1.setY(400);
        mensaje1.setAncho(300); mensaje1.setAlto(150);
    }
    
    private void dibujar(Graphics g) {
        fondo1.dibujar(g);
        mensaje1.dibujar(g);
        jugador.dibujar(g);
    }
    
    private void actualizar(){
        jugador.actualizar(anchoPantalla);
        fondo1.actualizar(jugador);
        mensaje1.actualizar(jugador);
    }
    
    /**
     * Este metodo heredado de JPanel se ejecuta sin necesidad de llamarlo.
     * Sobre él ponemos todo lo que se mostrará en pantalla.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujar(g);
        actualizar();
    }

    /*
    * Luego de aquí en adelante vendrán todos los getters y setters
    * de los atributos del juego, pero por ahora para que no se vea
    * tan complejo el código no los pondremos.
    */

    public boolean isEjecutandose() {
        return ejecutandose;
    }
    
}
