
package doitbot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que genera la interfaz contra Doit
 * @author Nicolas Scordamaglia
 */
class Doit extends Service{
    
    private ArrayList<TKTobj> arrayTKT;
    private final String url;
    private String response;
    private ArrayList<String> arrayResponse;

    public ArrayList<String> getArrayResponse() {
        return arrayResponse;
    }

    public void setArrayResponse(ArrayList<String> arrayResponse) {
        this.arrayResponse = arrayResponse;
    }
    
     public ArrayList<TKTobj> getArrayTKT() {
        return arrayTKT;
    }

    public void setArrayTKT(ArrayList<TKTobj> arrayTKT) {
        this.arrayTKT = arrayTKT;
    }

 

    /**
     * Constructor
     * @param arrayTKT 
     */
    Doit(ArrayList<TKTobj> arrayTKT) {
        
        this.arrayTKT = arrayTKT;
        this.url = ConfigManager.getAppSetting("urlProxy");
        this.arrayResponse = new ArrayList<>();
        
    }

    /**
     * Metodo que genera las consultas a Doit
     * @param d 
     */
    @Override
    void run(DataNodes d) {
        /*setResponse("<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Body><RetrieveIncidentResponse message=\"Success\" returnCode=\"0\" schemaRevisionDate=\"2015-10-27\" schemaRevisionLevel=\"1\" status=\"SUCCESS\" xmlns=\"http://schemas.hp.com/SM/7\" xmlns:cmn=\"http://schemas.hp.com/SM/7/Common\" xmlns:xmime=\"http://www.w3.org/2005/05/xmlmime\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://schemas.hp.com/SM/7 http://DWIN0487.appdom.app.telecom.com.ar:13080/SM/7/Incident.xsd\"><model><keys><IncidentID type=\"String\">IM00900247</IncidentID></keys><instance recordid=\"IM00900247 - Prueba Llamada\" uniquequery=\"number=&quot;IM00900247&quot;\"><IncidentID type=\"String\">IM00900247</IncidentID><Category type=\"String\">INCIDENTE</Category><OpenTime type=\"DateTime\">2015-10-13T21:00:14+00:00</OpenTime><OpenedBy type=\"String\">U531820</OpenedBy><FinalPriority type=\"String\">3</FinalPriority><Urgency type=\"String\">3</Urgency><UpdatedTime type=\"DateTime\">2015-10-15T18:06:21+00:00</UpdatedTime><PrimaryAssignmentGroup type=\"String\">APLR_COR_ABUSE</PrimaryAssignmentGroup><ClosedTime type=\"DateTime\">2015-10-15T18:06:20+00:00</ClosedTime><ClosedBy type=\"String\">U531820</ClosedBy><ClosureCode type=\"String\">Con conformidad del usuario</ClosureCode><ConfigurationItem type=\"String\">ABUSE_APL</ConfigurationItem><Description type=\"Array\"><Description type=\"String\">describe falla</Description></Description><Solution type=\"Array\"><Solution type=\"String\">resuelto ok</Solution></Solution><AffectedContact type=\"String\">U186831</AffectedContact><JournalUpdates type=\"Array\"><JournalUpdates type=\"String\">15/10/15 15:05:13 Argentina/Bs.As. (MARCELA GODOY):</JournalUpdates><JournalUpdates type=\"String\">ok</JournalUpdates><JournalUpdates type=\"String\">15/10/15 15:04:58 Argentina/Bs.As. (MARCELA GODOY):</JournalUpdates><JournalUpdates type=\"String\">ok</JournalUpdates><JournalUpdates type=\"String\">13/10/15 18:05:03 Argentina/Bs.As. (MARCELA GODOY):</JournalUpdates><JournalUpdates type=\"String\">revisar AAAA</JournalUpdates></JournalUpdates><Company type=\"String\">TC</Company><BriefDescription type=\"String\">Prueba Llamada</BriefDescription><InteractionID type=\"String\">SD02000357</InteractionID><UpdatedBy type=\"String\">U531820</UpdatedBy><Status type=\"String\">Closed</Status><Phase type=\"String\">Closure</Phase><Subcategory type=\"String\">APLICACION</Subcategory><Area type=\"String\">ERROR EN FUNCIONALIDAD</Area><InitialImpact type=\"String\">3</InitialImpact><Service type=\"String\">SA ABUSE</Service></instance></model></RetrieveIncidentResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>");
        arrayResponse.add(getResponse());*/
        int lenght = arrayTKT.size();
        for(int i=0; i<lenght;i++){
            
            
                    try {
                            
                            //System.out.println("inicio request...");
                            Request rq = new Request(url,"tkt="+arrayTKT.get(i).getIncidentID());
                            rq.SendGet();
                            //System.out.println("response: " + rq.getGetResponse());
                            setResponse(rq.getGetResponse());
                            //agrego la respuesta al array de respuestas doit
                            arrayResponse.add(getResponse());
                            //System.out.println(arrayResponse.get(i).toString());

                    } catch (MalformedURLException ex) {
                        Logger.getLogger(Doit.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Doit.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
         
        }     
    }
    
    
    
}
