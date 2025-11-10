import controlador.CMSControlador;
import modelo.Administrador;
import modelo.Contenido;
import modelo.Editor;
import modelo.Usuario;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Usuario login(String nombre, String rolDeseado) {
        String nombreLimpio = nombre.trim();
        
        if (nombreLimpio.isEmpty()) {
            System.out.println("Hubo un error. El nombre del usuario no puede estar vacío.");
            return null;
        }

        if (rolDeseado.equalsIgnoreCase("admin")) {
            return new Administrador(nombreLimpio); 
        } else if (rolDeseado.equalsIgnoreCase("editor")) {
            return new Editor(nombreLimpio);
        }
        
        System.out.println("Hubo un error. El Rol '" + rolDeseado + "' es inválido. Debe ser 'admin' o 'editor'.");
        return null;
    }

    private static void mostrarListaContenidos(List<Contenido> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay contenidos para mostrar.");
            return;
        }
        System.out.println("\n--- LISTA DE CONTENIDOS ---");
        for (Contenido c : lista) {
            System.out.println("  > " + c.toString()); 
        }
        System.out.println("---------------------------\n");
    }

    public static void main(String[] args) {
        CMSControlador controller = new CMSControlador();
        Scanner scanner = new Scanner(System.in);
        Usuario usuarioActual = null;
        boolean corriendo = true;

        System.out.println("--- Bienvenido al Sistema de Gestión de Contenidos de EGA ---");

        // --- SIMULACIÓN DE DATOS INICIALES ---
        Usuario adminInicial = new Administrador("Setup"); 
        
        try {
            Contenido art = controller.crearContenido(adminInicial, 1, "Guía de programación a objetos", "Profesor", "Un resumen sobre lo más importante de la Programación Orientada a Objetos.");
            if (art != null) art.agregarCategoria("Educación");
            Contenido vid = controller.crearContenido(adminInicial, 2, "Taller de programación", "Auxiliares", "2000"); 
            if (vid != null) vid.agregarCategoria("Tutorial");
            Contenido img = controller.crearContenido(adminInicial, 3, "Logo del EGA", "Diseñador Gráfico", "JPG");
            if (img != null) img.agregarCategoria("Diseño");
            System.out.println("\nDatos de ejemplo cargados por SetupAdmin.");
        } catch (Exception e) {
            System.out.println("\nHubo un error Error. Lo sentimos, ocurrió un fallo inesperado en la carga de datos iniciales: " + e.getMessage());
        }

        while (corriendo) {

            while (usuarioActual == null) {
                System.out.println("\n--- Inicio de sesion ---");
                System.out.print("Ingrese su nombre de usuario: ");
                String nombreDeseado = scanner.nextLine();
                
                System.out.print("Ingrese su rol (admin/editor): ");
                String rolDeseado = scanner.nextLine();
                
                usuarioActual = login(nombreDeseado, rolDeseado); 

                if (usuarioActual != null) {
                    System.out.println("\n¡Bienvenido, " + usuarioActual.getNombreUsuario() + " (" + usuarioActual.getRol() + ")!");
                }
            }

            System.out.println("\n--- Menu principal ---");
            System.out.println("1. Crear contenido");
            System.out.println("2. Editar el título de un contenido");
            System.out.println("3. Eliminar un contenido (admin)");
            System.out.println("4. Publicar/Visualizar contenido");
            System.out.println("5. Filtrar contenido por tipo");
            System.out.println("6. Filtrar contenido por categoría");
            System.out.println("7. Generar reporte");
            System.out.println("8. Salir del programa");
            System.out.println("9. Cambiar de Usuario/Rol"); 
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1: // Crear Contenido
                        if (!usuarioActual.puedeCrearEditar()) {
                            System.out.println("Hubo un error. Solo Editores y Administradores pueden crear contenido.");
                            break;
                        }
                        System.out.println("\n--- Crear contenido ---");
                        System.out.print("Tipo (1 = Artículo, 2 = Video, 3 = Imagen): ");
                        int tipo = Integer.parseInt(scanner.nextLine());
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();

                        String extraPrompt = "";
                        if (tipo == 1) extraPrompt = "Resumen: ";
                        else if (tipo == 2) extraPrompt = "Duración en segundos: ";
                        else if (tipo == 3) extraPrompt = "Formato de imagen: ";

                        System.out.print(extraPrompt);
                        String extra = scanner.nextLine();
                        
                        // Llama al controlador y evalúa el resultado
                        Contenido c = controller.crearContenido(usuarioActual, tipo, titulo, autor, extra);
                        if (c != null) {
                            System.out.println("Hubo éxito " + c.getTipo() + " creado con ID " + c.getId());
                            System.out.print("Categoría (opcional): ");
                            String cat = scanner.nextLine();
                            if (!cat.isEmpty()) {
                                c.agregarCategoria(cat);
                                System.out.println("(INFO) Categoría agregada.");
                            }
                        } else {
                            System.out.println("Hubo un error. No se pudo crear el contenido. Verifique sus requisitos.");
                        }
                        break;

                    case 2: // Editar Contenido
                         if (!usuarioActual.puedeCrearEditar()) {
                            System.out.println("Hubo un error. Solo Editores y Administradores pueden editar.");
                            break;
                        }
                        System.out.print("\nIngrese el ID del contenido que quiere editar: ");
                        int idEdit = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nuevo título: ");
                        String nuevoTitulo = scanner.nextLine();
                        
                        // Llama al controlador y evalúa el resultado
                        if (controller.editarContenido(usuarioActual, idEdit, nuevoTitulo)) {
                             System.out.println("Hubo éxito. El contenido ID " + idEdit + " fue editado. Nuevo título: " + nuevoTitulo);
                        } else {
                             System.out.println("Hubo un error. El contenido ID " + idEdit + " no pudo ser encontrado o no tiene permisos.");
                        }
                        break;

                    case 3: // Eliminar Contenido
                         if (!usuarioActual.puedeEliminar()) {
                            System.out.println("Hubo un error. Solo los Administradores pueden eliminar un contenido.");
                            break;
                        }
                        System.out.print("\nIngrese el ID del contenido a eliminar: ");
                        int idDelete = Integer.parseInt(scanner.nextLine());

                        if (controller.eliminarContenido(usuarioActual, idDelete)) {
                            System.out.println("Hubo éxito. El contenido ID " + idDelete + " fue eliminado con éxito.");
                        } else {
                            System.out.println("Hubo un error. El Contenido ID " + idDelete + " no pudo ser encontrado.");
                        }
                        break;

                    case 4:
                        System.out.print("\nIngrese el ID del contenido que quiere publicar: ");
                        int idPublicar = Integer.parseInt(scanner.nextLine());

                        Contenido contenidoAPublicar = controller.publicarContenido(usuarioActual, idPublicar); 
                        
                        if (contenidoAPublicar != null) {
                            contenidoAPublicar.publicarContenido(); 
                        } else if (!usuarioActual.puedePublicar()) {
                            System.out.println("Hubo un error. Solo los Administradores pueden publicar contenido.");
                        } else {
                            System.out.println("Hubo un error. El contenido ID " + idPublicar + " no fue encontrado para publicar.");
                        }
                        break;
                        
                    case 5: // Filtrar por Tipo
                        System.out.print("\nFiltrar por tipo (Artículo, Video, Imagen): ");
                        String tipoFiltro = scanner.nextLine();
                        List<Contenido> porTipo = controller.filtrarPorTipo(tipoFiltro);
                        mostrarListaContenidos(porTipo);
                        break;
                        
                    case 6: // Filtrar por Categoría
                        System.out.print("\nFiltrar por categoría (Deporte, Ciencia, etc): ");
                        String catFiltro = scanner.nextLine();
                        List<Contenido> porCat = controller.filtrarPorCategoria(catFiltro);
                        mostrarListaContenidos(porCat);
                        break;

                    case 7: // Generar Reporte
                        List<String> reporte = controller.generarReporte();
                        for (String linea : reporte) {
                            System.out.println(linea);
                        }
                        break;

                    case 8: // Salir del Programa
                        corriendo = false;
                        System.out.println("Saliendo del Sistema de Gestión de Contenidos.");
                        break;
                        
                    case 9: // Cambiar de Usuario/Rol
                        System.out.println("\n--- Cerrando la sesión ---");
                        usuarioActual = null; 
                        break;

                    default:
                        System.out.println("Hubo un error. Opción inválida. Intente de nuevo, por favor.");
                }
                
                if (opcion != 9 && opcion != 8) {
                    System.out.print("\nPresione ENTER para continuar, por favor.");
                    scanner.nextLine();
                }

            } catch (NumberFormatException e) {
                System.out.println("Hubo un error. Entrada inválida. Por favor, ingrese un número.");
            } catch (Exception e) {
                System.out.println("Hubo un error. Lo sentimos, ocurrió un error inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }
}