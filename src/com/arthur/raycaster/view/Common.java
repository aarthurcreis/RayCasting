package com.arthur.raycaster.view;

import com.arthur.raycaster.core.Bolinha;
import com.arthur.raycaster.map.Mapa;
import com.arthur.raycaster.math.Raios;

public class Common {
    public final Mapa mapa;
    public final Raios raios;
    public final Bolinha bolinha;

    public Common() {
        mapa = new Mapa();
        mapa.inicializarParedes();
        bolinha = new Bolinha(mapa);
        raios = new Raios(bolinha, mapa);
    }
}