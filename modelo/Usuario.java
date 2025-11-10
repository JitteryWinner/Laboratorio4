package modelo;

//Es la clase base para definir los roles del usuario (Administrador / Editor)
public abstract class Usuario {
    protected String nombreUsuario;
    protected String rol;

    public Usuario(String nombreUsuario, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    public String getNombreUsuario(){
        return nombreUsuario; 
    }
    public String getRol() {
        return rol;
    }

    // Son los métodos para verificar los permisos
    public abstract boolean puedePublicar();
    public abstract boolean puedeEliminar();
    public abstract boolean puedeCrearEditar();
}