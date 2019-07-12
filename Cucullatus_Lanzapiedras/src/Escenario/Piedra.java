/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Escenario;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;

/**
 *
 * @author diegocarvajal
 */
public class Piedra {
    private int x;
    private int y;
    private int ancho;
    private int alto;
    private Image imagen;
    private boolean visible=true;
    
    
    public Piedra(int x, int y, int ancho, int alto){
        this.x=x;
        this.y=y;
        this.ancho=ancho;
        this.alto=alto;
        this.imagen=new Image("Nucleo/Recursos/Piedra/Piedra.png");
    }
    
    public void dibujar(GraphicsContext lapiz){
       
        if(visible){
            lapiz.drawImage(imagen, x, y,ancho,alto);
        }
        
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    
}
