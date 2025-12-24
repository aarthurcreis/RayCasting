package com.arthur.raycaster.core;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import static java.lang.Math.*;

public class Bolinha extends JPanel{
    public int x = 100, y = 100;
    public final int DIAMETRO = 40;
    private final int TILE = 30;
    double anguloOlhar = 0;

    public final ArrayList<Rectangle> paredes = new ArrayList<>(); // armazena retângulos que representam paredes

    private boolean esquerda, direita, cima, baixo, olhaEsq, olhaDir;

    public int inicioLinhaX, inicioLinhaY, finalLinhaX, finalLinhaY;

    int[][] mapa = {// <editor-fold desc="array do mapa">
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
    };// </editor-fold>

    public Bolinha() {
        setPreferredSize(new Dimension(360, 360));
        setBackground(Color.DARK_GRAY);

        inicializarParedes();
        loopPrincipal();
        configurarTeclas();
    }

    private void loopPrincipal() {
        new Timer(16, e -> {
            movimentacao();
            calculoRaios();
            repaint();
        }).start();
    }

    private void configurarTeclas() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "esq_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true),  "esq_R");
        am.put("esq_P", new AbstractAction() { public void actionPerformed(ActionEvent e) { esquerda = true;  }});
        am.put("esq_R", new AbstractAction() { public void actionPerformed(ActionEvent e) { esquerda = false; }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "dir_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true),  "dir_R");
        am.put("dir_P", new AbstractAction() { public void actionPerformed(ActionEvent e) { direita = true;  }});
        am.put("dir_R", new AbstractAction() { public void actionPerformed(ActionEvent e) { direita = false; }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false),       "cima_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true),        "cima_R");
        am.put("cima_P", new AbstractAction() { public void actionPerformed(ActionEvent e) { cima = true;  }});
        am.put("cima_R", new AbstractAction() { public void actionPerformed(ActionEvent e) { cima = false; }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false),   "baixo_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true),    "baixo_R");
        am.put("baixo_P", new AbstractAction() { public void actionPerformed(ActionEvent e) { baixo = true;  }});
        am.put("baixo_R", new AbstractAction() { public void actionPerformed(ActionEvent e) { baixo = false; }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false),          "A_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true),           "A_R");
        am.put("A_P", new AbstractAction() { public void actionPerformed(ActionEvent e) { olhaEsq = true;  }});
        am.put("A_R", new AbstractAction() { public void actionPerformed(ActionEvent e) { olhaEsq = false; }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false),          "D_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true),           "D_R");
        am.put("D_P", new AbstractAction() {public void actionPerformed(ActionEvent e) { olhaDir = true;  }});
        am.put("D_R", new AbstractAction() {public void actionPerformed(ActionEvent e) { olhaDir = false; }});
    }

    private void movimentacao() {
        // cálculo da nova posição
        int deltaX = 0, deltaY = 0;
        int VEL = 4;
        if(esquerda) deltaX -= VEL;
        if(direita)  deltaX += VEL;
        if(cima)     deltaY -= VEL;
        if(baixo)    deltaY += VEL;

        // correção da velocidade diagonal
        if((direita || esquerda) && (cima || baixo)) {
            deltaX *= (double) 1 / sqrt(2);
            deltaY *= (double) 1 / sqrt(2);
        }

        // colisão horizontal
        if(deltaX != 0) {
            boolean colisao = false;
            Rectangle hitboxX = new Rectangle(x + deltaX, y, DIAMETRO, DIAMETRO);
            for(Rectangle parede : paredes) {
                if(hitboxX.intersects(parede)) {
                    colisao = true;
                    break;
                }
            }
            if(!colisao) x += deltaX;
        }

        // colisão vertical
        if(deltaY != 0) {
            boolean colisao = false;
            Rectangle hitboxY = new Rectangle(x, y + deltaY, DIAMETRO, DIAMETRO);
            for(Rectangle parede : paredes) {
                if(hitboxY.intersects(parede)) {
                    colisao = true;
                    break;
                }
            }
            if(!colisao) y += deltaY;
        }
    }

    private void calculoRaios() {
        // angulação da visão
        if(olhaEsq) anguloOlhar = floorMod((int) (anguloOlhar - 2), 360);
        if(olhaDir) anguloOlhar = floorMod((int) (anguloOlhar + 2), 360);

        // calcula raios
        double anguloRad = toRadians(anguloOlhar);

        inicioLinhaX = x + DIAMETRO / 2;
        inicioLinhaY = y + DIAMETRO / 2;

        double posMapaX = (double) inicioLinhaX / TILE;
        double posMapaY = (double) inicioLinhaY / TILE;

        double dirX = cos(anguloRad);
        double dirY = sin(anguloRad);

        double distancia = DDA(posMapaX, posMapaY, dirX, dirY, mapa);
        double distanciaPixels = distancia * TILE;

        finalLinhaX = (int) (inicioLinhaX + dirX * distanciaPixels);
        finalLinhaY = (int) (inicioLinhaY + dirY * distanciaPixels);
    }

    public double DDA(double x, double y, double direcaoRaioX, double direcaoRaioY, int[][] mapa) {
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

    private void inicializarParedes() {
        for(int linha = 0; linha < mapa.length; linha++) {
            for(int coluna = 0; coluna < mapa[0].length; coluna++) {
                if(mapa[linha][coluna] == 1) {
                    paredes.add(new Rectangle(coluna * TILE, linha * TILE, TILE, TILE));
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // casting de Graphics para Graphics2D
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // desenha paredes
        for(Rectangle parede : paredes) {
            g2d.setColor(new Color(88, 88, 88));
            g2d.fillRect(parede.x, parede.y, parede.width, parede.height);
            g2d.drawRect(parede.x, parede.y, parede.width, parede.height);
        }

        // desenha bola
        g2d.setColor(new Color(146, 77, 191));
        g2d.fillOval(x, y, DIAMETRO, DIAMETRO);
        g2d.setColor(new Color(158, 114, 195));
        g2d.drawOval(x, y, DIAMETRO, DIAMETRO);

        // desenha raios
        g2d.drawLine(inicioLinhaX, inicioLinhaY, finalLinhaX, finalLinhaY);
    }
}
