/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package doitbot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author u189299
 */
class Save {
    
     /**
             * Metodo que recibe el texto y la ruta para poder guardar en archivo segun se requiera
             * @param data
             * @param path 
             */
    
            public void file(String data, String path){
                
            
            //String headerCSV = "TKT iTracker;TKT Simplit;Grupo Simplit;Estado del TKT Simplit;Fecha de Apertura;Fecha de Cierre;Texto de apertura;Texto de cierre;Fecha de consulta";
            Date now = new Date();
            String dataExtended = data +" - "+ DateFormat.getInstance().format(now);
                
                //creo el archivo para guardar la hash para otro momento
                            File file = null;
                            FileWriter filew = null;
                            PrintWriter pw = null;
                            try
                            {
                                
                                file = new File(path);/*  destino de fichero */
                                
                                filew = new FileWriter(path,true);
                                pw = new PrintWriter(filew);
                                
                                if ("logs/reporting.csv".equals(path)||"logs/reportingHist.csv".equals(path) ){
                                    
                                pw.println(data);
                                
                                }else{
                                
                                    pw.println(dataExtended);
                                
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                               try {
                               // Nuevamente aprovechamos el finally para 
                               // asegurarnos que se cierra el fichero.
                               if (null != filew)
                                  filew.close();
                               } catch (Exception e2) {
                                  e2.printStackTrace();
                               }
                            }
                
            
            }
            
            /**
             * Metodo que verifica la existencia del archivo para evaluar su creacion de ser necesario
             * @param path
             * @throws IOException 
             */
            public void Exist(String path) throws IOException {
                
                 String headerCSV = "TKT iTracker;TKT Simplit;Grupo Simplit;Estado del TKT Simplit;Fecha de Apertura;Fecha de Cierre;Texto de apertura;Texto de cierre;Fecha de consulta";
                 File file = null;
                 File oldFile = null;
                 file = new File(path);/*  destino de fichero */
                 oldFile = new File("logs/reportingHist.csv");
                
                if (path == "logs/reporting.csv"){
                
                                        //si no existe lo creo con la cabecera de columnas CSV
                                        if(!file.exists()){
                                                file.createNewFile();
                                                this.file(headerCSV, path);
                                                oldFile.createNewFile();
                                                this.file(headerCSV, "logs/reportingHist.csv");
                                                
                                        }else{
                                            
                                           //lo renombro
                                            file.renameTo(oldFile);
                                            file.delete();
                                            file.createNewFile();
                                            this.file(headerCSV, path);
                                            
                                        }
                                        
                
                                }else if (path == "logs/master.log"){
                                
                                        //si existe lo borro, ya que el master debe ser unico por cada tarea realizada
                                        if(file.exists()){
                                                file.delete();  
                                                
                                        }
                                       
                                }else{
                                
                                
                                }
            
        
            
            }
}
