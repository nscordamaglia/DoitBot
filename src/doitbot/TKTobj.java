/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package doitbot;

import java.util.ArrayList;

/**
 *
 * @author u189299
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
        
        this.IncidentID = a.get(0)+a.get(1);
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
        if(p == 0){setIncidentID(d);}else if(p == 1){setOpenTime(d);}else if(p == 2){setClosedTime(d);}else if(p == 3){setPrimaryAssignmentGroup(d);}
        else if(p == 4){setDescription(d);}else if(p == 5){setSolution(d);}else if(p == 6){setJournalUpdates(d);}else if(p == 7){setStatus(d);}
        
    
    
    
    
    }
    
}
