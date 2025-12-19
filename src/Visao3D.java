import javax.swing.*;
import java.awt.*;

public class Visao3D extends JPanel {

    public Visao3D() {
        setPreferredSize(new Dimension(720, 360));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}