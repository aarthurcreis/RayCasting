package com.arthur.raycaster.view;

import java.awt.*;
import javax.swing.*;

public class Visao3D extends JPanel {
    private final Contexto contexto;
    private static final double PROJECAO = 200d;

    public Visao3D(Contexto contexto) {
        this.contexto = contexto;

        setPreferredSize(new Dimension(720, 360));
        setBackground(Color.BLACK);

        new Timer(16, e -> repaint()).start();
    }

    private void desenharParedes(Graphics2D g2d) {
        double[] dist = contexto.raios.getDistanciasCorrigidas();
        int n = dist.length;

        int larguraTela = getWidth();
        int alturaTela = getHeight();
        int larguraColuna = larguraTela / n;

        for(int i = 0; i < n; i++) {
            if(dist[i] <= 0) continue;

            int altura = (int) (PROJECAO / dist[i]) * 2;
            if(altura > alturaTela) altura = alturaTela;

            int x = i * larguraColuna;
            int y = (alturaTela - altura) / 2;

            int shade = (int) Math.max(0, 255 - dist[i] * 25);
            g2d.setColor(new Color(shade, shade, shade));

            g2d.fillRect(x, y, larguraColuna + 1, altura);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenharParedes((Graphics2D) g);
    }
}