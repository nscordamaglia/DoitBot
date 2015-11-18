
package simplitreborn;



/**
 * Clase que se extiende de Action y usa como parametro al servicio que instancia y realizar una determinada accion.
 * @author Nicolas Scordamaglia
 */
class Login extends Action{

    private String user;
    private String pass;
    private final String method;
    private String status;
    private String servicename;
    private Service service;
    private DataNodes datanodes;

        

    public Login(Service s) {
        super(s);
        this.user = configManager.getAppSetting("user");
        this.pass = configManager.getAppSetting("pass");
        this.method = "LOGIN";
        this.servicename = s.getClass().getCanonicalName().toString(); System.out.println(servicename);
        servicename = servicename.replace(".", "-");
        String[] classname = servicename.split("-");
        System.out.println(classname[0]);
        System.out.println(classname[1]);
        this.servicename = classname[1];
        this.status="status";
        this.service = s;
        this.datanodes = new DataNodes(method);
    }
    
    public DataNodes getDatanodes() {
        return datanodes;
    }

    public void setDatanodes(DataNodes datanodes) {
        this.datanodes = datanodes;
    }
    
    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
    
     public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
    void Ejecute(){
        
        System.out.println("login...");
        service.run(datanodes);
        
        
    }
    
    void GetResponse(){
    
        XMLparser parser = new XMLparser(service.getResponse());
        parser.setData("response");
        parser.ReadResult();
        if("error".equals(parser.getResponse().substring(0,5))){
        
            setStatus(parser.getResponse());
            
        }else{
            
            parser.setResponse(service.getResponse());
            parser.setData("hash");
            parser.ReadTag();
            Hash hash = new Hash();
            hash.setValue(parser.getResponse());
            service.setHash(hash);
        }
        /*
         * obtengo un resultado positivo o negativo de la accion ejecutada
         * si hay <error> setStatus("error") - si hay <data> setStatus("ok")
         * login da como resultad el hash
         * getlist tiene que crear la lista de tkt itracker
         * 
        */
    
    
    }
    
    
}