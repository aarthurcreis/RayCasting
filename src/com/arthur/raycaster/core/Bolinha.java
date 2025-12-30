package com.arthur.raycaster.core;

import java.awt.Rectangle;
import static java.lang.Math.sqrt;

import com.arthur.raycaster.map.Mapa;

public class Bolinha {
    private int x = 100, y = 100;
    private int deltaX, deltaY;
    private boolean esquerda, direita, cima, baixo;

    private final int DIAMETRO = 40;

    private final Mapa mapa = new Mapa();

    public Bolinha() {
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getDiametro() { return DIAMETRO; }

    public void setEsquerda(boolean valor) { this.esquerda = valor; }
    public void setDireita(boolean valor) { this.direita = valor; }
    public void setCima(boolean valor) { this.cima = valor; }
    public void setBaixo(boolean valor) { this.baixo = valor; }

    public void movimentacao() {
        // cálculo da nova posição
        deltaX = 0;
        deltaY = 0;
        final int VEL = 4;

        if(esquerda) deltaX -= VEL;
        if(direita) deltaX += VEL;
        if(cima) deltaY -= VEL;
        if(baixo) deltaY += VEL;

        // correção da velocidade diagonal
        if((direita || esquerda) && (cima || baixo)) {
            deltaX *= 1 / sqrt(2);
            deltaY *= 1 / sqrt(2);
        }
    }

    public void colisao() {
        // colisão horizontal
        if (deltaX != 0) {
            boolean colisao = false;
            Rectangle hitboxX = new Rectangle(x + deltaX, y, DIAMETRO, DIAMETRO);
            for (Rectangle parede : mapa.getParedes()) {
                if (hitboxX.intersects(parede)) {
                    colisao = true;
                    break;
                }
            }
            if (!colisao) x += deltaX;
        }

        // colisão vertical
        if (deltaY != 0) {
            boolean colisao = false;
            Rectangle hitboxY = new Rectangle(x, y + deltaY, DIAMETRO, DIAMETRO);
            for (Rectangle parede : mapa.getParedes()) {
                if (hitboxY.intersects(parede)) {
                    colisao = true;
                    break;
                }
            }
            if (!colisao) y += deltaY;
        }
    }
}
