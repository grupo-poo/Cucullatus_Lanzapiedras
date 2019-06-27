package Nucleo;

import Control.Teclado;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Nicolas Hoyos
 * @author Nicolas Mejia
 * @author Milton Lenis
 * @author Diego Carvajal
 */
public class Principal extends Application {
    
    private Scene escena;
    private Pane panel;
    private GraficosJuego juego;
    private int anchoPantalla;
    private int altoPantalla;

    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void start(Stage ventana) throws Exception {
        getDimPantalla();
        juego = new GraficosJuego(anchoPantalla, altoPantalla);
        
        panel = new Pane();
        panel.getChildren().add(juego);
        
        escena = new Scene(panel);
        escena.setOnKeyPressed(tecla -> Teclado.keyPressed(tecla.getCode()));
        escena.setOnKeyReleased(tecla -> Teclado.keyReleased(tecla.getCode()));
        ventana.initStyle(StageStyle.UNDECORATED); // Esconder barra ( - [] X ).
        ventana.setMaximized(true);
        ventana.setScene(escena);
        ventana.show();
        
        AnimationTimer animacion = new AnimationTimer() {
            @Override
            public void handle(long now) {
                juego.repintar();
            }
        };
        animacion.start();
    }
    
    public void getDimPantalla() {
        Rectangle2D dim = Screen.getPrimary().getBounds();
        anchoPantalla = (int) dim.getWidth();
        altoPantalla = (int) dim.getHeight();
    }
}

