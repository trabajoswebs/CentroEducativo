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
import java.util.logging.Level;
import java.util.logging.Logger;
import static profesorampliado.Cursos.sc;

/**
 *
 * @author Acer
 */
public class SucursalesBancarias {
    
    public static final String directory = "C:\\Users\\Acer\\Documents\\Formacion Profesional Desarrollo Aplicaciones Web\\Practica java\\Practicas Personas\\centroeducativo\\Profesores\\";
    public static final String sucBancFileName = "SucursalesBancarias.txt";
    public static final String sucBancFilePath = directory + sucBancFileName;
    
    
    /**
     * Se da de alta a un curso en el fichero SucursalesBancarias.txt
     */
    public static void altaSucursalBancaria() {
        
        String codEntidadBancaria, codSucurBancaria, nombreSucursalBancaria, cadena, continuar;        
        RandomAccessFile fichero = null; 
        long size = 0;
        int indice;
        boolean repetir = false;
        
        do {
                System.out.println("\nListado de las sucursales bancarias: ");
                System.out.println(imprimeSucursalesBancarias());

            try {
                System.out.println("Introduzca el código de la Sucursal Bancaria:");
                codSucurBancaria = sc.nextLine();
                
                 if (codSucurBancaria.isEmpty()) {
                    throw new Exception("Debe introducir el código de la Sucursal Bancaria.");
                }
                codEntidadBancaria = codSucurBancaria.substring(0, 4); // Obtenemos el código de la Entidad Bancaria
                
                if (! CentroEducativo.getTmEEEE().containsKey(codEntidadBancaria)) { //Comprobamos la existencia del código de la entidad bancaria
                    throw new Exception("La Entidad Bancaria no existe.");
                }
                
                System.out.println("Introduzca el nombre de la Sucursal Bancaria:");
                
                nombreSucursalBancaria = sc.nextLine();                
               
                if (nombreSucursalBancaria.isEmpty()) {
                    throw new Exception("Debe introducir el nombre de la Sucursal Bancaria.");
                }

                TablasCursos.crearFichero(directory, sucBancFileName); //Se crea el fichero si no existe
                
                fichero = new RandomAccessFile(sucBancFilePath, "rw");
                cadena = fichero.readLine();
                
                while(cadena != null){
                    indice = cadena.indexOf(",");
                    if(indice != -1){                        
                        if (cadena.substring(0, indice).equalsIgnoreCase(codSucurBancaria)) //Obtenemos el código del curso
                            throw new Exception("El código de la Sucursal Bancaria ya se encuentra en la lista");
                    }
                    cadena = fichero.readLine();                    
                }                
                size = fichero.length(); 
                fichero.seek(size);// nos situamos al final del fichero
                cadena = codSucurBancaria.toUpperCase() + "," + nombreSucursalBancaria + "\n";
                fichero.writeBytes(cadena);
                
                if (CentroEducativo.getTmEEEESSSS().containsKey(codSucurBancaria)) { //Actualizamos el TreeMap
                    CentroEducativo.getTmEEEESSSS().put(codSucurBancaria, "," + nombreSucursalBancaria + "\n"); // añadimos el la sucursal bancaria del treemap
                } else {
                    CentroEducativo.getTmEEEESSSS().clear();//Eliminamos todos los datos del TreeMap
                    Cuenta.cargaSucursalesBancarias(CentroEducativo.getTmEEEESSSS()); //volcamos los datos del fichero al TreeMap y lo actualizamos
                }
                System.out.println("Se ha añadido correctamente la Sucursal Bancaria en el fichero.");
                System.out.println("Si desea añadir más Sucursales Bancarias al fichero introduzca la letra: \"S\"");
                continuar = sc.nextLine();
                repetir =(continuar.equalsIgnoreCase("S")); //Si se desea continuar añadiendo sucursal bancaria
                
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
                System.out.println("Si desea añadir más Sucursales Bancarias al fichero introduzca la letra: \"S\"");
                continuar = sc.nextLine();
                repetir =(continuar.equalsIgnoreCase("S")); //Si se desea continuar añadiendo sucursal bancaria
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
     * Elimina la sucursal bancaria en el fichero Profesores/SucursalesBancarias.txt
     */
    
    public static void bajaSucursalBancaria(){
        
        File ficheroActualizado = new File(directory + "sucursalesActualizadas.txt");
        String codSucursal, cadena, continuar;
        int indice;
        RandomAccessFile fichero = null;
        RandomAccessFile ficheroNuevo = null;
        boolean repetir = false;
        
       
        do {
            boolean existeCodigo = false;
            
            try {
                System.out.println("\nListado de las sucursales bancarias: ");
                System.out.println(imprimeSucursalesBancarias());
                System.out.print("Indique el código de la sucursal que desea eliminar: ");
                codSucursal = sc.nextLine();
                
                if (codSucursal.trim().isEmpty()) {
                    throw new Exception("Debe introducir un código de asignatura válido.");
                }
                
                if (! CentroEducativo.getTmEEEESSSS().containsKey(codSucursal)) { //Comprobamos la existencia del código del curso
                    throw new Exception("La sucursal no existe.");
                }
                
                fichero = new RandomAccessFile(sucBancFilePath, "r");
                
                if(fichero.length() == 0) throw new Exception("El fichero de las sucursales se encuentra vacio.");
                
                cadena = fichero.readLine();
                
                while(cadena != null){
                    indice = cadena.indexOf(",");
                    if (indice != -1) {
                        if(cadena.substring(0, indice).equalsIgnoreCase(codSucursal)){
                            existeCodigo = true;
                            break; // dejamos de recorrer el fichero saliendo del while
                        } 
                            
                    }                    
                    cadena = fichero.readLine();
                }
                
                if (! existeCodigo) // Si el código no existe en el fichero lanzamos la exepción
                        throw new Exception("El código de la sucursal (" + codSucursal.toUpperCase() + ") que se desea eliminar no existe en el fichero.");
                
                TablasCursos.crearFichero(directory, "sucursalesActualizadas.txt"); //creamos un nuevo fichero
                
                ficheroNuevo = new RandomAccessFile(ficheroActualizado, "rw");
                
                fichero.seek(0); //Llevamos el puntero al inicio
                
                while (cadena != null) {
                    indice = cadena.indexOf(",");
                    if(indice != -1){  
                        if (! cadena.substring(0, indice).equalsIgnoreCase(codSucursal)) {

                            ficheroNuevo.seek(ficheroNuevo.getFilePointer());
                            ficheroNuevo.writeBytes(cadena + "\n");
                        }
                    }
                    cadena = fichero.readLine();
                }
             
                ficheroNuevo.close(); //Cerramos el fichero actualizado
                fichero.close(); //Debemos cerrar el fichero antes de eliminarlo
                
                File ficheroOriginal = new File(directory + "sucursalesActualizadas.txt");
                File destFichero = new File(sucBancFilePath);
                
                 if (destFichero.delete()) { //Borramos el fichero anterior
                     
                        if (ficheroOriginal.renameTo(destFichero)) {//Renombramos el fichero
                            
                            if (CentroEducativo.getTmEEEESSSS().containsKey(codSucursal)) { //Actualizamos el TreeMap
                                CentroEducativo.getTmEEEESSSS().remove(codSucursal); // Eliminamos la sucursal bancaria del treemap
                            }else{
                                CentroEducativo.getTmEEEESSSS().clear();//Eliminamos todos los datos del TreeMap
                                Cuenta.cargaSucursalesBancarias(CentroEducativo.getTmEEEESSSS()); //Si ocurre un error volcamos los datos del fichero al TreeMap y lo actualizamos
                            }

                        System.out.println("Se ha eliminado correctamente la sucursal bancaria  " + codSucursal + " del fichero.");
                        System.out.println("Si desea eliminar más sucursales de la lista introduzca la letra: \"S\"");

                        continuar = sc.nextLine();
                        repetir = (continuar.equalsIgnoreCase("S")); //Si se desea continuar añadiendo cursos

                    } else {
                        throw new Exception("No se ha podido renombrar el archivo " + ficheroActualizado.getName() + " a " + sucBancFileName); 
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
                System.out.println("Si desea eliminar alguna sucursal bancaria de la lista introduzca la letra: \"S\"");
                continuar = sc.nextLine();
                repetir = (continuar.equalsIgnoreCase("S")); //Si se desea continuar añadiendo sucursales bancarias

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
     * Imprime por pantalla Sucursales bancarias que se encuentran en el fichero
     * @return String codigo y nombre de las sucursales bancarias
     */
    
    public static String imprimeSucursalesBancarias() {
        String cadena;
        FileReader fr = null;
        BufferedReader entrada = null;
        StringBuilder cursos = new StringBuilder();
        
        try {            
            fr = new FileReader(sucBancFilePath);
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
}
