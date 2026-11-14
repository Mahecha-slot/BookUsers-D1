package modelo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaCrearUsuario extends JFrame {
    private JPanel panelCrearUsuario;
    private JTextField textField1; // DANE
    private JTextField textField2; // Documento
    private JTextField textField3; // Nombres
    private JTextField textField4; // Apellidos
    private JTextField textField5; // Contraseña
    private JTextField textField6; // Teléfono
    private JTextField textField7; // Correo
    private JButton cancelarButton;
    private JButton guardarButton;
    private JTextField textField8;
    private JTextField textField9;
    private JComboBox comboBox1;
    private JButton seleccionarFotografiaButton;

    private GestorUsuarios gestor;

    public VentanaCrearUsuario(GestorUsuarios gestor) {
        this.gestor = gestor;

        setTitle("Crear Nuevo Usuario - UNIPAZ");

        // Blindaje anti-nulos: Si el panel del form por alguna razón no se enlazó,
        // creamos un panel por defecto para que la ventana abra sin crashear.
        if (panelCrearUsuario == null) {
            panelCrearUsuario = new JPanel();
        }
        setContentPane(panelCrearUsuario);

        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // --- BOTÓN GUARDAR (Con confirmación Sí / No) ---
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Validar campos obligatorios básicos
                if (textField2.getText().isEmpty() || textField3.getText().isEmpty() || textField4.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaCrearUsuario.this,
                            "Por favor completa al menos el documento, nombres y apellidos.",
                            "Campos incompletos",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 2. Cuadro de diálogo de confirmación Sí / No
                int respuesta = JOptionPane.showConfirmDialog(
                        VentanaCrearUsuario.this,
                        "¿Estás seguro de que deseas registrar este usuario?",
                        "Confirmar registro",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                // 3. Si hace clic en "Sí"
                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        // Capturamos los datos de las cajas de texto
                        Usuarios nuevoUsuario = getNuevoUsuario();

                        // Pasamos el objeto completo al gestor
                        gestor.agregarUsuario(nuevoUsuario);

                        JOptionPane.showMessageDialog(VentanaCrearUsuario.this,
                                "¡Usuario registrado con éxito!",
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);

                        dispose(); // Cierra esta ventana

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(VentanaCrearUsuario.this,
                                "Error al guardar el usuario: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // --- BOTÓN CANCELAR ---
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private Usuarios getNuevoUsuario() {
        String dane = textField1.getText();
        String documento = textField2.getText();
        String nombres = textField3.getText();
        String apellidos = textField4.getText();
        String contrasena = textField5.getText();
        String telefono = textField6.getText();
        String correo = textField7.getText();
        String foto = "";

        // Creamos el objeto Usuarios con los 8 argumentos
        Usuarios nuevoUsuario = new Usuarios(dane, documento, nombres, apellidos, contrasena, telefono, correo, foto);
        return nuevoUsuario;
    }
}