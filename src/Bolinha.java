import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import static java.lang.Math.*;

public class Bolinha extends JPanel {
    private int x = 100, y = 100; // posição da bola
    private final int VEL = 4, DIAMETRO = 40; // pixels por frame e tamanho da bola
    private boolean movendoEsquerda, movendoDireita, movendoCima, movendoBaixo; // controles

    // lista pra armazenar retângulos que representam paredes
    private final ArrayList<Rectangle> paredes = new ArrayList<>();

    // <editor-fold desc="array do mapa">
    int[][] mapa = {
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
    };
    // </editor-fold>

    public Bolinha() {
        setPreferredSize(new Dimension(360, 360));
        setBackground(Color.DARK_GRAY);

        inicializarParedes();
        loopPrincipal();
        configurarTeclas();
    }

    private void loopPrincipal() {
        new Timer(16, e -> {
            // cálculo da nova posição
            int deltaX = 0;
            int deltaY = 0;

            if(movendoEsquerda) deltaX -= VEL;
            if(movendoDireita)  deltaX += VEL;
            if(movendoCima)     deltaY -= VEL;
            if(movendoBaixo)    deltaY += VEL;

            // correção da velocidade diagonal
            if((movendoDireita || movendoEsquerda) && (movendoCima || movendoBaixo)) {
                deltaX *= 1 / sqrt(2);
                deltaY *= 1 / sqrt(2);
            }

            // colisão horizontal
            if(deltaX != 0) {
                Rectangle hitboxX = new Rectangle(x + deltaX, y, DIAMETRO, DIAMETRO);
                boolean colisao = false;
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
                Rectangle hitboxY = new Rectangle(x, y + deltaY, DIAMETRO, DIAMETRO);
                boolean colisao = false;
                for(Rectangle parede : paredes) {
                    if(hitboxY.intersects(parede)) {
                        colisao = true;
                        break;
                    }
                }
                if(!colisao) y += deltaY;
            }

            repaint();
        }).start();
    }

    private void configurarTeclas() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "L_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true),  "L_R");
        am.put("L_P", new AbstractAction(){ public void actionPerformed(ActionEvent e){ movendoEsquerda = true; }});
        am.put("L_R", new AbstractAction(){ public void actionPerformed(ActionEvent e){ movendoEsquerda = false; }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "R_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true),  "R_R");
        am.put("R_P", new AbstractAction(){ public void actionPerformed(ActionEvent e){ movendoDireita = true; }});
        am.put("R_R", new AbstractAction(){ public void actionPerformed(ActionEvent e){ movendoDireita = false; }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "U_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true),  "U_R");
        am.put("U_P", new AbstractAction(){ public void actionPerformed(ActionEvent e){ movendoCima = true; }});
        am.put("U_R", new AbstractAction(){ public void actionPerformed(ActionEvent e){ movendoCima = false; }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "D_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true),  "D_R");
        am.put("D_P", new AbstractAction(){ public void actionPerformed(ActionEvent e){ movendoBaixo = true; }});
        am.put("D_R", new AbstractAction(){ public void actionPerformed(ActionEvent e){ movendoBaixo = false; }});
    }

    private void inicializarParedes() {
        for(int linha = 0; linha < mapa.length; linha++) {
            for(int coluna = 0; coluna < mapa[0].length; coluna++) {
                if(mapa[linha][coluna] == 1) {
                    final int TILE = 30;
                    paredes.add(new Rectangle(coluna * TILE, linha * TILE, TILE, TILE));
                }
            }
        }
    }

    private void DDA(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        int steps = max(deltaX, deltaY);
        int somaX = deltaX / steps;
        int somaY = deltaY / steps;

        int x = x1;
        int y = y1;

        for(int i = 0; i <= steps; i++) {
            x += (int) somaX;
            y += (int) somaY;
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

        g2d.setColor(new Color(146, 77, 191));
        g2d.fillOval(x, y, DIAMETRO, DIAMETRO); // desenha bola
        g2d.setColor(new Color(158, 114, 195));
        g2d.drawOval(x, y, DIAMETRO, DIAMETRO); // desenha borda da bola

        g2d.setColor(new Color(240, 240, 240));
        g2d.drawString("X: " + x, 10, 20);
        g2d.drawString("Y: " + y, 55, 20);
    }
}