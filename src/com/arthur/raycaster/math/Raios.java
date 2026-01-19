package com.arthur.raycaster.math;

import com.arthur.raycaster.map.Mapa;
import com.arthur.raycaster.core.Bolinha;

public class Raios {
    private final Mapa mapa;
    private final Bolinha bolinha;

    private static final double FOV = 80d;
    private static final int NUM_RAIOS = 80;
    private static final double[] DISTANCIAS = new double[NUM_RAIOS];
    private static final double[] DISTANCIAS_CORRIGIDAS = new double[NUM_RAIOS];

    private double anguloOlhar = 0;
    private boolean olhaEsq, olhaDir;
    private int inicioLinhaX, inicioLinhaY;

    public Raios(Bolinha bolinha, Mapa mapa) {
        this.bolinha = bolinha;
        this.mapa = mapa;
    }

    public double getFOV() { return FOV; }
    public int getNumRaios() { return NUM_RAIOS; }
    public int getInicioLinhaX() { return inicioLinhaX; }
    public int getInicioLinhaY() { return inicioLinhaY; }
    public double getAnguloOlhar() { return anguloOlhar; }
    public double[] getDistancias() { return DISTANCIAS; }
    public double[] getDistanciasCorrigidas() { return DISTANCIAS_CORRIGIDAS; }

    public void setOlhaEsq(boolean olhaEsq) { this.olhaEsq = olhaEsq; }
    public void setOlhaDir(boolean olhaDir) { this.olhaDir = olhaDir; }

    public void calculoRaios() {
        if (olhaEsq) anguloOlhar = Math.floorMod((int)(anguloOlhar - 2), 360);
        if (olhaDir) anguloOlhar = Math.floorMod((int)(anguloOlhar + 2), 360);

        inicioLinhaX = bolinha.getX() + bolinha.getDiametro() / 2;
        inicioLinhaY = bolinha.getY() + bolinha.getDiametro() / 2;
        double posMapaX = inicioLinhaX / (double) mapa.getTile();
        double posMapaY = inicioLinhaY / (double) mapa.getTile();

        double anguloInicial = anguloOlhar - FOV / 2;
        double passo = FOV / NUM_RAIOS;

        for(int i = 0; i < NUM_RAIOS; i++) {
            double angGraus = anguloInicial + i * passo;
            double ang = Math.toRadians(angGraus);
            double distReal = DDA(posMapaX, posMapaY, Math.cos(ang), Math.sin(ang), mapa.getMapa());

            DISTANCIAS[i] = distReal;
            DISTANCIAS_CORRIGIDAS[i] = distReal * Math.cos(Math.toRadians(angGraus - anguloOlhar));
        }
    }

    private double DDA(double x, double y, double dx, double dy, int[][] mapa) {
        int mx = (int)x, my = (int)y;
        double deltaX = Math.abs(1 / dx);
        double deltaY = Math.abs(1 / dy);
        int stepX = dx < 0 ? -1 : 1;
        int stepY = dy < 0 ? -1 : 1;
        double sideX = (dx < 0) ? (x - mx) * deltaX : (mx + 1.0 - x) * deltaX;
        double sideY = (dy < 0) ? (y - my) * deltaY : (my + 1.0 - y) * deltaY;

        boolean hit = false;
        int side = 0;

        while(!hit) {
            if(sideX < sideY) {
                sideX += deltaX;
                mx += stepX;
                side = 0;
            } else {
                sideY += deltaY;
                my += stepY;
                side = 1;
            }
            if(mapa[my][mx] > 0) hit = true;
        }

        return side == 0 ? (mx - x + (1 - stepX) / 2.0) / dx : (my - y + (1 - stepY) / 2.0) / dy;
    }
}