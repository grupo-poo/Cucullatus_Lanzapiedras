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
public class Nivel1 extends EscenarioLayout{

    public Nivel1(int anchoPantalla, int altoPantalla) throws IOException {
        super(anchoPantalla, altoPantalla);
        this.elementosDeFondo = new ObjetoInerte[3];
        this.obstaculos = new ObjetoInerte[9];
        this.piedras = new ObjetoRecogible[2];
        this.aerosoles = new ObjetoRecogible[3];
        this.enemigos = new Enemigo[2];
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
        enemigo1.setX(obstaculos[4].getX() + obstaculos[4].getAncho() - obstaculos[8].getAncho() - enemigo1.getAncho() - 1);
        enemigo1.setY(obstaculos[4].getY() - enemigo1.getAlto());
        enemigo1.setAnimacion1(true);
        enemigos[0] = enemigo1;
        
        Enemigo enemigo2 = new Enemigo(anchoPantalla);
        enemigo2.setX(obstaculos[5].getX() + (obstaculos[5].getAncho() / 2));
        enemigo2.setY(obstaculos[5].getY() - enemigo2.getAlto());
        enemigo2.setAnimacion2(true);
        enemigo2.setLanzarPiedra(true);
        enemigos[1] = enemigo2; 
    }
    
    private void cargarObstaculos() {
        Image imagen = new Image("Nucleo/Recursos/Madera.png");
        ObjetoInerte paredLimiteIzquierdo = new ObjetoInerte(imagen); // borde para que no caiga
        paredLimiteIzquierdo.setAncho(1);
        paredLimiteIzquierdo.setAlto(altoPantalla);
        paredLimiteIzquierdo.setX(-1);
        paredLimiteIzquierdo.setY(0);
        obstaculos[0] = paredLimiteIzquierdo;
        
        ObjetoInerte ObjetoFlotanteDePrueba1 = new ObjetoInerte(imagen);
        ObjetoFlotanteDePrueba1.setAncho(47);
        ObjetoFlotanteDePrueba1.setAlto(40);
        ObjetoFlotanteDePrueba1.setX(502);
        ObjetoFlotanteDePrueba1.setY(425);
        obstaculos[1] = ObjetoFlotanteDePrueba1; 
        
        ObjetoInerte ObjetoFlotanteDePrueba2 = new ObjetoInerte(imagen);
        ObjetoFlotanteDePrueba2.setAlto(40);
        ObjetoFlotanteDePrueba2.setAncho(94);
        ObjetoFlotanteDePrueba2.setX(502);
        ObjetoFlotanteDePrueba2.setY(202);
        obstaculos[2] = ObjetoFlotanteDePrueba2;
        
        ObjetoInerte suelo1_DePrueba = new ObjetoInerte(imagen);
        suelo1_DePrueba.setAncho(anchoPantalla / 3);
        suelo1_DePrueba.setAlto(altoPantalla / 9);
        suelo1_DePrueba.setX(0);
        suelo1_DePrueba.setY(altoPantalla - altoPantalla / 9);
        obstaculos[3] = suelo1_DePrueba;
        
        ObjetoInerte suelo2_DePrueba = new ObjetoInerte(imagen);
        suelo2_DePrueba.setX(suelo1_DePrueba.getX() + suelo1_DePrueba.getAncho() + 200);
        suelo2_DePrueba.setY(suelo1_DePrueba.getY());
        suelo2_DePrueba.setAncho(anchoPantalla + 100 - suelo2_DePrueba.getX());
        suelo2_DePrueba.setAlto(180);
        obstaculos[4] = suelo2_DePrueba;
        
        ObjetoInerte suelo3_DePrueba = new ObjetoInerte(imagen);
        suelo3_DePrueba.setAncho(suelo2_DePrueba.getAncho());
        suelo3_DePrueba.setAlto(suelo2_DePrueba.getAlto());
        suelo3_DePrueba.setX(suelo2_DePrueba.getX() + suelo2_DePrueba.getAncho() + 210);
        suelo3_DePrueba.setY(suelo2_DePrueba.getY());
        obstaculos[5] = suelo3_DePrueba;
        
        ObjetoInerte obstaculo1_DePrueba = new ObjetoInerte(imagen);
        obstaculo1_DePrueba.setAncho(150);
        obstaculo1_DePrueba.setAlto(250);
        obstaculo1_DePrueba.setX(850);
        obstaculo1_DePrueba.setY(suelo1_DePrueba.getY() - obstaculo1_DePrueba.getAlto());
        obstaculos[6] = obstaculo1_DePrueba;
        
        ObjetoInerte obstaculo2_DePrueba = new ObjetoInerte(imagen);
        obstaculo2_DePrueba.setAncho(150);
        obstaculo2_DePrueba.setAlto(40);
        obstaculo2_DePrueba.setX(800);         
        obstaculo2_DePrueba.setY(obstaculo1_DePrueba.getY() - obstaculo2_DePrueba.getAlto());
        obstaculos[7] = obstaculo2_DePrueba;
        
        ObjetoInerte obstaculo3_DePrueba = new ObjetoInerte(imagen);
        obstaculo3_DePrueba.setAncho(40);
        obstaculo3_DePrueba.setAlto(110);
        obstaculo3_DePrueba.setX(suelo2_DePrueba.getX() + suelo2_DePrueba.getAncho() - 40);
        obstaculo3_DePrueba.setY(suelo2_DePrueba.getY() - obstaculo3_DePrueba.getAlto());
        obstaculos[8] = obstaculo3_DePrueba;
    }
    
