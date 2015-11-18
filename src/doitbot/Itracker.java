
package doitbot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que se extiende de Service y ejecuta los metodos segun la clase Action que lo instancia.
 * @author Nicolas Scordamaglia
 */
class Itracker extends Service{
    
    private final String url;
    private String response;
    private Hash hash;
    private ArrayList<TKTobj> arrayTKT;
    

    public ArrayList<TKTobj> getArrayTKT() {
        return arrayTKT;
    }

    public void setArrayTKT(ArrayList<TKTobj> arrayTKT) {
        this.arrayTKT = arrayTKT;
    }

    public Hash getHash() {
        return hash;
    }

    public void setHash(Hash hash) {
        this.hash = hash;
    }

    

    public Itracker() {
        this.url = ConfigManager.getAppSetting("urlItracker");
        this.response = null;
        this.hash = new Hash();
    }
    
    
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    @Override
    void run(DataNodes d){
        try {
            
            System.out.println("make header...");
            //deberia saber que clase lo invoca para poder pedirle los datos
            MakeHeader header = new MakeHeader(d,hash);
            header.run();
            System.out.println("inicio request...");
            Request rq = new Request(url,header.getUrlParameters());
            rq.SendPost();
            //System.out.println("response: " + rq.getGetResponse());
            setResponse(rq.getGetResponse());
        } catch (MalformedURLException ex) {
            Logger.getLogger(Itracker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Itracker.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
