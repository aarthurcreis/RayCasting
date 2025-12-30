package com.arthur.raycaster.input;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import com.arthur.raycaster.math.Raios;
import com.arthur.raycaster.core.Bolinha;

public class Teclas {
    private final Raios raios;
    private final Bolinha bolinha;

    public Teclas(Bolinha bolinha, Raios raios) {
        this.bolinha = bolinha;
        this.raios = raios;
    }

    public void configurarTeclas(JComponent JComponent) {
        InputMap im = JComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = JComponent.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false),   "esq_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true),    "esq_R");
        am.put("esq_P", new AbstractAction() { public void actionPerformed(ActionEvent e) { bolinha.setEsquerda(true);  }});
        am.put("esq_R", new AbstractAction() { public void actionPerformed(ActionEvent e) { bolinha.setEsquerda(false); }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false),  "dir_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true),   "dir_R");
        am.put("dir_P", new AbstractAction() { public void actionPerformed(ActionEvent e) { bolinha.setDireita(true);  }});
        am.put("dir_R", new AbstractAction() { public void actionPerformed(ActionEvent e) { bolinha.setDireita(false); }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false),    "cima_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true),     "cima_R");
        am.put("cima_P", new AbstractAction() { public void actionPerformed(ActionEvent e) { bolinha.setCima(true);  }});
        am.put("cima_R", new AbstractAction() { public void actionPerformed(ActionEvent e) { bolinha.setCima(false); }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "baixo_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true),  "baixo_R");
        am.put("baixo_P", new AbstractAction() { public void actionPerformed(ActionEvent e) { bolinha.setBaixo(true);  }});
        am.put("baixo_R", new AbstractAction() { public void actionPerformed(ActionEvent e) { bolinha.setBaixo(false); }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false),        "A_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true),         "A_R");
        am.put("A_P", new AbstractAction() { public void actionPerformed(ActionEvent e) { raios.setOlhaEsq(true);  }});
        am.put("A_R", new AbstractAction() { public void actionPerformed(ActionEvent e) { raios.setOlhaEsq(false); }});

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false),        "D_P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true),         "D_R");
        am.put("D_P", new AbstractAction() {public void actionPerformed(ActionEvent e) { raios.setOlhaDir(true);  }});
        am.put("D_R", new AbstractAction() {public void actionPerformed(ActionEvent e) { raios.setOlhaDir(false); }});
    }
}
