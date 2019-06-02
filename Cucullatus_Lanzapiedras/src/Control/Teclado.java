/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Personajes.Jugador;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author diegocarvajal
 */
public class Teclado implements KeyListener{

    
    
    public Jugador jugador;
    
    public boolean arriba;
    public boolean abajo;
    public boolean derecha;
    public boolean izquierda;
    
    
    public void actualizar(){
         if (derecha) {
            jugador.x+=3;
        }if (izquierda) {
            jugador.x-=3;
        }if (abajo) {
            jugador.y+=3;
        }if (arriba) {
            jugador.y-=3;
        }
        
        
   }
    
    
    
    
    @Override
    public void keyTyped(KeyEvent e) {  
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int codTecla=e.getKeyCode();
         System.out.println(e.getKeyCode() +" "+ KeyEvent.VK_W);
         if (codTecla==KeyEvent.VK_W) {
            arriba=true;
        }if (codTecla==KeyEvent.VK_S) {
            abajo=true;
        }if (codTecla==KeyEvent.VK_D) {
            derecha=true;
        }if (codTecla==KeyEvent.VK_A) {
            izquierda=true;
        }
        System.out.println(arriba);
       
        }

    @Override
    public void keyReleased(KeyEvent e) {
         int codTecla=e.getKeyCode();
     
         if (codTecla==KeyEvent.VK_W) {
            arriba=false;
        }if (codTecla==KeyEvent.VK_S) {
            abajo=false;
        }if (codTecla==KeyEvent.VK_D) {
            derecha=false;
        }if (codTecla==KeyEvent.VK_A) {
            izquierda=false;
        }
    }
    
}
