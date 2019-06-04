package Nucleo;

import Escenario.Fondo;
import Escenario.Mensaje;
import Escenario.Obstaculo;
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
    private Obstaculo tierra;
    private Obstaculo suelo;
    private Obstaculo suelo2;
    private Obstaculo obstaculo1;
    
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
        mensaje1.setX(100);     
        mensaje1.setY(400);
        mensaje1.setAncho(300); 
        mensaje1.setAlto(150);
        
        url = this.getClass().getResource("Fondos/Madera.png");
        imagen = ImageIO.read(url);
        tierra = new Obstaculo(imagen, jugador.getPasos());
        tierra.setX(502);               
        tierra.setY(402);
        tierra.setAncho(47);            
        tierra.setAlto(40);
        tierra.setAtravesable(false);
        
        suelo = new Obstaculo(imagen, jugador.getPasos());
        suelo.setAncho(600);           
        suelo.setAlto(100);
        suelo.setX(0);                
        suelo.setY(altoPantalla - suelo.getAlto());
        suelo.setAtravesable(false);
        
        suelo2 = new Obstaculo(imagen, jugador.getPasos());
        suelo2.setAncho(600);           
        suelo2.setAlto(100);
        suelo2.setX(700);                
        suelo2.setY(suelo.getY());
        suelo2.setAtravesable(false);
        
        obstaculo1 = new Obstaculo(imagen, jugador.getPasos());
        obstaculo1.setAncho(150);           
        obstaculo1.setAlto(300);
        obstaculo1.setX(850);                
        obstaculo1.setY(suelo.getY() - obstaculo1.getAlto());
        obstaculo1.setAtravesable(false);
        
    }
    
    private void dibujar(Graphics g) {
        fondo1.dibujar(g);
        mensaje1.dibujar(g);
        tierra.dibujar(g);
        suelo.dibujar(g);
        suelo2.dibujar(g);
        obstaculo1.dibujar(g);
        jugador.dibujar(g);
    }
    
    private void actualizar(){
        
        jugador.actualizar(anchoPantalla);
        fondo1.actualizar(jugador);
        mensaje1.actualizar(jugador);
        tierra.actualizar(jugador);
        suelo.actualizar(jugador);
        suelo2.actualizar(jugador);
        obstaculo1.actualizar(jugador);
        
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
