
package doitbot;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal
 * @author Nicolas Scordamaglia
 */
public class Doitbot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         try {
            HTTPserver server = new HTTPserver();
            server.start();
            
        } catch (Exception ex) {
            Logger.getLogger(Doitbot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Metodo que se inicia con el scheduling para comenzar las tareas de busqueda y actualizacion de incidentes doit
     */

    static void start() {
        
        
        
        
        Date now = new Date();
        String date = DateFormat.getInstance().format(now);
        Save logServer = new Save();
        SendMail mail = new SendMail();
        System.out.println("Start ok " + date);
        logServer.file("Start ok " + date, "logs/logserver.log");
        
       Itracker itracker = new Itracker();
       /** Login **/
       Login login = new Login(itracker);
       
       login.Ejecute();
       login.GetResponse();//me dice si el login se ejecuto correctamente y en caso afirmativo extrae hash. En caso de error lo informa.
       if ("error".equals(login.getStatus().substring(0, 5))){
           
           System.out.println(login.getStatus());
           logServer.file(login.getStatus(), "logs/logserver.log");
           
       }else{
            /** Generacion del listado de tkt itracker **/
            GetList getlist = new GetList(itracker);
            getlist.Ejecute();
            getlist.GetResponse();
            if("error".equals(getlist.getStatus().subSequence(0, 5))){
                
                        System.out.println(getlist.getStatus());
                        logServer.file(getlist.getStatus(), "logs/logserver.log");
                
            }else{
            
                        /** Busqueda en Doit **/
                        //imprimo la cantidad de tkt vigentes
                        logServer.file("Existen "+ itracker.getArrayTKT().size() + " tickets...", "logs/logserver.log");
                        /** Primero descarto los tktobj que tengan un doit cerrado para evitar consultas innecesarias**/
                        for(Iterator<TKTobj> itertkt = itracker.getArrayTKT().iterator(); itertkt.hasNext();){
                                     TKTobj tkt = itertkt.next();
                                     
                                        
                                                boolean closed = Verify.Closed(tkt.getIncidentID());
                                                //System.out.println(closed);
                                                if (closed == true){

                                                System.out.println("No se volvera a cerrar el tkt " + tkt.getId());
                                                logServer.file("No se volvera a cerrar el tkt " + tkt.getId(), "logs/logserver.log");
                                                itertkt.remove();

                                                }

                                       
                               }
                        Doit doit = new Doit (itracker.getArrayTKT());
                        GetDoitList doitlist = new GetDoitList(doit);
                        doitlist.Ejecute();
                        doitlist.GetResponse();
                        if(!"Error de autenticacion contra Doit".equals(doitlist.getStatus())){
                        /** Ejecuto las acciones en itracker x cada tktobj **/
                        itracker.setArrayTKT(doit.getArrayTKT());  
                        //imprimo la cantidad de tkt luego de quitar los cerrados
                        logServer.file("Quedan "+ itracker.getArrayTKT().size() + " tickets luego del filtro...", "logs/logserver.log");
                        /** Primero descarto los tktobj que tengan comentarios ya realizados para evitar consultas innecesarias **/
                        for(Iterator<TKTobj> itertkt = itracker.getArrayTKT().iterator(); itertkt.hasNext();){
                                     TKTobj tkt = itertkt.next();
                                     
                                        if(!"Closed".equals(tkt.getStatus())){
                                                boolean commented = Verify.Commented(tkt.getIncidentID());
                                                //System.out.println(commented);
                                                if (commented == true){
                                                    
                                                    System.out.println("No se volvera a comentar el tkt " + tkt.getId());
                                                    logServer.file("No se volvera a comentar el tkt " + tkt.getId(), "logs/logserver.log");
                                                    //Guardo en el csv los IM que hayan cambiado de grupo
                                                    logServer.file(tkt.getIncidentID()+";"+tkt.getPrimaryAssignmentGroup()+";"+tkt.getStatus(), "logs/ReporteIM.csv");
                                                    itertkt.remove();

                                                }

                                       }else if(Verify.Slave(tkt.getId()) == true  ){
                                       
                                                boolean commented = Verify.Commented(tkt.getIncidentID()+"M");
                                                //System.out.println(commented);
                                                if (commented == true){

                                                    System.out.println("No se volvera a comentar el tkt " + tkt.getId());
                                                    logServer.file("No se volvera a comentar el tkt " + tkt.getId(), "logs/logserver.log");
                                                    logServer.file(tkt.getIncidentID()+";"+tkt.getPrimaryAssignmentGroup()+";"+tkt.getStatus(), "logs/ReporteIM.csv");
                                                    itertkt.remove();

                                                }
                                       
                                       }
                               }
                        
                        /** Luego recorro el array de tktobj y consulto los estados para ejecutar las acciones **/
                        for (int i = 0; i<itracker.getArrayTKT().size();i++){
                            SendAction sendaction = new SendAction(itracker, i);
                            sendaction.Ejecute();
                            sendaction.GetResponse();
                        }  
                   }else{
                        
                        System.out.println(doitlist.getStatus());
                        logServer.file(doitlist.getStatus(), "logs/logserver.log");
                        
                        }   
            }
       }
        
        
        mail.Ready();
    }
}
