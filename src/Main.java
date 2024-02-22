import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean infoMostrada = false;

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca(new Usuario("admin", "admin")); // Usuario administrador
        int opcion;

        do {
            // Mostrar información solo la primera vez
            if (!infoMostrada) {
                mostrarInformacionInicial(biblioteca);
                infoMostrada = true;
            }

            // Mostrar menú principal
            mostrarMenuPrincipal();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            // Procesar la opción del menú principal
            procesarOpcionMenuPrincipal(opcion, biblioteca);

        } while (opcion != 9); // Cambiado de 8 a 9 (opción de salir)

        scanner.close();
    }

    private static void procesarOpcionMenuPrincipal(int opcion, Biblioteca biblioteca) {
        switch (opcion) {
            case 1:
                // Registrar libro
                registrarLibro(biblioteca);
                break;

            case 2:
                // Prestar libro
                prestarLibro(biblioteca);
                break;

            case 3:
                // Devolver libro
                devolverLibro(biblioteca);
                break;

            case 4:
                // Buscar libro por título
                buscarLibroPorTitulo(biblioteca);
                break;

            case 5:
                // Registrar usuario
                registrarUsuario(biblioteca);
                break;

            case 6:
                // Opciones de eliminar
                System.out.println("\nOpciones de Eliminar");
                System.out.println("\n1. Eliminar libro");
                System.out.println("2. Eliminar usuario");
                System.out.println("3. Volver al menú principal");
                System.out.print("\nSelecciona una opción: ");
                int opcionEliminar = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (opcionEliminar) {
                    case 1:
                        // Eliminar libro
                        System.out.print("Título del libro a eliminar: ");
                        String tituloEliminar = scanner.nextLine();
                        biblioteca.eliminarLibro(tituloEliminar);
                        break;

                    case 2:
                        // Eliminar usuario
                        System.out.print("Nombre de usuario a eliminar: ");
                        String nombreUsuarioEliminar = scanner.nextLine();
                        biblioteca.eliminarUsuario(nombreUsuarioEliminar);
                        break;

                    case 3:
                        System.out.println("Volviendo al menú principal.");
                        break;

                    default:
                        System.out.println("Opción no válida. Volviendo al menú principal.");
                        break;
                }
                break;

            case 7:
                // Opciones de modificar
                System.out.println("\nOpciones de Modificar");
                System.out.println("1. Modificar libro");
                System.out.println("2. Modificar usuario");
                System.out.println("3. Volver al menú principal");
                System.out.print("\nSelecciona una opción: ");
                int opcionModificar = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (opcionModificar) {
                    case 1:
                        // Modificar libro
                        System.out.print("Título del libro a modificar: ");
                        String tituloModificar = scanner.nextLine();
                        modificarLibro(biblioteca, tituloModificar);
                        break;

                    case 2:
                        // Modificar usuario
                        System.out.print("Nombre de usuario a modificar: ");
                        String nombreUsuarioModificar = scanner.nextLine();
                        modificarUsuario(biblioteca, nombreUsuarioModificar);
                        break;

                    case 3:
                        System.out.println("Volviendo al menú principal.");
                        break;

                    default:
                        System.out.println("Opción no válida. Volviendo al menú principal.");
                        break;
                }
                break;

            case 8:
                // Ver listas
                mostrarListas(biblioteca);
                break;

            case 9:
                System.out.println("Saliendo de la Biblioteca. ¡Hasta luego!");
                break;

            default:
                System.out.println("Opción no válida. Inténtalo de nuevo.");
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\nMenú Principal");
        System.out.println("1. Registrar libro");
        System.out.println("2. Prestar libro");
        System.out.println("3. Devolver libro");
        System.out.println("4. Buscar libro por título");
        System.out.println("5. Registrar usuario");
        System.out.println("6. Eliminar");
        System.out.println("7. Modificar");
        System.out.println("8. Ver listas");
        System.out.println("9. Salir");
        System.out.print("\nSelecciona una opción: ");
    }

    private static void mostrarInformacionInicial(Biblioteca biblioteca) {
        System.out.println("\nLibros Registrados: " + biblioteca.getListaLibros().size());
        System.out.println("Usuarios Registrados: " + biblioteca.getListaUsuarios().size());
        System.out.println("Libros Prestados: " + biblioteca.getListaUsuarios().stream()
                .mapToInt(usuario -> usuario.getLibrosPrestados().size()).sum());
    }

    private static void registrarLibro(Biblioteca biblioteca) {
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
    }

    private static void prestarLibro(Biblioteca biblioteca) {
        System.out.print("Título del libro a prestar: ");
        String titulo = scanner.nextLine();
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
    }

    private static void devolverLibro(Biblioteca biblioteca) {
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
    }

    private static void buscarLibroPorTitulo(Biblioteca biblioteca) {
        System.out.print("Título del libro: ");
        String titulo = scanner.nextLine();
        Libro buscado = biblioteca.buscarLibro(titulo);
        if (buscado != null) {
            System.out.println("Libro encontrado: " + buscado.getTitulo() + " por " + buscado.getAutor());
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private static void registrarUsuario(Biblioteca biblioteca) {
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        Usuario usuario = new Usuario(nombreUsuario, contrasena);
        biblioteca.registrarUsuario(usuario);
        System.out.println("Usuario registrado con éxito.");
    }

    private static void modificarLibro(Biblioteca biblioteca, String titulo) {
        Libro libro = biblioteca.buscarLibroPorTitulo(titulo);

        if (libro != null) {
            System.out.println("\n¿Qué deseas modificar para el libro '" + titulo + "'?");
            System.out.println("1. Autor");
            System.out.println("2. Año de publicación");
            System.out.println("3. Número de ejemplares totales");
            System.out.print("\nSelecciona una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Nuevo autor: ");
                    String nuevoAutor = scanner.nextLine();
                    libro.setAutor(nuevoAutor);
                    System.out.println("Autor modificado con éxito.");
                    break;

                case 2:
                    System.out.print("Nuevo año de publicación: ");
                    int nuevoAnio = scanner.nextInt();
                    libro.setAnioPublicacion(nuevoAnio);
                    System.out.println("Año de publicación modificado con éxito.");
                    break;

                case 3:
                    System.out.print("Nuevo número de ejemplares totales: ");
                    int nuevoNumEjemplares = scanner.nextInt();
                    libro.setNumEjemplaresTotales(nuevoNumEjemplares);
                    libro.setNumEjemplaresDisponibles(nuevoNumEjemplares); // Puedes ajustar según tus reglas
                    System.out.println("Número de ejemplares totales modificado con éxito.");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private static void modificarUsuario(Biblioteca biblioteca, String nombreUsuario) {
        Usuario usuario = biblioteca.buscarUsuarioPorNombre(nombreUsuario);

        if (usuario != null) {
            System.out.println("¿Qué deseas modificar para el usuario '" + nombreUsuario + "'?");
            System.out.println("1. Contraseña");
            System.out.println("2. Lista de libros prestados");
            System.out.print("Selecciona una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.print("Nueva contraseña: ");
                    String nuevaPassword = scanner.nextLine();
                    usuario.setPassword(nuevaPassword);
                    System.out.println("Contraseña modificada con éxito.");
                    break;

                case 2:
                    System.out.println("\n¿Qué deseas hacer con la lista de libros prestados?");
                    System.out.println("1. Agregar libro");
                    System.out.println("2. Quitar libro");
                    System.out.println("3. Volver al menú principal");
                    System.out.print("\nSelecciona una opción: ");

                    int opcionListaLibros = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea

                    switch (opcionListaLibros) {
                        case 1:
                            // Agregar libro a la lista de libros prestados
                            System.out.print("Título del libro a agregar: ");
                            String tituloAgregar = scanner.nextLine();
                            Libro libroAgregar = biblioteca.buscarLibroPorTitulo(tituloAgregar);

                            if (libroAgregar != null) {
                                usuario.prestarLibro(libroAgregar);
                                System.out.println("Libro prestado con éxito.");
                            } else {
                                System.out.println("Libro no encontrado.");
                            }
                            break;

                        case 2:
                            // Quitar libro de la lista de libros prestados
                            System.out.print("Título del libro a quitar: ");
                            String tituloQuitar = scanner.nextLine();
                            Libro libroQuitar = biblioteca.buscarLibroPorTitulo(tituloQuitar);

                            if (libroQuitar != null) {
                                usuario.devolverLibro(libroQuitar);
                                System.out.println("Libro devuelto con éxito.");
                            } else {
                                System.out.println("Libro no encontrado en la lista de libros prestados.");
                            }
                            break;

                        case 3:
                            System.out.println("Volviendo al menú principal.");
                            break;

                        default:
                            System.out.println("Opción no válida.");
                    }
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private static void mostrarListas(Biblioteca biblioteca) {
        System.out.println("=================================");
        System.out.println("         Lista de Libros:");
        System.out.println("=================================");
        for (Libro libro : biblioteca.getListaLibros()) {
            System.out.println("   - " + libro.getTitulo() + " escrito por: " + libro.getAutor());
        }

        System.out.println("=================================");
        System.out.println("     Lista de Libros Prestados:");
        System.out.println("=================================");
        for (Usuario usuario : biblioteca.getListaUsuarios()) {
            System.out.println("Usuario: " + usuario.getNombreUsuario());
            for (Libro libro : usuario.getLibrosPrestados()) {
                System.out.println("   - " + libro.getTitulo());
            }
        }

        System.out.println("=================================");
        System.out.println("         Lista de Usuarios:");
        System.out.println("=================================");
        for (Usuario usuario : biblioteca.getListaUsuarios()) {
            System.out.println("   - " + usuario.getNombreUsuario());
        }
        System.out.println("=================================");
    }

}