package Nucleo;

import Escenario.*;
import Personajes.*;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Dibujamos todo en el panel que va sobre la ventana del juego.
 * @author Milton Lenis
 * @author diegocarvajal
 */
public class GraficosJuego extends Canvas {
    private Jugador jugador;
    private ObjetoInerte paredLimiteIzquierdo;
    private ObjetoInerte fondoDePrueba;
    private ObjetoInerte mensajeDePrueba;
    private ObjetoInerte mensaje2DePrueba;
    private ObjetoInerte ObjetoFlotanteDePrueba1;
    private ObjetoInerte ObjetoFlotanteDePrueba2;
    private ObjetoInerte suelo1_DePrueba;
    private ObjetoInerte suelo2_DePrueba;
    private ObjetoInerte suelo3_DePrueba;
    private ObjetoInerte obstaculo1_DePrueba;
    private ObjetoInerte obstaculo2_DePrueba;
    private ObjetoInerte obstaculo3_DePrueba;
    private Pared pared1;
    private Pared pared2;
    private Pared pared3;
    private Piedra piedra1;
    private Piedra piedra2;
    private Piedra aerosol1;
    private Piedra aerosol2;
    private Piedra aerosol3;
    private Enemigo enemigo1;
    private Enemigo enemigo2;
    private Portal portal;
    
    private final int altoPantalla;
    private final int anchoPantalla;
    
    private boolean ejecutandose;

