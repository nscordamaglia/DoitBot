
package doitbot;

import java.util.ArrayList;

/**
 * Clase abstracta de donode van a extenderse todos los servicios, en este caso itracker y service center.
 * @author Nicolas Scordamaglia
 */
abstract class Service {
    
    private String response;
    private Hash hash;
    private ArrayList<TKTobj> arrayTKT;
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

   
    
     public Hash getHash() {
        return hash;
    }

    public void setHash(Hash hash) {
        this.hash = hash;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Service() {
    }
    
    
    void run(DataNodes d){};
    
}
