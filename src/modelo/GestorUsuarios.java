package modelo;

import java.util.ArrayList;

public class GestorUsuarios {

    // Lista que almacena los objetos de usuarios
    private ArrayList<Usuarios> listaUsuarios = new ArrayList<>();

    // Método para agregar un usuario nuevo
    public void agregarUsuario(Usuarios usuario) {

        if (usuario != null) {
            listaUsuarios.add(usuario);
        }
    }

    // Método para obtener todos los usuarios
    public ArrayList<Usuarios> obtenerUsuarios() {

        return new ArrayList<>(listaUsuarios);
    }

    // Método para buscar un usuario mediante su username
    public Usuarios buscarPorUsername(String username) {

        for (Usuarios u : listaUsuarios) {

            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }

        return null;
    }

    // Método para conocer la cantidad de usuarios
    public int cantidadUsuarios() {

        return listaUsuarios.size();
    }
}