/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplitreborn;

/**
 *
 * @author u189299
 */
class SendAction extends Action{
    
    private final String method;
    private String status;
    private Service service;
    private DataNodes datanodes;
    private TKTobj tkt;

    public TKTobj getTkt() {
        return tkt;
    }

    public void setTkt(TKTobj tkt) {
        this.tkt = tkt;
    }

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

    public SendAction(Service s,int i) {
        super(s);
        this.method = "ACTION";
        this.status = "status";
        this.service = s;
        this.tkt = s.getArrayTKT().get(i);
        this.datanodes = new DataNodes(method,tkt);
    }

    

    @Override
    void Ejecute() {
            //ejecuto las acciones x cada tktobj
            System.out.println("send actions...");
            service.run(datanodes);
    }
    
    @Override
    void GetResponse() {
        
        XMLparser parser = new XMLparser(service.getResponse());
                parser.setData("response");
                parser.ReadResult();
        if("error".equals(parser.getResponse().substring(0, 5))){
        
                setStatus(parser.getResponse());
            
        }else{
            
                
                parser.setResponse(service.getResponse());
                parser.setData("result");
                parser.ReadTag();
                setStatus(parser.getResponse());
                if("ok".equals(getStatus())){
                
                save saveLog = new save();
                    String path = null;
                    String tktid = null;
                if("CERRAR_EXT".equals(datanodes.action)){
                  
                     path = "logs/closed.log";
                     tktid = tkt.getIncidentID();
                    
                }else if("STATUS_TKT_EXT".equals(datanodes.action)){
                    if("".equals(tkt.getMaster())){
                     path = "logs/commented.log";
                     tktid = tkt.getIncidentID();
                         }else if ("Closed".equals(tkt.getStatus())){
                    
                        path = "logs/commented.log";
                        tktid = tkt.getIncidentID()+"M";
                    }

                }
                
                System.out.println(path);
                saveLog.file(tktid, path);
                
                }
                
        }
        
    }
    
    
}
