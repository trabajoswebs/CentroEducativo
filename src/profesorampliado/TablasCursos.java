/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profesorampliado;

import java.util.TreeMap;

/**
 *
 * @author Acer
 */
public class TablasCursos {
    
    public static final String directory = "C:\\Users\\Acer\\Documents\\Formacion Profesional Desarrollo Aplicaciones Web\\Practica java\\Practicas Personas\\profesoresyAlumnos\\Profesores\\";
    
    static void cargaCursos(TreeMap<String, String> tmCC){
        tmCC.put("1E", "SMR Mañana");
        tmCC.put("1G", "SMR Tarde");
        tmCC.put("1F", "SMR Tarde");
        tmCC.put("1W", "DAW Tarde");
        tmCC.put("1S", "DAW Mañana");
    }
    static void cargaCursosAsignaturas(TreeMap<String, String> tmCCASIGNA){
        tmCCASIGNA.put("1EREDES", "Redes de Área Local");
        tmCCASIGNA.put("1EOFIMA", "Ofimática");
        tmCCASIGNA.put("1ESISOP", "Sistemas Operativos");
        tmCCASIGNA.put("1EFOL", "Formación y Orientación Laboral");
        tmCCASIGNA.put("1EINGLES", "Inglés");
        tmCCASIGNA.put("1GREDES", "Redes de Área Local");
        tmCCASIGNA.put("1GOFIMA", "Ofimática");
        tmCCASIGNA.put("1GSISOP", "Sistemas Operativos");
        tmCCASIGNA.put("1GFOL", "Formación y Orientación Laboral");
        tmCCASIGNA.put("1FREDES", "Redes de Área Local");
        tmCCASIGNA.put("1FOFIMA", "Ofimática");
        tmCCASIGNA.put("1FSISOP", "Sistemas Operativos");
        tmCCASIGNA.put("1FFOL", "Formación y Orientación Laboral");
        tmCCASIGNA.put("1FINGLES", "Inglés");
        tmCCASIGNA.put("1WPROGRAM", "Programación");
        tmCCASIGNA.put("1WLENGMAR", "Lenguajes de Marcas");
        tmCCASIGNA.put("1WENTORNOS", "Entornos de Desarrollo");
        tmCCASIGNA.put("1WSISOPE", "Sistemas Operativos");
        tmCCASIGNA.put("1WFOL", "Formación y Orientación Laboral");
        tmCCASIGNA.put("1WINGLES", "Inlés");
        tmCCASIGNA.put("1SPROGRAM", "Programación");
        tmCCASIGNA.put("1SLENGMAR", "Lenguajes de Marcas");
        tmCCASIGNA.put("1SENTORNOS", "Entornos de Desarrollo");
        tmCCASIGNA.put("1SSISOPE", "Sistemas Operativos");
        tmCCASIGNA.put("1SFOL", "Formación y Orientación Laboral");
        tmCCASIGNA.put("1SINGLES", "Inlés");
    }
}

