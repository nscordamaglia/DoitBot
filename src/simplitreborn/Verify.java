/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplitreborn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author u189299
 */
class Verify {
    
    

    static boolean Closed(String incidentid) {
        
        
        return Search(incidentid, "logs/closed.log");
        
    }
    static boolean Commented(String incidentid) {
        
        
         return Search(incidentid, "logs/commented.log");
        
    }
    static boolean Slave(String itid) {
        
        
         return Search(itid, "logs/master.log");
        
    }
    static boolean Search(String tkt, String path) {
        
        save logStart = new save();
        
            System.out.println("verifico en el archivo " +  path);
            logStart.file("verifico en el archivo" + path, "logs/logserver.log");
            
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
