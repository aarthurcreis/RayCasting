package com.arthur.raycaster.view;

import com.arthur.raycaster.input.Teclas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.Timer;
import javax.swing.JPanel;

public class Visao2D extends JPanel {
    private final Common common;

    private static final int ALTURA_TELA = 360;
    private static final int LARGURA_TELA = 360;
    private static final int DELAY_ENTRE_CADA_QUADR0 = 16; // milissegundos

    private int[] raiosX, raiosY;

    public Visao2D(Common common) {
        this.common = common;

        setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
        setBackground(Color.DARK_GRAY);

        Teclas teclas = new Teclas(common.bolinha, common.raios);
        teclas.configurarTeclas(this);
        inicializarBuffers();
        loop();
    }

    private void inicializarBuffers() {
        int n = common.raios.getNumRaios();
        raiosX = new int[n];
        raiosY = new int[n];
    }

    private void loop() {
        new Timer(DELAY_ENTRE_CADA_QUADR0, e -> {
            common.bolinha.colisao();
            common.raios.calculoRaios();
            common.bolinha.movimentacao();
            atualizarRaios2D();

            repaint();
        }).start();
    }

    private void atualizarRaios2D() {
        double[] dist = common.raios.getDistancias();
        int n = common.raios.getNumRaios();

        double angIni = common.raios.getAnguloOlhar() - common.raios.getFOV() / 2;
        double passo = common.raios.getFOV() / n;

        int x0 = common.raios.getInicioLinhaX();
        int y0 = common.raios.getInicioLinhaY();
        int tile = common.mapa.getTile();

        for(int i = 0; i < n; i++) {
            double ang = Math.toRadians(angIni + i * passo);
            raiosX[i] = (int) (x0 + Math.cos(ang) * dist[i] * tile);
            raiosY[i] = (int) (y0 + Math.sin(ang) * dist[i] * tile);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // desenha paredes
        g.setColor(new Color(88, 88, 88));
        common.mapa.getParedes().forEach(retangulo -> {
            g.fillRect(retangulo.x, retangulo.y, retangulo.width, retangulo.height);
            g.drawRect(retangulo.x, retangulo.y, retangulo.width, retangulo.height);
        });

        // desenha raios
        g.setColor(new Color(128, 0, 128));
        for(int i = 0; i < raiosX.length; i++) {
            g.drawLine(common.raios.getInicioLinhaX(), common.raios.getInicioLinhaY(), raiosX[i], raiosY[i]);
        }

        // desenha bolinha
        g.setColor(new Color(146, 77, 191));
        g.fillOval(
            common.bolinha.getX(),
            common.bolinha.getY(),
            common.bolinha.getDiametro(),
            common.bolinha.getDiametro()
        );

        g.setColor(new Color(158, 114, 195));
        g.drawOval(
            common.bolinha.getX(),
            common.bolinha.getY(),
            common.bolinha.getDiametro(),
            common.bolinha.getDiametro()
        );
    }
}