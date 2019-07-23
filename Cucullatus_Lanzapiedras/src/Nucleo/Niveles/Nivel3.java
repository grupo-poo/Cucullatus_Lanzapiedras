package Nucleo.Niveles;

import Escenario.ObjetoInerte;
import Escenario.ObjetoRecogible;
import Escenario.Pared;
import Escenario.Portal;
import Personajes.Enemigo;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 * @author Milton Lenis
 */
public class Nivel3 extends EscenarioLayout{

    public Nivel3(int anchoPantalla, int altoPantalla) throws IOException {
        super(anchoPantalla, altoPantalla);
        this.elementosDeFondo = new ObjetoInerte[3];
        this.obstaculos = new ObjetoInerte[6];
        this.piedras = new ObjetoRecogible[0];
        this.aerosoles = new ObjetoRecogible[3];
        this.enemigos = new Enemigo[3];
        this.paredes = new Pared[3];
        cargarJuego();
    }
    
    private void cargarJuego() throws IOException {
        cargarObstaculos();
        cargarEnemigos();
        cargarElementosDeFondo();
        cargarParedes();
        cargarPiedras();
        cargarAerosoles();
        cargarPortal();
    }
    
    private void cargarEnemigos() {
        Enemigo enemigo1 = new Enemigo(anchoPantalla);
        enemigo1.setX(((obstaculos[3].getX() - (obstaculos[2].getX() + obstaculos[2].getAncho()))/2) + (obstaculos[2].getX() + obstaculos[2].getAncho()));
        enemigo1.setY(obstaculos[1].getY() - enemigo1.getAlto());
        enemigo1.setAnimacion1(true);
        enemigos[0] = enemigo1;
        
        Enemigo enemigo2 = new Enemigo(anchoPantalla);
        enemigo2.setX(((obstaculos[4].getX() - (obstaculos[3].getX() + obstaculos[3].getAncho()))/2) + (obstaculos[3].getX() + obstaculos[3].getAncho()));
        enemigo2.setY(obstaculos[1].getY() - enemigo2.getAlto());
        enemigo2.setAnimacion1(true);
        enemigos[1] = enemigo2;
        
        Enemigo enemigo3 = new Enemigo(anchoPantalla);
        enemigo3.setX(((obstaculos[5].getX() - (obstaculos[4].getX() + obstaculos[4].getAncho()))/2) + (obstaculos[4].getX() + obstaculos[4].getAncho()));
        enemigo3.setY(obstaculos[1].getY() - enemigo1.getAlto());
        enemigo3.setAnimacion1(true);
        enemigos[2] = enemigo3;
    }
    
    private void cargarObstaculos() {
        Image imagen = new Image("Nucleo/Recursos/Madera.png");
        ObjetoInerte paredLimiteIzquierdo = new ObjetoInerte(imagen); // borde para que no caiga
        paredLimiteIzquierdo.setAncho(1);
        paredLimiteIzquierdo.setAlto(altoPantalla);
        paredLimiteIzquierdo.setX(-1);
        paredLimiteIzquierdo.setY(0);
        obstaculos[0] = paredLimiteIzquierdo;
                
        ObjetoInerte suelo1_DePrueba = new ObjetoInerte(imagen);
        suelo1_DePrueba.setAncho(anchoPantalla * 2);
        suelo1_DePrueba.setAlto(altoPantalla / 9);
        suelo1_DePrueba.setX(0);
        suelo1_DePrueba.setY(altoPantalla - altoPantalla / 9);
        obstaculos[1] = suelo1_DePrueba;
                
        ObjetoInerte obstaculo1_DePrueba = new ObjetoInerte(imagen);
        obstaculo1_DePrueba.setAncho(50);
        obstaculo1_DePrueba.setAlto(50);
        obstaculo1_DePrueba.setX(anchoPantalla*3/8);
        obstaculo1_DePrueba.setY(suelo1_DePrueba.getY() - obstaculo1_DePrueba.getAlto());
        obstaculos[2] = obstaculo1_DePrueba;
        
        ObjetoInerte obstaculo2_DePrueba = new ObjetoInerte(imagen);
        obstaculo2_DePrueba.setAncho(50);
        obstaculo2_DePrueba.setAlto(50);
        obstaculo2_DePrueba.setX((obstaculo1_DePrueba.getX() + obstaculo1_DePrueba.getAncho()) + (anchoPantalla/4));         
        obstaculo2_DePrueba.setY(suelo1_DePrueba.getY() - obstaculo2_DePrueba.getAlto());
        obstaculos[3] = obstaculo2_DePrueba;
        
        ObjetoInerte obstaculo3_DePrueba = new ObjetoInerte(imagen);
        obstaculo3_DePrueba.setAncho(50);
        obstaculo3_DePrueba.setAlto(50);
        obstaculo3_DePrueba.setX((obstaculo2_DePrueba.getX() + obstaculo2_DePrueba.getAncho()) + (anchoPantalla/4));         
        obstaculo3_DePrueba.setY(suelo1_DePrueba.getY() - obstaculo3_DePrueba.getAlto());
        obstaculos[4] = obstaculo3_DePrueba;
        
        ObjetoInerte obstaculo4_DePrueba = new ObjetoInerte(imagen);
        obstaculo4_DePrueba.setAncho(50);
        obstaculo4_DePrueba.setAlto(50);
        obstaculo4_DePrueba.setX((obstaculo3_DePrueba.getX() + obstaculo3_DePrueba.getAncho()) + (anchoPantalla/4));         
        obstaculo4_DePrueba.setY(suelo1_DePrueba.getY() - obstaculo4_DePrueba.getAlto());
        obstaculos[5] = obstaculo4_DePrueba;
    }
    
