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
import java.util.TreeMap;

/**
 *
 * @author Acer
 */
public class TablasCursos {
    public static final String CAFilePath = "C:\\Users\\Acer\\Documents\\Formacion Profesional Desarrollo Aplicaciones Web\\Practica java\\Practicas Personas\\centroeducativo\\Profesores\\CursoAsignatura.txt";
    
    
    static void cargaCursos(TreeMap<String, String> tmCC){
        cargaDatos(Cursos.filePath, CentroEducativo.tmCC);
    }
    
    
    static void cargaCursosAsignaturas(TreeMap<String, String> tmCCASIGNA){
        cargaDatos(CAFilePath, CentroEducativo.tmCCASIGNA);
    }
    
    /**
     * Lee los datos que se encuentra en el fichero y los carga en el treeMap
     * @param filePath
     * @param tm
     */
    public static void cargaDatos(String filePath, TreeMap<String, String> tm){
        FileReader fr = null;
        BufferedReader entrada = null;
        int indice = 0;
        String cadena, key, value;
        
        try {
            fr = new FileReader(filePath);
            entrada = new BufferedReader(fr);
            
            cadena = entrada.readLine();
            
            while(cadena != null){
                indice = cadena.indexOf(",");
                if (indice != -1) {
                    key = cadena.substring(0, indice).toUpperCase();
                    value = cadena.substring(indice + 1 ).toUpperCase();
                    tm.put(key, value);
                }
                cadena = entrada.readLine();
            }
        }
        catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        catch (IOException ioe) {
            System.out.println("Ha ocurrido una excepción: " + ioe.getMessage());    
        }
        catch (Exception e) {
            System.out.println("Ha ocurrido una excepción: " + e.getMessage());   
        }finally{
            try {
                if (fr != null) {
                    fr.close();
                }
                if(entrada != null){
                    entrada.close();
                }
            } catch (IOException e) {
                System.out.println("Se ha producido un error al intentar cerrar el fichero: " + e.getMessage());
            }
        }
    }
    
    
     /**
     * Crea el fichero, pasado por parametro, si no existe
     * @param ruta direccion de la ubicacion del fichero
     * @param fichero nombre del fichero
     * @throws IOException 
     */
    public static void crearFichero(String ruta, String fichero) throws IOException {
        File cursoRuta = new File(ruta);
        File cursoFichero = new File(cursoRuta, fichero);
        
        if (! cursoFichero.exists()) {

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

