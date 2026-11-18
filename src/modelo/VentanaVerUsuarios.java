package modelo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaVerUsuarios extends JFrame {

    private JComboBox ListaSeccionUsuario;
    private JPanel PanelVerUsuario;
    private JScrollPane ScrollVerUsuario;
    private JButton PDFButton;

    // Gestor compartido
    private GestorUsuarios gestor;

    // Lista visual de usuarios
    private JList<Usuarios> listaUsuarios;

    // Modelo de la lista
    private DefaultListModel<Usuarios> modeloLista;

    public VentanaVerUsuarios(GestorUsuarios gestor) {

        this.gestor = gestor;

        // CONFIGURACIÓN DE LA VENTANA CON EL FONDO
        setTitle("Usuarios registrados - UNIPAZ");

        PanelConFondo fondo =
                new PanelConFondo("/imagenes/Imagen4.jpeg");

        fondo.setLayout(new BorderLayout());

        // Hacer transparente el panel principal
        PanelVerUsuario.setOpaque(false);

        fondo.add(PanelVerUsuario, BorderLayout.CENTER);

        setContentPane(fondo);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(600, 600);
        setLocationRelativeTo(null);

        // Crear modelo de la lista
        modeloLista = new DefaultListModel<>();

        listaUsuarios = new JList<>(modeloLista);

        // PERSONALIZAR CÓMO APARECEN LOS USUARIOS
        listaUsuarios.setCellRenderer(
                new DefaultListCellRenderer() {

                    @Override
                    public Component getListCellRendererComponent(
                            JList<?> list,
                            Object value,
                            int index,
                            boolean isSelected,
                            boolean cellHasFocus) {

                        super.getListCellRendererComponent(
                                list,
                                value,
                                index,
                                isSelected,
                                cellHasFocus
                        );

                        if (value instanceof Usuarios) {

                            Usuarios usuario = (Usuarios) value;

                            setText(
                                    usuario.getPrimerNombre() + " " +
                                            usuario.getPrimerApellido() + " - " +
                                            usuario.getUsername()
                            );
                        }

                        return this;
                    }
                });

        // TRANSPARENCIA DEL ÁREA DE USUARIOS
        ScrollVerUsuario.setOpaque(false);
        ScrollVerUsuario.getViewport().setOpaque(false);
        listaUsuarios.setOpaque(false);

        // Colocar la lista dentro del JScrollPane
        ScrollVerUsuario.setViewportView(listaUsuarios);

        // Cargar usuarios registrados
        cargarUsuarios();

        // Selección desde el JComboBox
        ListaSeccionUsuario.addActionListener(e -> {

            int indice = ListaSeccionUsuario.getSelectedIndex();

            if (indice >= 0 &&
                    indice < modeloLista.size()) {

                listaUsuarios.setSelectedIndex(indice);
            }
        });

        // Selección desde el JScrollPane
        listaUsuarios.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int indice = listaUsuarios.getSelectedIndex();

                if (indice >= 0 &&
                        ListaSeccionUsuario.getSelectedIndex() != indice) {

                    ListaSeccionUsuario.setSelectedIndex(indice);
                }
            }
        });

        // BOTÓN DESCARGAR PDF
        PDFButton.addActionListener(e -> {

            Usuarios usuarioSeleccionado =
                    listaUsuarios.getSelectedValue();

            if (usuarioSeleccionado == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Primero debes seleccionar un usuario.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuario seleccionado: " +
                                usuarioSeleccionado.getUsername() +
                                "\nLa generación del PDF se conectará próximamente."
                );
            }
        });
    }

    // MÉTODO PARA CARGAR LOS USUARIOS
    private void cargarUsuarios() {

        modeloLista.clear();

        ListaSeccionUsuario.removeAllItems();

        // Obtener los usuarios del gestor compartido
        ArrayList<Usuarios> usuariosRegistrados =
                gestor.obtenerUsuarios();

        // Recorrer los objetos registrados
        for (Usuarios usuario : usuariosRegistrados) {

            modeloLista.addElement(usuario);

            ListaSeccionUsuario.addItem(
                    usuario.getPrimerNombre() + " " +
                            usuario.getPrimerApellido() + " - " +
                            usuario.getUsername()
            );
        }
    }
}