
package simplitreborn;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas Scordamaglia
 */
public class SimplitReborn {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         try {
            httpServer server = new httpServer();
            server.start();
            
        } catch (Exception ex) {
            Logger.getLogger(SimplitReborn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void start() {
        
        
        
        
        Date now = new Date();
        String date = DateFormat.getInstance().format(now);
        System.out.println("Start ok " + date);
        
       Itracker itracker = new Itracker();
       /** Login **/
       Login login = new Login(itracker);
       
       login.Ejecute();
       login.GetResponse();//me dice si el login se ejecuto correctamente y en caso afirmativo extrae hash. En caso de error lo informa.
       if ("error".equals(login.getStatus().substring(0, 5))){
           
           System.out.println(login.getStatus());
           
       }else{
            /** Generacion del listado de tkt itracker **/
            GetList getlist = new GetList(itracker);
            getlist.Ejecute();
            getlist.GetResponse();
            if("error".equals(getlist.getStatus().subSequence(0, 5))){
                
                        System.out.println(getlist.getStatus());
                
            }else{
            
                        /** Busqueda en Doit **/
                        /** Primero descarto los tktobj que tengan un doit cerrado para evitar consultas innecesarias**/
                        for(Iterator<TKTobj> itertkt = itracker.getArrayTKT().iterator(); itertkt.hasNext();){
                                     TKTobj tkt = itertkt.next();
                                     
                                        
                                                boolean closed = Verify.Closed(tkt.getIncidentID());
                                                System.out.println(closed);
                                                if (closed == true){

                                                itertkt.remove();

                                                }

                                       
                               }
                        Doit doit = new Doit (itracker.getArrayTKT());
                        GetDoitList doitlist = new GetDoitList(doit);
                        doitlist.Ejecute();
                        doitlist.GetResponse();
                        /** Ejecuto las acciones en itracker x cada tktobj **/
                        itracker.setArrayTKT(doit.getArrayTKT());              
                        /** Primero descarto los tktobj que tengan comentarios ya realizados para evitar consultas innecesarias**/
                        for(Iterator<TKTobj> itertkt = itracker.getArrayTKT().iterator(); itertkt.hasNext();){
                                     TKTobj tkt = itertkt.next();
                                     
                                        if(!"Closed".equals(tkt.getStatus())){
                                                boolean commented = Verify.Commented(tkt.getIncidentID());
                                                System.out.println(commented);
                                                if (commented == true){

                                                itertkt.remove();

                                                }

                                       }else if(Verify.Slave(tkt.getId()) == true  ){
                                       
                                                boolean commented = Verify.Commented(tkt.getIncidentID()+"M");
                                                System.out.println(commented);
                                                if (commented == true){

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
            
            }
       }
             
        
    }
}
