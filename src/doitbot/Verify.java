
package doitbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase ue se encarga de verificar la existencia de los tkt en los diferentes archivos
 * @author Nicolas Scordamaglia
 */
class Verify {
    
    
    /**
     * Metodo para buscar en el archivo pasado por parametro
     * @param incidentid
     * @return 
     */
    static boolean Closed(String incidentid) {
        
        
        return Search(incidentid, "logs/closed.log");
        
    }
    /**
     * Metodo para buscar en el archivo pasado por parametro
     * @param incidentid
     * @return 
     */
    static boolean Commented(String incidentid) {
        
        
         return Search(incidentid, "logs/commented.log");
        
    }
    /**
     * Metodo para buscar en el archivo pasado por parametro
     * @param itid
     * @return 
     */
    static boolean Slave(String itid) {
        
        
         return Search(itid, "logs/master.log");
        
    }
    /**
     * Metodo generico para buscar en el archivo pasado por parametro
     * @param tkt
     * @param path
     * @return 
     */
    static boolean Search(String tkt, String path) {
        
        Save logStart = new Save();
        
            System.out.println("verifico el tkt " + tkt + " en el archivo " + path);
            logStart.file("verifico el tkt " + tkt + " en el archivo " + path, "logs/logserver.log");
            
            //si no hay archivo no directamente no esta
                        double count = 0,countBuffer=0,countLine=0;
                        String lineNumber = "";
                        String filePath = path;
                        BufferedReader br;
                        String inputSearch = tkt;
                        String line = "";
                        File file = new File(filePath);
                        

                        try {
                            br = new BufferedReader(new FileReader(file));
                            try {
                                while((line = br.readLine()) != null)
                                {
                                    countLine++;
                                    //System.out.println(line);
                                    String[] words = line.split(" ");

                                    for (String word : words) {
                                      if (word.equals(inputSearch)) {
                                        count++;
                                        countBuffer++;
                                        return true;
                                      }
                                    }

                                    if(countBuffer > 0)
                                    {
                                        countBuffer = 0;
                                        lineNumber += countLine + ",";
                                    }

                                }
                                br.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            //e.printStackTrace();
                            return false;
                        }

                       
            return false;
        
    }
    
}
