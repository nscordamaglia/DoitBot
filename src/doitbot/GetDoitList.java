
package doitbot;

import java.util.Iterator;

/**
 * Clase que se extiende de Action y usa como parametro al servicio que instancia y realizar una determinada accion.
 * @author Nicolas Scordamaglia
 */
class GetDoitList extends Action{
    
    private final String method;
    private String status;
    private Service service;
    private DataNodes datanodes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public DataNodes getDatanodes() {
        return datanodes;
    }

    public void setDatanodes(DataNodes datanodes) {
        this.datanodes = datanodes;
    }

    /**
     * Constructor
     * @param s 
     */
    public GetDoitList(Service s) {
        super(s);
        this.method = "DOIT";
        this.status = "status";
        this.service = s;
        this.datanodes = new DataNodes(method);
    }

    /**
     * Metodo que usa el servicio instanciado como interfaz contra el sitio remoto del sistema
     */
   @Override
    void Ejecute(){
        
        System.out.println("getdoit list...");
        Save logServer = new Save();
            logServer.file("Connection Doit...", "logs/logserver.log");
        service.run(datanodes);
        
        
    }
   /**
    * Metodo que interpreta la respuesta del sitio remoto
    */
    @Override
    void GetResponse(){
                 
                XMLparser parser = new XMLparser("");
                Iterator<TKTobj> itertkt = service.getArrayTKT().iterator();
               for(Iterator<String> iter = service.getArrayResponse().iterator(); iter.hasNext();){
                                     String rqResponse = iter.next();
                                     TKTobj tkt = itertkt.next();
                                     
                  if(!"HTML".equals(rqResponse.substring(1, 5))){
                                                    parser.setResponse(rqResponse);
                                                    parser.setData("RetrieveIncidentResponse");
                                                    parser.ReadResultSoap();
                                if("9".equals(parser.getResponse())){
                                    System.out.println("El incidente " + tkt.getIncidentID() + " no existe en Doit");
                                    Save logServer = new Save();
                                    logServer.file("El incidente " + tkt.getIncidentID() + " no existe en Doit", "logs/logserver.log");
                                               iter.remove();
                                               itertkt.remove();

                               }
                               else{
                                  tkt.Update(rqResponse);
                               }
                  }else{
                  
                      System.out.println("Error de autenticacion contra Doit");
                      setStatus("Error de autenticacion contra Doit");
                                               break;
                      
                  }
           }    

                      
    }
    
}
