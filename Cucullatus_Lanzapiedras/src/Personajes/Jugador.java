package Personajes;

import Control.Teclado;
import Escenario.ObjetoInerte;
import Nucleo.Debug;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Todas las mecanicas del Jugador se establecen aquí.
 */
public class Jugador {
    
    private byte pasos;   // Número de pixeles que debe recorrer por frame.
    private int velocidad; // pixeles que recorre en tiempo real.
    private int ancho, alto; // Dimensiones de la imagen del Jugador.
    private int x, y; // Coordenadas del jugador en la pantalla.
    private int desplazamiento; // Cambia cada vez que el jugador se desplaaza.
    private boolean distanciaCritica; // true cuando esté en el punto donde x no cambia.
    private Image imagen;
    
    private int secuencia = 1;//Numero de imagenes, empieza por 1
   
    private int cuenta= 0;//Ayuda a controlar la cantidad de veces que se pintan las imágenes
    
    //las cuatro weas creadas aqui abbajo sirven para la gravedad
    private int velAGIn = 5;
    private int velAG = velAGIn;
    private int auxG = 0;
    private int auxGIn = 0;
    private int tSal = 10;
    private int modulador = 3;
    private int acG = 2; //aceleracion de la gravedad en pixeleles/modulador^2
    private boolean saltoIn = false;
    private Boolean bloqueoARR = false;
    private Boolean bloqueoSal = false;
    private Boolean jumped = false;
    
