package modelo;

import java.util.ArrayList;

public class GestorUsuarios {
    // Lista estática en memoria para almacenar los usuarios registrados
    private static ArrayList<Usuarios> listaUsuarios = new ArrayList<>();

    // Método para agregar un nuevo usuario a la lista
    public static void agregarUsuario(Usuarios usuario) {
        listaUsuarios.add(usuario);
    }

    // Método para obtener la lista completa (Ideal para mostrarla en el 1er botón "Ver usuarios")
    public static ArrayList<Usuarios> obtenerUsuarios() {
        return listaUsuarios;
    }

    // Método para buscar un usuario por su username
    public static Usuarios buscarPorUsername(String username) {
        for (Usuarios u : listaUsuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null; // Retorna nulo si no lo encuentra
    }
}