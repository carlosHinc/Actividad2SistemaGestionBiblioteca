/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package actividad2sistemagestionbiblioteca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author CARLOS
 */
public class Actividad2SistemaGestionBiblioteca {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String[]> libros = new ArrayList<>(); // Array
        LinkedList<String[]> usuarios = new LinkedList<>(); // Lista
        Stack<String[]> librosPrestados = new Stack<>(); // pila
        Queue<String[]> colaEspera = new LinkedList<>();
        
        
        Scanner entrada = new Scanner(System.in);
        
        int opcion;
        
        do {
            mostrarOpcionesMenu();
            opcion = validarOpcionMenu(entrada);
            entrada.hasNextLine();
            
            switch (opcion) {
                case 1:
                    ejecucionOpcion1(entrada, libros);
                    break;
                case 2:
                    ejecucionOpcion2(entrada, usuarios);
                    break;
                case 3:
                    ejecucionOpcion3(entrada, libros, usuarios, librosPrestados, colaEspera);
                    break;
                case 4:
                    ejecucionOpcion4(entrada, libros, librosPrestados);
                    break;
                 case 5:
                     mostrarLibrosDisponibles(libros);
                    break;
                case 6:
                    mostrarUsuariosRegistrados(usuarios);
                    break;
                case 7:
                    System.out.println("Chao, saliendo del sistema");
                    break;
                default: 
                     System.out.println("Opción no valida. Intente de nuevo");
                     break;
            }
            
        } while (opcion != 7);
        
    }
    
    
    public static void mostrarOpcionesMenu(){
        System.out.println("========================================");
        System.out.println("    SISTEMA DE GESTIÓN DE BIBLIOTECAS   ");
        System.out.println("========================================");
        System.out.println("1. Agregar Libro");
        System.out.println("2. Registrar Usuario");
        System.out.println("3. Prestar libro"); 
        System.out.println("4. Devolver libro");
        System.out.println("5. Mostrar libros disponibles"); 
        System.out.println("6. Mostrar usuarios registrados");
        System.out.println("7. Salir"); 
        System.out.println("Seleccione una opción");
    }
    
    
    public static int validarOpcionMenu(Scanner entrada){
       while(!entrada.hasNextInt()){
            System.out.println("Error: Ingrese un número valido!!");
            entrada.next();
            System.out.println("Seleccione una opción");

        }    
        return entrada.nextInt();
    }
    
    public static void mostrarUsuariosRegistrados(LinkedList<String[]> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        System.out.println("Usuarios registrados:");
        for (String[] usuario : usuarios) {
            System.out.println("Cédula: " + usuario[0] + ", Nombre: " + usuario[1] + ", Apellido: " + usuario[2]);
        }
    }
    
    public static void mostrarLibrosDisponibles(ArrayList<String[]> libros) {
        if (libros.isEmpty()) {
            System.out.println("No hay libros disponibles.");
            return;
        }

        System.out.println("Libros disponibles:");
        for (String[] libro : libros) {
            System.out.println("ID: " + libro[0] + ", Nombre: " + libro[1] + ", Autor: " + libro[2]);
        }
    }
    
    public static void ejecucionOpcion4(Scanner entrada, ArrayList<String[]> libros, Stack<String[]> librosPrestados) {
        if (librosPrestados.isEmpty()) {
            System.out.println("No hay libros prestados.");
            return;
        }

        System.out.println("Ingrese el ID del libro a devolver:");
        String idLibro = entrada.nextLine();

        String[] libroDevuelto = null;
        for (int i = 0; i < librosPrestados.size(); i++) {
            String[] libro = librosPrestados.get(i);
            if (libro[0].equals(idLibro)) {
                libroDevuelto = libro;
                librosPrestados.remove(i);
                break;
            }
        }

        if (libroDevuelto != null) {
            System.out.println("Libro devuelto con éxito: " + libroDevuelto[1]);
        } else {
            System.out.println("Error: Libro no encontrado en los libros prestados.");
        }
    }
    
    public static void ejecucionOpcion3(Scanner entrada, ArrayList<String[]> libros, LinkedList<String[]> usuarios,
                                      Stack<String[]> librosPrestados, Queue<String[]> colaEspera) {
        System.out.println("Ingrese el ID del libro a prestar:");
        String idLibro = entrada.nextLine();

        // Buscar libro
        String[] libro = buscarLibroPorId(idLibro, libros);
        if (libro != null) {
            System.out.println("Ingrese la cedula del usuario:");
            int cedulaUsuario = obtenerCedulaValidada(entrada);

            // Validar si el usuario está registrado
            boolean usuarioRegistrado = validarUsuarioRegistrado(cedulaUsuario, usuarios);

            if (usuarioRegistrado) {
                librosPrestados.push(libro);
                System.out.println("Libro prestado con éxito: " + libro[1]);
            } else {
                System.out.println("Error: Usuario no registrado.");
                // Agregar a cola de espera (opcional)
                // colaEspera.add(libro);
            }
        } else {
            System.out.println("Error: Libro no encontrado.");
        }
    }
    
    public static String[] buscarLibroPorId(String idLibro, ArrayList<String[]> libros) {
        for (String[] libro : libros) {
            if (libro[0].equals(idLibro)) {
                return libro;
            }
        }
        return null; // Libro no encontrado
    }
    
    public static boolean validarUsuarioRegistrado(int cedulaUsuario, LinkedList<String[]> usuarios) {
        for (String[] usuario : usuarios) {
            if (Integer.parseInt(usuario[0]) == cedulaUsuario) {
                return true;
            }
        }
        return false; // Usuario no registrado
    }
    
    public static void ejecucionOpcion2(Scanner entrada,  LinkedList<String[]> usuarios){
        
        int cedulaUsuario = obtenerCedulaValidada(entrada);
        entrada.nextLine();
        
        boolean cedulaDuplicada = validarUsuarioDuplicado(cedulaUsuario, usuarios);
        
        if(cedulaDuplicada){
             System.out.println("Error: El usuario ya existe"); 
        } else {
            guardarUsuario(cedulaUsuario, usuarios, entrada);
        }
    }
    
    
    public static int obtenerCedulaValidada(Scanner entrada){
        System.out.println("Ingrese la cedula del usuario (solo número): "); 
        
        while(!entrada.hasNextInt()){
            System.out.println("Error: Ingrese un número valido!!"); 
            entrada.next();  
            System.out.println("Ingrese la cedula del usuario (solo con números): "); 
        }
        
        return entrada.nextInt();
    }
    
    public static void guardarUsuario(int cedulaUsuario, LinkedList<String[]> usuarios, Scanner entrada){
        System.out.println("Ingrese el nombre del usuario"); 
        String nombreUsuario = entrada.nextLine();
        System.out.println("Ingrese los apellidos del usuario"); 
        String apellidoUsuario = entrada.nextLine();
        usuarios.add(new String[]{String.valueOf(cedulaUsuario), nombreUsuario, apellidoUsuario});
        System.out.println("Usuario registrado exitosamente"); 
    }
    
    
    public static boolean validarUsuarioDuplicado(int cedulaUsuario, LinkedList<String[]> usuarios){
       boolean cedulaDuplicada = false;
        for(String[] usuario: usuarios){
            if(usuario[0].equals(String.valueOf(cedulaUsuario))){
                cedulaDuplicada = true;
                break;
            }
        }
        return cedulaDuplicada;
    }
    
    
    public static void ejecucionOpcion1(Scanner entrada, ArrayList<String[]> libros){
        System.out.println("Ingrese el ID el libro (Unico) "); 
        String idLibro = entrada.nextLine();
        boolean idDuplicado = validarLibroDuplicado(idLibro, libros);

        if(idDuplicado){
             System.out.println("Error: El ID del libro ya existe"); 
        } else {
            guardarLibro(idLibro, entrada, libros);    
        }
    }
    
    public static void guardarLibro(String idLibro, Scanner entrada, ArrayList<String[]> libros){
        System.out.println("Ingrese el nombre del libro"); 
        String nombreLibro = entrada.nextLine();
        System.out.println("Ingrese el autor del libro");
        String autorLibro = entrada.nextLine();
        libros.add(new String[]{idLibro, nombreLibro, autorLibro});
        System.out.println("Libro agregado con exito");
    }
    
    public static boolean validarLibroDuplicado(String idLibro, ArrayList<String[]> libros){
        boolean idDuplicado = false;
        for(String[] libro: libros){
            if(libro[0].equals(idLibro)){
                idDuplicado = true;
                break;
            }
        }
        return idDuplicado;
    }
    
}
