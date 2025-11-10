package modelo;

// La clase Imagen extiende la clase Contenido
public class Imagen extends Contenido {
    private String formato;

    public Imagen(String titulo, String autor, String formato) {
        super(titulo, autor);
        this.formato = formato;
    }

    @Override
    public String getTipo() {
        return "Imagen";
    }

    @Override
    public void publicarContenido() {
        System.out.println("Se está publicando la imagen: " + getTitulo());
        System.out.println("La imgen en formato " + formato + " se cargó.");
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}