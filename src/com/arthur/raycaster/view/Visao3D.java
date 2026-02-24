package com.arthur.raycaster.view;

import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

public class Visao3D extends JPanel {
    private final Common common;

    private static final double PROJECAO = 200d;
    private static final int ALTURA_TELA = 540;
    private static final int LARGURA_TELA = 1080;
    private static final int DELAY_ENTRE_CADA_QUADR0 = 16; // milissegundos

    public Visao3D(Common common) {
        this.common = common;

        setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
        setBackground(Color.BLACK);

        new Timer(DELAY_ENTRE_CADA_QUADR0, e -> repaint()).start();
    }

    private void desenharParedes(Graphics g) {
        double[] distancias = common.raios.getDistanciasCorrigidas();
        int larguraColuna = getWidth() / distancias.length;

        for (int i = 0; i < distancias.length; i++) {
            double distancia = distancias[i];
            if (distancia <= 0) continue;

            int alturaParede = Math.min((int) ((PROJECAO / distancia) * 2), getHeight());
            int posicaoX = i * larguraColuna;
            int posicaoY = (getHeight() - alturaParede) / 2;

            int intensidadeCor = (int) Math.max(0, 255 - distancia * 25);
            g.setColor(new Color(intensidadeCor, intensidadeCor, intensidadeCor));
            g.fillRect(posicaoX, posicaoY, larguraColuna + 1, alturaParede);
        }
    }

    @Override
    protected void paintComponent(Graphics g) { super.paintComponent(g); desenharParedes(g); }
}