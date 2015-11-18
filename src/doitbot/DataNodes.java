
package doitbot;



/**
 * Clase que se encarga de armas setear los parametros que se van a agregar al xml para la consulta
 * @author Nicolas Scordamaglia
 */
class DataNodes {
    
    String editHeader; // hash, class, method
    String createNodes; // lista de nodos segun sea la accion
    String editNodesData; // completar los nodos creados 
    String method;
    String txtform;
    String action;
    

    DataNodes(String method, TKTobj tkt) {
        
        this.method = method;
        System.out.println("estado: " + tkt.getStatus());
        if ("Closed".equals(tkt.getStatus())){
            //evaluo si es posible ejecutar la accion de cerrar, caso contrario solo comento
            if(Verify.Slave(tkt.getId()) == false){action="CERRAR_EXT";}else{action="STATUS_TKT_EXT";}
            this.txtform = "[{\"id\":\"actionform_tipo\",\"value\":\"" + tkt.getIncidentID().substring(0, 2) + "\"},{\"id\":\"actionform_numero\",\"value\":\"" 
                            + tkt.getIncidentID().substring(2, tkt.getIncidentID().length()) + "\"},{\"id\":\"actionform_grupo\",\"value\":\"" + tkt.getPrimaryAssignmentGroup() + "\"},{\"id\":\"actionform_status\",\"value\":\"" 
                            + tkt.getStatus() + "\"},{\"id\":\"actionform_fecha\",\"value\":\"" + tkt.getClosedTime() + "\"},{\"id\":\"actionform_comment\",\"value\":\"" 
                            + tkt.getJournalUpdates() + "- Solucion: " + tkt.getSolution() + "\"}]";//crear formulario apropiado al evento
            
        }else{
            action="STATUS_TKT_EXT";
            this.txtform = "[{\"id\":\"actionform_tipo\",\"value\":\"" + tkt.getIncidentID().substring(0, 2) + "\"},{\"id\":\"actionform_numero\",\"value\":\"" 
                            + tkt.getIncidentID().substring(2, tkt.getIncidentID().length()) + "\"},{\"id\":\"actionform_grupo\",\"value\":\"" + tkt.getPrimaryAssignmentGroup() + "\"},{\"id\":\"actionform_status\",\"value\":\"" 
                            + tkt.getStatus() + "\"},{\"id\":\"actionform_fecha\",\"value\":\"" + tkt.getOpenTime() + "\"},{\"id\":\"actionform_comment\",\"value\":\"" 
                            + tkt.getJournalUpdates()+ "\"}]";//crear formulario apropiado al evento
        }
        this.editHeader = ConfigManager.getAppSetting("editHeader"+method);
        this.createNodes = ConfigManager.getAppSetting("createNodes"+method);
        this.editNodesData = ConfigManager.getAppSetting("editNodesData"+method)+action+";"+tkt.getId()+";"+txtform;
       
    }

    public String getEditHeader() {
        return editHeader;
    }

    public void setEditHeader(String editHeader) {
        this.editHeader = editHeader;
    }

    public String getCreateNodes() {
        return createNodes;
    }

    public void setCreateNodes(String createNodes) {
        this.createNodes = createNodes;
    }

    public String getEditNodesData() {
        return editNodesData;
    }

    public void setEditNodesData(String editNodesData) {
        this.editNodesData = editNodesData;
    }

    public String getTxtform() {
        return txtform;
    }

    public void setTxtform(String txtform) {
        this.txtform = txtform;
    }
    
    
            

    DataNodes(String method) {
        
        this.method = method;
        this.editHeader = ConfigManager.getAppSetting("editHeader"+method);
        this.createNodes = ConfigManager.getAppSetting("createNodes"+method);
        this.editNodesData = ConfigManager.getAppSetting("editNodesData"+method);
               
        
    }

    
    
    
    
}
