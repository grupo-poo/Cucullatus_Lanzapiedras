package Nucleo;

import Escenario.Corazones;
import Escenario.ObjetoInerte;
import Escenario.ObjetoRecogible;
import Escenario.Pared;
import Escenario.Portal;
import Nucleo.Niveles.Nivel1;
import Nucleo.Niveles.Nivel2;
import Nucleo.Niveles.Nivel3;
import Nucleo.Niveles.Nivel4;
import Nucleo.Niveles.Nivel5;
import Nucleo.Niveles.Nivel6;
import Personajes.Enemigo;
import Personajes.Jugador;
import java.io.IOException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * Dibujamos todo en el panel que va sobre la ventana del juego.
 * @author Milton Lenis
 */
public class GraficosJuego extends Canvas {
    
    private ObjetoInerte[] elementosDeFondo;
    private ObjetoInerte[] obstaculos;
    private ObjetoRecogible[] aerosoles;
    private ObjetoRecogible[] piedras;
    private Enemigo[] enemigos;
    private Pared[] paredes;
    private Jugador jugador;
    private Portal portal;
    
    private int nivel = 1;
    private final int altoPantalla;
    private final int anchoPantalla;
    private boolean ejecutandose;

    public GraficosJuego(double anchoPantalla, double altoPantalla) throws IOException {
        super(anchoPantalla, altoPantalla);
        this.altoPantalla = (int) altoPantalla;
        this.anchoPantalla = (int) anchoPantalla;
        ejecutandose = true;
        cargarJuego();
    }

    private void cargarJuego() throws IOException {
        Corazones.Setall(anchoPantalla/5,altoPantalla/500);
        cargarJugador();
        cargarEsceneraio();
    }
    
    private void cargarEsceneraio() throws IOException {
        if (nivel == 1) {
            Nivel1 nivel1 = new Nivel1(anchoPantalla, altoPantalla);
            this.obstaculos = nivel1.getObstaculos();
            this.enemigos = nivel1.getEnemigos();
            this.elementosDeFondo = nivel1.getElementosDeFondo();
            this.paredes = nivel1.getParedes();
            this.piedras = nivel1.getPiedras();
            this.aerosoles = nivel1.getAerosoles();
            this.portal = nivel1.getPortal();
            
        } else if (nivel == 2) {
            Nivel2 nivel2 = new Nivel2(anchoPantalla, altoPantalla);
            this.obstaculos = nivel2.getObstaculos();
            this.enemigos = nivel2.getEnemigos();
            this.elementosDeFondo = nivel2.getElementosDeFondo();
            this.paredes = nivel2.getParedes();
            this.piedras = nivel2.getPiedras();
            this.aerosoles = nivel2.getAerosoles();
            this.portal = nivel2.getPortal();
            
        } else if (nivel == 3) {
            Nivel3 nivel3 = new Nivel3(anchoPantalla, altoPantalla);
            this.obstaculos = nivel3.getObstaculos();
            this.enemigos = nivel3.getEnemigos();
            this.elementosDeFondo = nivel3.getElementosDeFondo();
            this.paredes = nivel3.getParedes();
            this.piedras = nivel3.getPiedras();
            this.aerosoles = nivel3.getAerosoles();
            this.portal = nivel3.getPortal();
            
        } else if (nivel == 4) {
            Nivel4 nivel4 = new Nivel4(anchoPantalla, altoPantalla);
            this.obstaculos = nivel4.getObstaculos();
            this.enemigos = nivel4.getEnemigos();
            this.elementosDeFondo = nivel4.getElementosDeFondo();
            this.paredes = nivel4.getParedes();
            this.piedras = nivel4.getPiedras();
            this.aerosoles = nivel4.getAerosoles();
            this.portal = nivel4.getPortal();
            
        } else if (nivel == 5) {
            Nivel5 nivel5 = new Nivel5(anchoPantalla, altoPantalla);
            this.obstaculos = nivel5.getObstaculos();
            this.enemigos = nivel5.getEnemigos();
            this.elementosDeFondo = nivel5.getElementosDeFondo();
            this.paredes = nivel5.getParedes();
            this.piedras = nivel5.getPiedras();
            this.aerosoles = nivel5.getAerosoles();
            this.portal = nivel5.getPortal();
            
        } else if (nivel == 6) {
            Nivel6 nivel6 = new Nivel6(anchoPantalla, altoPantalla);
            this.obstaculos = nivel6.getObstaculos();
            this.enemigos = nivel6.getEnemigos();
            this.elementosDeFondo = nivel6.getElementosDeFondo();
            this.paredes = nivel6.getParedes();
            this.piedras = nivel6.getPiedras();
            this.aerosoles = nivel6.getAerosoles();
            this.portal = nivel6.getPortal();
        }
    }
    
