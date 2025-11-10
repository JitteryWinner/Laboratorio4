package modelo;

// La clase Administrador puede publicar y eliminar
public class Administrador extends Usuario {
    public Administrador(String nombreUsuario) {
        super(nombreUsuario, "Administrador");
    }

    @Override
    public boolean puedePublicar() { return true; }
    @Override
    public boolean puedeEliminar() { return true; }
    @Override
    public boolean puedeCrearEditar() { return true; }
}