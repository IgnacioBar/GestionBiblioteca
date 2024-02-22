public class Libro {
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private int numEjemplaresTotales;
    private int numEjemplaresDisponibles;

    // Constructor
    public Libro(String titulo, String autor, int anioPublicacion, int numEjemplaresTotales) {
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.numEjemplaresTotales = numEjemplaresTotales;
        this.numEjemplaresDisponibles = numEjemplaresTotales; // Inicialmente todos disponibles
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public void setNumEjemplaresTotales(int numEjemplaresTotales) {
        this.numEjemplaresTotales = numEjemplaresTotales;
    }

    public void setNumEjemplaresDisponibles(int numEjemplaresDisponibles) {
        this.numEjemplaresDisponibles = numEjemplaresDisponibles;
    }

    // Métodos adicionales
    public boolean prestarEjemplar() {
        if (numEjemplaresDisponibles > 0) {
            numEjemplaresDisponibles--;
            return true;
        }
        return false;
    }
}
