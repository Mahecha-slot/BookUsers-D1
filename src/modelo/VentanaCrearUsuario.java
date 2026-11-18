package modelo;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Image;

public class VentanaCrearUsuario extends JFrame {

    private JPanel panelCrearUsuario;
    private JTextField textField2; // Número de documento
    private JTextField textField3; // Primer nombre
    private JTextField textField4; // Segundo nombre
    private JTextField textField5; // Contraseña
    private JTextField textField6; // Teléfono
    private JTextField textField7; // Correo
    private JTextField textField8; // Primer apellido
    private JTextField textField9; // Segundo apellido

    private JComboBox comboBox1; // Tipo de documento

    private JButton cancelarButton;
    private JButton guardarButton;
    private JButton seleccionarFotografiaButton;
    private JLabel labelFoto;

    private GestorUsuarios gestor;

    // Almacena la ruta de la fotografía seleccionada
    private String rutaFoto = "";

    public VentanaCrearUsuario(GestorUsuarios gestor) {

        this.gestor = gestor;

        setTitle("Crear Nuevo Usuario - UNIPAZ");


        // Fondo VentanaCrearUsuario

        if (panelCrearUsuario == null) {
            panelCrearUsuario = new JPanel();
        }

// La imagen
        PanelConFondo fondo = new PanelConFondo("/imagenes/Imagen2.jpg");

// Espacion de la Foto
        fondo.setLayout(new java.awt.BorderLayout());

        panelCrearUsuario.setOpaque(false);

// Agregamos La informacion de el from
        fondo.add(panelCrearUsuario, java.awt.BorderLayout.CENTER);

// Establecer el fondo como contenido de la ventana
        setContentPane(fondo);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // CARGAR TIPOS DE DOCUMENTO
        comboBox1.removeAllItems();

        comboBox1.addItem("Seleccione tipo de documento");
        comboBox1.addItem("Cédula de ciudadanía (CC)");
        comboBox1.addItem("Tarjeta de identidad (TI)");
        comboBox1.addItem("Cédula de extranjería (CE)");
        comboBox1.addItem("Pasaporte (PA)");
        comboBox1.addItem("Permiso por Protección Temporal (PPT)");
        comboBox1.addItem("Registro civil (RC)");

        // BOTÓN GUARDAR
        guardarButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Tipo documento: " + comboBox1.getSelectedIndex());
                System.out.println("Documento: " + textField2.getText());
                System.out.println("Primer nombre: " + textField3.getText());
                System.out.println("Segundo nombre: " + textField4.getText());
                System.out.println("Contraseña: " + textField5.getText());
                System.out.println("Teléfono: " + textField6.getText());
                System.out.println("Correo: " + textField7.getText());
                System.out.println("Primer apellido: " + textField8.getText());
                System.out.println("Segundo apellido: " + textField9.getText());
                System.out.println("Fotografía: " + rutaFoto);

                // VALIDAR CAMPOS OBLIGATORIOS
                if (comboBox1.getSelectedIndex() <= 0

                        || textField2.getText().trim().isEmpty()
                        || textField3.getText().trim().isEmpty()
                        || textField5.getText().trim().isEmpty()
                        || textField6.getText().trim().isEmpty()
                        || textField7.getText().trim().isEmpty()
                        || textField8.getText().trim().isEmpty()
                        || rutaFoto.trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            VentanaCrearUsuario.this,
                            "Completa todos los datos, por favor",
                            "Campos incompletos",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                // CONFIRMAR REGISTRO
                int respuesta = JOptionPane.showConfirmDialog(
                        VentanaCrearUsuario.this,
                        "¿Estás seguro de que deseas registrar este usuario?",
                        "Confirmar registro",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (respuesta == JOptionPane.YES_OPTION) {

                    try {

                        // Obtener datos del formulario
                        Usuarios nuevoUsuario = getNuevoUsuario();

                        // Registrar usuario
                        gestor.agregarUsuario(nuevoUsuario);

                        JOptionPane.showMessageDialog(
                                VentanaCrearUsuario.this,
                                "¡Usuario registrado con éxito!\n"
                                        + "ID: " + nuevoUsuario.getId()
                                        + "\nUsername: " + nuevoUsuario.getUsername(),
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        // Cerrar ventana
                        dispose();

                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(
                                VentanaCrearUsuario.this,
                                "Error al guardar el usuario: "
                                        + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

        // BOTÓN CANCELAR
        cancelarButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });

        // BOTÓN SELECCIONAR FOTOGRAFÍA
        seleccionarFotografiaButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Crear explorador de archivos
                JFileChooser selector = new JFileChooser();

                selector.setDialogTitle(
                        "Seleccionar fotografía del usuario"
                );

                // Permitir únicamente imágenes
                FileNameExtensionFilter filtro =
                        new FileNameExtensionFilter(
                                "Imágenes (JPG, JPEG, PNG)",
                                "jpg", "jpeg", "png"
                        );

                selector.setFileFilter(filtro);

                // Abrir explorador
                int resultado = selector.showOpenDialog(
                        VentanaCrearUsuario.this
                );

                // Comprobar si seleccionó una imagen
                if (resultado == JFileChooser.APPROVE_OPTION) {

                    File archivo = selector.getSelectedFile();

                    // Guardar la ruta de la fotografía
                    rutaFoto = archivo.getAbsolutePath();

                    //  Cargar la fotografía seleccionada
                    ImageIcon imagenOriginal = new ImageIcon(rutaFoto);

                    //  Obtener el tamaño del JLabel
                    int ancho = labelFoto.getWidth();
                    int alto = labelFoto.getHeight();

                    // Comprobar que el JLabel tenga dimensiones
                    if (ancho <= 0 || alto <= 0) {
                        ancho = 150;
                        alto = 150;
                    }

                    // Calcular escala para conservar proporciones
                    double escala = Math.min(
                            (double) ancho / imagenOriginal.getIconWidth(),
                            (double) alto / imagenOriginal.getIconHeight()
                    );

                    int nuevoAncho = (int) (
                            imagenOriginal.getIconWidth() * escala
                    );

                    int nuevoAlto = (int) (
                            imagenOriginal.getIconHeight() * escala
                    );

                    // Redimensionar la imagen
                    Image imagen = imagenOriginal.getImage();

                    Image imagenEscalada = imagen.getScaledInstance(
                            nuevoAncho,
                            nuevoAlto,
                            Image.SCALE_SMOOTH
                    );

                    // Mostrar imagen en el JLabel
                    labelFoto.setText("");
                    labelFoto.setIcon(new ImageIcon(imagenEscalada));

                    // Centrar la fotografía
                    labelFoto.setHorizontalAlignment(SwingConstants.CENTER);
                    labelFoto.setVerticalAlignment(SwingConstants.CENTER);

                    JOptionPane.showMessageDialog(
                            VentanaCrearUsuario.this,
                            "Fotografía seleccionada correctamente.",
                            "Fotografía seleccionada",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                }
            }
        });
    }

    // MÉTODO PARA CREAR EL OBJETO USUARIOS
    private Usuarios getNuevoUsuario() {

        String tipoDocumento =
                comboBox1.getSelectedItem().toString();

        String documento = textField2.getText().trim();

        String primerNombre = textField3.getText().trim();
        String segundoNombre = textField4.getText().trim();

        String primerApellido = textField8.getText().trim();
        String segundoApellido = textField9.getText().trim();

        String contrasena = textField5.getText();
        String telefono = textField6.getText().trim();
        String correo = textField7.getText().trim();

        String foto = rutaFoto;

        // Crear usuario con los 10 argumentos
        Usuarios nuevoUsuario = new Usuarios(
                tipoDocumento,
                documento,
                primerNombre,
                segundoNombre,
                primerApellido,
                segundoApellido,
                contrasena,
                telefono,
                correo,
                foto
        );

        return nuevoUsuario;
    }
}