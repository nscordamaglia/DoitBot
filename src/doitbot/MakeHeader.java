
package doitbot;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Clase que se encarga de completar el xml con los nodos y los valores correspondientes para completar los parametros de consulta
 * contra el servicio al que estan invocados
 * @author Nicolas Scordamaglia
 */
class MakeHeader {
    private String urlParameters;
    private Document document;
    private DataNodes d;
    private Hash hash;
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public MakeHeader(DataNodes d,Hash hash) {
        
        this.hash = hash;
        this.d = d;
        System.out.println("creo xml");
        toXML();
    }
    
    

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
    
    

    public String getUrlParameters() {
        return urlParameters;
    }

    public void setUrlParameters(String urlParameters) {
        this.urlParameters = urlParameters;
    }
    
    /**
     * Metodo que genera la estructura xml
     */
    void run(){
        
        String[] dataheader;
        String[] nodes;
        String[] datanodes;
        
         
            //edito los nodos cabecera
            dataheader = d.editHeader.split(";"); //System.out.println("creo el header");
            document.getElementsByTagName("hash").item(0).setTextContent(hash.getValue());//System.out.println(hash.getValue());
            document.getElementsByTagName("class").item(0).setTextContent(dataheader[1]);
            document.getElementsByTagName("method").item(0).setTextContent(dataheader[2]);
            
            //inserto los nodos de la accion y edito los nodos insertados
            nodes = d.createNodes.split(";");
            datanodes = d.editNodesData.split(";");
            
            for (int i = 0; i<nodes.length; i++){
            
                //System.out.println("creo los nodos");
                if("actions".equals(nodes[i])){
                   // System.out.println("nodo " + nodes[i] + " valor " + datanodes[i]);
                    setAction(datanodes[i]);
                }
                Node node = document.createElement(nodes[i]);
                document.getElementsByTagName("params").item(0).appendChild(node);
                if(i==1){
                node.setTextContent(datanodes[i].replace(",", ";"));
                //System.out.println("nodo " + nodes[i] + " valor " + datanodes[i].replace(",", ";"));
                }else{
                node.setTextContent(datanodes[i]);
                }
            }
                       
            //setAction(document.getElementsByTagName("actions").getLength());
            toTXT(getDocument());
            

           
        
    }
    
    /**
     * Metodo que toma el archivo xml para su edicion
     */
   private void toXML (){
       //creo el xml
       Document document = null;
        try {    
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            File file = new File(ConfigManager.getAppSetting("urlXML"));
            document = documentBuilder.parse(file);
            setDocument(document);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MakeHeader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(MakeHeader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MakeHeader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   /**
    * Metodo que toma el documento editado para pasarlo a string y ser enviado como parametro
    * @param document 
    */
   private void toTXT (Document document){
        try {
            //paso a string para imprimirlo
               DOMSource domSource = new DOMSource(document);
               StringWriter writer = new StringWriter();
               StreamResult result = new StreamResult(writer);
               TransformerFactory tf = TransformerFactory.newInstance();
               Transformer transformer = tf.newTransformer();
               transformer.transform(domSource, result);

              
               urlParameters = writer.toString();
               setUrlParameters(urlParameters);
               
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(MakeHeader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(MakeHeader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
