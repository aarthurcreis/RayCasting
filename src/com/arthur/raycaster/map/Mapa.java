package com.arthur.raycaster.map;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Mapa {
    private static final int TILE = 30;

    // armazena retângulos que representam paredes
    private static final ArrayList<Rectangle> PAREDES = new ArrayList<>();

    private static final int[][] MAPA = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public Mapa() {}

    public int getTile() { return TILE; }
    public int[][] getMapa() { return MAPA; }
    public ArrayList<Rectangle> getParedes() { return PAREDES; }

    public void inicializarParedes() {
        for(int linha = 0; linha < MAPA.length; linha++) {
            for(int coluna = 0; coluna < MAPA[0].length; coluna++) {
                if(MAPA[linha][coluna] == 1) {
                    PAREDES.add(new Rectangle(coluna * TILE, linha * TILE, TILE, TILE));
                }
            }
        }
    }
}