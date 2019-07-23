package Personajes;

import Control.Teclado;
import Escenario.ObjetoInerte;
import Escenario.Pared;
import Escenario.ObjetoRecogible;
import Nucleo.Debug;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Todas las mecanicas del Jugador se establecen aquí.
 */
public class Jugador extends Personaje{
    
    private final int altoPantalla;
    private final int anchoPantalla;
    private byte pasos;   // Número de pixeles que debe recorrer por frame.
    private int velocidadHorizontal; // pixeles que recorre horizontalmente en tiempo real.
    private int desplazamiento; // Cambia cada vez que el jugador se desplaaza.
    private boolean distanciaCritica; // true cuando esté en el punto donde x no cambia.
    private boolean respawn; // true cuando esté en el punto donde x no cambia.
    private int vida = 10; //Vida inicial del jugador
    private int piedras = 0; //Cantidad de piedras del jugador
    private int aerosoles = 0; //Cantidad de piedras del jugador
    private int enmigosAbatidosPorNivel = 0;
    private int totalEnemigosAbatidos = 0;
    private int aerosolesFaltantes = 3;
    private int murosFaltantes = 3;
    private int puntuacionPorNivel = 0;
    private int puntuacionTotal = 0;
    private String cronometro;
    
    // Auxiliares para el cronometro
    private int contadorAux = 0;
    private int segundos = 0;
    
    // Auxiliares para la animación.
    private int secuencia = 1;//Numero de imagenes, empieza por 1
    private int cuenta= 0;//Ayuda a controlar la cantidad de veces que se pintan las imágenes
    
