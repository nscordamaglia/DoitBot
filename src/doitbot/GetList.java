
package doitbot;

/**
 * Clase que se extiende de Action y usa como parametro al servicio que instancia y realizar una determinada accion.
 * @author Nicolas Scordamaglia
 */
class GetList extends Action{
    
    private final String method;
    private String status;
    private Service service;
    private DataNodes datanodes;

    /**
     * Constructor
     * @param s 
     */
    public GetList(Service s) {
        super(s);
        this.method = "LISTING";
        this.status = "status";
        this.service = s;
        this.datanodes = new DataNodes(method);
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
    /**
    * Metodo que interpreta la respuesta del sitio remoto
    */
    @Override
    void Ejecute(){
        
        System.out.println("getlist...");
        Save logServer = new Save();
            logServer.file("Connection webApp...", "logs/logserver.log");
        service.run(datanodes);
        
        
    }
    /**
    * Metodo que interpreta la respuesta del sitio remoto
    */
    @Override
    void GetResponse(){
    
                XMLparser parser = new XMLparser(service.getResponse());
                parser.setData("response");
                parser.ReadResult();
        if("error".equals(parser.getResponse().substring(0, 5))){
        
                setStatus(parser.getResponse());
            
        }else{
                try{
                parser.setResponse(service.getResponse());
                parser.setData("tkt/ths/th/itform/element/value");
                service.setArrayTKT(parser.webAppList());
                }catch (NullPointerException n){setStatus(parser.getResponse());}
        }
        
        
        
        
        /*
         * obtengo un resultado positivo o negativo de la accion ejecutada
         * si hay <error> setStatus("error") - si hay <data> setStatus("ok")
         * login da como resultad el hash
         * getlist tiene que crear la lista de tkt webApp
         * 
        */
    
    
    }
}
