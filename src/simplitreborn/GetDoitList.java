/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplitreborn;

import java.util.Iterator;

/**
 *
 * @author u189299
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

    public GetDoitList(Service s) {
        super(s);
        this.method = "DOIT";
        this.status = "status";
        this.service = s;
        this.datanodes = new DataNodes(method);
    }

   @Override
    void Ejecute(){
        
        System.out.println("getdoit list...");
        service.run(datanodes);
        
        
    }
    @Override
    void GetResponse(){
                 
                XMLparser parser = new XMLparser("");
                Iterator<TKTobj> itertkt = service.getArrayTKT().iterator();
               for(Iterator<String> iter = service.getArrayResponse().iterator(); iter.hasNext();){
                                     String rqResponse = iter.next();
                                     TKTobj tkt = itertkt.next();
                                     parser.setResponse(rqResponse);
                                     parser.setData("RetrieveIncidentResponse");
                                     parser.ReadResultSoap();
                 if("9".equals(parser.getResponse())){
                     System.out.println("Error o no existe reclamo");
                                iter.remove();
                                itertkt.remove();

                }
                else{
                   tkt.Update(rqResponse);
                }
           }    

                      
    }
    
}
