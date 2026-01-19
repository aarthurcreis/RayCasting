package com.arthur.raycaster.view;

import java.awt.*;
import javax.swing.*;

import com.arthur.raycaster.input.Teclas;

public class Visao2D extends JPanel {
    private final Contexto contexto;
    private int[] raiosX;
    private int[] raiosY;

    public Visao2D(Contexto contexto) {
        this.contexto = contexto;

        setPreferredSize(new Dimension(360, 360));
        setBackground(Color.DARK_GRAY);

        Teclas teclas = new Teclas(contexto.bolinha, contexto.raios);
        teclas.configurarTeclas(this);
        inicializarBuffers();
        loop();
    }

    private void inicializarBuffers() {
        int n = contexto.raios.getNumRaios();
        raiosX = new int[n];
        raiosY = new int[n];
    }

    private void loop() {
        new Timer(16, e -> {
            contexto.bolinha.movimentacao();
            contexto.bolinha.colisao();
            contexto.raios.calculoRaios();
            atualizarRaios2D();

            repaint();
        }).start();
    }

    private void atualizarRaios2D() {
        double[] dist = contexto.raios.getDistancias();
        int n = contexto.raios.getNumRaios();

        double angIni = contexto.raios.getAnguloOlhar() - contexto.raios.getFOV() / 2.0;
        double passo = contexto.raios.getFOV() / n;

        int x0 = contexto.raios.getInicioLinhaX();
        int y0 = contexto.raios.getInicioLinhaY();
        int tile = contexto.mapa.getTile();

        for(int i = 0; i < n; i++) {
            double ang = Math.toRadians(angIni + i * passo);
            raiosX[i] = (int) (x0 + Math.cos(ang) * dist[i] * tile);
            raiosY[i] = (int) (y0 + Math.sin(ang) * dist[i] * tile);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // paredes
        g2d.setColor(new Color(88, 88, 88));
        contexto.mapa.getParedes().forEach(r -> {
            g2d.fillRect(r.x, r.y, r.width, r.height);
            g2d.drawRect(r.x, r.y, r.width, r.height);
        });

        // desenha raios
        g2d.setColor(new Color(128, 0, 128));
        for(int i = 0; i < raiosX.length; i++) {
            g2d.drawLine(contexto.raios.getInicioLinhaX(), contexto.raios.getInicioLinhaY(), raiosX[i], raiosY[i]);
        }

        // desenha bolinha
        g2d.setColor(new Color(146, 77, 191));
        g2d.fillOval(
            contexto.bolinha.getX(),
            contexto.bolinha.getY(),
            contexto.bolinha.getDiametro(),
            contexto.bolinha.getDiametro()
        );
        g2d.setColor(new Color(158, 114, 195));
        g2d.drawOval(
            contexto.bolinha.getX(),
            contexto.bolinha.getY(),
            contexto.bolinha.getDiametro(),
            contexto.bolinha.getDiametro()
        );
    }
}