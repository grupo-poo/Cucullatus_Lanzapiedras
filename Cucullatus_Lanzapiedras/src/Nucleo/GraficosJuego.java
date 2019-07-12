package Nucleo;

import Escenario.Corazones;
import Escenario.ObjetoInerte;
import Escenario.Pared;
import Escenario.Piedra;
import Personajes.Enemigo;
import Personajes.Jugador;
import java.io.IOException;
import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Screen;


/**
 * Dibujamos todo en el panel que va sobre la ventana del juego.
 * @author Milton Lenis
 * @author diegocarvajal
 */
public class GraficosJuego extends Canvas {
    private Jugador jugador;
    private ObjetoInerte fondoDePrueba;
    private ObjetoInerte mensajeDePrueba;
    private ObjetoInerte ObjetoFlotanteDePrueba1;
    private ObjetoInerte ObjetoFlotanteDePrueba2;
    private ObjetoInerte suelo1_DePrueba;
    private ObjetoInerte suelo2_DePrueba;
    private ObjetoInerte obstaculo1_DePrueba;
    private ObjetoInerte obstaculo2_DePrueba;
    private int cuenta=0;
    private Enemigo enemigo1;
    private ArrayList<Piedra> piedras;
   
    
    private ArrayList<Pared> paredes;
    
    private final int altoPantalla;
    private final int anchoPantalla;
    
    private boolean ejecutandose;

