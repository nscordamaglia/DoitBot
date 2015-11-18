/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package doitbot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author u189299
 */
class ConfigManager {
    
    private static Properties p = null;
	
	private static Properties getInstance() {
		if (p==null) {
			p = new Properties();
			try {
				p.load(new FileInputStream("config.properties"));
		 	 } catch (IOException ex) {}
		}
		return p;
	}
	
        /**
         * Metodo que toma la variable como clave de configuracion y devuelve el valor del mismo configurado
         * de forma estatica en un archivo externo. Dicha configuracion esta normalizada como clave=valor
         * @param appSetting
         * @return valor de la clave solicitada
         */
	public static String getAppSetting(String appSetting) {
		Properties prop = getInstance();            
		return prop.getProperty(appSetting);
	}
}
