package Nucleo;

import Escenario.Fondo;
import Escenario.Mensaje;
import Escenario.Obstaculo;
import Personajes.Jugador;
import java.io.File;
import java.io.IOException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Dibujamos todo en el panel que va sobre la ventana del juego.
 * @author Milton Lenis
 * @author diegocarvajal
 */
public class GraficosJuego extends Canvas {
    GraphicsContext lapiz;
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

    public GraficosJuego( double anchoPantalla, double altoPantalla) throws IOException {
        super(anchoPantalla, altoPantalla);
        lapiz = this.getGraphicsContext2D();
        this.altoPantalla = (int) altoPantalla;
        this.anchoPantalla = (int) anchoPantalla;
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
        File url = new File("src/Recursos/Cucullatus.png");
        Image imagen = new Image(url.toURI().toString());
        jugador = new Jugador(imagen);
    }
    
    private void cargarEscenario() throws IOException {
        // Se cargan la imagen del fondo
        File url = new File("src/Recursos//fondo.png");
        Image imagen = new Image(url.toURI().toString());

        // Se crea el fondo y se le añade la imagen
        fondo1 = new Fondo(imagen, jugador.getPasos());
        fondo1.setAlto(altoPantalla);
        
        // Se preparan los elementos del escenario
        url = new File("src/Recursos/Mensaje.png");
        imagen = new Image(url.toURI().toString());
        mensaje1 = new Mensaje(imagen, jugador.getPasos());
        mensaje1.setX(100);     
        mensaje1.setY(400);
        mensaje1.setAncho(300); 
        mensaje1.setAlto(150);
        
        url = new File("src/Recursos/Madera.png");
        imagen = new Image(url.toURI().toString());
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
    
    private void dibujar() {
        fondo1.dibujar(lapiz);
        mensaje1.dibujar(lapiz);
        tierra.dibujar(lapiz);
        suelo.dibujar(lapiz);
        suelo2.dibujar(lapiz);
        obstaculo1.dibujar(lapiz);
        jugador.dibujar(lapiz);
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
    
    public void repintar() {
        lapiz.clearRect(0, 0, anchoPantalla, altoPantalla);
        dibujar();
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
