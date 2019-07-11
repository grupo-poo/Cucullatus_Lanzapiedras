/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Escenario.ObjetoInerte;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author diegocarvajal
 */
public class Enemigo {
   private byte pasos;   // Número de pixeles que debe recorrer por frame.
   
    private int ancho, alto; // Dimensiones de la imagen del Jugador.
    private int x, y; // Coordenadas del jugador en la pantalla.
    
    private Image imagen;
    private int vida;
    
    
    
    private int secuencia = 1;//Numero de imagenes, empieza por 1
   
    private int cuenta= 0;//Ayuda a controlar la cantidad de veces que se pintan las imágenes
    
    public Enemigo(int x, int y, int ancho, int alto, int vidaInicial) {
        this.imagen=new Image("Nucleo/Recursos/Enemigo/Enemigo.png");
        this.ancho = ancho;
        this.alto = alto;
        this.x = x;
        this.y = y;
        this.vida = vidaInicial;
    }
  
    
    

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
    
    public void dibujar(GraphicsContext lapiz, Jugador jugador) {
        if(jugador.getDesplazamiento()>=1212){
        lapiz.drawImage(imagen, x, y, ancho, alto);
        }
    }
    
    public void actualizar(Jugador jugador) {
        if (jugador.isDistanciaCritica()) {
            x -= jugador.getVelocidad();
        }
    }
    
    public void mover(){
        
        
    }
}
