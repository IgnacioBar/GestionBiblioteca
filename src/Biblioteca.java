import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Libro> listaLibros;
    private ArrayList<Usuario> listaUsuarios;
    private Usuario administrador;

    // Constructor
    public Biblioteca(Usuario administrador) {
        this.administrador = administrador;
        this.listaLibros = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
    }

    // Métodos para gestionar libros
    public void agregarLibro(String titulo, String autor, int anioPublicacion, int numEjemplaresTotales) {
        for (int i = 0; i < numEjemplaresTotales; i++) {
            Libro nuevoLibro = new Libro(titulo, autor, anioPublicacion, numEjemplaresTotales);
            listaLibros.add(nuevoLibro);
        }
        System.out.println("Libro registrado con éxito.");
    }

    public Libro buscarLibro(String titulo) {
        for (Libro libro : listaLibros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

    // Métodos para gestionar usuarios
    public void registrarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    public Usuario autenticarUsuario(String nombreUsuario, String pasword) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.getPassword().equals(pasword)) {
                return usuario;
            }
        }
        return null;
    }

    // Métodos para prestar y devolver libros
    public boolean prestarLibro(String titulo, Usuario usuario) {
        Libro libro = buscarLibroPorTitulo(titulo);
        if (libro != null && libro.prestarEjemplar()) {
            usuario.prestarLibro(libro);
            listaLibros.remove(libro); // Retirar el libro de la lista general

            System.out.println("Libro prestado con éxito.");
            return true;
        } else {
            System.out.println("No se pudo prestar el libro.");
            return false;
        }
    }

    public boolean devolverLibro(String titulo, Usuario usuario) {
        Libro libroDevuelto = usuario.buscarLibroPrestadoPorTitulo(titulo);

        if (libroDevuelto != null && usuario.devolverLibro(libroDevuelto)) {
            listaLibros.add(libroDevuelto);  // Agregar el libro devuelto a la lista general
            System.out.println("Libro devuelto con éxito.");
            return true;
        } else {
            System.out.println("No se pudo devolver el libro.");
            return false;
        }
    }


    // Métodos para buscar libros o usuarios
    public Libro buscarLibroPorTitulo(String titulo) {
        for (Libro libro : listaLibros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        return null;
    }

    public ArrayList<Libro> buscarLibrosPorAutor(String autor) {
        ArrayList<Libro> librosAutor = new ArrayList<>();
        for (Libro libro : listaLibros) {
            if (libro.getAutor().equalsIgnoreCase(autor)) {
                librosAutor.add(libro);
            }
        }
        return librosAutor;
    }

    public Usuario buscarUsuarioPorNombre(String nombreUsuario) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    // Método para eliminar un libro o un usuario
    public boolean eliminarLibro(String titulo) {
        Libro libroAEliminar = buscarLibroPorTitulo(titulo);

        if (libroAEliminar != null) {
            listaLibros.remove(libroAEliminar);
            System.out.println("Libro eliminado con éxito.");
            return true;
        } else {
            System.out.println("No se encontró el libro en la biblioteca.");
            return false;
        }
    }

    public boolean eliminarUsuario(String nombreUsuario) {
        Usuario usuarioAEliminar = buscarUsuarioPorNombre(nombreUsuario);

        if (usuarioAEliminar != null) {
            listaUsuarios.remove(usuarioAEliminar);
            System.out.println("Usuario eliminado con éxito.");
            return true;
        } else {
            System.out.println("No se encontró el usuario en la biblioteca.");
            return false;
        }
    }

    public ArrayList<Libro> getListaLibros() {
        return listaLibros;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public Usuario getAdministrador() {
        return administrador;
    }

    public int getNumeroLibrosPrestados() {
        int totalLibrosPrestados = 0;

        for (Usuario usuario : listaUsuarios) {
            totalLibrosPrestados += usuario.getLibrosPrestados().size();
        }

        return totalLibrosPrestados;
    }
}
