/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nucleo;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Singleton {
    private static Singleton singleton;
    private Stage stage;
    private Scene escena;
    
    public static Singleton getSingleton(){
      if(singleton == null)
          singleton = new Singleton();
      return singleton;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getEscena() {
        return escena;
    }

    public void setEscena(Scene escena) {
        this.escena = escena;
    }
    
}