    public GraficosJuego( double anchoPantalla, double altoPantalla) throws IOException {
        super(anchoPantalla, altoPantalla);
        this.altoPantalla = (int) altoPantalla;
        this.anchoPantalla = (int) anchoPantalla;
        paredes= new ArrayList<>();
        piedras=new ArrayList<>();
        inicializar();
        
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * @throws IOException 
     */
    private void inicializar() throws IOException{
        ejecutandose = true;
        prepararJuego();
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * @throws IOException 
     */
    private void prepararJuego() throws IOException {
        cargarJugador();
        cargarEscenarioDePrueba();
        //cargarEnemigos(); PRÓXIMAMENTE
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * @throws IOException 
     */
    private void cargarJugador() throws IOException {
        // Se cargan la imagen del jugador        
        Image imagen = new Image("Nucleo/Recursos/Cucullatus.png");
        jugador = new Jugador(imagen);
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * @throws IOException 
     */
    private void cargarEscenarioDePrueba() throws IOException {
        // Se cargan la imagen del fondo
        Corazones.Setall(anchoPantalla/5,altoPantalla/500);
        
        Image imagen = new Image("Nucleo/Recursos/fondo.png");
        Image pared = new Image("Nucleo/Recursos/Pared.png");
        
        //Enemigo
        enemigo1=new Enemigo(2000,500,20,150,150);
        
        
        // Se crea el fondo y se le añade la imagen 
        fondoDePrueba = new ObjetoInerte(imagen);
        fondoDePrueba.setAlto(altoPantalla);
        
        
        // Se preparan los elementos del escenario
        
        /**
         * °°°°°°°°°°°°°°°°°°°°NOTA°°°°°°°°°°°°°°°°°°°°
         * Podemos cargar nuestro obstaculo de varias formas:
         * 
         * 1. podemos poner las coordenadas con el metodo
         * setCoordenadas(int, int) y dimensiones con setDimensiones(int, int).
         */
        imagen = new Image("Nucleo/Recursos/Mensaje.png");
        mensajeDePrueba = new ObjetoInerte(imagen);
        mensajeDePrueba.setCoordenadas(100, 400);
        mensajeDePrueba.setDimensiones(300, 150);
        
        /**
         * 2. Poner las coordenas en el contructor.
         */
        imagen = new Image("Nucleo/Recursos/Madera.png");
        ObjetoFlotanteDePrueba1 = new ObjetoInerte(imagen, 502, 402);
        ObjetoFlotanteDePrueba1.setDimensiones(47, 40);
        
        /**
         * 3. Poner TODO en el constructor.
         */
        ObjetoFlotanteDePrueba2 = new ObjetoInerte(imagen, 502, 202, 94, 40);
        
        /**
         * 4, 5, 6, 7... y muchas otras pero me da flojera harcerlas todas.
         * Ah, y el orden no importa.
         */
        System.out.println(this.anchoPantalla + " "+ this.altoPantalla);
        suelo1_DePrueba = new ObjetoInerte(imagen);
        suelo1_DePrueba.setDimensiones(this.anchoPantalla/3, this.altoPantalla/9);
        suelo1_DePrueba.setX(0);
        suelo1_DePrueba.setY(altoPantalla - suelo1_DePrueba.getAlto());
        
        //Piedra
        
        AñadirPiedra(suelo1_DePrueba.getX()+400,suelo1_DePrueba.getY()-30,40,30);
        AñadirPiedra(suelo1_DePrueba.getX()+300,suelo1_DePrueba.getY()-30,40,30);
        //Pared
        
        this.añadirPared(pared,suelo1_DePrueba.getX()+160,suelo1_DePrueba.getY()-90, 90,90);
        // 
        suelo2_DePrueba = new ObjetoInerte(imagen);
        suelo2_DePrueba.setCoordenadas(suelo1_DePrueba.getX()+suelo1_DePrueba.getAncho()+200, suelo1_DePrueba.getY());
        Rectangle2D dim = Screen.getPrimary().getBounds();
        suelo2_DePrueba.setAncho((int) dim.getWidth()-suelo2_DePrueba.getX());
        suelo2_DePrueba.setAlto(100);
        suelo2_DePrueba.setAlargamiento(true);
        
        obstaculo1_DePrueba = new ObjetoInerte(imagen);
        obstaculo1_DePrueba.setAncho(150);
        obstaculo1_DePrueba.setAlto(300);
        obstaculo1_DePrueba.setX(850);
        obstaculo1_DePrueba.setY(suelo1_DePrueba.getY() - obstaculo1_DePrueba.getAlto());
        
        obstaculo2_DePrueba = new ObjetoInerte(imagen);
        obstaculo2_DePrueba.setAncho(150);
        obstaculo2_DePrueba.setX(800);         
        obstaculo2_DePrueba.setAlto(40);       
        obstaculo2_DePrueba.setY(obstaculo1_DePrueba.getY() - obstaculo2_DePrueba.getAlto());
        
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * @param lapiz 
     */
    private void dibujar(GraphicsContext lapiz) {

        fondoDePrueba.dibujar(lapiz);
        for(int i=0; i<paredes.size();i++){
            paredes.get(i).dibujar(lapiz);
        }
        for(int i=0; i<piedras.size();i++){
            piedras.get(i).dibujar(lapiz);
        }
        
        
        mensajeDePrueba.dibujar(lapiz);
        ObjetoFlotanteDePrueba1.dibujar(lapiz);
        ObjetoFlotanteDePrueba2.dibujar(lapiz);
        suelo1_DePrueba.dibujar(lapiz);
        suelo2_DePrueba.dibujar(lapiz);
        obstaculo1_DePrueba.dibujar(lapiz);
        obstaculo2_DePrueba.dibujar(lapiz);
        jugador.dibujar(lapiz);
        Corazones.dibujar(lapiz, jugador.getVida());
        enemigo1.dibujar(lapiz,jugador);
        
        if(cuenta<=10){
                if(cuenta == 10) {
                    cuenta = 0;
                    if(jugador.getVida() == 10) jugador.setVida(0);
                    else jugador.setVida(jugador.getVida()+1); 
                 }
                else cuenta++;
            }
        
        
        
        //////////////////////////////////////////
        /**
         * Todo esto puede ser eliminado, solo sirve para depurar.
         */
        new Debug(lapiz);
        //////////////////////////////////////////
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     */
    private void actualizar(){
        // Agregamos los obstaculos a una colección
        ArrayList<ObjetoInerte> obstaculos = new ArrayList<ObjetoInerte>();
        obstaculos.add(suelo1_DePrueba);
        obstaculos.add(suelo2_DePrueba);
        obstaculos.add(obstaculo1_DePrueba);
        obstaculos.add(obstaculo2_DePrueba);
        obstaculos.add(ObjetoFlotanteDePrueba1);
        obstaculos.add(ObjetoFlotanteDePrueba2);
        
        jugador.actualizar(anchoPantalla , obstaculos);
        fondoDePrueba.actualizar(jugador);
        mensajeDePrueba.actualizar(jugador);
        ObjetoFlotanteDePrueba1.actualizar(jugador);
        ObjetoFlotanteDePrueba2.actualizar(jugador);
        suelo1_DePrueba.actualizar(jugador);
        suelo2_DePrueba.actualizar(jugador);
        obstaculo1_DePrueba.actualizar(jugador);
        obstaculo2_DePrueba.actualizar(jugador);
        jugador.Graffitear(paredes);
        jugador.RecogerPiedra(piedras);
        enemigo1.actualizar(jugador);
        
        for(int i=0; i<paredes.size();i++){
            paredes.get(i).actualizar(jugador);
        }
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     */
    public void repintar() {
        GraphicsContext lapiz = this.getGraphicsContext2D();
        lapiz.clearRect(0, 0, anchoPantalla, altoPantalla);
        dibujar(lapiz);
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
    
    
    public void añadirPared(Image imagen, int x, int y, int ancho, int alto){
        Pared pared=new Pared(imagen, x, y, ancho, alto);
        paredes.add(pared);
    }
    
    public void AñadirPiedra(int x, int y, int ancho, int alto){
        piedras.add(new Piedra(x, y, ancho, alto));
    }
    
}