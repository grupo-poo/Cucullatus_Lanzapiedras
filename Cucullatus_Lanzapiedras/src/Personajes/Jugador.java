package Personajes;

import Control.Teclado;
import Escenario.ObjetoInerte;
import Escenario.Pared;
import Nucleo.Debug;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * @author Milton Lenis
 */
public class Jugador {
    
    private byte pasos;   // Número de pixeles que debe recorrer por frame.
    private int velocidad; // pixeles que recorre en tiempo real.
    private int ancho, alto; // Dimensiones de la imagen del Jugador.
    private int x, y; // Coordenadas del jugador en la pantalla.
    private int desplazamiento; // Cambia cada vez que el jugador se desplaaza.
    private boolean distanciaCritica; // true cuando esté en el punto donde x no cambia.
    private Image imagen;
    
    private int graf; //Total de grafitis en el bolsillo
    
    private int secuencia = 1;//Numero de imagenes, empieza por 1
   
    private int cuenta= 0;//Ayuda a controlar la cantidad de veces que se pintan las imágenes
  
    
    public Jugador(Image imagen) {
        this.imagen = imagen;
        ancho = (int) imagen.getWidth();
        alto  = (int) imagen.getHeight();
        x = 40;     y = 550;
        desplazamiento = x;
        pasos = 3;
    }
    
    public void dibujar(GraphicsContext lapiz) {
        lapiz.drawImage(imagen, x, y, ancho, alto);
    }
    
    public void actualizar(int anchoDePantalla, ArrayList<ObjetoInerte> obstaculos) {
        // Cuando el jugador esté en un cierto punto el escenario se mueve.
        movimientoDeEscenario(anchoDePantalla);
        mover(obstaculos); // Aquí movemos al jugador.
        animacion();
        
        
        
        
        
    }
    
    private void mover(ArrayList<ObjetoInerte> obstaculos) {
        velocidad = desplazamiento;
        if (Teclado.isIZQUIERDA()) {
            desplazarseIzquierda(obstaculos);
        }
        if (Teclado.isDERECHA()) {
            desplazarseDerecha(obstaculos);
        }
        if (Teclado.isARRIBA()) {
            desplazarseArriba(obstaculos);
        }
        if (Teclado.isABAJO()) {
            desplazarseAbajo(obstaculos);
        }
        velocidad = desplazamiento - velocidad;
        
        //////////////////////////////////////////
        /**
         * Todo lo que va desde la linea 60 a 70 puede ser
         * eliminado, solo sirve para depurar.
         */
        Debug.lapiz.fillText("Desplazamiento: " + desplazamiento, 20, 30);
        Debug.lapiz.fillText("Posición x: " + x, 20, 42);
        Debug.lapiz.fillText("Posición y: " + y, 20, 54);
        Debug.lapiz.fillText("Velocidad: " + velocidad, 20, 66);
        Debug.lapiz.fillText("Distancia Critica: " + distanciaCritica, 20, 78);
        //////////////////////////////////////////
    }
    
    
    
