
package doitbot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * Clase que se encarga de la comunicacion http contra el servicio invocado
 * @author Nicolas Scordamaglia
 */
class Request {
        
        private String getResponse;
        private Save logStart = new Save();
        private String url;
        private String params;

    

    public Request(String url, String params) {
        this.url = url;
        this.params = params;
    }
        
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }   

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
        
        

    public String getGetResponse() {
        return getResponse;
    }

    public void setGetResponse(String getResponse) {
        this.getResponse = getResponse;
    }
        
        
        
         
        /**
         * Establece la conexion tomando los parametros y la url devolviendo la respuesta en formato XML 
         * @param url
         * @param urlParameters
         * @return XML de iTracker
         * @throws MalformedURLException
         * @throws IOException 
         */
       void SendPost() throws MalformedURLException, IOException{
            
            
                // Create a trust manager that does not validate certificate chains
                TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
                };

                // Install the all-trusting trust manager
                try {
                    SSLContext sc = SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                }

                // Now you can access an https URL without having the certificate in the truststore
                try {
                    
                    
                    
                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                     //add reuqest header
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Authorization", "OAuth 2.0"); 
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                    
                    // Send post request
                    con.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                    
                    wr.writeBytes(params);
                    wr.flush();
                    wr.close();

                    int responseCode = con.getResponseCode();
                   //System.out.println("\nSending 'POST' request to URL : " + url);
                    System.out.println("Post parameters : " + params);
                    //System.out.println("Response Code : " + responseCode);
                    logStart.file("Response Code : " + responseCode, "logs/logserver.log");
                    if (responseCode != 404 && responseCode != 500){
                    //System.out.println("Comunication success...  ");
                    logStart.file("Comunication success...  ", "logs/logserver.log");
                        
                    BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                    }
                    in.close();

                    //imprime resultado
                   System.out.println(response.toString());
                  
                    getResponse = response.toString();
                    }else{
                    
                        System.out.println("Comunication fail... ");
                        logStart.file("Comunication fail...  ", "logs/logserver.log");
                    
                    }
                } catch (  UnknownHostException unknownHostException) {
                    try {
                        System.out.println("UnknownHost error");
                        logStart.file("Comunication fail...UnknownHost error  ", "logs/logserver.log");
                        Thread.sleep(20000);//espera 20 segundos para volver a ejecutar la comunicacion en caso de fallar
                        this.SendPost();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      }
                  catch (  RemoteException remoteException) {
                    try {
                        System.out.println("Remote error");
                        logStart.file("Comunication fail...Remote error  ", "logs/logserver.log");
                        Thread.sleep(20000);//espera 20 segundos para volver a ejecutar la comunicacion en caso de fallar
                        this.SendPost();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      }
                 catch (  MalformedURLException malformedException) {
                    try {
                        System.out.println("MalformedURL error");
                        logStart.file("Comunication fail...MalformedURL error  ", "logs/logserver.log");
                        Thread.sleep(20000);//espera 20 segundos para volver a ejecutar la comunicacion en caso de fallar
                        this.SendPost();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      }
                catch (  ConnectException connectException) {
                    try {
                        System.out.println("Connect error");
                        logStart.file("Comunication fail...Connect error ", "logs/logserver.log");
                        Thread.sleep(20000);//espera 20 segundos para volver a ejecutar la comunicacion en caso de fallar
                        this.SendPost();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      }
                catch( SocketException socket){
                    try {
                        System.out.println("socket error");
                        logStart.file("Comunication fail...socket error ", "logs/logserver.log");
                        Thread.sleep(20000);//espera 20 segundos para volver a ejecutar la comunicacion en caso de fallar
                        this.SendPost();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                    }

                        }
                
            setGetResponse(getResponse);
        
                
		
                
               
                
        
        }
       void SendGet() throws MalformedURLException, IOException{
            
            
                // Create a trust manager that does not validate certificate chains
                TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
                };

                // Install the all-trusting trust manager
                try {
                    SSLContext sc = SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                } catch (Exception e) {
                }

                // Now you can access an https URL without having the certificate in the truststore
                try {
                    
                    
                    
                    URL obj = new URL(url+"&"+params+"&hash=0793C285DF127DABAB8575EB3070078C");
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                     //add reuqest header
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Authorization", "OAuth 2.0"); 
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                    
                   

                    int responseCode = con.getResponseCode();
                   //System.out.println("\nSending 'POST' request to URL : " + url);
                    //System.out.println("Post parameters : " + params);
                   // System.out.println("Response Code : " + responseCode);
                    logStart.file("Response Code : " + responseCode, "logs/logserver.log");
                    if (responseCode != 404 && responseCode != 500){
                   // System.out.println("Comunication success...  ");
                    logStart.file("Comunication success...  ", "logs/logserver.log");
                        
                    BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                    }
                    in.close();

                    //imprime resultado
                   System.out.println(response.toString());
                  
                    getResponse = response.toString();
                    }else{
                    
                        System.out.println("Comunication fail... ");
                        logStart.file("Comunication fail...  ", "logs/logserver.log");
                    
                    }
                } catch (  UnknownHostException unknownHostException) {
                    try {
                        System.out.println("UnknownHost error");
                        logStart.file("Comunication fail...UnknownHost error  ", "logs/logserver.log");
                        Thread.sleep(20000);//espera 20 segundos para volver a ejecutar la comunicacion en caso de fallar
                        this.SendGet();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      }
                  catch (  RemoteException remoteException) {
                    try {
                        System.out.println("Remote error");
                        logStart.file("Comunication fail...Remote error  ", "logs/logserver.log");
                        Thread.sleep(20000);//espera 20 segundos para volver a ejecutar la comunicacion en caso de fallar
                        this.SendGet();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      }
                 catch (  MalformedURLException malformedException) {
                    try {
                        System.out.println("MalformedURL error");
                        logStart.file("Comunication fail...MalformedURL error  ", "logs/logserver.log");
                        Thread.sleep(20000);//espera 20 segundos para volver a ejecutar la comunicacion en caso de fallar
                        this.SendGet();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      }
                catch (  ConnectException connectException) {
                    try {
                        System.out.println("Connect error");
                        logStart.file("Comunication fail...Connect error ", "logs/logserver.log");
                        Thread.sleep(20000);//espera 20 segundos para volver a ejecutar la comunicacion en caso de fallar
                        this.SendGet();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      }
                catch( SocketException socket){
                    try {
                        System.out.println("socket error");
                        logStart.file("Comunication fail...socket error ", "logs/logserver.log");
                        Thread.sleep(20000);//espera 20 segundos para volver a ejecutar la comunicacion en caso de fallar
                        this.SendGet();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, ex);
                    }

                        }
                
            setGetResponse(getResponse);
        
                
		
                
               
                
        
        }
}

