
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
