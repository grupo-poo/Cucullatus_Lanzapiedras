package Personajes;

import Control.Teclado;
import Escenario.ObjetoInerte;
import Escenario.Pared;
import Escenario.Piedra;
import Nucleo.Debug;
import Nucleo.ObjetoEscenario;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Todas las mecanicas del Jugador se establecen aquí.
 */
public class Jugador extends ObjetoEscenario{
    
    private final int altoPantalla;
    private final int anchoPantalla;
    private byte pasos;   // Número de pixeles que debe recorrer por frame.
    private int velocidadHorizontal; // pixeles que recorre horizontalmente en tiempo real.
    private int velocidadVertical; // pixeles que recorre verticalmente en tiempo real.
    private int ancho, alto; // Dimensiones de la imagen del Jugador.
    private int x, y; // Coordenadas del jugador en la pantalla.
    private int desplazamiento; // Cambia cada vez que el jugador se desplaaza.
    private boolean distanciaCritica; // true cuando esté en el punto donde x no cambia.
    private Image imagen;
    private int vida=10;//Vida inicial del jugador
    private int piedras=0; //Cantidad de piedras del jugador
    private Piedra piedra;
    private boolean direccion; // Si es true el jugador mira a la derecha.
    private boolean muerto;
    private boolean respawn;
    
    // Auxiliares para la gravedad.
    private int countAuxForGravity = 0; // Contador auxiliar para la gravedad.
    private int aceleracion = -1; // cambio de la velocidad con respecto a cada frame.
    
    // Auxiliares para la animación.
    private int secuencia = 1;//Numero de imagenes, empieza por 1
    private int cuenta= 0;//Ayuda a controlar la cantidad de veces que se pintan las imágenes
    
    // Direcciones a las que la piedra es lanzada.
    private boolean piedraDerecha = false;
    private boolean piedraIzquierda = false;
    private boolean piedraArriba = false;
    private boolean piedraAbajo = false;
  
    
    public Jugador(Image imagen, int anchoPantalla, int altoPantalla) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.imagen = imagen;
        x = 40;
        y = 150;
        desplazamiento = x;
        ancho = (int) imagen.getWidth();
        alto  = (int) imagen.getHeight();
        pasos = 3;
        velocidadVertical = 1;
        direccion = true;
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
        if (piedra != null) {
            piedra.dibujar(lapiz);
        }
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
     * @param obstaculos
     * @param enemigos
     */
    public void actualizar(ArrayList<ObjetoInerte> obstaculos, ArrayList<Enemigo> enemigos) {
        velocidadHorizontal = desplazamiento;
        int distCrit = determinarDistanciaCritica();
        mover(obstaculos); // Aquí movemos al jugador.
        morir(enemigos);
        boolean SobrepasadoDistCrit = respawn1(distCrit, enemigos);
        velocidadHorizontal = desplazamiento - velocidadHorizontal;
        respawn2(SobrepasadoDistCrit);
        animacion(); // Aquí se ejecuta la animación del jugador.
        lanzarPiedra(obstaculos);
        eliminarPiedraSiSaleDeEscenario();
        abatir(enemigos);

        //////////////////////////////////////////
        /**
         * Todo esto puede ser eliminado, solo sirve para depurar.
         */
        Debug.lapiz.fillText("Desplazamiento: " + desplazamiento, 20, 30);
        Debug.lapiz.fillText("Posición x: " + x, 20, 42);
        Debug.lapiz.fillText("Posición y: " + y, 20, 54);
        Debug.lapiz.fillText("Velocidad: " + velocidadHorizontal, 20, 66);
        Debug.lapiz.fillText("Distancia Critica: " + distanciaCritica, 20, 78);
        Debug.lapiz.fillText("Aceleracion: " + aceleracion, 20, 90);
        Debug.lapiz.fillText("Piedras: " + piedras, 20, 102);
        //////////////////////////////////////////
    }
    
    private void abatir(ArrayList<Enemigo> enemigos) {
        if (piedra != null) {
            for (Enemigo enemigo : enemigos) {
                if (enemigo.getRectangulo().intersects(piedra.getRectangulo().getBoundsInLocal())) {
                    enemigo.setMuerto(true);
                    piedra = null;
                    piedras--;
                    abortarLanzamiento();
                    break;
                }
            }
        }
    }
    
    private void revivirEnemigo(ArrayList<Enemigo> enemigos) {
        for (Enemigo enemigo : enemigos) {
            enemigo.setMuerto(false);
        }
    }
    
