package modelo;

import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

public class GestorUsuarios {

    // RUTA DE ALMACENAMIENTO
    private static final String CARPETA =
            System.getProperty("user.home")
                    + File.separator + "UNIPAZ";

    private static final String ARCHIVO =
            CARPETA + File.separator + "usuarios.dat";


    // LISTA DE USUARIOS
    private ArrayList<Usuarios> listaUsuarios;


    // CONSTRUCTOR
    public GestorUsuarios() {

        listaUsuarios = new ArrayList<>();

        // Recuperar usuarios al iniciar
        cargarUsuarios();
    }


    // AGREGAR USUARIO
    public void agregarUsuario(Usuarios usuario)
            throws IOException {

        if (usuario != null) {

            listaUsuarios.add(usuario);

            try {

                // Guardar automáticamente
                guardarUsuarios();

            } catch (IOException e) {

                // Si no se pudo guardar,
                // retirar el usuario de la lista
                listaUsuarios.remove(usuario);

                throw e;
            }
        }
    }


    // GUARDAR USUARIOS EN EL ARCHIVO
    private void guardarUsuarios() throws IOException {

        // Crear carpeta si no existe
        Files.createDirectories(Paths.get(CARPETA));

        // Escribir la lista completa
        try (
                ObjectOutputStream salida =
                        new ObjectOutputStream(
                                new FileOutputStream(ARCHIVO)
                        )
        ) {

            salida.writeObject(listaUsuarios);

        }

        System.out.println(
                "Usuarios guardados correctamente."
        );
    }


    // RECUPERAR USUARIOS DEL ARCHIVO
    @SuppressWarnings("unchecked")
    private void cargarUsuarios() {

        File archivo = new File(ARCHIVO);

        // Si todavía no existe el archivo,
        // comenzar con una lista vacía
        if (!archivo.exists()) {

            System.out.println(
                    "No existe un archivo anterior. "
                            + "Se creará al registrar el primer usuario."
            );

            return;
        }

        try (
                ObjectInputStream entrada =
                        new ObjectInputStream(
                                new FileInputStream(archivo)
                        )
        ) {

            Object objeto = entrada.readObject();

            if (objeto instanceof ArrayList<?>) {

                ArrayList<?> usuariosGuardados =
                        (ArrayList<?>) objeto;

                int mayorId = 0;

                for (Object elemento : usuariosGuardados) {

                    if (elemento instanceof Usuarios) {

                        Usuarios usuario = (Usuarios) elemento;

                        listaUsuarios.add(usuario);

                        if (usuario.getId() > mayorId) {
                            mayorId = usuario.getId();
                        }
                    }
                }

                // Evitar repetir IDs después de reiniciar
                Usuarios.establecerContadorId(mayorId + 1);

                System.out.println(
                        "Usuarios recuperados: "
                                + listaUsuarios.size()
                );
            }

        } catch (IOException | ClassNotFoundException e) {

            JOptionPaneHelper.mostrarError(
                    "No se pudieron recuperar los usuarios: "
                            + e.getMessage()
            );
        }
    }


    // OBTENER TODOS LOS USUARIOS
    public ArrayList<Usuarios> obtenerUsuarios() {

        return new ArrayList<>(listaUsuarios);
    }


    // BUSCAR POR USERNAME
    public Usuarios buscarPorUsername(String username) {

        if (username == null) {
            return null;
        }

        for (Usuarios u : listaUsuarios) {

            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }

        return null;
    }


    // BUSCAR POR ID
    public Usuarios buscarPorId(int id) {

        for (Usuarios u : listaUsuarios) {

            if (u.getId() == id) {
                return u;
            }
        }

        return null;
    }


    // CANTIDAD DE USUARIOS
    public int cantidadUsuarios() {

        return listaUsuarios.size();
    }


    // CONSULTAR UBICACIÓN DEL ARCHIVO
    public String obtenerRutaArchivo() {

        return ARCHIVO;
    }
}


// CLASE AUXILIAR PARA MOSTRAR ERRORES
class JOptionPaneHelper {

    public static void mostrarError(String mensaje) {

        javax.swing.JOptionPane.showMessageDialog(
                null,
                mensaje,
                "Error de almacenamiento",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}