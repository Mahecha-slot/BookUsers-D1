package modelo;

public class Usuarios {

    // Contador estático para autoincrementar el ID con cada nuevo usuario registrado
    private static int contadorId = 1;

    private int id;
    private String dane;
    private String documento;
    private String nombres;
    private String apellidos;
    private String username;
    private String contrasena;
    private String telefono;
    private String correo;
    private String rutaFoto;

    // Constructor único que recibe los 8 datos que le envía tu ventana
    public Usuarios(String dane, String documento, String nombres, String apellidos,
                    String contrasena, String telefono, String correo, String rutaFoto) {
        this.id = contadorId++; // Asigna el ID actual y suma 1 para el siguiente
        this.dane = dane;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.correo = correo;
        this.rutaFoto = rutaFoto;

        // Generar username de forma automática usando el apellido y el nombre
        this.username = generarUsername(apellidos, nombres);
    }

    // Lógica para generar el username de forma limpia
    private String generarUsername(String apellido, String nombre) {
        String apellidoLim = (apellido != null && !apellido.isEmpty()) ? apellido.toLowerCase().trim() : "x";
        String nombreLim = (nombre != null && !nombre.isEmpty()) ? nombre.toLowerCase().trim() : "user";

        char letraApellido = apellidoLim.charAt(0);
        return "" + letraApellido + nombreLim;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getDane() { return dane; }
    public String getDocumento() { return documento; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getRutaFoto() { return rutaFoto; }
}
