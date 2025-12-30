package com.arthur.raycaster.view;

import java.awt.*;
import javax.swing.*;

import com.arthur.raycaster.map.Mapa;
import com.arthur.raycaster.math.Raios;
import com.arthur.raycaster.core.Bolinha;
import com.arthur.raycaster.input.Teclas;

public class Visao2D extends JPanel{
    private final Mapa mapa;
    private final Raios raios;
    private final Bolinha bolinha;

    public Visao2D() {
        setPreferredSize(new Dimension(360, 360));
        setBackground(Color.DARK_GRAY);

        mapa = new Mapa();
        mapa.inicializarParedes();
        bolinha = new Bolinha();
        raios = new Raios(bolinha, mapa);
        Teclas teclas = new Teclas(bolinha, raios);
        teclas.configurarTeclas(this);

        loopPrincipal();
    }

    private void loopPrincipal() {
        new Timer(16, e -> {

            bolinha.movimentacao();
            bolinha.colisao();
            raios.calculoRaios();

            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // casting de Graphics para Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // desenha paredes
        for(Rectangle parede : mapa.getParedes()) {
            g2d.setColor(new Color(88, 88, 88));
            g2d.fillRect(parede.x, parede.y, parede.width, parede.height);
            g2d.drawRect(parede.x, parede.y, parede.width, parede.height);
        }

        // desenha bola
        g2d.setColor(new Color(146, 77, 191));
        g2d.fillOval(bolinha.getX(), bolinha.getY(), bolinha.getDiametro(), bolinha.getDiametro());
        g2d.setColor(new Color(158, 114, 195));
        g2d.drawOval(bolinha.getX(), bolinha.getY(), bolinha.getDiametro(), bolinha.getDiametro());

        // desenha raios
        g2d.drawLine(raios.getInicioLinhaX(), raios.getInicioLinhaY(), raios.getFinalLinhaX(), raios.getFinalLinhaY());
    }
}
