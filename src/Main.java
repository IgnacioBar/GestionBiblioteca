import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca(new Usuario("admin", "admin"));
        int opcion = 0;

        do {
            try {
                System.out.println("\nLibros Registrados: " + biblioteca.getListaLibros().size());

                System.out.println("\nLibros Prestados: " + biblioteca.getNumeroLibrosPrestados());

                System.out.println("\nUsuarios Registrados: " + biblioteca.getListaUsuarios().size());

                System.out.println("\nBienvenido a la Biblioteca");
                System.out.println("\n1. Registrar libro");
                System.out.println("2. Prestar libro");
                System.out.println("3. Devolver libro");
                System.out.println("4. Buscar libro por título");
                System.out.println("5. Registrar usuario");
                System.out.println("6. Eliminar libro");
                System.out.println("7. Eliminar usuario");
                System.out.println("8. Salir");
                System.out.print("\nSelecciona una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Por favor, introduce un número válido.");
                scanner.next();
            }

            switch (opcion) {
                case 1:
                    // Registrar libro
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Autor: ");
                    String autor = scanner.nextLine();
                    System.out.print("Año de Publicación: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Por favor, introduce un año válido.");
                        scanner.next();
                    }
                    int anio = scanner.nextInt();
                    System.out.print("Número de Ejemplares: ");
                    int ejemplares = scanner.nextInt();
                    Libro libro = new Libro(titulo, autor, anio, ejemplares);
                    biblioteca.agregarLibro(titulo, autor, anio, ejemplares);
                    System.out.println("Libro registrado con éxito.");
                    break;
                case 2:
                    // Prestar libro
                    System.out.print("Título del libro a prestar: ");
                    titulo = scanner.nextLine();
                    Libro libroPrestamo = biblioteca.buscarLibroPorTitulo(titulo);
                    if (libroPrestamo != null) {
                        System.out.print("Nombre de usuario: ");
                        String nombreUsuarioPrestamo = scanner.nextLine();
                        System.out.print("Contraseña: ");
                        String contrasenaUsuarioPrestamo = scanner.nextLine();
                        Usuario usuarioPrestamo = biblioteca.autenticarUsuario(nombreUsuarioPrestamo, contrasenaUsuarioPrestamo);
                        if (usuarioPrestamo != null) {
                            if (biblioteca.prestarLibro(titulo, usuarioPrestamo)) {
                                System.out.println("Libro prestado con éxito.");
                            } else {
                                System.out.println("No se pudo prestar el libro.");
                            }
                        } else {
                            System.out.println("Usuario no encontrado o contraseña incorrecta.");
                        }
                    } else {
                        System.out.println("El libro no se encuentra en la biblioteca.");
                    }
                    break;

                case 3:
                    // Devolver libro
                    System.out.print("Título del libro a devolver: ");
                    String tituloDevolucion = scanner.nextLine();
                    Libro libroDevolucion = biblioteca.buscarLibroPorTitulo(tituloDevolucion);
                    if (libroDevolucion != null) {
                        System.out.print("Nombre de usuario: ");
                        String nombreUsuarioDevolucion = scanner.nextLine();
                        System.out.print("Contraseña: ");
                        String contrasenaUsuarioDevolucion = scanner.nextLine();
                        Usuario usuarioDevolucion = biblioteca.autenticarUsuario(nombreUsuarioDevolucion, contrasenaUsuarioDevolucion);
                        if (usuarioDevolucion != null) {
                            if (biblioteca.devolverLibro(tituloDevolucion, usuarioDevolucion)) {
                                System.out.println("Libro devuelto con éxito.");
                            } else {
                                System.out.println("No se pudo devolver el libro.");
                            }
                        } else {
                            System.out.println("Usuario no encontrado o contraseña incorrecta.");
                        }
                    } else {
                        System.out.println("El libro no se encuentra en la biblioteca.");
                    }
                    break;

                case 4:
                    // Buscar libro por título
                    System.out.print("Título del libro: ");
                    titulo = scanner.nextLine();
                    Libro buscado = biblioteca.buscarLibro(titulo);
                    if (buscado != null) {
                        System.out.println("Libro encontrado: " + buscado.getTitulo() + " por " + buscado.getAutor());
                    } else {
                        System.out.println("Libro no encontrado.");
                    }
                    break;

                case 5:
                    // Registrar usuario
                    System.out.print("Nombre de usuario: ");
                    String nombreUsuario = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String contrasena = scanner.nextLine();
                    Usuario usuario = new Usuario(nombreUsuario, contrasena);
                    biblioteca.registrarUsuario(usuario);
                    System.out.println("Usuario registrado con éxito.");
                    break;

                case 6:
                    // Eliminar libro
                    System.out.print("Título del libro a eliminar: ");
                    String tituloEliminar = scanner.nextLine();
                    biblioteca.eliminarLibro(tituloEliminar);
                    break;

                case 7:
                    // Eliminar usuario
                    System.out.print("Nombre de usuario a eliminar: ");
                    String nombreUsuarioEliminar = scanner.nextLine();
                    biblioteca.eliminarUsuario(nombreUsuarioEliminar);
                    break;

                case 8:
                    // Ver listas
                    mostrarListas(biblioteca);
                    break;

                case 9:
                    System.out.println("Saliendo de la Biblioteca. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opcion != 9);

        scanner.close();
    }

    private static void mostrarListas(Biblioteca biblioteca) {
        System.out.println("\nLista de Libros:");
        for (Libro libro : biblioteca.getListaLibros()) {
            System.out.println(libro.getTitulo());
        }

        System.out.println("\nLista de Libros Prestados:");
        for (Usuario usuario : biblioteca.getListaUsuarios()) {
            System.out.println("Usuario: " + usuario.getNombreUsuario());
            for (Libro libro : usuario.getLibrosPrestados()) {
                System.out.println("   - " + libro.getTitulo());
            }
        }

        System.out.println("\nLista de Usuarios:");
        for (Usuario usuario : biblioteca.getListaUsuarios()) {
            System.out.println(usuario.getNombreUsuario());
        }
    }
}
