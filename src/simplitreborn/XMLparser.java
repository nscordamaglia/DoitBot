
package simplitreborn;

import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Clase que entiende como leer el xml_response que viene como respuesta al request http contra cada servicio0
 * @author Nicolas Scordamaglia
 */
class XMLparser {
    
    private String data;
    private String response;
    

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    XMLparser(String r) {
        
        this.response = r;
        
    }
    
    
    
    void ReadTag(){
      
             
             Node r = SearchTag(response, data);
             //System.out.println(" tag: " + r.getTextContent());
             //extraigo el tag d del result r
             try{
                 setResponse(r.getTextContent());
             }catch (NullPointerException n){setResponse("sin datos");}
             
       
       
    
    }
    
    Node SearchTag(String response, String data){
        
        Node node1 = null;
        try {
            //creo el documento desde la lista de ticket devuelta
                 DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                 documentBuilderFactory.setNamespaceAware(false);
                 DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                 //transformo el string para poder generar el documento
                 InputSource is = new InputSource(new StringReader(response));
                 Document document = documentBuilder.parse(is);
                 XPath Path1 = XPathFactory.newInstance().newXPath();
                 XPathExpression exp1 = Path1.compile("//"+ data);
                 NodeList nl1 = (NodeList) exp1.evaluate(document, XPathConstants.NODESET);
                 save logStart = new save();
                 logStart.file("Get "+ nl1.getLength() + " tickets...", "logs/logserver.log");
                 node1 = nl1.item(0);
                 
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLparser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLparser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLparser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XMLparser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex){
                  
        }
        
        
            return node1;
        
        
    
    }

    ArrayList<TKTobj> itrackerList() {
        
        ArrayList<String> tktattr = new ArrayList<>();
        ArrayList<TKTobj> tktarray = new ArrayList<>();
        
        
        try {
            //creo el documento desde la lista de ticket devuelta
             DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
             DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
             
             //transformo el string para poder generar el documento
             InputSource is = new InputSource(new StringReader(response));
             Document document = documentBuilder.parse(is);
                    
             
             XPath Path1 = XPathFactory.newInstance().newXPath();
             XPathExpression exp1 = Path1.compile("//"+ data);
             NodeList nl1 = (NodeList) exp1.evaluate(document, XPathConstants.NODESET);
             save logStart = new save();
             logStart.file("Get "+ nl1.getLength() + " tickets...", "logs/logserver.log");
             //recorro los nodos tkt y seteo los atributos del tktobj
             for (int index = 0; index < nl1.getLength(); index = index + 2) {
                                tktattr.clear();
                                Node type = nl1.item(index); //tipo de tkt simplit                                
                                Node number = nl1.item(index+1);//nro de tkt simplit                                
                                Node node21 = type.getParentNode();
                                Node node22 = node21.getParentNode();
                                Node node23 = node22.getParentNode();
                                Node node24 = node23.getParentNode();
                                Node node25 = node24.getParentNode();
                                String tktParent = node25.getChildNodes().item(0).getTextContent();//id tkt itracker                                
                                String FA = node25.getChildNodes().item(1).getTextContent();//fecha de creacion tkt itracker                                
                                String master = node25.getChildNodes().item(3).getTextContent();//id master itracker                                
                                System.out.println(" simplit: " + type.getTextContent() + number.getTextContent() + " tkt: " + tktParent + " fecha de creacion: " + FA + " master: " + master);
                                //agrego los datos al array                                
                                tktattr.add(type.getTextContent());
                                tktattr.add(number.getTextContent());
                                tktattr.add(tktParent);
                                tktattr.add(FA);
                                tktattr.add(master);
                                if(master != ""){
                                    //guardo en archivo master el tkt
                                    System.out.println("guardo en master");
                                    save masterLog = new save();
                                    masterLog.file(tktParent, "logs/master.log");
                                }
                                //creo el objeto tkt
                                TKTobj tktobj = new TKTobj(tktattr);
                                //lo inserto en el array de tkt´s
                                tktarray.add(tktobj);
                                  
                               
                
             }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLparser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLparser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLparser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XMLparser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return tktarray;
        
    }
    
     ArrayList<TKTobj> doitList() {
         
         return null;
    }
     
     

    void ReadResult() {
        try{
            Node r = SearchTag(response, data);
            setResponse(r.getFirstChild().getNodeName().toString() + ": " + r.getFirstChild().getTextContent() );
        }catch (NullPointerException n){System.out.println("no hay tkt itracker");setResponse("error: no hay tkt itracker");}
    }
    
    void ReadResultSoap() {
        try{
            Node r = SearchTag(response, data);
            setResponse(r.getAttributes().item(1).getTextContent() );System.out.println(r.getAttributes().item(1).getTextContent());
        }catch (NullPointerException n){System.out.println("no hay tkt doit");setResponse("error: no hay tkt doit");}
    }
    
}
