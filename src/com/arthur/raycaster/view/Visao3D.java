package com.arthur.raycaster.view;

import java.awt.*;
import javax.swing.*;

public class Visao3D extends JPanel {

    public Visao3D() {
        setPreferredSize(new Dimension(720, 360));
        setBackground(Color.BLACK);

        loopPrincipal();
    }

    private void loopPrincipal() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
    }
}