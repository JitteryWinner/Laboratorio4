package modelo;

// La clase Articulo extiende la clase Contenido
public class Articulo extends Contenido {
    private String resumen;

    public Articulo(String titulo, String autor, String resumen) {
        super(titulo, autor);
        this.resumen = resumen;
    }

    @Override
    public String getTipo() {
        return "Artículo";
    }

    @Override
    public void publicarContenido() {
        System.out.println("Se está publicando el artículo: " + getTitulo());
        System.out.println("Se está cargando el texto en los artículos");
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Resumen: " + resumen.substring(0, Math.min(resumen.length(), 20)) + "...";
    }
}