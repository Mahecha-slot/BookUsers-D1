package modelo;

import java.text.Normalizer;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int contadorId = 1;

    private int id;
    private String tipoDocumento;
    private String documento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String username;
    private String telefono;
    private String contrasena;
    private String correo;
    private byte[] fotografia;
    private String rutaFoto;

    // CONSTRUCTOR
    public Usuarios(
            String tipoDocumento,
            String documento,
            String primerNombre,
            String segundoNombre,
            String primerApellido,
            String segundoApellido,
            String contrasena,
            String telefono,
            String correo,
            String rutaFoto
    ) throws IOException {

        this.id = contadorId++;

        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;

        this.username = generarUsername(
                primerApellido,
                segundoApellido,
                primerNombre
        );

        this.telefono = telefono;
        this.contrasena = contrasena;
        this.correo = correo;
        this.rutaFoto = rutaFoto;

        this.fotografia = Files.readAllBytes(
                Paths.get(rutaFoto)
        );
    }

    // GENERAR USERNAME
    private String generarUsername(
            String apellido1,
            String apellido2,
            String nombre
    ) {

        String a1 = normalizar(apellido1);
        String a2 = normalizar(apellido2);
        String n = normalizar(nombre);

        String prefijo;

        if (!a1.isEmpty() && !a2.isEmpty()) {

            prefijo = a1.substring(0, 1)
                    + a2.substring(0, 1);

        } else if (!a1.isEmpty()) {

            prefijo = a1.substring(
                    0, Math.min(2, a1.length())
            );

        } else {

            prefijo = "u";
        }

        return prefijo + n;
    }

    // NORMALIZAR TEXTO
    private String normalizar(String texto) {

        if (texto == null) {
            return "";
        }

        return Normalizer.normalize(
                        texto.trim().toLowerCase(),
                        Normalizer.Form.NFD
                ).replaceAll("\\p{M}", "")
                .replaceAll("\\s+", "");
    }

    // ACTUALIZAR CONTADOR
    static void establecerContadorId(int siguienteId) {
        contadorId = siguienteId;
    }

    // ACTUALIZAR DATOS DEL MISMO USUARIO
    public void actualizarDatos(
            String tipoDocumento,
            String documento,
            String primerNombre,
            String segundoNombre,
            String primerApellido,
            String segundoApellido,
            String contrasena,
            String telefono,
            String correo,
            String nuevaRutaFoto
    ) throws IOException {

        // Leer primero la fotografía nueva, si se seleccionó
        byte[] nuevaFotografia = this.fotografia;

        if (nuevaRutaFoto != null
                && !nuevaRutaFoto.trim().isEmpty()) {

            nuevaFotografia = Files.readAllBytes(
                    Paths.get(nuevaRutaFoto)
            );
        }

        // Actualizar información
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;

        this.contrasena = contrasena;
        this.telefono = telefono;
        this.correo = correo;

        // Actualizar username automáticamente
        this.username = generarUsername(
                primerApellido,
                segundoApellido,
                primerNombre
        );

        // Actualizar fotografía únicamente si se seleccionó otra
        if (nuevaRutaFoto != null
                && !nuevaRutaFoto.trim().isEmpty()) {

            this.rutaFoto = nuevaRutaFoto;
            this.fotografia = nuevaFotografia;
        }
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getUsername() {
        return username;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public byte[] getFotografia() {
        return fotografia == null ? null : fotografia.clone();
    }
}