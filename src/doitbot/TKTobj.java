
package doitbot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase creadora de objetos tkt, se inicializan con los datos de itracker y luego se actualizan con los datos de doit
 * @author Nicolas Scordamaglia
 */
class TKTobj {
    
    private String id;
    private String FA;
    private String master;
    private String IncidentID;
    private String OpenTime;
    private String ClosedTime;
    private String PrimaryAssignmentGroup;
    private String Description;
    private String Solution;
    private String JournalUpdates;
    private String Status;

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String OpenTime) {
        this.OpenTime = OpenTime;
    }

    public String getClosedTime() {
        return ClosedTime;
    }

    public void setClosedTime(String ClosedTime) {
        this.ClosedTime = ClosedTime;
    }

    public String getPrimaryAssignmentGroup() {
        return PrimaryAssignmentGroup;
    }

    public void setPrimaryAssignmentGroup(String PrimaryAssignmentGroup) {
        this.PrimaryAssignmentGroup = PrimaryAssignmentGroup;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getSolution() {
        return Solution;
    }

    public void setSolution(String Solution) {
        this.Solution = Solution;
    }

    public String getJournalUpdates() {
        return JournalUpdates;
    }

    public void setJournalUpdates(String JournalUpdates) {
        this.JournalUpdates = JournalUpdates;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getIncidentID() {
        return IncidentID;
    }

    public void setIncidentID(String IncidentID) {
        this.IncidentID = IncidentID;
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

  

    public String getFA() {
        return FA;
    }

    public void setFA(String FA) {
        this.FA = FA;
    }
    
    public TKTobj(ArrayList<String> a) {
        
        
        String number = a.get(1).replaceAll("\\W","");
        int aux = 8 - (number.length());
        for (int i=0; i<aux; i++){
            number="0"+number;
        }
        this.IncidentID = a.get(0)+number;System.out.println(getIncidentID());
        this.id = a.get(2);
        this.FA = a.get(3);
        this.master = a.get(4);
    }
    
    public void Update(String response){
    
        XMLparser parser = new XMLparser(response);
        String[] arrayinfo = ConfigManager.getAppSetting("IncidentInfo").split(";");
        
        for (int i = 0; i<arrayinfo.length; i++){
            
            //System.out.println("tag: " + arrayinfo[i]);
            parser.setData("instance/" + arrayinfo[i]);
            parser.ReadTag();
            StoringOrder(parser.getResponse(),i);
            parser.setResponse(response);
            
        }
        
        /*
         *  <IncidentID type="String">IM00900247</IncidentID>
         * <OpenTime type="DateTime">2015-10-13T21:00:14+00:00</OpenTime>   
         * <ClosedTime type="DateTime">2015-10-15T18:06:20+00:00</ClosedTime>
         * <PrimaryAssignmentGroup type="String">APLR_COR_ABUSE</PrimaryAssignmentGroup>
         * <Description type="Array">
                  <Description type="String">describe falla</Description>
           </Description>
           <Solution type="Array">
                  <Solution type="String">resuelto ok</Solution>
           </Solution>
           <JournalUpdates type="Array">
                <JournalUpdates type="String">15/10/15 15:05:13 Argentina/Bs.As. (MARCELA GODOY):</JournalUpdates>
                <JournalUpdates type="String">ok</JournalUpdates>
                <JournalUpdates type="String">15/10/15 15:04:58 Argentina/Bs.As. (MARCELA GODOY):</JournalUpdates>
                <JournalUpdates type="String">ok</JournalUpdates>
                <JournalUpdates type="String">13/10/15 18:05:03 Argentina/Bs.As. (MARCELA GODOY):</JournalUpdates>
                <JournalUpdates type="String">revisar AAAA</JournalUpdates>
           </JournalUpdates>
           <Status type="String">Closed</Status>
         */
    
    
    }
    
    void StoringOrder(String d,int p){
  
    //IncidentID;OpenTime;ClosedTime;PrimaryAssignmentGroup;Description;Solution;JournalUpdates;Status
        if(p == 0){setIncidentID(d);}else if(p == 1){ 
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(caracterControl(d).split("T")[0]);
                String opentime = new SimpleDateFormat("dd-MM-yyyy").format(date).toString();System.out.println(opentime);
                setOpenTime(opentime);
            } catch (ParseException ex) {
                Logger.getLogger(TKTobj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(p == 2){
            System.out.println("fecha: "+d);
            if (!"sin datos".equals(d)){
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = formatter.parse(caracterControl(d).split("T")[0]);
                            String closetime = new SimpleDateFormat("dd-MM-yyyy").format(date).toString();System.out.println(closetime);
                            setClosedTime(closetime);
                        } catch (ParseException ex) {
                            Logger.getLogger(TKTobj.class.getName()).log(Level.SEVERE, null, ex);
                        }
            }
        }else if(p == 3){setPrimaryAssignmentGroup(d);}
        else if(p == 4){setDescription(this.caracterControl(d)); System.out.println(getDescription());}else if(p == 5){setSolution(this.caracterControl(d));}else if(p == 6){setJournalUpdates(this.caracterControl(d));}else if(p == 7){setStatus(d);}
        
    }
    private String caracterControl(String data){
        
        String replace = null;
        String[] caracter = {";","/","\\\\/","'","&","<",">","\""};
        for (int i=0;i<8;i++){
            if (";".equals(caracter[i])){
                
                 replace = ".";
                
            }            
            else if ("/".equals(caracter[i])){
            
                replace = "-";
            
            
            }else{
            
            replace = " ";
            
            }
            data = data.replace(caracter[i], replace);
            //System.out.println(data);
        }
        
        data = data.replaceAll("\\t", " ");
        data = data.replaceAll("\\n", ". ");
        data = data.replaceAll("\\r", ". ");
        return data;
    }
    
}
