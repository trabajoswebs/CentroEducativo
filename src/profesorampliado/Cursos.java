/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profesorampliado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permite crear un fichero, si no esta creado, en la ruta indicada
 * @author Acer
 */
public class Cursos {
    
    public static final String cursoFilePath = "C:\\Users\\Acer\\Documents\\Formacion Profesional Desarrollo Aplicaciones Web\\Practica java\\Practicas Personas\\centroeducativo\\Profesores\\";
    public static final String cursoFileName = "cursos.txt";
    public static final String filePath = cursoFilePath + cursoFileName;
    
    static Scanner sc = new Scanner(System.in);
    
    /**
     * Elimina el código del curso en el fichero Profesores/cursos.txt
     */
    public static void bajaCurso(){
        File ficheroActualizado = new File(cursoFilePath + "cursosActualizados.txt");
        String codCurso, cadena, continuar;
        int indice;
        RandomAccessFile fichero = null;
        RandomAccessFile ficheroNuevo = null;
        boolean repetir = false;
        
       
        do {
            boolean existeCodigo = false;
            
            try {
                System.out.println("\nListado de cursos: ");
                System.out.println(imprimeCursos());
                System.out.print("Indique el código del curso que desea eliminar: ");
                codCurso = sc.nextLine();
                
                if (codCurso.trim().isEmpty()) {
                    throw new Exception("El código del curso es erroneo.");
                }
                fichero = new RandomAccessFile(filePath, "r");
                
                if(fichero.length() == 0) throw new Exception("El fichero de los cursos se encuentra vacio.");
                
                cadena = fichero.readLine();
                
                while(cadena != null){
                    indice = cadena.indexOf(",");
                    if (indice != -1) {
                        if(cadena.substring(0, indice).equalsIgnoreCase(codCurso)){
                            existeCodigo = true;
                            break; // dejamos de recorrer el fichero saliendo del while
                        } 
                            
                    }                    
                    cadena = fichero.readLine();
                }
                
                if (! existeCodigo) // Si el código no existe en el fichero lanzamos la exepción
                        throw new Exception("El código del curso (" + codCurso.toUpperCase() + ") que se desea eliminar no existe en el fichero.");
                
                crearFichero(cursoFilePath, "cursosActualizados.txt"); //creamos un nuevo fichero
                
                ficheroNuevo = new RandomAccessFile(ficheroActualizado, "rw");
                
                fichero.seek(0); //Llevamos el puntero al inicio
                
                while (cadena != null) {
                    indice = cadena.indexOf(",");
                    if(indice != -1){  
                        if (! cadena.substring(0, indice).equalsIgnoreCase(codCurso)) {

                            ficheroNuevo.seek(ficheroNuevo.getFilePointer());
                            ficheroNuevo.writeBytes(cadena + "\n");
                        }
                    }
                    cadena = fichero.readLine();
                }
             
                ficheroNuevo.close(); //Cerramos el fichero actualizado
                fichero.close(); //Debemos cerrar el fichero antes de eliminarlo
                
                File ficheroOriginal = new File(cursoFilePath + "cursosActualizados.txt");
                File destFichero = new File(cursoFilePath + cursoFileName);
                
                 if (destFichero.delete()) { //Borramos el fichero anterior
                     
                        if (ficheroOriginal.renameTo(destFichero)) {//Renombramos el fichero
                    
                        System.out.println("Se ha eliminado correctamente el curso  " + codCurso + " del fichero.");
                        System.out.println("Si desea eliminar más cursos de la lista introduzca la letra: \"S\"");

                        continuar = sc.nextLine();
                        repetir = (continuar.equalsIgnoreCase("S")); //Si se desea continuar añadiendo cursos

                    } else {
                        throw new Exception("No se ha podido renombrar el archivo " + ficheroActualizado.getName() + " a " + cursoFileName); 
                    }

                } else {
                    throw new Exception("No se ha podido eliminar el fichero " + destFichero.getName() + " necesario para reeditarlo.");
                }
                

            } catch (FileNotFoundException ex) {//Si no se encuentra el fichero            
                System.out.println("Ha ocurrido una excepción: " + ex.getMessage());
            }catch(IOException ieo){
                System.out.println("Se ha producido un error: " + ieo.getMessage());
                sc.nextLine();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                sc.nextLine();
                System.out.println("Si desea eliminar algún curso de la lista introduzca la letra: \"S\"");
                continuar = sc.nextLine();
                repetir = (continuar.equalsIgnoreCase("S")); //Si se desea continuar añadiendo cursos

            } finally {
                try {
                    if (fichero != null) {
                        fichero.close();
                    }
                    if (ficheroNuevo != null) {
                        ficheroNuevo.close();
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        } while (repetir);

    }
    /**
     * Imprime por pantalla los cursos que se encuentran en el fichero
     * @return String codigo y nombre de los cursos
     */
    
    public static String imprimeCursos() {
        String cadena;
        FileReader fr = null;
        BufferedReader entrada = null;
        StringBuilder cursos = new StringBuilder();
        try {            
            fr = new FileReader(filePath);
            entrada = new BufferedReader(fr);
            cadena = entrada.readLine();
            while(cadena != null){
                cursos.append(cadena);
                cursos.append("\n");
                cadena = entrada.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cursos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cursos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Cursos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return cursos.toString();
    }

    /**
     * Se da de alta a un curso en el fichero cursos.txt
     */
    public static void altaCurso() {        
        String codCurso, nombreCurso, cadena, continuar;        
        RandomAccessFile fichero = null; 
        long size = 0;
        int indice;
        boolean repetir = false;
        
        do {
            System.out.println("Introduzca el código del Curso:");
            codCurso = sc.nextLine();
            System.out.println("Introduzca el nombre del Curso:");
            nombreCurso = sc.nextLine();
            
            try {
                if(codCurso.isEmpty()) throw new Exception("Debe introducir el código del curso.");
                if(nombreCurso.isEmpty()) throw new Exception("Debe introducir el nombre del curso.");
                
                crearFichero(cursoFilePath, cursoFileName);
                fichero = new RandomAccessFile(filePath, "rw");
                cadena = fichero.readLine();
                
                while(cadena != null){
                    indice = cadena.indexOf(",");
                    if(indice != -1){                        
                        if (cadena.substring(0, indice).equalsIgnoreCase(codCurso)) //Obtenemos el código del curso
                            throw new Exception("El código del curso ya se encuentra en la lista");
                    }
                    cadena = fichero.readLine();                    
                }                
                size = fichero.length(); 
                fichero.seek(size);// nos situamos al final del fichero
                cadena = codCurso.toUpperCase() + "," + nombreCurso + "\n";
                fichero.writeBytes(cadena);
                System.out.println("Se ha añadido correctamente el curso en el fichero.");
                System.out.println("Si desea añadir más cursos al fichero introduzca la letra: \"S\"");
                continuar = sc.nextLine();
                repetir =(continuar.equalsIgnoreCase("S")); //Si se desea continuar añadiendo cursos
                
            } catch (FileNotFoundException ex) {     //Si no se encuentra el fichero            
                System.out.println("Ha ocurrido una excepción: " + ex.getMessage());
                sc.nextLine();
            } catch (IOException ex) {
                repetir = true;
                Logger.getLogger("Ha ocurrido una excepción: " + Cursos.class.getName()).log(Level.SEVERE, null, ex);
                sc.nextLine();
            } catch (Exception ex) {                
                repetir = true;
                System.out.println("Ha ocurrido una excepción: " + ex.getMessage());
                sc.nextLine();
                System.out.println("Si desea añadir más cursos al fichero introduzca la letra: \"S\"");
                continuar = sc.nextLine();
                repetir =(continuar.equalsIgnoreCase("S")); //Si se desea continuar añadiendo cursos
            } finally{
                if (fichero != null) {
                    try {
                        fichero.close();
                    } catch (IOException ex) {
                        Logger.getLogger("Ha ocurrido una excepción: " +Cursos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } while (repetir);

    }
    
    /**
     * Crea el fichero, pasado por parametro, si no existe
     * @param ruta
     * @param fichero
     * @throws IOException 
     */
    public static void crearFichero(String ruta, String fichero) throws IOException {
        File cursoRuta = new File(ruta);
        File cursoFichero = new File(cursoRuta, fichero);
        
        if (!cursoFichero.exists()) {

            System.out.println("El fichero " + cursoFichero.getAbsolutePath() + " no existe.");

            if (!cursoRuta.exists()) {
                System.out.println("El directorio " + cursoRuta.getAbsolutePath() + " no existe.");

                if (cursoRuta.mkdir()) {
                    System.out.println("Se ha creado el directorio " + cursoRuta.getAbsolutePath());

                    if (cursoFichero.createNewFile()) {
                        System.out.println("Se ha creado el fichero " + cursoFichero.getName());
                    } else {
                        throw new IOException("No se ha podido crear el fichero " + cursoFichero.getName());
                    }

                } else {
                    throw new IOException("No se ha podido crear la ruta " + cursoRuta.getAbsolutePath());
                }
            } else {

                if (cursoFichero.createNewFile()) {
                    System.out.println("Se ha creado el fichero " + cursoFichero.getName());
                } else {
                    throw new IOException("No se ha podido crear el fichero " + cursoFichero.getName());
                }

            }
        }
    }
    
}
