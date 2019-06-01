package Escenario;

import ElementosEscenario.Fondo;
import Personajes.Jugador;
import java.awt.Graphics;
import java.io.IOException;
import javax.swing.JPanel;

/**
 * Dibujamos todo en el panel que va sobre la ventana del juego.
 * @author Milton Lenis
 */
public class Panel extends JPanel{
    
    private Jugador jugador;
    private Fondo fondo1;
    private int altoPantalla;
    private int anchoPantalla;
    private boolean ejecutandose;
    
    public Panel(int altoPantalla, int anchoPantalla) throws Exception {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        inicializar();
    }
    
    private void inicializar() throws IOException{
        ejecutandose = true;
        prepararJuego();
    }
    
    private void prepararJuego() throws IOException {
        prepararEscenario();
        // Creamos el jugador
        jugador = new Jugador("../Escenario/Sprites/Cucullatus.png");
    }
    
    public void prepararEscenario() throws IOException {
        // Creamos el fondo
        fondo1 = new Fondo("../Escenario/Fondos/fondo.png");
        fondo1.setAlto(altoPantalla);
    }
    
    public void dibujar(Graphics g) {
        fondo1.dibujar(g);
        jugador.dibujar(g);
    }
    
    public void actualizar(){
        jugador.actualizar();
        fondo1.actualizar();
    }

    public boolean isEjecutandose() {
        return ejecutandose;
    }
    
    /**
     * Este metodo heredado de JPanel se ejecuta sin necesidad de llamarlo.
     * Sobre él ponemos todo lo que se mostrará en pantalla.
     * @Override
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujar(g);
        actualizar();
    }
    
}