    private void cargarPiedras() {
        Image imagen = new Image("Nucleo/Recursos/Piedra/Piedra.png");
        ObjetoRecogible piedra1 = new ObjetoRecogible(imagen);
        piedra1.setX(obstaculos[3].getX() + 250);
        piedra1.setY(obstaculos[3].getY() - piedra1.getAlto());
        piedras[0] = piedra1;
        
        ObjetoRecogible piedra2 = new ObjetoRecogible(imagen);
        piedra2.setX(obstaculos[3].getX() + 350);
        piedra2.setY(obstaculos[3].getY() - piedra2.getAlto());
        piedras[1] = piedra2;
    }
    
    private void cargarAerosoles() {
        Image imagen = new Image("Nucleo/Recursos/Aerosol.png");
        ObjetoRecogible aerosol1 = new ObjetoRecogible(imagen);
        aerosol1.setAncho(20);
        aerosol1.setAlto(60);
        aerosol1.setX(obstaculos[3].getX() + obstaculos[3].getAncho() - aerosol1.getAncho());
        aerosol1.setY(obstaculos[3].getY() - aerosol1.getAlto());
        aerosoles[0] = aerosol1;
        
        imagen = new Image("Nucleo/Recursos/Aerosol.png");
        ObjetoRecogible aerosol2 = new ObjetoRecogible(imagen);
        aerosol2.setAncho(20);
        aerosol2.setAlto(60);
        aerosol2.setX(obstaculos[1].getX() + obstaculos[1].getAncho() - aerosol2.getAncho());
        aerosol2.setY(obstaculos[1].getY() - aerosol2.getAlto());
        aerosoles[1] = aerosol2;
        
        imagen = new Image("Nucleo/Recursos/Aerosol.png");
        ObjetoRecogible aerosol3 = new ObjetoRecogible(imagen);
        aerosol3.setAncho(20);
        aerosol3.setAlto(60);
        aerosol3.setX(obstaculos[5].getX() + obstaculos[5].getAncho() - aerosol3.getAncho() - 20);
        aerosol3.setY(obstaculos[5].getY() - aerosol3.getAlto());
        aerosoles[2] = aerosol3;
    }
    
    private void cargarParedes() {
        Image imagen = new Image("Nucleo/Recursos/Pared.png");
        Pared pared1 = new Pared(imagen);
        pared1.setAncho(90);
        pared1.setAlto(90);
        pared1.setX(obstaculos[3].getX() + 100);
        pared1.setY(obstaculos[3].getY() - pared1.getAlto());
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
        portal.setY(obstaculos[3].getY() - portal.getAlto());
    }
}