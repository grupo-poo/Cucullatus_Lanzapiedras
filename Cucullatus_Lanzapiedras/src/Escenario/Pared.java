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
public class Pared extends ObjetoInerte{
    
    private boolean isGraffiteada;
    
    public Pared(Image imagen) {
        super(imagen);
    }
    
    @Override
    public void dibujar(GraphicsContext lapiz) {
        if (isGraffiteada) {
            Image imagenGra = new Image("Nucleo/Recursos/Paredmodificada.png");
            lapiz.drawImage(imagenGra, x, y, ancho, alto);
        } else {
            lapiz.drawImage(this.imagen, x, y, ancho, alto);
        }
    }

    public boolean isIsGraffiteada() {
        return isGraffiteada;
    }

    public void setIsGraffiteada(boolean isGraffiteada) {
        this.isGraffiteada = isGraffiteada;
    }
    
}
    