    private void cargarJugador() throws IOException {
        Image imagen = new Image("Nucleo/Recursos/Cucullatus.png");
        jugador = new Jugador(imagen, anchoPantalla, altoPantalla);
    }
    
    private void dibujar(GraphicsContext lapiz) {
        for (ObjetoInerte objetoDeFondo : elementosDeFondo) {
            objetoDeFondo.dibujar(lapiz);
        }
        for (ObjetoInerte obstaculo : obstaculos) {
            obstaculo.dibujar(lapiz);
        }
        for (Pared pared : paredes) {
            pared.dibujar(lapiz);
        }
        for (Enemigo enemigo : enemigos) {
            enemigo.dibujar(lapiz);
        }
        for (ObjetoRecogible aerosol : aerosoles) {
            aerosol.dibujar(lapiz);
        }
        for (ObjetoRecogible piedra : piedras) {
            piedra.dibujar(lapiz);
        }
        portal.dibujar(lapiz);
        jugador.dibujar(lapiz);
        Corazones.dibujar(lapiz, jugador.getVida());
        
        //////////////////////////////////////////
        /**
         * Todo esto puede ser eliminado, solo sirve para depurar.
         */
        new Debug(lapiz);
        Debug.lapiz.fillText("fondo: " + elementosDeFondo[0].getX(), anchoPantalla - 200, 126);
        //////////////////////////////////////////
    }
    
    private void actualizar() throws IOException{
        jugador.actualizar(obstaculos, enemigos, paredes, piedras, aerosoles);
        portal.actualizar(jugador, paredes);
        for (ObjetoInerte objetoDeFondo : elementosDeFondo) {
            objetoDeFondo.actualizar(jugador);
        }
        for (ObjetoInerte obstaculo : obstaculos) {
            obstaculo.actualizar(jugador);
        }
        for (Pared pared : paredes) {
            pared.actualizar(jugador);
        }
        for (Enemigo enemigo : enemigos) {
            enemigo.actualizar(jugador, obstaculos);
        }
        for (ObjetoRecogible aerosol : aerosoles) {
            aerosol.actualizar(jugador);
        }
        for (ObjetoRecogible piedra : piedras) {
            piedra.actualizar(jugador);
        }
        if (jugador.getVida() == 0) {
            ejecutandose = false;
        }
        if (portal.isCambiarNivel()) {
            nivel++;
            cargarEsceneraio();
            int valor = jugador.getEnmigosAbatidosPorNivel();
            jugador.setTotalEnemigosAbatidos(jugador.getTotalEnemigosAbatidos() + valor);
            valor = jugador.getPuntuacionPorNivel();
            jugador.setPuntuacionTotal(jugador.getPuntuacionTotal() + valor);
            jugador.setRespawn(true);
        }
    }
    
    public void repintar() throws IOException {
        GraphicsContext lapiz = this.getGraphicsContext2D();
        lapiz.clearRect(0, 0, anchoPantalla, altoPantalla);
        dibujar(lapiz);
        actualizar();
    }

    public boolean isEjecutandose() {
        return ejecutandose;
    }
}