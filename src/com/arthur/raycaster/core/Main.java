package com.arthur.raycaster.core;

import javax.swing.*;

import com.arthur.raycaster.view.Visao2D;
import com.arthur.raycaster.view.Visao3D;

public class Main {
    public static void main(String[] args) {
        Visao3D visao3D = new Visao3D();
        JFrame visao3DJanela = new JFrame("Visão 3D");
        janela(visao3DJanela, visao3D, 75, 75, false);

        Visao2D visao2D = new Visao2D();
        JFrame visao2DJanela = new JFrame("Visão 2D");
        janela(visao2DJanela, visao2D, 850, 250, false);
    }

    private static void janela(JFrame janela, JPanel painel, int x, int y, boolean redimensionavel) {
        janela.add(painel);
        janela.setLocation(x, y);
        janela.setResizable(redimensionavel);
        janela.pack(); // obtém altura e largura do painel
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}