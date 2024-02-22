import java.util.ArrayList;

public class Usuario {
    private String nombreUsuario;
    private String password;
    private ArrayList<Libro> librosPrestados;

    // Constructor
    public Usuario(String nombreUsuario, String pasword) {
        this.nombreUsuario = nombreUsuario;
        this.password = pasword;
        this.librosPrestados = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Libro> getLibrosPrestados() {
        return librosPrestados;
    }

    // Métodos para prestar y devolver libros
    public void prestarLibro(Libro libro) {
        librosPrestados.add(libro);
    }

    public boolean devolverLibro(Libro libro) {
        if (librosPrestados.contains(libro)) {
            librosPrestados.remove(libro);
            return true;
        } else {
            System.out.println("El libro no estaba en tu lista de libros prestados.");
            return false;
        }
    }

    public Libro buscarLibroPrestadoPorTitulo(String titulo) {
        for (Libro libro : librosPrestados) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

}