    private void desplazarseIzquierda(ArrayList<ObjetoInerte> obstaculos) {
        boolean obstaculoEnfrente = false;
        Rectangle Ju = getRectangulo();
        Ju.setX(x - pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Ju, obs.getRectangulo())) {
                    obstaculoEnfrente = true;
                    desplazamiento = obs.getxInit() + obs.getAncho();
                    if (!distanciaCritica) { x = desplazamiento; }
                }
            } 
        }
        if (!obstaculoEnfrente) {
            if ((desplazamiento - x) % pasos != 0) {
                while ((desplazamiento - x) % pasos != 0) { desplazamiento--; }
            } else {
                desplazamiento -= pasos;
                if (!distanciaCritica) { x = desplazamiento; }
            }
        }
    }
        
    private void desplazarseDerecha(ArrayList<ObjetoInerte> obstaculos) {
        boolean obstaculoEnfrente = false;
        Rectangle Ju = getRectangulo();
        Ju.setX(x + pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Ju, obs.getRectangulo())) {
                    obstaculoEnfrente = true;
                    desplazamiento = obs.getxInit() - ancho;
                    if (!distanciaCritica) { x = desplazamiento; }
                }
            }
        }
        if (!obstaculoEnfrente) {
            if ((desplazamiento - x) % pasos != 0) {
                while ((desplazamiento - x) % pasos != 0) { desplazamiento++; }
            } else { 
                desplazamiento += pasos;
                if (!distanciaCritica) { x = desplazamiento; } 
            }
        }
    }
    
    private void desplazarseArriba(ArrayList<ObjetoInerte> obstaculos) {
        boolean obstaculoEnfrente = false;
        Rectangle Ju = getRectangulo();
        Ju.setY(y - pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Ju, obs.getRectangulo())) {
                    obstaculoEnfrente = true;
                    y = obs.getY() + obs.getAlto();
                }
            }
            
               
        
        
            
            
        }
        if (!obstaculoEnfrente) { y -= pasos;
       
        }
        
    }
    
    private void desplazarseAbajo(ArrayList<ObjetoInerte> obstaculos) {
        boolean obstaculoEnfrente = false;
        Rectangle Ju = getRectangulo();
        Ju.setY(y + pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Ju, obs.getRectangulo())) {
                    obstaculoEnfrente = true;
                    y = obs.getY() - alto;
                }
            }
        }
        if (!obstaculoEnfrente) { y += pasos; }
    }
    
    private void movimientoDeEscenario(int anchoDePantalla) {
        int distCrit = ((anchoDePantalla / 2) - 2*ancho);
        if (desplazamiento >= distCrit) {
            distanciaCritica = !(desplazamiento < (distCrit + pasos) && Teclado.isIZQUIERDA());
        } else distanciaCritica = false;
    }
    
    // true si hay un obstaculo a la derecha o a la izquierda.
    private boolean ObstaculoDirHorizontal(Rectangle Jugador, Rectangle obstaculo) {
        return !(Jugador.getBoundsInLocal().getMinY() == obstaculo.getBoundsInLocal().getMaxY()
                || Jugador.getBoundsInLocal().getMaxY() == obstaculo.getBoundsInLocal().getMinY());
    }
    
    // true si hay un obstaculo arriba o abajo.
    private boolean ObstaculoDirVertical(Rectangle Jugador, Rectangle obstaculo) {
        return !(Jugador.getBoundsInLocal().getMaxX() == obstaculo.getBoundsInLocal().getMinX()
                || Jugador.getBoundsInLocal().getMinX() == obstaculo.getBoundsInLocal().getMaxX());
    }
    
    private void animacion() {
        
        boolean movimientoI = false; // si se está moviendo esto será true
        boolean movimientoD = false;
        
        if (velocidad < 0) {
            this.imagen = new Image("CorrerI/"+this.secuencia+".png");
            movimientoI = true;
            
        } 
        
        if (velocidad > 0) {
            
            movimientoD = true;
            this.imagen = new Image("CorrerD/"+this.secuencia+".png");
        } 
        
        
        if (((!movimientoI  && movimientoD) || (!movimientoD && movimientoI))) { //Operador lógico "XOR"
            
            if(cuenta<=10){
                if(this.cuenta == 10) {
                    cuenta = 0;
                    if(this.secuencia == 7) secuencia = 1;
                    else secuencia++; 
                 }
                else cuenta++;
            }
            
            
            }
        
        
    
    }
    
    
  
    
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, ancho, alto);
    }

    public byte getPasos() {
        return pasos;
    }

    public void setPasos(byte pasos) {
        this.pasos = pasos;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
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

    public int getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(int desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

    public boolean isDistanciaCritica() {
        return distanciaCritica;
    }

    public void setDistanciaCritica(boolean distanciaCritica) {
        this.distanciaCritica = distanciaCritica;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }
    
    
    public void Graffitear(ArrayList<Pared> paredes){
        
        for(Pared pared: paredes){
            if((pared.getX()+40<=this.ancho+this.x && pared.getX()+pared.getAncho()>=this.x+40)  && Teclado.isVANDALIZAR() && y==727){//Se puede generalizar para todo obstáculo que tenga encima la pared
                Image imagen=new Image("Nucleo/Recursos/Paredmodificada.png");
                pared.setImagen(imagen);
            }
        }
    }
    

    
    
}
