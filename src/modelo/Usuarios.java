package modelo;

import java.text.Normalizer;

public class Usuarios {

    // Contador automático para asignar ID
    private static int contadorId = 1;

    // Atributos del usuario
    private int id;

    private String tipoDocumento;
    private String documento;

    private String primerNombre;
    private String segundoNombre;

    private String primerApellido;
    private String segundoApellido;

    private String username;
    private String contrasena;
    private String telefono;
    private String correo;
    private String rutaFoto;

    // Constructor
    public Usuarios(String tipoDocumento, String documento,
                    String primerNombre, String segundoNombre,
                    String primerApellido, String segundoApellido,
                    String contrasena, String telefono,
                    String correo, String rutaFoto) {

        this.id = contadorId++;

        this.tipoDocumento = tipoDocumento;
        this.documento = documento;

        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;

        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;

        this.contrasena = contrasena;
        this.telefono = telefono;
        this.correo = correo;
        this.rutaFoto = rutaFoto;

        // Generar username automáticamente
        this.username = generarUsername(
                primerApellido,
                segundoApellido,
                primerNombre
        );
    }

    // Método para generar username
    private String generarUsername(String apellido1,
                                   String apellido2,
                                   String nombre) {

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

    // Método para normalizar texto
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

    public String getCorreo() {
        return correo;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }
}