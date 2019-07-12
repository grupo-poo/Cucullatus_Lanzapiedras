/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nucleo;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 * @author Nicolas
 */
public class Menu {
      
        //menu
        private Scene escena;
        private Button bt1 ;
        private Button bt2 ;
        private Button bt3 ;
        private VBox layout1;
        public Menu(){
            bt1 = new Button("empezar juego");
            bt2 = new Button("Cargar Partida");
            bt3 = new Button("Salir");
            //bt1.setOnAction(e -> ventana.setScene(escena));
            VBox layout1 = new VBox(20);
            layout1.getChildren().addAll(bt1,bt2,bt3);
            layout1.setAlignment(Pos.CENTER);
            this.escena = new Scene(layout1,250,250);
            escena.getStylesheets().add(getClass().getResource("menudesign.css").toExternalForm());
        }

    public Scene getEscena() {
        return escena;
    }

    public Button getBt1() {
        return bt1;
    }

    public Button getBt2() {
        return bt2;
    }

    public Button getBt3() {
        return bt3;
    }
        

        
        
        
}