    private boolean intersectsEnemigo(ArrayList<Enemigo> enemigos) {
        boolean hayIntercepcion = false;
        for (Enemigo enemigo : enemigos) {
            if (!enemigo.isMuerto()) {
                if (enemigo.getRectangulo().intersects(this.getRectangulo().getBoundsInLocal())) {
                    hayIntercepcion = true;
                    break;
                }
            }
        }
        return hayIntercepcion;
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
     * Contiene las instrucciones que permiten mover
     * al jugador por el escenario.
     * 
     * Se le pueden añadir nuevos metodos si así se requiere o cualquier
     * otra alteración de su estructura.
     * 
     * @param obstaculos array de obstaculos.
     */
    private void mover(ArrayList<ObjetoInerte> obstaculos) {
        boolean estaTocandoSuelo = gravedad(obstaculos);
        
        if (Teclado.isIZQUIERDA()) {
            desplazarseIzquierda(obstaculos);
        }
        if (Teclado.isDERECHA()) {
            desplazarseDerecha(obstaculos);
        }
        if (Teclado.isARRIBA()) {
            if (estaTocandoSuelo) {
               aceleracion = 0;
            }
        }
        saltar(obstaculos, 6);
    }
    
    private void lanzarPiedra(ArrayList<ObjetoInerte> obstaculos) {
        if (piedras > 0) {
            if (Teclado.isLANZARFRONTAL()) {
                if (direccion) {
                    if (!(piedraIzquierda || piedraArriba || piedraAbajo)) {
                        piedraDerecha = true;
                        crearPiedra();
                    }
                } else {
                    if (!(piedraDerecha || piedraArriba || piedraAbajo)) {
                        piedraIzquierda = true;
                        crearPiedra();
                    }
                }
            } else if (Teclado.isLANZARARRIBA()) {
                if (!(piedraIzquierda || piedraDerecha || piedraAbajo)) {
                    piedraArriba = true;
                    crearPiedra();
                }
            } else if (Teclado.isLANZARABAJO()) {
                if (!(piedraIzquierda || piedraDerecha || piedraArriba)) {
                    piedraAbajo = true;
                    crearPiedra();
                }
            }
            desplazarPiedraDerecha(obstaculos);
            desplazarPiedraIzquierda(obstaculos);
            desplazarPiedraArriba(obstaculos);
            desplazarPiedraAbajo(obstaculos);
        }
    }
    
    private void crearPiedra() {
        if (piedra == null) {
            piedra = new Piedra(x, y + 20, 40, 30);
        }
    }
    
    private void desplazarPiedraDerecha(ArrayList<ObjetoInerte> obstaculos) {
        if (piedraDerecha) {
            piedra.actualizar(this);
            if (!piedra.desplazarseDerecha(obstaculos, 9)) {
                piedraDerecha = false;
                piedra = null;
                piedras--;
            }
        }
    }
    
    private void desplazarPiedraIzquierda(ArrayList<ObjetoInerte> obstaculos) {
        if (piedraIzquierda) {
            piedra.actualizar(this);
            if (!piedra.desplazarseIzquierda(obstaculos, 9)) {
                piedraIzquierda = false;
                piedra = null;
                piedras--;
            }
        }
    }
    
    private void desplazarPiedraArriba(ArrayList<ObjetoInerte> obstaculos) {
        if (piedraArriba) {
            piedra.actualizar(this);
            if (!piedra.desplazarseArriba(obstaculos, 9)) {
                piedraArriba = false;
                piedra = null;
                piedras--;
            }
        }
    }
    
    private void desplazarPiedraAbajo(ArrayList<ObjetoInerte> obstaculos) {
        if (piedraAbajo) {
            piedra.actualizar(this);
            if (!piedra.desplazarseAbajo(obstaculos, 9)) {
                piedraAbajo = false;
                piedra = null;
                piedras--;
            }
        }
    }
    
    public void eliminarPiedraSiSaleDeEscenario() {
        if (piedra != null) {
            if (isFueraDeEscenario(piedra)) {
                abortarLanzamiento();
                piedra = null;
                piedras--;
            }
        }
    }
    
    private void abortarLanzamiento() {
        piedraAbajo = false;
        piedraArriba = false;
        piedraDerecha = false;
        piedraIzquierda = false;
    }
    
    private boolean isFueraDeEscenario(Piedra piedra) {
        return piedra.getX() > anchoPantalla || piedra.getX() + piedra.getAncho() < 0;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * 
     * Este metodo cambia los siguientes atributos:
     * - countAuxForGravity
     * - velocidadVertical
     * 
     * Hace que el jugador caiga con aceleración hasta posarse sobre alguna
     * base.
     * 
     * No se puede invocar este metodo más de una vez dentro de
     * ningún metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @return Devuelve true si el jugador intercepta con algo debajo de él.
     * @author Milton Lenis
     */
    private boolean gravedad(ArrayList<ObjetoInerte> obstaculos) {
        if(countAuxForGravity == 5) {
            countAuxForGravity = 0;
            velocidadVertical++;
        } else {
            countAuxForGravity++;
        }
        boolean hayAlgoAbajo = !desplazarseAbajo(obstaculos, velocidadVertical);
        if (hayAlgoAbajo && aceleracion < 0) {
            velocidadVertical = 1;
            countAuxForGravity = 0;
        }
        return hayAlgoAbajo;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * 
     * Este metodo cambia los siguientes atributos:
     * - aceleracion
     * - velocidadVertical
     * 
     * Hace que el jugador salte con desaceleracion hasta cierto punto.
     * Si choca con algún objeto arriba de él la desaceleración es total.
     * 
     * No se puede invocar este metodo más de una vez dentro de
     * ningún metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @author Milton Lenis
     */
    private void saltar(ArrayList<ObjetoInerte> obstaculos, int alturaDeSalto) {
        if (aceleracion >= 0) {
            boolean hayAlgoArriba = !desplazarseArriba(obstaculos, alturaDeSalto);
            aceleracion = alturaDeSalto - velocidadVertical;
            if (aceleracion < 0 || hayAlgoArriba) {
                velocidadVertical = 1;
                aceleracion = -1;
            }
        }
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
     * No se puede invocar este metodo más de una vez dentro de
     * ningún metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @return Devuelve false si se intersepta un obstaculo.
     * @author Milton Lenis
     */
    private boolean desplazarseIzquierda(ArrayList<ObjetoInerte> obstaculos) {
        boolean viaLibre = true;
        Rectangle clon = getRectangulo();
        clon.setX(x - pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(clon, obs.getRectangulo())) {
                    viaLibre = false;
                    desplazamiento = obs.getxInit() + obs.getAncho();
                    if (!distanciaCritica) { x = desplazamiento; }
                    break;
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
     * No se puede invocar este metodo más de una vez dentro de
     * ningún metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @return Devuelve false si se intersepta un obstaculo.
     * @author Milton Lenis
     */
    private boolean desplazarseDerecha(ArrayList<ObjetoInerte> obstaculos) {
        boolean viaLibre = true;
        Rectangle clon = getRectangulo();
        clon.setX(x + pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(clon, obs.getRectangulo())) {
                    viaLibre = false;
                    desplazamiento = obs.getxInit() - ancho;
                    if (!distanciaCritica) { x = desplazamiento; }
                    break;
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
     * No se puede invocar este metodo más de una vez dentro de
     * ningún metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @param velocidad velocidad a la que se moverá el jugador.
     * @return Devuelve false si se intersepta un obstaculo.
     * @author Milton Lenis
     */
    private boolean desplazarseArriba(ArrayList<ObjetoInerte> obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle clon = getRectangulo();
        clon.setY(y - (pasos * velocidad));
        for (ObjetoInerte obs : obstaculos) {
            if (clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(clon, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() + obs.getAlto();
                    break;
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
     * No se puede invocar este metodo más de una vez dentro de
     * ningún metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @param velocidad velocidad a la que se moverá el jugador.
     * @return Devuelve false si se intersepta un obstaculo.
     * @author Milton Lenis
     */
    private boolean desplazarseAbajo(ArrayList<ObjetoInerte> obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle clon = getRectangulo();
        clon.setY(y + (pasos * velocidad));
        for (ObjetoInerte obs : obstaculos) {
            if (clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(clon, obs.getRectangulo())) {
                    viaLibre = false;
                    y = obs.getY() - alto;
                    break;
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
    private int determinarDistanciaCritica() {
        int distCrit = ((anchoPantalla / 2) - 2*ancho);
        if (desplazamiento >= distCrit) {
            distanciaCritica = !(desplazamiento < (distCrit + pasos) && Teclado.isIZQUIERDA());
        } else {
            distanciaCritica = false;
        }
        return distCrit;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * @param clon rectangulo con las dimensiones del objeto.
     * @param obstaculo rectangulo con las dimensiones del obstaculo con el que colisiona.
     * @return true si la colision con un obstaculo es por la derecha o por la izquierda.
     * @author Milton Lenis
     */
    private boolean ObstaculoDirHorizontal(Rectangle clon, Rectangle obstaculo) {
        return !(clon.getBoundsInLocal().getMinY() == obstaculo.getBoundsInLocal().getMaxY()
                || clon.getBoundsInLocal().getMaxY() == obstaculo.getBoundsInLocal().getMinY());
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * @param clon rectangulo con las dimensiones del objeto
     * @param obstaculo rectangulo con las dimensiones del obstaculo con el que colisiona.
     * @return true si la colision con un obstaculo es por la derecha o por la izquierda.
     * @author Milton Lenis
     */
    private boolean ObstaculoDirVertical(Rectangle clon, Rectangle obstaculo) {
        return !(clon.getBoundsInLocal().getMaxX() == obstaculo.getBoundsInLocal().getMinX()
                || clon.getBoundsInLocal().getMinX() == obstaculo.getBoundsInLocal().getMaxX());
    }
    
    /**
     * @author Diego Carvajal
     */
    private void animacion() {
        boolean movimientoI = false; // si se está moviendo esto será true
        boolean movimientoD = false;
        if (velocidadHorizontal < 0) {
            this.imagen = new Image("CorrerI/"+this.secuencia+".png");
            movimientoI = true;
            direccion = false;
        } 
        if (velocidadHorizontal > 0) {
            movimientoD = true;
            this.imagen = new Image("CorrerD/"+this.secuencia+".png");
            direccion = true;
        } 
        if (((!movimientoI  && movimientoD) || (!movimientoD && movimientoI))) { //Operador lógico "XOR"
            if(this.cuenta == 10) {
                cuenta = 0;
                if(this.secuencia == 7) {
                    secuencia = 1;
                } else {
                    secuencia++;
                } 
             }
            else {
                cuenta++;
            }
        }
    }
    
    public void Graffitear(ArrayList<Pared> paredes){
        for(Pared pared: paredes){
            if (Teclado.isVANDALIZAR()) {
                if((pared.getX() + pared.getAncho() >= x + ancho && pared.getX() < x
                        && pared.getY() + pared.getAlto() >= y + alto && pared.getY() <= y)){//Se puede generalizar para todo obstáculo que tenga encima la pared
                    Image imagen = new Image("Nucleo/Recursos/Paredmodificada.png");
                    pared.setImagen(imagen);
                }
            }
        }
    }
    
    public void RecogerPiedra(ArrayList<Piedra> piedras){
        for(Piedra piedra: piedras){
            if (piedra.isVisible()) {
                if (piedra.getRectangulo().intersects(this.getRectangulo().getBoundsInLocal())) {
                    this.piedras++;
                    piedra.setVisible(false);
                }
            }
        }
    }
    
    private void morir(ArrayList<Enemigo> enemigos) {
        if (y > altoPantalla || intersectsEnemigo(enemigos)) {
            muerto = true;
        }
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * 
     * Este metodo cambia los siguientes atributos:
     * - x
     * - y
     * - desplazamiento
     * 
     * Sirve para hacer respawn (el jugador regresa a la posición incial).
     * 
     * @param altoPantalla
     * @param distCritica
     * @return true si se ha superado la distancia critica y se a caido por un abismo.
     */
    private boolean respawn1(int distCritica, ArrayList<Enemigo> enemigos){
        boolean seHaSuperadoLaDistanciaCritica = false; 
        if (muerto) {
            if (distanciaCritica) {
                x = distCritica;
                seHaSuperadoLaDistanciaCritica = true;
            } else {
                x = 40;
            }
            y = 200;
            desplazamiento = x;
            muerto = false;
            respawn = true;
            vida--;
            piedra = null;
            piedras = 0;
            abortarLanzamiento();
            revivirEnemigo(enemigos);
        }
        return seHaSuperadoLaDistanciaCritica;
    }
    
    /**
     * ************************* METODO NO ALTERABLE *************************
     * 
     * Este metodo cambia los siguientes atributos:
     * - x
     * - desplazamiento
     * 
     * Sirve para hacer respawn (el jugador regresa a la posición incial),
     * pero solo cuando se ha superado la distancia critica..
     * 
     * @param seHaSuperadoLaDistanciaCritica 
     */
    private void respawn2(boolean seHaSuperadoLaDistanciaCritica) {
        if (seHaSuperadoLaDistanciaCritica) {
            x = 40;
            desplazamiento = x;
        }
    }
     
    @Override
    public Rectangle getRectangulo() {
        return new Rectangle(x, y, ancho, alto);
    }

    public byte getPasos() {
        return pasos;
    }

    public void setPasos(byte pasos) {
        this.pasos = pasos;
    }

    public int getVelocidadHorizontal() {
        return velocidadHorizontal;
    }

    public void setVelocidadHorizontal(int velocidad) {
        this.velocidadHorizontal = velocidad;
    }

    public int getVelocidadVertical() {
        return velocidadVertical;
    }

    public void setVelocidadVertical(int velocidadVertical) {
        this.velocidadVertical = velocidadVertical;
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
    
    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public boolean isMuerto() {
        return muerto;
    }

    public void setMuerto(boolean muerto) {
        this.muerto = muerto;
    }

    public boolean isRespawn() {
        return respawn;
    }

    public void setRespawn(boolean respawn) {
        this.respawn = respawn;
    }
    
}