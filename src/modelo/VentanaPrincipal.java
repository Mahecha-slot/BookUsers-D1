package modelo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {

    private JPanel PanelPrincipal;
    private JButton creditosDeCreadorButton;
    private JButton verUsuariosButton;
    private JButton crearUsuariosButton;

    // Gestor compartido para todos los usuarios registrados
    private GestorUsuarios gestorPrincipal = new GestorUsuarios();

    public VentanaPrincipal() {

        // Configuración de la ventana con el fondo
        setTitle("Sistema de Gestión de Usuarios - UNIPAZ");

        PanelConFondo fondo =
                new PanelConFondo("/imagenes/Imagen1.jpg");

        fondo.setLayout(new java.awt.BorderLayout());

        PanelPrincipal.setOpaque(false);

        fondo.add(PanelPrincipal, java.awt.BorderLayout.CENTER);

        setContentPane(fondo);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        // --- ACCIÓN BOTÓN: "Ver usuarios" ---
        verUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                VentanaVerUsuarios ventanaVer =
                        new VentanaVerUsuarios(gestorPrincipal);

                ventanaVer.setVisible(true);
            }
        });

        // --- ACCIÓN BOTÓN: "Crear usuarios" ---
        crearUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Utilizamos el gestor compartido
                VentanaCrearUsuario ventanaCrear =
                        new VentanaCrearUsuario(gestorPrincipal);

                ventanaCrear.setVisible(true);
            }
        });

        // --- ACCIÓN BOTÓN: "Créditos de creador" ---
        creditosDeCreadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(
                        VentanaPrincipal.this,
                        "Desarrollado por: Manuel Jose Londoño Vergara\n" +
                                "Mateo Mahecha Gutierrez\n" +
                                "Ingeniería Informática - UNIPAZ",
                        "Créditos de Creador",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    // Método main para ejecutar y probar la ventana
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}