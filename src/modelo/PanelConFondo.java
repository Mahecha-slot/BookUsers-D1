package modelo;

import javax.swing.*;
import java.awt.*;

public class PanelConFondo extends JPanel {

    private Image imagen;

    public PanelConFondo(String rutaImagen) {

        java.net.URL recurso = getClass().getResource(rutaImagen);

        if (recurso != null) {
            imagen = new ImageIcon(recurso).getImage();
        } else {
            System.out.println("No se encontró la imagen: " + rutaImagen);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (imagen != null) {
            g.drawImage(
                    imagen,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );
        }
    }
}