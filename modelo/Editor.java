package modelo;

// La clase Editor solo puede crear y editar
public class Editor extends Usuario {
    public Editor(String nombreUsuario) {
        super(nombreUsuario, "Editor");
    }

    @Override
    public boolean puedePublicar() {
        return false;
    }
    @Override
    public boolean puedeEliminar() {
        return false;
    }
    @Override
    public boolean puedeCrearEditar() {
        return true;
    }
}