import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame bolinhaJanela = new JFrame("Bolinha");
        JFrame visao3DJanela = new JFrame("Visão 3D");

        // janela do 3D
        Visao3D visao3D = new Visao3D();
        visao3DJanela.add(visao3D);
        visao3DJanela.pack(); // desconsidera o topo da janela na altura
        visao3DJanela.setLocation(75, 75);
        visao3DJanela.setResizable(false);
        visao3DJanela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visao3DJanela.setVisible(true);

        // janela do 2D
        Bolinha bolinha = new Bolinha();
        bolinhaJanela.add(bolinha);
        bolinhaJanela.pack();
        bolinhaJanela.setLocation(850, 250);
        bolinhaJanela.setResizable(false);
        bolinhaJanela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bolinhaJanela.setVisible(true);
    }
}