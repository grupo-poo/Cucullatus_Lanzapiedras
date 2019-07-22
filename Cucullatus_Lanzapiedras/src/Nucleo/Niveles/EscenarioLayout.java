package Nucleo.Niveles;

import Escenario.ObjetoInerte;
import Escenario.ObjetoRecogible;
import Escenario.Pared;
import Escenario.Portal;
import Personajes.Enemigo;

/**
 * @author Milton Lenis
 */
public class EscenarioLayout {
    protected ObjetoInerte[] elementosDeFondo;
    protected ObjetoInerte[] obstaculos;
    protected ObjetoRecogible[] aerosoles;
    protected ObjetoRecogible[] piedras;
    protected Enemigo[] enemigos;
    protected Pared[] paredes;
    protected Portal portal;
    
    protected final int anchoPantalla;
    protected final int altoPantalla;

    public EscenarioLayout(int anchoPantalla, int altoPantalla) {
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
    }

    public ObjetoInerte[] getElementosDeFondo() {
        return elementosDeFondo;
    }

    public ObjetoInerte[] getObstaculos() {
        return obstaculos;
    }

    public ObjetoRecogible[] getAerosoles() {
        return aerosoles;
    }

    public ObjetoRecogible[] getPiedras() {
        return piedras;
    }

    public Enemigo[] getEnemigos() {
        return enemigos;
    }

    public Pared[] getParedes() {
        return paredes;
    }

    public Portal getPortal() {
        return portal;
    }

    public int getAltoPantalla() {
        return altoPantalla;
    }

    public int getAnchoPantalla() {
        return anchoPantalla;
    }
}
