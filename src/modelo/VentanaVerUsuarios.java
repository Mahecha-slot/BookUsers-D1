package modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.Image;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;

public class VentanaVerUsuarios extends JFrame {

    private JComboBox ListaSeccionUsuario;
    private JPanel PanelVerUsuario;
    private JScrollPane ScrollVerUsuario;
    private JButton PDFButton;
    private JButton eliminarButton;
    private JButton editarButton;

    private GestorUsuarios gestor;

    private JList<Usuarios> listaUsuarios;
    private DefaultListModel<Usuarios> modeloLista;

    public VentanaVerUsuarios(GestorUsuarios gestor) {

        this.gestor = gestor;

        setTitle("Usuarios registrados - UNIPAZ");

        PanelConFondo fondo =
                new PanelConFondo("/imagenes/Imagen4.jpeg");

        fondo.setLayout(new BorderLayout());

        PanelVerUsuario.setOpaque(false);
        fondo.add(PanelVerUsuario, BorderLayout.CENTER);

        setContentPane(fondo);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        // CREAR LISTA DE USUARIOS
        modeloLista = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloLista);

        // PERSONALIZAR LA APARIENCIA DE CADA USUARIO
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
                }
        );

        // TRANSPARENCIA
        ScrollVerUsuario.setOpaque(false);
        ScrollVerUsuario.getViewport().setOpaque(false);
        listaUsuarios.setOpaque(false);

        ScrollVerUsuario.setViewportView(listaUsuarios);

        // CARGAR USUARIOS
        cargarUsuarios();

        // SINCRONIZAR COMBOBOX CON LISTA
        ListaSeccionUsuario.addActionListener(e -> {

            int indice = ListaSeccionUsuario.getSelectedIndex();

            if (indice >= 0 && indice < modeloLista.size()) {
                listaUsuarios.setSelectedIndex(indice);
            }
        });

        // SINCRONIZAR LISTA CON COMBOBOX
        listaUsuarios.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int indice = listaUsuarios.getSelectedIndex();

                if (indice >= 0 &&
                        ListaSeccionUsuario.getSelectedIndex() != indice) {

                    ListaSeccionUsuario.setSelectedIndex(indice);
                }
            }
        });

        // BOTÓN PDF
        PDFButton.addActionListener(e -> {

            Usuarios usuario = obtenerUsuarioSeleccionado();

            if (usuario == null) {
                mostrarAvisoSeleccion();
                return;
            }

            generarPDF(usuario);
        });

        // BOTÓN EDITAR
        editarButton.addActionListener(e -> editarUsuario());

        // BOTÓN ELIMINAR
        eliminarButton.addActionListener(e -> eliminarUsuario());
    }

    // OBTENER EL USUARIO SELECCIONADO
    private Usuarios obtenerUsuarioSeleccionado() {

        int indice = listaUsuarios.getSelectedIndex();

        if (indice >= 0 && indice < modeloLista.size()) {
            return modeloLista.getElementAt(indice);
        }

        indice = ListaSeccionUsuario.getSelectedIndex();

        if (indice >= 0 && indice < modeloLista.size()) {
            return modeloLista.getElementAt(indice);
        }

        return null;
    }

    // MENSAJE CUANDO NO HAY SELECCIÓN
    private void mostrarAvisoSeleccion() {

        JOptionPane.showMessageDialog(
                this,
                "Primero debes seleccionar un usuario.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );
    }

    // CARGAR USUARIOS REGISTRADOS
    private void cargarUsuarios() {

        modeloLista.clear();
        ListaSeccionUsuario.removeAllItems();

        ArrayList<Usuarios> usuariosRegistrados =
                gestor.obtenerUsuarios();

        for (Usuarios usuario : usuariosRegistrados) {

            modeloLista.addElement(usuario);

            ListaSeccionUsuario.addItem(
                    usuario.getPrimerNombre() + " " +
                            usuario.getPrimerApellido() + " - " +
                            usuario.getUsername()
            );
        }

        listaUsuarios.clearSelection();
        ListaSeccionUsuario.setSelectedIndex(-1);
    }

    // EDITAR USUARIO
    private void editarUsuario() {

        Usuarios usuarioSeleccionado =
                obtenerUsuarioSeleccionado();

        if (usuarioSeleccionado == null) {
            mostrarAvisoSeleccion();
            return;
        }

        int idUsuario = usuarioSeleccionado.getId();

        VentanaCrearUsuario ventanaEdicion =
                new VentanaCrearUsuario(gestor, usuarioSeleccionado);

        // ACTUALIZAR LA LISTA CUANDO SE CIERRE LA VENTANA
        ventanaEdicion.addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosed(WindowEvent e) {

                        cargarUsuarios();
                        seleccionarUsuarioPorId(idUsuario);
                    }
                }
        );

        ventanaEdicion.setVisible(true);
    }

    // ELIMINAR USUARIO
    private void eliminarUsuario() {

        Usuarios usuarioSeleccionado =
                obtenerUsuarioSeleccionado();

        if (usuarioSeleccionado == null) {
            mostrarAvisoSeleccion();
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas eliminar al usuario "
                        + usuarioSeleccionado.getUsername() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            boolean eliminado =
                    gestor.eliminarUsuario(usuarioSeleccionado.getId());

            if (eliminado) {

                cargarUsuarios();

                JOptionPane.showMessageDialog(
                        this,
                        "El usuario fue eliminado correctamente.",
                        "Eliminación exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el usuario que deseas eliminar.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el usuario:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // VOLVER A SELECCIONAR UN USUARIO POR SU ID
    private void seleccionarUsuarioPorId(int id) {

        for (int i = 0; i < modeloLista.size(); i++) {

            Usuarios usuario = modeloLista.getElementAt(i);

            if (usuario.getId() == id) {

                listaUsuarios.setSelectedIndex(i);
                listaUsuarios.ensureIndexIsVisible(i);

                ListaSeccionUsuario.setSelectedIndex(i);

                return;
            }
        }
    }

    // GENERAR PDF INDIVIDUAL
    private void generarPDF(Usuarios usuario) {

        JFileChooser selector = new JFileChooser();

        selector.setDialogTitle("Guardar PDF del usuario");

        String nombreArchivo =
                "Usuario_" + usuario.getUsername() + ".pdf";

        selector.setSelectedFile(new File(nombreArchivo));

        int resultado = selector.showSaveDialog(this);

        if (resultado != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();

        // ASEGURAR EXTENSIÓN PDF
        if (!archivo.getName().toLowerCase().endsWith(".pdf")) {

            archivo = new File(
                    archivo.getAbsolutePath() + ".pdf"
            );
        }

        // COMPROBAR SI EL ARCHIVO YA EXISTE
        if (archivo.exists()) {

            int confirmar = JOptionPane.showConfirmDialog(
                    this,
                    "El archivo ya existe. ¿Deseas reemplazarlo?",
                    "Confirmar reemplazo",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmar != JOptionPane.YES_OPTION) {
                return;
            }
        }

        Document documento = new Document();

        try {

            PdfWriter.getInstance(
                    documento,
                    new FileOutputStream(archivo)
            );

            documento.open();

            // TÍTULO
            Font titulo = new Font(
                    Font.HELVETICA,
                    18,
                    Font.BOLD
            );

            Paragraph encabezado = new Paragraph(
                    "FICHA INDIVIDUAL DE USUARIO",
                    titulo
            );

            encabezado.setAlignment(Element.ALIGN_CENTER);
            encabezado.setSpacingAfter(20);

            documento.add(encabezado);

            // TABLA DE INFORMACIÓN
            PdfPTable tabla = new PdfPTable(2);

            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1, 2});

            agregarFila(tabla, "ID",
                    String.valueOf(usuario.getId()));

            agregarFila(tabla, "Tipo de documento",
                    usuario.getTipoDocumento());

            agregarFila(tabla, "Número de documento",
                    usuario.getDocumento());

            agregarFila(tabla, "Primer nombre",
                    usuario.getPrimerNombre());

            agregarFila(tabla, "Segundo nombre",
                    usuario.getSegundoNombre());

            agregarFila(tabla, "Primer apellido",
                    usuario.getPrimerApellido());

            agregarFila(tabla, "Segundo apellido",
                    usuario.getSegundoApellido());

            agregarFila(tabla, "Nombre de usuario",
                    usuario.getUsername());

            agregarFila(tabla, "Teléfono",
                    usuario.getTelefono());

            agregarFila(tabla, "Correo electrónico",
                    usuario.getCorreo());

            documento.add(tabla);

            // FOTOGRAFÍA
            byte[] fotografia = usuario.getFotografia();

            if (fotografia != null && fotografia.length > 0) {

                documento.add(
                        new Paragraph("\nFotografía del usuario:")
                );

                Image imagen = Image.getInstance(fotografia);

                imagen.scaleToFit(150, 150);
                imagen.setAlignment(Element.ALIGN_CENTER);

                documento.add(imagen);
            }

            documento.close();

            JOptionPane.showMessageDialog(
                    this,
                    "El PDF del usuario se generó correctamente.",
                    "PDF generado",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {

            if (documento.isOpen()) {
                documento.close();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo generar el PDF:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    // AGREGAR FILAS A LA TABLA DEL PDF
    private void agregarFila(
            PdfPTable tabla,
            String campo,
            String valor) {

        Font negrita = new Font(
                Font.HELVETICA,
                10,
                Font.BOLD
        );

        PdfPCell celdaCampo = new PdfPCell(
                new Paragraph(campo, negrita)
        );

        PdfPCell celdaValor = new PdfPCell(
                new Paragraph(valor == null ? "" : valor)
        );

        celdaCampo.setPadding(8);
        celdaValor.setPadding(8);

        tabla.addCell(celdaCampo);
        tabla.addCell(celdaValor);
    }
}