    public Jugador(Image imagen, int anchoPantalla, int altoPantalla) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
        this.imagen = imagen;
        this.x = 40;
        this.y = 150;
        this.desplazamiento = x;
        this.ancho = (int) imagen.getWidth();
        this.alto  = (int) imagen.getHeight();
        this.pasos = 3;
        this.velocidadVertical = 1;
        this.direccion = true;
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
     * @param paredes
     * @param piedras
     * @param aerosoles 
     */
    public void actualizar(ObjetoInerte[] obstaculos, Enemigo[] enemigos,
            Pared[] paredes, ObjetoRecogible[] piedras, ObjetoRecogible[] aerosoles) {
        velocidadHorizontal = desplazamiento;
        int distCrit = determinarDistanciaCritica();
        mover(obstaculos); // Aquí movemos al jugador.
        morir(enemigos);
        respawn1(distCrit, enemigos, paredes, piedras, aerosoles);
        velocidadHorizontal = desplazamiento - velocidadHorizontal;
        respawn2();
        lanzarPiedra(obstaculos);
        eliminarPiedraSiSaleDeEscenario();
        abatir(enemigos);
        graffitear(paredes);
        this.cronometro = cronometro();
        if (recogerObjeto(piedras)) { this.piedras++; }
        if (recogerObjeto(aerosoles)) {
            this.aerosoles++;
            this.aerosolesFaltantes--;
        }

        //////////////////////////////////////////
        /**
         * Todo esto puede ser eliminado, solo sirve para depurar.
         */
        Debug.lapiz.fillText("Desplazamiento: " + desplazamiento, anchoPantalla - 200, 30);
        Debug.lapiz.fillText("Posición x: " + x, anchoPantalla - 200, 42);
        Debug.lapiz.fillText("Posición y: " + y, anchoPantalla - 200, 54);
        Debug.lapiz.fillText("Velocidad: " + velocidadHorizontal, anchoPantalla - 200, 66);
        Debug.lapiz.fillText("Distancia Critica: " + distanciaCritica, anchoPantalla - 200, 78);
        Debug.lapiz.fillText("Aceleracion: " + aceleracion, anchoPantalla - 200, 90);
        Debug.lapiz.fillText("Piedras: " + this.piedras, anchoPantalla - 200, 102);
        Debug.lapiz.fillText("Aerosoles: " + this.aerosoles, anchoPantalla - 200, 114);
        Debug.lapiz.fillText("Aeaaaaa: " + distCrit, anchoPantalla - 200, 140);
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
     * Contiene las instrucciones que permiten mover
     * al jugador por el escenario.
     * 
     * Se le pueden añadir nuevos metodos si así se requiere o cualquier
     * otra alteración de su estructura.
     * 
     * @param obstaculos array de obstaculos.
     */
    private void mover(ObjetoInerte[] obstaculos) {
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
    
    private void lanzarPiedra(ObjetoInerte[] obstaculos) {
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
    
    private void desplazarPiedraDerecha(ObjetoInerte[] obstaculos) {
        if (piedraDerecha) {
            piedra.actualizar(this);
            if (!piedra.desplazarseDerecha(obstaculos, 9)) {
                piedraDerecha = false;
                piedra = null;
                piedras--;
            }
        }
    }
    
    private void desplazarPiedraIzquierda(ObjetoInerte[] obstaculos) {
        if (piedraIzquierda) {
            piedra.actualizar(this);
            if (!piedra.desplazarseIzquierda(obstaculos, 9)) {
                piedraIzquierda = false;
                piedra = null;
                piedras--;
            }
        }
    }
    
    private void desplazarPiedraArriba(ObjetoInerte[] obstaculos) {
        if (piedraArriba) {
            piedra.actualizar(this);
            if (!piedra.desplazarseArriba(obstaculos, 9)) {
                piedraArriba = false;
                piedra = null;
                piedras--;
            }
        }
    }
    
    private void desplazarPiedraAbajo(ObjetoInerte[] obstaculos) {
        if (piedraAbajo) {
            piedra.actualizar(this);
            if (!piedra.desplazarseAbajo(obstaculos, 9)) {
                piedraAbajo = false;
                piedra = null;
                piedras--;
            }
        }
    }
    
    @Override
    protected void eliminarPiedraSiSaleDeEscenario() {
        if (piedra != null) {
            if (isFueraDeEscenario(piedra)) {
                abortarLanzamiento();
                piedra = null;
                piedras--;
            }
        }
    }
    
    @Override
    protected void abortarLanzamiento() {
        piedraAbajo = false;
        piedraArriba = false;
        piedraDerecha = false;
        piedraIzquierda = false;
    }
    
    @Override
    protected boolean isFueraDeEscenario(ObjetoRecogible piedra) {
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
    @Override
    protected boolean gravedad(ObjetoInerte[] obstaculos) {
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
     * @param alturaDeSalto
     */
    @Override
    protected void saltar(ObjetoInerte[] obstaculos, int alturaDeSalto) {
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
    private boolean desplazarseIzquierda(ObjetoInerte[] obstaculos) {
        boolean viaLibre = true;
        Rectangle clon = getRectangulo();
        clon.setX(x - pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(clon, obs.getRectangulo())) {
                    viaLibre = false;
                    desplazamiento = obs.getxInit() + obs.getAncho();
                    if (!distanciaCritica) { x = desplazamiento; }
                }
            } 
        }
        if (viaLibre) {
            while ((desplazamiento - xInit) % pasos != 0) { desplazamiento--;}
            desplazamiento -= pasos;
            if (!distanciaCritica) { x = desplazamiento; }
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
    private boolean desplazarseDerecha(ObjetoInerte[] obstaculos) {
        boolean viaLibre = true;
        Rectangle clon = getRectangulo();
        clon.setX(x + pasos);
        for (ObjetoInerte obs : obstaculos) {
            if (clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirHorizontal(clon, obs.getRectangulo())) {
                    viaLibre = false;
                    desplazamiento = obs.getxInit() - ancho;
                    if (!distanciaCritica) { x = desplazamiento; }
                }
            }
        }
        if (viaLibre) {
            while ((desplazamiento - xInit) % pasos != 0) { desplazamiento++; }
            desplazamiento += pasos;
            if (!distanciaCritica) { x = desplazamiento; }
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
    private boolean desplazarseArriba(ObjetoInerte[] obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle clon = getRectangulo();
        clon.setY(y - (pasos * velocidad));
        for (ObjetoInerte obs : obstaculos) {
            if (clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(clon, obs.getRectangulo())) {
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
     * No se puede invocar este metodo más de una vez dentro de
     * ningún metodo.
     * 
     * @param obstaculos array de obstaculos.
     * @param velocidad velocidad a la que se moverá el jugador.
     * @return Devuelve false si se intersepta un obstaculo.
     * @author Milton Lenis
     */
    private boolean desplazarseAbajo(ObjetoInerte[] obstaculos, int velocidad) {
        boolean viaLibre = true;
        Rectangle clon = getRectangulo();
        clon.setY(y + (pasos * velocidad));
        for (ObjetoInerte obs : obstaculos) {
            if (clon.intersects(obs.getRectangulo().getBoundsInLocal())) {
                if (ObstaculoDirVertical(clon, obs.getRectangulo())) {
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
    
    @Override
    public void dibujar(GraphicsContext lapiz) {
        if (piedra != null) {
            piedra.dibujar(lapiz);
        }
        cronometro();
        
        boolean moviendose = false;
        if (velocidadHorizontal > 0) {
            direccion = true;
            moviendose = true;
        } else if ( velocidadHorizontal < 0 ) {
            direccion = false;
            moviendose = true;
        }
        int aux = 1;
        if (moviendose) {
            if (cuenta == 30) {
                cuenta = 0;
            } else if (cuenta < 16) {
                aux = 0;
            }
            cuenta++;
        }
        
        if (direccion) {
            lapiz.drawImage(imagen, aux*(imagen.getWidth()/2) , 0, (imagen.getWidth()/2), imagen.getHeight()/2 , x, y, ancho, alto);
        } else {
            lapiz.drawImage(imagen, aux*(imagen.getWidth()/2) , imagen.getHeight()/2, (imagen.getWidth()/2), imagen.getHeight()/2 , x, y, ancho, alto);
        }
        
        if (cronometro != null) {
            lapiz.fillText("Tiempo de juego: " + cronometro, 20, 30);
        }
        lapiz.fillText("Muros que faltan por graffitear: " + murosFaltantes, 20, 44);
        lapiz.fillText("Enemigos Abatidos: " + (enmigosAbatidosPorNivel + totalEnemigosAbatidos), 20, 58);
        lapiz.fillText("Aerosoles Faltantes: " + aerosolesFaltantes, 20, 72);
        lapiz.fillText("Puntuación: " + (puntuacionTotal + puntuacionPorNivel), 20, 86);
        lapiz.fillText("Piedras: " + piedras, 20, 100);
    }
    
    public void graffitear(Pared[] paredes){
        for(Pared pared: paredes){
            if (Teclado.isVANDALIZAR() && !pared.isIsGraffiteada() && aerosoles > 0) {
                if((pared.getX() + pared.getAncho() >= x + ancho && pared.getX() < x
                        && pared.getY() + pared.getAlto() >= y + alto && pared.getY() <= y)){//Se puede generalizar para todo obstáculo que tenga encima la pared
                    pared.setIsGraffiteada(true);
                    aerosoles--;
                    murosFaltantes--;
                    puntuacionPorNivel += 10;
                }
            }
        }
    }
    
    public boolean recogerObjeto(ObjetoRecogible[] objetos){
        boolean recoger = false;
        for(ObjetoRecogible objeto: objetos){
            if (objeto.isVisible()) {
                if (objeto.getRectangulo().intersects(this.getRectangulo().getBoundsInLocal())) {
                    recoger = true;
                    objeto.setVisible(false);
                    puntuacionPorNivel += 5;
                }
            }
        }
        return recoger;
    }
    
    private void morir(Enemigo[] enemigos) {
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
    private void respawn1(int distCritica, Enemigo[] enemigos,
            Pared[] paredes, ObjetoRecogible[] piedras, ObjetoRecogible[] aerosoles){
        if (muerto || respawn) {
            if (distanciaCritica) {
                this.x = distCritica;
            } else {
                this.x = 40;
            }
            if (muerto) this.vida--;
            this.y = 200;
            this.desplazamiento = x;
            this.piedra = null;
            this.piedras = 0;
            this.aerosoles = 0;
            this.enmigosAbatidosPorNivel = 0;
            this.aerosolesFaltantes = 3;
            this.murosFaltantes = 3;
            this.puntuacionPorNivel = 0;
            abortarLanzamiento();
            revivirEnemigo(enemigos);
            desgraffitear(paredes);
            reaparecerObjetos(piedras);
            reaparecerObjetos(aerosoles);
        }
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
     */
    private void respawn2() {
        if (muerto || respawn) {
            if (distanciaCritica) {
                x = 40;
                desplazamiento = x;
            }
            muerto = false;
            respawn = false;
        }
    }
    
    private void abatir(Enemigo[] enemigos) {
        if (piedra != null) {
            for (Enemigo enemigo : enemigos) {
                if (enemigo.getRectangulo().intersects(piedra.getRectangulo().getBoundsInLocal())) {
                    enemigo.setMuerto(true);
                    piedra = null;
                    piedras--;
                    enmigosAbatidosPorNivel++;
                    puntuacionPorNivel += 15;
                    abortarLanzamiento();
                    break;
                }
            }
        }
    }
    
    private void revivirEnemigo(Enemigo[] enemigos) {
        for (Enemigo enemigo : enemigos) {
            enemigo.setMuerto(false);
        }
    }
    
    private void desgraffitear(Pared[] paredes) {
        for (Pared pared : paredes) {
            pared.setIsGraffiteada(false);
        }
    }
    
    private void reaparecerObjetos(ObjetoRecogible[] piedras) {
        for (ObjetoRecogible piedra : piedras) {
            piedra.setVisible(true);
        }
    }
    
    private boolean intersectsEnemigo(Enemigo[] enemigos) {
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
    
    private String cronometro() {
        if (contadorAux == 120) {
            contadorAux=0;
            segundos++;
        } else {
            contadorAux++;
        }
        return "hh " + ((segundos/3600)%60) + " / mm " + ((segundos/60)%60) + " / ss " + (segundos%60);
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
    
    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public boolean isRespawn() {
        return respawn;
    }

    public void setRespawn(boolean respawn) {
        this.respawn = respawn;
    }

    public int getEnmigosAbatidosPorNivel() {
        return enmigosAbatidosPorNivel;
    }

    public void setEnmigosAbatidosPorNivel(int enmigosAbatidosPorNivel) {
        this.enmigosAbatidosPorNivel = enmigosAbatidosPorNivel;
    }

    public int getTotalEnemigosAbatidos() {
        return totalEnemigosAbatidos;
    }

    public void setTotalEnemigosAbatidos(int totalEnemigosAbatidos) {
        this.totalEnemigosAbatidos = totalEnemigosAbatidos;
    }

    public String getCronometro() {
        return cronometro;
    }

    public int getPiedras() {
        return piedras;
    }

    public int getAerosoles() {
        return aerosoles;
    }

    public int getAerosolesFaltantes() {
        return aerosolesFaltantes;
    }

    public int getMurosFaltantes() {
        return murosFaltantes;
    }

    public int getPuntuacionPorNivel() {
        return puntuacionPorNivel;
    }

    public void setPuntuacionPorNivel(int puntuacionPorNivel) {
        this.puntuacionPorNivel = puntuacionPorNivel;
    }

    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public void setPuntuacionTotal(int puntuacionTotal) {
        this.puntuacionTotal = puntuacionTotal;
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }
    
}