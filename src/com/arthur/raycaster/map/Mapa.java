package com.arthur.raycaster.map;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Mapa {
    private final int TILE = 30;

    private final ArrayList<Rectangle> paredes = new ArrayList<>(); // armazena retângulos que representam paredes

    private final int[][] mapa = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public Mapa() {
    }

    public int getTILE() { return TILE; }
    public int[][] getMapa() { return mapa; }
    public ArrayList<Rectangle> getParedes() { return paredes; }

    public void inicializarParedes() {
        for(int linha = 0; linha < mapa.length; linha++) {
            for(int coluna = 0; coluna < mapa[0].length; coluna++) {
                if(mapa[linha][coluna] == 1) {
                    paredes.add(new Rectangle(coluna * TILE, linha * TILE, TILE, TILE));
                }
            }
        }
    }
}
