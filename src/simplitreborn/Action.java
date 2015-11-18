
package simplitreborn;

/**
 * Clase abstracta de la cual van a extender las acciones que se pueden ejecutar en los diferentes servicios
 * tales como login, listing IT, listing SC, etc
 * @author Nicolas Scordamaglia
 */
abstract class Action {

    public Action(Service s) {
        
        
    }
    
    
    
    void GetResponse(){};
    void Ejecute(){};
    
    
    
}
