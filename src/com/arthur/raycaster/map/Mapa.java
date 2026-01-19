package com.arthur.raycaster.map;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Mapa {
    private static final int TILE = 30;

    // armazena retângulos que representam paredes
    private static final ArrayList<Rectangle> PAREDES = new ArrayList<>();

    private static final int[][] mapa = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public Mapa() {}

    public int getTile() { return TILE; }
    public int[][] getMapa() { return mapa; }
    public ArrayList<Rectangle> getParedes() { return PAREDES; }

    public void inicializarParedes() {
        for(int linha = 0; linha < mapa.length; linha++) {
            for(int coluna = 0; coluna < mapa[0].length; coluna++) {
                if(mapa[linha][coluna] == 1) {
                    PAREDES.add(new Rectangle(coluna * TILE, linha * TILE, TILE, TILE));
                }
            }
        }
    }
}