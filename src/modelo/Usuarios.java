package modelo;

public class Usuarios {

    // Contador automático para asignar el ID
    private static int contadorId = 1;

    // Atributos del usuario
    private int id;
    private String dane;
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
    public Usuarios(String dane, String documento,
                    String primerNombre, String segundoNombre,
                    String primerApellido, String segundoApellido,
                    String contrasena, String telefono,
                    String correo, String rutaFoto) {

        this.id = contadorId++;

        this.dane = dane;
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

    // Método para generar el username
    private String generarUsername(String primerApellido,
                                   String segundoApellido,
                                   String primerNombre) {

        String apellido1 = primerApellido.trim();
        String apellido2 = segundoApellido.trim();
        String nombre = primerNombre.trim();

        // Caso 1: Tiene dos apellidos
        if (!apellido2.isEmpty()) {

            return apellido1.substring(0, 1).toUpperCase()
                    + apellido2.substring(0, 1).toUpperCase()
                    + nombre;

        } else {

            // Caso 2: Solo tiene un apellido
            if (apellido1.length() >= 2) {

                return apellido1.substring(0, 2).toUpperCase()
                        + nombre;

            } else {

                return apellido1.toUpperCase() + nombre;
            }
        }
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getDane() {
        return dane;
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