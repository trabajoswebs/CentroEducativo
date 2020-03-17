/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profesorampliado;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Johan Manuel
 */
public class FuncionesFicheros {
    
    /**
     * 
     * @param lista
     * @param fichero
     * @return 
     * @throws IOException 
     */
    public static void almacenarColPersonasEnArchivo(ArrayList<Persona> lista, File fichero) throws IOException{
        FileOutputStream fos = null;
        ObjectOutputStream salida = null;
        Persona p;
        
        try {
           if (! fichero.exists()) fichero.createNewFile(); //Si el fichero no existiese se crea 
            //Se crea el fichero
            
            if(! lista.isEmpty()){
                fos = new FileOutputStream(fichero);
                
                Iterator it = lista.iterator();
                
                while(it.hasNext()){
                    salida.writeObject(it.next());
                }
            }
            
            salida.flush();
            
        } catch (FileNotFoundException e) {
            System.out.println("1"+e.getMessage());
        } catch (IOException e) {
            System.out.println("2"+e.getMessage());
        } finally {
            try {
                if(fos !=null) fos.close();
                if(salida !=null) salida.close();
            } catch (IOException e) {
                System.out.println("Error al intenta guardar el fichero serializable " +e.getMessage());
            }
        }
    }

    /**
     * Carga el treeMap con los datos del fichero Persona3.txt
     * @param fichero
     * @return
     * @throws IOException 
     */
    public static ArrayList<Persona> obtenerTreeMapDeArchivo(File fichero) throws IOException {
        ArrayList<Persona> lista = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream entrada = null;
        Persona p;
        
        try{
            if(! fichero.exists()) throw new Exception("El fichero no se encunetra. ");
            
            fis = new FileInputStream(fichero);
            entrada = new ObjectInputStream(fis);
            Persona obj = (Persona) entrada.readObject();
            
            while(obj != null){
                lista.add(obj);
                obj = (Persona) entrada.readObject();
            }
            
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return lista;
    }
}
