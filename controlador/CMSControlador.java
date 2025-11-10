package controlador;

import modelo.*; 
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// El Controlador maneja la lógica del programa
public class CMSControlador {
    private List<Contenido> repositorioContenidos;

    public CMSControlador() {
        this.repositorioContenidos = new ArrayList<>();
    }

    public Contenido crearContenido(Usuario usuario, int tipo, String titulo, String autor, String extra) {
        if (!usuario.puedeCrearEditar()) {
            return null;
        }

        Contenido nuevoContenido = null;
        try {
            switch (tipo) {
                case 1: // Artículo
                    nuevoContenido = new Articulo(titulo, autor, extra);
                    break;
                case 2: // Video
                    int duracion = Integer.parseInt(extra);
                    nuevoContenido = new Video(titulo, autor, duracion);
                    break;
                case 3: // Imagen
                    nuevoContenido = new Imagen(titulo, autor, extra);
                    break;
                default:
                    return null; // El tipo es inválido
            }
        } catch (NumberFormatException e) {
            return null;
        }

        if (nuevoContenido != null) {
            repositorioContenidos.add(nuevoContenido);
        }
        return nuevoContenido;
    }

    public boolean editarContenido(Usuario usuario, int idContenido, String nuevoTitulo) {
        if (!usuario.puedeCrearEditar()) {
            return false;
        }

        Contenido contenido = buscarContenidoPorId(idContenido);
        if (contenido != null) {
            contenido.setTitulo(nuevoTitulo);
            return true;
        }
        return false;
    }

    public boolean eliminarContenido(Usuario usuario, int idContenido) {
        if (!usuario.puedeEliminar()) {
            return false;
        }

        return repositorioContenidos.removeIf(c -> c.getId() == idContenido);
    }

    public Contenido publicarContenido(Usuario usuario, int idContenido) {
        if (!usuario.puedePublicar()) {
            return null; 
        }

        Contenido contenido = buscarContenidoPorId(idContenido);

        return contenido; 
    }


    // Buscar contenido por ID
    public Contenido buscarContenidoPorId(int id) {
        return repositorioContenidos.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Filtrar contenido por Tipo 
    public List<Contenido> filtrarPorTipo(String tipo) {
        return repositorioContenidos.stream()
                .filter(c -> c.getTipo().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }

    // Filtrar contenido por Categoría
    public List<Contenido> filtrarPorCategoria(String categoria) {
        return repositorioContenidos.stream()
                .filter(c -> c.getCategorias().contains(categoria))
                .collect(Collectors.toList());
    }

    public List<String> generarReporte() {
        List<String> reporte = new ArrayList<>();
        reporte.add("Reporte de los contenidos publicados (Total: " + repositorioContenidos.size());

        for (Contenido c : repositorioContenidos) {
            reporte.add(c.toString());
        }
        return reporte;
    }
}