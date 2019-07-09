/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Escenario;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author diegocarvajal
 */
public class Corazones {
    
    private static int Altura;
    private static int Anchura;
    private static int X;
    private static int Y;
    private static Image Imagen;
    private static int cuenta=0;
  
    
    
    
    
    public static void dibujar(GraphicsContext lapiz, int vida){
        
        Image Corazones=new Image("Nucleo/Recursos/Corazones/Heart"+vida+".png");
        Imagen=Corazones;
        lapiz.drawImage(Imagen, X, Y,Imagen.getWidth()/2,Imagen.getHeight()/2);
        
    }
    
    
    public static void Setall(int x, int y){
     X=x;
     Y=y;
     }
    
    

    public static int getAltura() {
        return Altura;
    }

    public static void setAltura(int Altura) {
        Corazones.Altura = Altura;
    }

    public static int getAnchura() {
        return Anchura;
    }

    public static void setAnchura(int Anchura) {
        Corazones.Anchura = Anchura;
    }

    public static int getX() {
        return X;
    }

    public static void setX(int X) {
        Corazones.X = X;
    }

    public static int getY() {
        return Y;
    }

    public static void setY(int Y) {
        Corazones.Y = Y;
    }

    public static Image getImagen() {
        return Imagen;
    }

    public static void setImagen(Image Imagen) {
        Corazones.Imagen = Imagen;
    }
    
   
    
}
