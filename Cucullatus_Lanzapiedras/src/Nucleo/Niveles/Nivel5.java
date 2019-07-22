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
public class Nivel5 extends EscenarioLayout{

    public Nivel5(int anchoPantalla, int altoPantalla) throws IOException {
        super(anchoPantalla, altoPantalla);
        this.elementosDeFondo = new ObjetoInerte[1];
        this.obstaculos = new ObjetoInerte[2];
        this.piedras = new ObjetoRecogible[2];
        this.aerosoles = new ObjetoRecogible[3];
        this.enemigos = new Enemigo[1];
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
        enemigo1.setX(999);
        enemigo1.setY(999);
        enemigos[0] = enemigo1;
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
        suelo1_DePrueba.setAncho(300);
        suelo1_DePrueba.setAlto(69);
        suelo1_DePrueba.setX(0);
        suelo1_DePrueba.setY(altoPantalla - altoPantalla / 9);
        obstaculos[1] = suelo1_DePrueba;
    }
    
    private void cargarPiedras() {
        Image imagen = new Image("Nucleo/Recursos/Piedra/Piedra.png");
        ObjetoRecogible piedra1 = new ObjetoRecogible(imagen);
        piedra1.setX(obstaculos[1].getX() + 180);
        piedra1.setY(obstaculos[1].getY() - piedra1.getAlto());
        piedras[0] = piedra1;
        
        ObjetoRecogible piedra2 = new ObjetoRecogible(imagen);
        piedra2.setX(obstaculos[1].getX() + 80);
        piedra2.setY(obstaculos[1].getY() - piedra2.getAlto());
        piedras[1] = piedra2;
    }
    
    private void cargarAerosoles() {
        Image imagen = new Image("Nucleo/Recursos/Aerosol.png");
        ObjetoRecogible aerosol1 = new ObjetoRecogible(imagen);
        aerosol1.setAncho(20);
        aerosol1.setAlto(60);
        aerosol1.setX(100);
        aerosol1.setY(obstaculos[1].getY() - aerosol1.getAlto());
        aerosoles[0] = aerosol1;
        
        imagen = new Image("Nucleo/Recursos/Aerosol.png");
        ObjetoRecogible aerosol2 = new ObjetoRecogible(imagen);
        aerosol2.setAncho(20);
        aerosol2.setAlto(60);
        aerosol2.setX(140);
        aerosol2.setY(aerosoles[0].getY());
        aerosoles[1] = aerosol2;
        
        imagen = new Image("Nucleo/Recursos/Aerosol.png");
        ObjetoRecogible aerosol3 = new ObjetoRecogible(imagen);
        aerosol3.setAncho(20);
        aerosol3.setAlto(60);
        aerosol3.setX(180);
        aerosol3.setY(aerosoles[0].getY());
        aerosoles[2] = aerosol3;
    }
    
    private void cargarParedes() {
        Image imagen = new Image("Nucleo/Recursos/Pared.png");
        Pared pared1 = new Pared(imagen);
        pared1.setAncho(90);
        pared1.setAlto(90);
        pared1.setX(0);
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