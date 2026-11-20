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

        cargarUsuarios();
    }

    // AGREGAR USUARIO
    public void agregarUsuario(Usuarios usuario)
            throws IOException {

        if (usuario != null) {

            listaUsuarios.add(usuario);

            try {

                guardarUsuarios();

            } catch (IOException e) {

                listaUsuarios.remove(usuario);

                throw e;
            }
        }
    }

    // ELIMINAR USUARIO
    public boolean eliminarUsuario(int id)
            throws IOException {

        for (int i = 0; i < listaUsuarios.size(); i++) {

            Usuarios usuario = listaUsuarios.get(i);

            if (usuario.getId() == id) {

                listaUsuarios.remove(i);

                try {

                    guardarUsuarios();

                    return true;

                } catch (IOException e) {

                    // Restaurar si ocurre un error
                    listaUsuarios.add(i, usuario);

                    throw e;
                }
            }
        }

        return false;
    }

    // ACTUALIZAR USUARIO
    public boolean actualizarUsuario(Usuarios usuario)
            throws IOException {

        if (usuario == null) {
            return false;
        }

        for (Usuarios existente : listaUsuarios) {

            if (existente.getId() == usuario.getId()) {

                // El objeto ya fue actualizado
                // Guardamos los cambios en el archivo
                guardarUsuarios();

                return true;
            }
        }

        return false;
    }

    // GUARDAR USUARIOS
    private void guardarUsuarios() throws IOException {

        Files.createDirectories(Paths.get(CARPETA));

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

    // RECUPERAR USUARIOS
    @SuppressWarnings("unchecked")
    private void cargarUsuarios() {

        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {

            System.out.println(
                    "No existe un archivo anterior."
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

    // OBTENER USUARIOS
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

    // UBICACIÓN DEL ARCHIVO
    public String obtenerRutaArchivo() {

        return ARCHIVO;
    }
}

// CLASE AUXILIAR
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