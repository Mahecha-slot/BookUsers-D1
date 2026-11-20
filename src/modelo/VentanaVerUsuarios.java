package modelo;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
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

    private GestorUsuarios gestor;

    private JList<Usuarios> listaUsuarios;

    private DefaultListModel<Usuarios> modeloLista;

    public VentanaVerUsuarios(GestorUsuarios gestor) {

        this.gestor = gestor;

        // CONFIGURACIÓN DE LA VENTANA
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

        // CREAR MODELO DE LA LISTA
        modeloLista = new DefaultListModel<>();

        listaUsuarios = new JList<>(modeloLista);

        // PERSONALIZAR USUARIOS
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

            if (indice >= 0 &&
                    indice < modeloLista.size()) {

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

        // BOTÓN GENERAR PDF
        PDFButton.addActionListener(e -> {

            int indice = ListaSeccionUsuario.getSelectedIndex();

            if (indice < 0 || indice >= modeloLista.size()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Primero debes seleccionar un usuario.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Usuarios usuarioSeleccionado =
                    modeloLista.getElementAt(indice);

            generarPDF(usuarioSeleccionado);
        });
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

        // COMPROBAR SI YA EXISTE
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

    // AGREGAR FILAS A LA TABLA
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