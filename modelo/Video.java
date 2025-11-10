package modelo;

// La clase Video extiende la clase Contenido
public class Video extends Contenido {
    private int duracionSegundos;

    public Video(String titulo, String autor, int duracionSegundos) {
        super(titulo, autor);
        this.duracionSegundos = duracionSegundos;
    }

    @Override
    public String getTipo() {
        return "Video";
    }

    @Override
    public void publicarContenido() {
        System.out.println("Se está publicando el video: " + getTitulo());
        System.out.println("Se está cargando reproductor de video. La duración es de: " + duracionSegundos + " segundos.");
    }

    public void setDuracionSegundos(int duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }
}