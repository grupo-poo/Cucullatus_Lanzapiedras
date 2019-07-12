/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nucleo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Nicolas
 */
public class Controlador {
    private Menu menu;
    private Scene escena;

    public Controlador() {
        this.menu = new Menu();
        this.escena = menu.getEscena();
        this.menu.getBt1().setOnAction(new EventoSiguiente());
        
    }

    public Menu getMenu() {
        return menu;
    }

    public Scene getEscena() {
        return escena;
    }
    
    
    //Registro eventos
    private class EventoSiguiente 
            implements  EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
          Singleton singleton =
                  Singleton.getSingleton();
          Stage stage = singleton.getStage();
                    
          Scene escena2 = singleton.getEscena();
          stage.setScene(escena2);
          stage.show();
          
        }
    
    }
}