    public GraficosJuego( double anchoPantalla, double altoPantalla) throws IOException {
        super(anchoPantalla, altoPantalla);
        this.altoPantalla = (int) altoPantalla;
        this.anchoPantalla = (int) anchoPantalla;
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
        cargarEnemigos();
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * @throws IOException 
     */
    private void cargarJugador() throws IOException {
        // Se cargan la imagen del jugador        
        Image imagen = new Image("Nucleo/Recursos/Cucullatus.png");
        jugador = new Jugador(imagen, anchoPantalla, altoPantalla);
    }
    
    private void cargarEnemigos() {
        enemigo1 = new Enemigo(anchoPantalla, altoPantalla);
        enemigo1.setX(suelo2_DePrueba.getX() + suelo2_DePrueba.getAncho() - obstaculo3_DePrueba.getAncho() - enemigo1.getAncho() - 1);
        enemigo1.setY(suelo2_DePrueba.getY() - enemigo1.getAlto());
        enemigo1.setAnimacion1(true);
        
        enemigo2 = new Enemigo(anchoPantalla, altoPantalla);
        enemigo2.setX(suelo3_DePrueba.getX() + (suelo3_DePrueba.getAncho() / 2));
        enemigo2.setY(suelo3_DePrueba.getY() - enemigo2.getAlto());
        enemigo2.setAnimacion2(true);
        enemigo2.setLanzarPiedra(true);
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * @throws IOException 
     */
    private void cargarEscenarioDePrueba() throws IOException {
        // Se cargan la imagen del fondo
        Corazones.Setall(anchoPantalla/5,altoPantalla/500);
        
        Image imagen = new Image("Nucleo/Recursos/Fondo.png");
        
        //Se crea el fondo y se le añade la imagen 
        fondoDePrueba = new ObjetoInerte(imagen);
        fondoDePrueba.setAlto(altoPantalla);
        
        //Se preparan los elementos del escenario
        imagen = new Image("Nucleo/Recursos/Mensaje.png");
        mensajeDePrueba = new ObjetoInerte(imagen, 100, 400, 300, 150);
        
        imagen = new Image("Nucleo/Recursos/Mensaje2.png");
        mensaje2DePrueba = new ObjetoInerte(imagen, 100, 250, 150, 140);
        
        imagen = new Image("Nucleo/Recursos/Madera.png");
        paredLimiteIzquierdo = new ObjetoInerte(imagen); // borde para que no caiga
        paredLimiteIzquierdo.setDimensiones(1, altoPantalla);
        paredLimiteIzquierdo.setCoordenadas(-1, 0);
        
        ObjetoFlotanteDePrueba1 = new ObjetoInerte(imagen, 502, 425, 47, 40);
        
        ObjetoFlotanteDePrueba2 = new ObjetoInerte(imagen, 502, 202, 94, 40);
        
        suelo1_DePrueba = new ObjetoInerte(imagen);
        suelo1_DePrueba.setCoordenadas(0, altoPantalla - altoPantalla / 9);
        suelo1_DePrueba.setDimensiones(anchoPantalla / 3, altoPantalla / 9);
        
        
        suelo2_DePrueba = new ObjetoInerte(imagen);
        suelo2_DePrueba.setX(suelo1_DePrueba.getX() + suelo1_DePrueba.getAncho() + 200);
        suelo2_DePrueba.setY(suelo1_DePrueba.getY());
        suelo2_DePrueba.setDimensiones(anchoPantalla + 100 - suelo2_DePrueba.getX() , 180);
        
        suelo3_DePrueba = new ObjetoInerte(imagen);
        suelo3_DePrueba.setDimensiones(suelo2_DePrueba.getAncho(), suelo2_DePrueba.getAlto());
        suelo3_DePrueba.setX(suelo2_DePrueba.getX() + suelo2_DePrueba.getAncho() + 210);
        suelo3_DePrueba.setY(suelo2_DePrueba.getY());
        
        obstaculo1_DePrueba = new ObjetoInerte(imagen);
        obstaculo1_DePrueba.setDimensiones(150, 300);
        obstaculo1_DePrueba.setX(850);
        obstaculo1_DePrueba.setY(suelo1_DePrueba.getY() - obstaculo1_DePrueba.getAlto());
        
        obstaculo2_DePrueba = new ObjetoInerte(imagen);
        obstaculo2_DePrueba.setDimensiones(150, 40);
        obstaculo2_DePrueba.setX(800);         
        obstaculo2_DePrueba.setY(obstaculo1_DePrueba.getY() - obstaculo2_DePrueba.getAlto());
        
        obstaculo3_DePrueba = new ObjetoInerte(imagen);
        obstaculo3_DePrueba.setDimensiones(40, 110);
        obstaculo3_DePrueba.setX(suelo2_DePrueba.getX() + suelo2_DePrueba.getAncho() - 40);
        obstaculo3_DePrueba.setY(suelo2_DePrueba.getY() - obstaculo3_DePrueba.getAlto());
        
        //Piedra
        imagen = new Image("Nucleo/Recursos/Piedra/Piedra.png");
        piedra1 = new Piedra(imagen);
        piedra1.setX(suelo1_DePrueba.getX() + 250);
        piedra1.setY(suelo1_DePrueba.getY() - piedra1.getAlto());
        
        piedra2 = new Piedra(imagen);
        piedra2.setX(suelo1_DePrueba.getX() + 350);
        piedra2.setY(suelo1_DePrueba.getY() - piedra2.getAlto());
        
        imagen = new Image("Nucleo/Recursos/Aerosol.png");
        aerosol1 = new Piedra(imagen);
        aerosol1.setAncho(20);
        aerosol1.setAlto(60);
        aerosol1.setX(suelo1_DePrueba.getX() + suelo1_DePrueba.getAncho() - aerosol1.getAncho());
        aerosol1.setY(suelo1_DePrueba.getY() - aerosol1.getAlto());
        
        imagen = new Image("Nucleo/Recursos/Aerosol.png");
        aerosol2 = new Piedra(imagen);
        aerosol2.setAncho(20);
        aerosol2.setAlto(60);
        aerosol2.setX(ObjetoFlotanteDePrueba1.getX() + ObjetoFlotanteDePrueba1.getAncho() - aerosol2.getAncho());
        aerosol2.setY(ObjetoFlotanteDePrueba1.getY() - aerosol2.getAlto());
        
        imagen = new Image("Nucleo/Recursos/Aerosol.png");
        aerosol3 = new Piedra(imagen);
        aerosol3.setAncho(20);
        aerosol3.setAlto(60);
        aerosol3.setX(obstaculo2_DePrueba.getX() + obstaculo2_DePrueba.getAncho() - aerosol3.getAncho());
        aerosol3.setY(obstaculo2_DePrueba.getY() - aerosol3.getAlto());
        
        //Pared
        imagen = new Image("Nucleo/Recursos/Pared.png");
        pared1 = new Pared(imagen);
        pared1.setAncho(90);
        pared1.setAlto(90);
        pared1.setX(suelo1_DePrueba.getX() + 100);
        pared1.setY(suelo1_DePrueba.getY() - pared1.getAlto());
        
        pared2 = new Pared(imagen);
        pared2.setAncho(pared1.getAncho());
        pared2.setAlto(pared1.getAlto());
        pared2.setX(pared1.getX() + pared1.getAncho() + 20);
        pared2.setY(pared1.getY());
        
        pared3 = new Pared(imagen);
        pared3.setAncho(pared1.getAncho());
        pared3.setAlto(pared1.getAlto());
        pared3.setX(pared2.getX() + pared1.getAncho() + 20);
        pared3.setY(pared1.getY());
        
        //Transportador
        imagen = new Image("Nucleo/Recursos/Portal.png");
        portal = new Portal(imagen);
        portal.setAncho(jugador.getAncho());
        portal.setAlto(jugador.getAlto());
        portal.setX(jugador.getX());
        portal.setY(suelo1_DePrueba.getY() - portal.getAlto());
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * @param lapiz 
     */
    private void dibujar(GraphicsContext lapiz) {

        fondoDePrueba.dibujar(lapiz);
        Corazones.dibujar(lapiz, jugador.getVida());
        pared1.dibujar(lapiz);
        pared2.dibujar(lapiz);
        pared3.dibujar(lapiz);
        piedra1.dibujar(lapiz);
        piedra2.dibujar(lapiz);
        aerosol1.dibujar(lapiz);
        aerosol2.dibujar(lapiz);
        aerosol3.dibujar(lapiz);
        mensajeDePrueba.dibujar(lapiz);
        mensaje2DePrueba.dibujar(lapiz);
        ObjetoFlotanteDePrueba1.dibujar(lapiz);
        ObjetoFlotanteDePrueba2.dibujar(lapiz);
        suelo1_DePrueba.dibujar(lapiz);
        suelo2_DePrueba.dibujar(lapiz);
        suelo3_DePrueba.dibujar(lapiz);
        obstaculo3_DePrueba.dibujar(lapiz);
        obstaculo1_DePrueba.dibujar(lapiz);
        obstaculo2_DePrueba.dibujar(lapiz);
        enemigo1.dibujar(lapiz,jugador);
        enemigo2.dibujar(lapiz,jugador);
        portal.dibujar(lapiz);
        jugador.dibujar(lapiz);
        
        //////////////////////////////////////////
        /**
         * Todo esto puede ser eliminado, solo sirve para depurar.
         */
        new Debug(lapiz);
        Debug.lapiz.fillText("fondo: " + fondoDePrueba.getX(), 20, 114);
        //////////////////////////////////////////
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     */
    private void actualizar() throws IOException{
        // Agregamos los obstaculos a una colección
        ArrayList<ObjetoInerte> obstaculos = new ArrayList<>();
        obstaculos.add(paredLimiteIzquierdo);
        obstaculos.add(suelo1_DePrueba);
        obstaculos.add(suelo2_DePrueba);
        obstaculos.add(suelo3_DePrueba);
        obstaculos.add(obstaculo3_DePrueba);
        obstaculos.add(obstaculo1_DePrueba);
        obstaculos.add(obstaculo2_DePrueba);
        obstaculos.add(ObjetoFlotanteDePrueba1);
        obstaculos.add(ObjetoFlotanteDePrueba2);
        
        ArrayList<Pared> paredes = new ArrayList<>(3);
        paredes.add(pared1);
        paredes.add(pared2);
        paredes.add(pared3);
        
        ArrayList<Enemigo> enemigos = new ArrayList<>();
        enemigos.add(enemigo1);
        enemigos.add(enemigo2);
        
        ArrayList<Piedra> piedras = new ArrayList<>();
        piedras.add(piedra1);
        piedras.add(piedra2);
        
        ArrayList<Piedra> aerosoles = new ArrayList<>(3);
        aerosoles.add(aerosol1);
        aerosoles.add(aerosol2);
        aerosoles.add(aerosol3);
                
        jugador.actualizar(obstaculos, enemigos, paredes, piedras, aerosoles);
        portal.actualizar(jugador, paredes);
        pared1.actualizar(jugador);
        pared2.actualizar(jugador);
        pared3.actualizar(jugador);
        piedra1.actualizar(jugador);
        piedra2.actualizar(jugador);
        aerosol1.actualizar(jugador);
        aerosol2.actualizar(jugador);
        aerosol3.actualizar(jugador);
        fondoDePrueba.actualizar(jugador);
        paredLimiteIzquierdo.actualizar(jugador);
        mensajeDePrueba.actualizar(jugador);
        mensaje2DePrueba.actualizar(jugador);
        ObjetoFlotanteDePrueba1.actualizar(jugador);
        ObjetoFlotanteDePrueba2.actualizar(jugador);
        suelo1_DePrueba.actualizar(jugador);
        suelo2_DePrueba.actualizar(jugador);
        suelo3_DePrueba.actualizar(jugador);
        obstaculo3_DePrueba.actualizar(jugador);
        obstaculo1_DePrueba.actualizar(jugador);
        obstaculo2_DePrueba.actualizar(jugador);
        jugador.recogerObjeto(piedras);
        enemigo1.actualizar(jugador, obstaculos);
        enemigo2.actualizar(jugador, obstaculos);
        
        if (jugador.getVida() == 0) {
            ejecutandose = false;
        }
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * @throws java.io.IOException
     */
    public void repintar() throws IOException {
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
    
}