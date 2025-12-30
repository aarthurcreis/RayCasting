package com.arthur.raycaster.math;

import static java.lang.Math.*;

import com.arthur.raycaster.map.Mapa;
import com.arthur.raycaster.core.Bolinha;

public class Raios {
    private final Mapa mapa;
    private final Bolinha bolinha;

    private double anguloOlhar = 0;
    private int inicioLinhaX, inicioLinhaY, finalLinhaX, finalLinhaY;
    private boolean olhaEsq, olhaDir;

    public Raios(Bolinha bolinha, Mapa mapa) {
        this.bolinha = bolinha;
        this.mapa = mapa;
    }

    public int getInicioLinhaX() { return inicioLinhaX; }
    public int getInicioLinhaY() { return inicioLinhaY; }
    public int getFinalLinhaX() { return finalLinhaX; }
    public int getFinalLinhaY() { return finalLinhaY; }

    public void setOlhaEsq(boolean valor) { this.olhaEsq = valor; }
    public void setOlhaDir(boolean valor) { this.olhaDir = valor; }

    public void calculoRaios() {
        // angulação da visão
        if(olhaEsq) anguloOlhar = floorMod((int) (anguloOlhar - 2), 360);
        if(olhaDir) anguloOlhar = floorMod((int) (anguloOlhar + 2), 360);

        // calcula raios
        double anguloRad = toRadians(anguloOlhar);

        inicioLinhaX = bolinha.getX() + bolinha.getDiametro() / 2;
        inicioLinhaY = bolinha.getY() + bolinha.getDiametro() / 2;

        double posMapaX = (double) inicioLinhaX / mapa.getTILE();
        double posMapaY = (double) inicioLinhaY / mapa.getTILE();

        double dirX = cos(anguloRad);
        double dirY = sin(anguloRad);

        double distancia = DDA(posMapaX, posMapaY, dirX, dirY, mapa.getMapa());
        double distanciaPixels = distancia * mapa.getTILE();

        finalLinhaX = (int) (inicioLinhaX + dirX * distanciaPixels);
        finalLinhaY = (int) (inicioLinhaY + dirY * distanciaPixels);
    }

    private double DDA(double x, double y, double direcaoRaioX, double direcaoRaioY, int[][] mapa) {
        int mapaX = (int) x;
        int mapaY = (int) y;

        double deltaDistanciaX = abs(1 / direcaoRaioX);
        double deltaDistanciaY = abs(1 / direcaoRaioY);

        int passoX;
        int passoY;

        double distanciaLadoX;
        double distanciaLadoY;

        if(direcaoRaioX < 0) {
            passoX = -1;
            distanciaLadoX = (x - mapaX) * deltaDistanciaX;
        } else {
            passoX = 1;
            distanciaLadoX = (mapaX + 1.0 - x) * deltaDistanciaX;
        }

        if(direcaoRaioY < 0) {
            passoY = -1;
            distanciaLadoY = (y - mapaY) * deltaDistanciaY;
        } else {
            passoY = 1;
            distanciaLadoY = (mapaY + 1.0 - y) * deltaDistanciaY;
        }

        boolean colisao = false;
        int ladoColidido = 0;

        while(!colisao) {
            if(distanciaLadoX < distanciaLadoY) {
                distanciaLadoX += deltaDistanciaX;
                mapaX += passoX;
                ladoColidido = 0;
            } else {
                distanciaLadoY += deltaDistanciaY;
                mapaY += passoY;
                ladoColidido = 1;
            }
            if(mapa[mapaY][mapaX] > 0) {
                colisao = true;
            }
        }

        double distanciaParede;

        if(ladoColidido == 0) {
            distanciaParede = (mapaX - x + (1 - passoX) / 2.0) / direcaoRaioX;
        } else {
            distanciaParede = (mapaY - y + (1 - passoY) / 2.0) / direcaoRaioY;
        }
        return distanciaParede;
    }
}