    private void cargarPiedras() {
        
    }
    
    private void cargarAerosoles() {
        Image imagen = new Image("Nucleo/Recursos/Aerosol.png");
        ObjetoRecogible aerosol1 = new ObjetoRecogible(imagen);
        aerosol1.setAncho(20);
        aerosol1.setAlto(60);
        aerosol1.setX(enemigos[0].getX());
        aerosol1.setY(obstaculos[1].getY() - aerosol1.getAlto());
        aerosoles[0] = aerosol1;
        
        imagen = new Image("Nucleo/Recursos/Aerosol.png");
        ObjetoRecogible aerosol2 = new ObjetoRecogible(imagen);
        aerosol2.setAncho(20);
        aerosol2.setAlto(60);
        aerosol2.setX(enemigos[1].getX());
        aerosol2.setY(obstaculos[1].getY() - aerosol2.getAlto());
        aerosoles[1] = aerosol2;
        
        imagen = new Image("Nucleo/Recursos/Aerosol.png");
        ObjetoRecogible aerosol3 = new ObjetoRecogible(imagen);
        aerosol3.setAncho(20);
        aerosol3.setAlto(60);
        aerosol3.setX(enemigos[2].getX());
        aerosol3.setY(obstaculos[1].getY() - aerosol3.getAlto());
        aerosoles[2] = aerosol3;
    }
    
    private void cargarParedes() {
        Image imagen = new Image("Nucleo/Recursos/Pared.png");
        Pared pared1 = new Pared(imagen);
        pared1.setAncho(90);
        pared1.setAlto(90);
        pared1.setX(obstaculos[1].getX() + 100);
        pared1.setY(obstaculos[1].getY() - pared1.getAlto());
        paredes[0] = pared1;
        
        Pared pared2 = new Pared(imagen);
        pared2.setAncho(pared1.getAncho());
        pared2.setAlto(pared1.getAlto());
        pared2.setX(pared1.getX() + pared1.getAncho() + 20);
        pared2.setY(pared1.getY());
        paredes[1] = pared2;
        
        Pared pared3 = new Pared(imagen);
        pared3.setAncho(pared1.getAncho());
        pared3.setAlto(pared1.getAlto());
        pared3.setX(pared2.getX() + pared1.getAncho() + 20);
        pared3.setY(pared1.getY());
        paredes[2] = pared3;
    }
    
    private void cargarElementosDeFondo() {
        Image imagen = new Image("Nucleo/Recursos/Fondo.png");
        ObjetoInerte fondoDePrueba = new ObjetoInerte(imagen);
        fondoDePrueba.setAlto(altoPantalla);
        elementosDeFondo[0] = fondoDePrueba;
        
        //Se preparan los elementos del escenario
        imagen = new Image("Nucleo/Recursos/Mensaje.png");
        ObjetoInerte mensaje1DePrueba = new ObjetoInerte(imagen);
        mensaje1DePrueba.setAncho(300);
        mensaje1DePrueba.setAlto(150);
        mensaje1DePrueba.setX(100);
        mensaje1DePrueba.setY(400);
        elementosDeFondo[1] = mensaje1DePrueba;
        
        imagen = new Image("Nucleo/Recursos/Mensaje2.png");
        ObjetoInerte mensaje2DePrueba = new ObjetoInerte(imagen);
        mensaje2DePrueba.setAncho(150);
        mensaje2DePrueba.setAlto(140);
        mensaje2DePrueba.setX(100);
        mensaje2DePrueba.setY(250);
        elementosDeFondo[2] = mensaje2DePrueba;
    }
    
    private void cargarPortal() {
        Image imagen = new Image("Nucleo/Recursos/Portal.png");
        portal = new Portal(imagen);
        portal.setAncho(50);
        portal.setAlto(70);
        portal.setX(40);
        portal.setY(obstaculos[1].getY() - portal.getAlto());
    }
}