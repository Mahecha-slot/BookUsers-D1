package modelo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VentanaVerUsuarios extends JFrame {

    private JComboBox ListaSeccionUsuario;
    private JPanel PanelVerUsuario;
    private JScrollPane ScrollVerUsuario;
    private JButton PDFButton;

    // Lista visual de usuarios
    private JList<Usuarios> listaUsuarios;

    // Modelo para organizar los usuarios
    private DefaultListModel<Usuarios> modeloLista;

    public VentanaVerUsuarios(GestorUsuarios gestor) {

        setTitle("Usuarios registrados - UNIPAZ");

        setContentPane(PanelVerUsuario);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(900, 650);
        setLocationRelativeTo(null);

        // Crear modelo de la lista
        modeloLista = new DefaultListModel<>();

        listaUsuarios = new JList<>(modeloLista);

        // Mostrar nombres de los usuarios en lugar de sus objetos
        listaUsuarios.setCellRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus
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

        // Colocar la lista dentro del JScrollPane
        ScrollVerUsuario.setViewportView(listaUsuarios);

        // Cargar los usuarios registrados
        cargarUsuarios();

        // Seleccionar usuario desde el JComboBox
        ListaSeccionUsuario.addActionListener(e -> {

            int indice = ListaSeccionUsuario.getSelectedIndex();

            if (indice >= 0 && indice < modeloLista.size()) {

                listaUsuarios.setSelectedIndex(indice);
            }
        });

        // Seleccionar usuario desde la lista
        listaUsuarios.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int indice = listaUsuarios.getSelectedIndex();

                if (indice >= 0 &&
                        ListaSeccionUsuario.getSelectedIndex() != indice) {

                    ListaSeccionUsuario.setSelectedIndex(indice);
                }
            }
        });

        // Botón Descargar PDF
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
                                "\nLa generación del PDF se conectará en el siguiente paso."
                );
            }
        });
    }

    // Método para cargar los usuarios registrados
    private void cargarUsuarios() {

        modeloLista.clear();

        ListaSeccionUsuario.removeAllItems();

        // Obtener usuarios del gestor
        ArrayList<Usuarios> usuariosRegistrados =
                GestorUsuarios.obtenerUsuarios();

        // Recorrer los usuarios registrados
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