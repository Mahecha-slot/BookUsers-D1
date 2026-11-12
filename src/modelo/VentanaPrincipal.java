package modelo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
    private JPanel PanelPrincipal;
    private JButton creditosDeCreadorButton;
    private JButton verUsuariosButton;
    private JButton crearUsuariosButton;
    private JPanel VentanaPrincipal;

    public VentanaPrincipal() {
        // Configuración básica de la ventana
        setTitle("Sistema de Gestión de Usuarios - UNIPAZ");
        setContentPane(PanelPrincipal); // Usamos tu panel principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null); // Centra la ventana en pantalla

        // --- ACCIÓN BOTÓN: "Ver usuarios" ---
        verUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(VentanaPrincipal.this,
                        "Abriendo lista de usuarios registrados...");
                // Aquí conectaremos la ventana de Ver Usuarios más adelante
            }
        });

        // --- ACCIÓN BOTÓN: "Crear usuarios" ---
        crearUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instanciamos el gestor y abrimos la ventana de creación
                GestorUsuarios gestorPrincipal = new GestorUsuarios();
                VentanaCrearUsuario ventanaCrear = new VentanaCrearUsuario(gestorPrincipal);
                ventanaCrear.setVisible(true);
            }
        });

        // --- ACCIÓN BOTÓN: "Créditos de creador" ---
        creditosDeCreadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(VentanaPrincipal.this,
                        "Desarrollado por: Manuel Jose Londoño Vergara\nMateo Mahecha Gutierrez\nIngeniería Informática - UNIPAZ",
                        "Créditos de Creador",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Método main para ejecutar y probar tu ventana
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}