    public Jugador(Image imagen) {
        this.imagen = imagen;
        ancho = (int) imagen.getWidth();
        alto  = (int) imagen.getHeight();
        x = 40;     y = 550;
        desplazamiento = x;
        pasos = 3;
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * 
     * Este metodo dibuja al jugador.
     * 
     * Se le pueden añadir nuevos metodos si así se requiere o cualquier
     * otra alteración de su estructura.
     * 
     * @param lapiz Objeto de graficos
     */
    public void dibujar(GraphicsContext lapiz) {
        lapiz.drawImage(imagen, x, y, ancho, alto);
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * 
     * Este metodo cambia los atributos que los metodos
     * que contiene cambian por cada frame.
     * 
     * Se le pueden añadir nuevos metodos si así se requiere o cualquier
     * otra alteración de su estructura.
     * 
     * @param anchoDePantalla
     * @param obstaculos 
     */
    public void actualizar(int anchoDePantalla, ArrayList<ObjetoInerte> obstaculos) {
        // Cuando el jugador esté en un cierto punto el escenario se mueve.
        determinarDistanciaCritica(anchoDePantalla);
        mover(obstaculos); // Aquí movemos al jugador.
        animacion(); // Aquí se ejecuta la animación del jugador.
        
        //////////////////////////////////////////
        /**
         * Todo esto puede ser eliminado, solo sirve para depurar.
         */
        Debug.lapiz.fillText("Desplazamiento: " + desplazamiento, 20, 30);
        Debug.lapiz.fillText("Posición x: " + x, 20, 42);
        Debug.lapiz.fillText("Posición y: " + y, 20, 54);
        Debug.lapiz.fillText("Velocidad: " + velocidad, 20, 66);
        Debug.lapiz.fillText("Distancia Critica: " + distanciaCritica, 20, 78);
        Debug.lapiz.fillText("auxG: " + auxG, 20, 90);
        Debug.lapiz.fillText("auxGIn: " + auxGIn, 20, 102);
        Debug.lapiz.fillText("auxG - auxGIn: " + (auxG - auxGIn), 20, 114);
        Debug.lapiz.fillText("bloqueoARR: " + bloqueoARR, 20, 126);
        Debug.lapiz.fillText("bloqueoSal: " + bloqueoSal, 20, 138);
        Debug.lapiz.fillText("velAG: " + velAG, 20, 150);
        //////////////////////////////////////////
    }
    
    /**
     * *************************** METODO ALTERABLE ***************************
     * 
     * Este metodo cambia los siguientes atributos:
     * - x
     * - y
     * - desplazamiento
     * - velocidad
     * 
     * Contiene la instrucciones que permiten mover
     * al jugador por el escenario.
     * 
     * Se le pueden añadir nuevos metodos si así se requiere o cualquier
     * otra alteración de su estructura.
     * 
     * @param obstaculos array de obstaculos.
     */
    private void mover(ArrayList<ObjetoInerte> obstaculos) {
        
        /**
         * author Milton
         * inspiracion : hilos mosc supongo no soy Milton :v
         * coder: Dios
         */
        
        velocidad = desplazamiento;
        auxG++;
        
        desplazarseAbajo(obstaculos, velAG+1); // Gravedad sin aceleración XD
        
        if (Teclado.isIZQUIERDA()) {
            desplazarseIzquierda(obstaculos);
        }
        if (Teclado.isDERECHA()) {
            desplazarseDerecha(obstaculos);
        }
        
        if(!bloqueoSal){
            if(Teclado.isARRIBA()){
                saltoIn = true;
                auxGIn = auxG;
                bloqueoSal = true;
                jumped = true;
            }
        }
        
        if(auxG - auxGIn == (tSal*2)){
            bloqueoSal = false;
            bloqueoARR = false;
            saltoIn = false;
        }
        
        if(auxG - auxGIn == tSal){
            bloqueoARR = true;
            velAG = velAGIn;
        }
        
        if(bloqueoARR && !bloqueoSal && jumped){
            if(auxG % modulador == 0){
                    velAG = velAG + acG;
                }
        }
        
        if(saltoIn){
            if(!bloqueoARR){
                desplazarseArriba(obstaculos, (velAG*2)+1); // 1+ que la gravedad.
                if(auxG % modulador == 0){
                    velAG = velAG - acG;
                }
            }
        }
        velocidad = desplazamiento - velocidad;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * 
     * Este metodo cambia los siguientes atributos:
     * - x
     * - desplazamiento
     * 
     * Permite que el jugador se mueva a la izquierda siempre y cuando
     * no haya un obstaculo en esa dirección.
     * 
     * No se puede invocar este metodo más de una vez en ningún otro metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @return Devuelve false si se intersepta un obstaculo.
     * @author Milton Lenis
     */
    private boolean desplazarseIzquierda(ArrayList<ObjetoInerte> obstaculos) {
        boolean viaLibre = true;
        Rectangle Ju = getRectangulo();
        Ju.setX(x - pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Ju, obs.getRectangulo())) {
                    viaLibre = false;
                    desplazamiento = obs.getxInit() + obs.getAncho();
                    if (!distanciaCritica) { x = desplazamiento; }
                }
            } 
        }
        if (viaLibre) {
            if ((desplazamiento - x) % pasos != 0) {
                while ((desplazamiento - x) % pasos != 0) { desplazamiento--; }
            } else {
                desplazamiento -= pasos;
                if (!distanciaCritica) { x = desplazamiento; }
            }
        }
        return viaLibre;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * 
     * Este metodo cambia los siguientes atributos:
     * - x
     * - desplazamiento
     * 
     * Permite que el jugador se mueva a la derecha siempre y cuando
     * no haya un obstaculo en esa dirección.
     * 
     * No se puede invocar este metodo más de una vez en ningún otro metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @return Devuelve false si se intersepta un obstaculo.
     * @author Milton Lenis
     */
    private boolean desplazarseDerecha(ArrayList<ObjetoInerte> obstaculos) {
        boolean viaLibre = true;
        Rectangle Ju = getRectangulo();
        Ju.setX(x + pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(Ju, obs.getRectangulo())) {
                    viaLibre = false;
                    desplazamiento = obs.getxInit() - ancho;
                    if (!distanciaCritica) { x = desplazamiento; }
                }
            }
        }
        if (viaLibre) {
            if ((desplazamiento - x) % pasos != 0) {
                while ((desplazamiento - x) % pasos != 0) { desplazamiento++; }
            } else { 
                desplazamiento += pasos;
                if (!distanciaCritica) { x = desplazamiento; } 
            }
        }
        return viaLibre;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * 
     * Este metodo cambia los siguientes atributos:
     * - y
     * 
     * Permite que el jugador se mueva hacia arriba siempre y cuando
     * no haya un obstaculo en esa dirección.
     * 
     * No se puede invocar este metodo más de una vez en ningún otro metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @param velocidad velocidad a la que se moverá el jugador.
     * @return Devuelve false si se intersepta un obstaculo.
     * @author Milton Lenis
     */
    private boolean desplazarseArriba(ArrayList<ObjetoInerte> obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Ju = getRectangulo();
        Ju.setY(y - (pasos * velocidad));
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Ju, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() + obs.getAlto();
                }
            }
        }
        if (viaLibre) { y -= (pasos * velocidad); }
        return viaLibre;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * 
     * Este metodo cambia los siguientes atributos:
     * - y
     * 
     * Permite que el jugador se mueva hacia abajo siempre y cuando
     * no haya un obstaculo en esa dirección.
     * 
     * No se puede invocar este metodo más de una vez en ningún otro metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @param velocidad velocidad a la que se moverá el jugador.
     * @return Devuelve false si se intersepta un obstaculo.
     * @author Milton Lenis
     */
    private boolean desplazarseAbajo(ArrayList<ObjetoInerte> obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle Ju = getRectangulo();
        Ju.setY(y + (pasos * velocidad));
        for (ObjetoInerte obs : obstaculos) {
            if (Ju.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(Ju, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() - alto;
                }
            }
        }
        if (viaLibre) { y += (pasos * velocidad); }
        return viaLibre;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * 
     * Este metodo cambia los siguientes atributos:
     * - distanciaCritica
     * 
     * Cuando el jugador esté en el punto ((anchoDePantalla / 2) - 2*ancho)
     * el jugador estará sobrepasando una frontera invisible y a partir
     * de ella el escenario se mueve pero la posición x del jugador no cambia.
     * Este metodo hace distanciaCritica = true cuando sobrepase esa frontera.
     * 
     * @param anchoDePantalla
     * @author Milton Lenis
     */
    private void determinarDistanciaCritica(int anchoDePantalla) {
        int distCrit = ((anchoDePantalla / 2) - 2*ancho);
        if (desplazamiento >= distCrit) {
            distanciaCritica = !(desplazamiento < (distCrit + pasos) && Teclado.isIZQUIERDA());
        } else distanciaCritica = false;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * @param Jugador rectangulo con las dimensiones del jugador
     * @param obstaculo rectangulo con las dimensiones del obstaculo con el que colisiona.
     * @return true si la colision con un obstaculo es por la derecha o por la izquierda.
     * @author Milton Lenis
     */
    private boolean ObstaculoDirHorizontal(Rectangle Jugador, Rectangle obstaculo) {
        return !(Jugador.getBoundsInLocal().getMinY() == obstaculo.getBoundsInLocal().getMaxY()
                || Jugador.getBoundsInLocal().getMaxY() == obstaculo.getBoundsInLocal().getMinY());
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * @param Jugador rectangulo con las dimensiones del jugador
     * @param obstaculo rectangulo con las dimensiones del obstaculo con el que colisiona.
     * @return true si la colision con un obstaculo es por la derecha o por la izquierda.
     * @author Milton Lenis
     */
    private boolean ObstaculoDirVertical(Rectangle Jugador, Rectangle obstaculo) {
        return !(Jugador.getBoundsInLocal().getMaxX() == obstaculo.getBoundsInLocal().getMinX()
                || Jugador.getBoundsInLocal().getMinX() == obstaculo.getBoundsInLocal().getMaxX());
    }
    
    /**
     * @author Diego Carvajal
     */
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

    
    
}
