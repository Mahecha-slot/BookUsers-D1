package modelo;

import javax.swing.*;
import java.awt.*;
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

        // Configuración de la ventana principal
        setTitle("Sistema de Gestión de Usuarios - UNIPAZ");

        PanelConFondo fondo =
                new PanelConFondo("/imagenes/Imagen1.jpg");

        fondo.setLayout(new BorderLayout());

        PanelPrincipal.setOpaque(false);

        fondo.add(PanelPrincipal, BorderLayout.CENTER);

        setContentPane(fondo);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        // BOTÓN VER USUARIOS
        verUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                VentanaVerUsuarios ventanaVer =
                        new VentanaVerUsuarios(gestorPrincipal);

                ventanaVer.setVisible(true);
            }
        });

        // BOTÓN CREAR USUARIOS
        crearUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                VentanaCrearUsuario ventanaCrear =
                        new VentanaCrearUsuario(gestorPrincipal);

                ventanaCrear.setVisible(true);
            }
        });

        // BOTÓN CRÉDITOS DEL CREADOR
        creditosDeCreadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mostrarCreditos();
            }
        });
    }

    // ==========================================
    // VENTANA DE CRÉDITOS
    // ==========================================

    private void mostrarCreditos() {

        JDialog dialogo = new JDialog(
                this,
                "BookUsers - Créditos",
                true
        );

        dialogo.setSize(520, 590);
        dialogo.setLocationRelativeTo(this);
        dialogo.setResizable(false);
        dialogo.setDefaultCloseOperation(
                JDialog.DISPOSE_ON_CLOSE
        );

        // Panel principal
        JPanel principal = new JPanel();
        principal.setLayout(new BoxLayout(
                principal,
                BoxLayout.Y_AXIS
        ));

        principal.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 25, 25, 25
                )
        );

        // TÍTULO
        JLabel titulo = new JLabel("BookUsers v1.0.1.1");

        titulo.setFont(new Font(
                "Arial",
                Font.BOLD,
                20
        ));

        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // INTEGRANTES
        JLabel integrantes = new JLabel("Integrantes:");

        integrantes.setFont(new Font(
                "Arial",
                Font.BOLD,
                17
        ));

        integrantes.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ESPACIO RESERVADO PARA LA IMAGEN
        // Posteriormente agregaremos aquí la imagen decorativa.

        // SEPARADOR
        JLabel separador1 = new JLabel("-");

        separador1.setAlignmentX(Component.CENTER_ALIGNMENT);

        // DATOS DE MATEO
        JLabel nombreMateo = new JLabel(
                "Nombre: Mateo Mahecha Gutiérrez"
        );

        JLabel documentoMateo = new JLabel(
                "Documento: 1097187362"
        );

        JLabel codigoMateo = new JLabel(
                "Código Estudiantil: UNP-3SUP-4RYA"
        );

        JLabel correoMateo = new JLabel(
                "Correo: Mateomahechagutierrez@gmail.com"
        );

        // SEPARADOR
        JLabel separador2 = new JLabel("-");

        separador2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // DATOS DE MANUEL
        JLabel nombreManuel = new JLabel(
                "Nombre: Manuel José Londoño Vergara"
        );

        JLabel documentoManuel = new JLabel(
                "Documento: 1101203526"
        );

        JLabel codigoManuel = new JLabel(
                "Código Estudiantil: UNP-ZKYP-9NCW"
        );

        JLabel correoManuel = new JLabel(
                "Correo: manueljoselondonovergara@gamil.com"
        );

        // MATERIA Y AÑO
        JLabel materia = new JLabel(
                "Materia: POO - Año: 2026"
        );

        // SEPARADOR FINAL
        JLabel separador3 = new JLabel("-");

        separador3.setAlignmentX(Component.CENTER_ALIGNMENT);

        // BOTÓN CERRAR
        JButton cerrar = new JButton("Cerrar");

        cerrar.addActionListener(e -> dialogo.dispose());

        cerrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ======================================
        // CONFIGURACIÓN DE LOS TEXTOS
        // ======================================

        Font fuenteDatos = new Font(
                "Arial",
                Font.BOLD,
                14
        );

        JLabel[] textos = {
                nombreMateo,
                documentoMateo,
                codigoMateo,
                correoMateo,
                nombreManuel,
                documentoManuel,
                codigoManuel,
                correoManuel
        };

        for (JLabel texto : textos) {

            texto.setFont(fuenteDatos);
            texto.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        materia.setFont(fuenteDatos);
        materia.setAlignmentX(Component.CENTER_ALIGNMENT);

        separador1.setFont(fuenteDatos);
        separador2.setFont(fuenteDatos);
        separador3.setFont(fuenteDatos);

        // ======================================
        // ORGANIZACIÓN DE LOS COMPONENTES
        // ======================================

        principal.add(titulo);

        principal.add(Box.createVerticalStrut(20));

        principal.add(integrantes);

        principal.add(Box.createVerticalStrut(15));

        // Aquí irá posteriormente la imagen.

        principal.add(separador1);

        principal.add(Box.createVerticalStrut(12));

        principal.add(nombreMateo);
        principal.add(documentoMateo);
        principal.add(codigoMateo);
        principal.add(correoMateo);

        principal.add(Box.createVerticalStrut(20));

        principal.add(separador2);

        principal.add(Box.createVerticalStrut(12));

        principal.add(nombreManuel);
        principal.add(documentoManuel);
        principal.add(codigoManuel);
        principal.add(correoManuel);

        principal.add(Box.createVerticalStrut(22));

        principal.add(materia);

        principal.add(Box.createVerticalStrut(15));

        principal.add(separador3);

        principal.add(Box.createVerticalStrut(20));

        principal.add(cerrar);

        dialogo.setContentPane(principal);

        dialogo.setVisible(true);
    }

    // ==========================================
    // MÉTODO MAIN
    // ==========================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}