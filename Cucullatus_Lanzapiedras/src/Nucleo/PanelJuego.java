package Nucleo;

import Escenario.Fondo;
import Escenario.Mensaje;
import Personajes.Jugador;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import Control.Teclado;


/**
 * Dibujamos todo en el panel que va sobre la ventana del juego.
 * @author Milton Lenis
 */
public class PanelJuego extends JPanel  {
    
    private Jugador jugador;
    private Teclado teclado;
    private Fondo fondo1;
    private Mensaje mensaje1;
    private int altoPantalla;
    private int anchoPantalla;
    private boolean ejecutandose;
    
    public PanelJuego(int altoPantalla, int anchoPantalla) throws Exception {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.teclado=new Teclado();
        
        //Hace que desde el panel se realicen controles de teclado 
        this.setFocusable(true);
       
        addKeyListener(teclado);
       
        inicializar();
    }
    
    private void inicializar() throws IOException{
        ejecutandose = true;
        prepararJuego();
    }
    
    private void prepararJuego() throws IOException {
        prepararEscenario();
    }
    
    public void prepararEscenario() throws IOException {
        //Se cargan las imagenes
        URL url = this.getClass().getResource("Fondos/fondo.png");
        BufferedImage imagen = ImageIO.read(url);

        //Se crean el fondo
        fondo1 = new Fondo(imagen);
        fondo1.setAlto(altoPantalla);
        
        //Se preparan los elementos del escenario
        url = this.getClass().getResource("Fondos/Mensaje.png");
        imagen = ImageIO.read(url);
        mensaje1 = new Mensaje(imagen);
        mensaje1.setX(100);
        mensaje1.setY(400);
        mensaje1.setAncho(300);
        mensaje1.setAlto(150);
        
        //se prepara el jugador
        url = this.getClass().getResource("Sprites/Cucullatus.png");
        imagen = ImageIO.read(url);
        jugador = new Jugador(imagen);
        //Asignacion del jugador al teclado, quizás haya que refinarlo
        teclado.jugador=jugador;
    }
    
    
    
    
    public void dibujar(Graphics g) {
        fondo1.dibujar(g);
        mensaje1.dibujar(g);
        jugador.dibujar(g);
    }
    
    public void actualizar(){
        fondo1.actualizar();
        mensaje1.actualizar();
        
        //En cada actualizacion se toma en cuenta las teclas presionadas para así modificar su posición
        teclado.actualizar();
      
        
        
    }
    

    public boolean isEjecutandose() {
        return ejecutandose;
    }

    
    
    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
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
    
    
    
}
