package modelo;

import java.util.ArrayList;
import java.util.List;

// Esta clase implementa la interfaz publicable
public abstract class Contenido implements Publicable {
    protected int id;
    protected String titulo;
    protected String autor;
    protected List<String> categorias;

    private static int nextId = 1;

    public Contenido(String titulo, String autor) {
        this.id = nextId++;
        this.titulo = titulo;
        this.autor = autor;
        this.categorias = new ArrayList<>();
    }

    public abstract String getTipo();

    public int getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getAutor() {
        return autor;
    }
    public List<String> getCategorias() {
        return categorias;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void agregarCategoria(String categoria) {
        if (!categorias.contains(categoria)) {
            categorias.add(categoria);
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Título: " + titulo + ", Autor: " + autor + ", Tipo: " + getTipo() + ", Categorías: " + categorias;
    }
}