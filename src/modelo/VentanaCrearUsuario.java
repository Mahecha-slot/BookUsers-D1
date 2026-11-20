package modelo;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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

    private JComboBox comboBox1;

    private JButton cancelarButton;
    private JButton guardarButton;
    private JButton seleccionarFotografiaButton;
    private JLabel labelFoto;

    private GestorUsuarios gestor;

    // NUEVO: usuario que se está editando
    private Usuarios usuarioEdicion;

    // Ruta de fotografía nueva
    private String rutaFoto = "";

    // CONSTRUCTOR PARA REGISTRAR
    public VentanaCrearUsuario(GestorUsuarios gestor) {

        this(gestor, null);
    }

    // CONSTRUCTOR PARA EDITAR
    public VentanaCrearUsuario(
            GestorUsuarios gestor,
            Usuarios usuario
    ) {

        this.gestor = gestor;
        this.usuarioEdicion = usuario;

        setTitle(
                usuario == null
                        ? "Crear Nuevo Usuario - UNIPAZ"
                        : "Editar Usuario - UNIPAZ"
        );

        // FONDO
        if (panelCrearUsuario == null) {
            panelCrearUsuario = new JPanel();
        }

        PanelConFondo fondo =
                new PanelConFondo("/imagenes/Imagen2.jpg");

        fondo.setLayout(new BorderLayout());

        panelCrearUsuario.setOpaque(false);

        fondo.add(
                panelCrearUsuario,
                BorderLayout.CENTER
        );

        setContentPane(fondo);

        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // TIPOS DE DOCUMENTO
        comboBox1.removeAllItems();

        comboBox1.addItem("Seleccione tipo de documento");
        comboBox1.addItem("Cédula de ciudadanía (CC)");
        comboBox1.addItem("Tarjeta de identidad (TI)");
        comboBox1.addItem("Cédula de extranjería (CE)");
        comboBox1.addItem("Pasaporte (PA)");
        comboBox1.addItem("Permiso por Protección Temporal (PPT)");
        comboBox1.addItem("Registro civil (RC)");

        // DETERMINAR MODO DEL FORMULARIO
        if (usuarioEdicion != null) {

            cargarDatosUsuario();

            guardarButton.setText("Actualizar usuario");

        }

        // BOTÓN GUARDAR
        guardarButton.addActionListener(e -> guardarUsuario());

        // BOTÓN CANCELAR
        cancelarButton.addActionListener(e -> dispose());

        // BOTÓN SELECCIONAR FOTOGRAFÍA
        seleccionarFotografiaButton.addActionListener(
                e -> seleccionarFotografia()
        );
    }

    // ==============================================
    // CARGAR DATOS DEL USUARIO PARA EDITAR
    // ==============================================

    private void cargarDatosUsuario() {

        comboBox1.setSelectedItem(
                usuarioEdicion.getTipoDocumento()
        );

        textField2.setText(
                usuarioEdicion.getDocumento()
        );

        textField3.setText(
                usuarioEdicion.getPrimerNombre()
        );

        textField4.setText(
                usuarioEdicion.getSegundoNombre()
        );

        textField5.setText(
                usuarioEdicion.getContrasena()
        );

        textField6.setText(
                usuarioEdicion.getTelefono()
        );

        textField7.setText(
                usuarioEdicion.getCorreo()
        );

        textField8.setText(
                usuarioEdicion.getPrimerApellido()
        );

        textField9.setText(
                usuarioEdicion.getSegundoApellido()
        );

        // Mostrar fotografía almacenada
        try {

            byte[] fotografia =
                    usuarioEdicion.getFotografia();

            if (fotografia != null && fotografia.length > 0) {

                ImageIcon imagenOriginal =
                        new ImageIcon(fotografia);

                mostrarImagen(imagenOriginal);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo cargar la fotografía anterior.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ==============================================
    // GUARDAR O ACTUALIZAR USUARIO
    // ==============================================

    private void guardarUsuario() {

        // VALIDAR CAMPOS
        if (comboBox1.getSelectedIndex() <= 0

                || textField2.getText().trim().isEmpty()
                || textField3.getText().trim().isEmpty()
                || textField5.getText().trim().isEmpty()
                || textField6.getText().trim().isEmpty()
                || textField7.getText().trim().isEmpty()
                || textField8.getText().trim().isEmpty()

                || (usuarioEdicion == null
                && rutaFoto.trim().isEmpty())) {

            mostrarAdvertenciaDatos();

            return;
        }

        // VALIDAR CORREO
        if (!textField7.getText().trim().contains("@")) {

            JOptionPane.showMessageDialog(
                    this,
                    "El correo electrónico debe contener @.",
                    "Correo inválido",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // CONFIRMAR OPERACIÓN
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                usuarioEdicion == null
                        ? "¿Estás seguro de que deseas registrar este usuario?"
                        : "¿Estás seguro de que deseas actualizar este usuario?",
                "Confirmar operación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            if (usuarioEdicion == null) {

                // MODO REGISTRO
                Usuarios nuevoUsuario = getNuevoUsuario();

                gestor.agregarUsuario(nuevoUsuario);

                mostrarFelicidades();

            } else {

                // MODO EDICIÓN
                usuarioEdicion.actualizarDatos(

                        comboBox1.getSelectedItem().toString(),

                        textField2.getText().trim(),

                        textField3.getText().trim(),

                        textField4.getText().trim(),

                        textField8.getText().trim(),

                        textField9.getText().trim(),

                        textField5.getText(),

                        textField6.getText().trim(),

                        textField7.getText().trim(),

                        rutaFoto
                );

                gestor.actualizarUsuario(usuarioEdicion);

                JOptionPane.showMessageDialog(
                        this,
                        "Los datos del usuario se actualizaron correctamente.",
                        "Actualización exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            dispose();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar el usuario: "
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // ==============================================
    // SELECCIONAR FOTOGRAFÍA
    // ==============================================

    private void seleccionarFotografia() {

        JFileChooser selector = new JFileChooser();

        selector.setDialogTitle(
                "Seleccionar fotografía del usuario"
        );

        FileNameExtensionFilter filtro =
                new FileNameExtensionFilter(
                        "Imágenes (JPG, JPEG, PNG)",
                        "jpg", "jpeg", "png"
                );

        selector.setFileFilter(filtro);

        int resultado = selector.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {

            File archivo = selector.getSelectedFile();

            rutaFoto = archivo.getAbsolutePath();

            ImageIcon imagenOriginal =
                    new ImageIcon(rutaFoto);

            mostrarImagen(imagenOriginal);

            JOptionPane.showMessageDialog(
                    this,
                    "Fotografía seleccionada correctamente.",
                    "Fotografía seleccionada",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // ==============================================
    // MOSTRAR IMAGEN REDIMENSIONADA
    // ==============================================

    private void mostrarImagen(ImageIcon imagenOriginal) {

        int ancho = labelFoto.getWidth();
        int alto = labelFoto.getHeight();

        if (ancho <= 0 || alto <= 0) {
            ancho = 150;
            alto = 150;
        }

        if (imagenOriginal.getIconWidth() <= 0
                || imagenOriginal.getIconHeight() <= 0) {
            return;
        }

        double escala = Math.min(
                (double) ancho / imagenOriginal.getIconWidth(),
                (double) alto / imagenOriginal.getIconHeight()
        );

        int nuevoAncho = Math.max(
                1,
                (int) (imagenOriginal.getIconWidth() * escala)
        );

        int nuevoAlto = Math.max(
                1,
                (int) (imagenOriginal.getIconHeight() * escala)
        );

        Image imagenEscalada =
                imagenOriginal.getImage().getScaledInstance(
                        nuevoAncho,
                        nuevoAlto,
                        Image.SCALE_SMOOTH
                );

        labelFoto.setText("");
        labelFoto.setIcon(new ImageIcon(imagenEscalada));

        labelFoto.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        labelFoto.setVerticalAlignment(
                SwingConstants.CENTER
        );
    }

    // ==============================================
    // ADVERTENCIA PERSONALIZADA
    // ==============================================

    private void mostrarAdvertenciaDatos() {

        JFrame ventanaAdvertencia =
                new JFrame("Campos incompletos");

        ventanaAdvertencia.setSize(600, 600);
        ventanaAdvertencia.setLocationRelativeTo(this);
        ventanaAdvertencia.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        ventanaAdvertencia.setResizable(false);

        java.net.URL recurso =
                getClass().getResource(
                        "/imagenes/Advertencia.jpeg"
                );

        if (recurso == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se encontró la imagen Advertencia.jpeg",
                    "Error de imagen",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        ImageIcon imagenOriginal =
                new ImageIcon(recurso);

        Image imagenEscalada =
                imagenOriginal.getImage().getScaledInstance(
                        600,
                        600,
                        Image.SCALE_SMOOTH
                );

        JLabel fondo = new JLabel(
                new ImageIcon(imagenEscalada)
        );

        fondo.setLayout(new BorderLayout());

        JPanel panelInferior = new JPanel(
                new BorderLayout()
        );

        panelInferior.setOpaque(false);

        JLabel mensaje = new JLabel(
                "Por favor, llena tus datos",
                SwingConstants.CENTER
        );

        mensaje.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        mensaje.setForeground(Color.WHITE);

        mensaje.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 5, 10, 5
                )
        );

        JButton botonEntendido =
                new JButton("Entendido");

        botonEntendido.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        botonEntendido.addActionListener(
                e -> ventanaAdvertencia.dispose()
        );

        JPanel panelBoton = new JPanel();

        panelBoton.setOpaque(false);
        panelBoton.add(botonEntendido);

        panelInferior.add(
                mensaje,
                BorderLayout.CENTER
        );

        panelInferior.add(
                panelBoton,
                BorderLayout.SOUTH
        );

        fondo.add(
                panelInferior,
                BorderLayout.SOUTH
        );

        ventanaAdvertencia.setContentPane(fondo);
        ventanaAdvertencia.setVisible(true);
    }

    // ==============================================
    // FELICITACIONES PERSONALIZADAS
    // ==============================================

    private void mostrarFelicidades() {

        JFrame ventanaFelicidades =
                new JFrame("Registro exitoso");

        ventanaFelicidades.setSize(600, 600);
        ventanaFelicidades.setLocationRelativeTo(this);

        ventanaFelicidades.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        ventanaFelicidades.setResizable(false);

        java.net.URL recurso =
                getClass().getResource(
                        "/imagenes/Felicidades.jpeg"
                );

        if (recurso == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se encontró la imagen Felicidades.jpeg",
                    "Error de imagen",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        ImageIcon imagenOriginal =
                new ImageIcon(recurso);

        Image imagenEscalada =
                imagenOriginal.getImage().getScaledInstance(
                        600,
                        600,
                        Image.SCALE_SMOOTH
                );

        JLabel fondo = new JLabel(
                new ImageIcon(imagenEscalada)
        );

        fondo.setLayout(new BorderLayout());

        JPanel panelInferior = new JPanel(
                new BorderLayout()
        );

        panelInferior.setOpaque(false);

        JLabel mensaje = new JLabel(
                "¡Usuario registrado con éxito!",
                SwingConstants.CENTER
        );

        mensaje.setFont(
                new Font("Arial", Font.BOLD, 23)
        );

        mensaje.setForeground(Color.BLACK);

        mensaje.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 5, 10, 5
                )
        );

        JButton botonEntendido =
                new JButton("Entendido");

        botonEntendido.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        botonEntendido.addActionListener(
                e -> ventanaFelicidades.dispose()
        );

        JPanel panelBoton = new JPanel();

        panelBoton.setOpaque(false);
        panelBoton.add(botonEntendido);

        panelInferior.add(
                mensaje,
                BorderLayout.CENTER
        );

        panelInferior.add(
                panelBoton,
                BorderLayout.SOUTH
        );

        fondo.add(
                panelInferior,
                BorderLayout.SOUTH
        );

        ventanaFelicidades.setContentPane(fondo);
        ventanaFelicidades.setVisible(true);
    }

    // ==============================================
    // CREAR OBJETO PARA REGISTRO
    // ==============================================

    private Usuarios getNuevoUsuario()
            throws IOException {

        String tipoDocumento =
                comboBox1.getSelectedItem().toString();

        String documento =
                textField2.getText().trim();

        String primerNombre =
                textField3.getText().trim();

        String segundoNombre =
                textField4.getText().trim();

        String primerApellido =
                textField8.getText().trim();

        String segundoApellido =
                textField9.getText().trim();

        String contrasena =
                textField5.getText();

        String telefono =
                textField6.getText().trim();

        String correo =
                textField7.getText().trim();

        return new Usuarios(
                tipoDocumento,
                documento,
                primerNombre,
                segundoNombre,
                primerApellido,
                segundoApellido,
                contrasena,
                telefono,
                correo,
                rutaFoto
        );
    }
}