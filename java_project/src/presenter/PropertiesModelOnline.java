package presenter;

import java.beans.XMLDecoder;
import java.io.InputStream;
import java.io.Serializable;


public class PropertiesModelOnline implements Serializable,PropertiesModel {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Boolean Diags;
	public String ip;	// "localhost"
	public Integer port;	// 5400
	
	
	
	/**
	 * Default copy constructor.
	 */
	private void copyConstructor(PropertiesModelOnline prop) {
		Diags=prop.Diags;
		ip=prop.ip;
		port=prop.port;
	}
	public String toString(){
		return "Bar";
	}
	/**
	 * Sets the properties model from an inputStream.
	 * @param from inputStream containing the XML file.
	 */
	public PropertiesModelOnline(InputStream from) {
		try{
			XMLDecoder XML = null;	
			XML = new XMLDecoder(from);	
			this.copyConstructor((PropertiesModelOnline) XML.readObject());
			XML.close();
		}catch(Exception e ){
			System.out.println("no found properties//run default values\n"
					+ "after exit command the properties save in resources/properties.xml");
			this.setDiag(false);
			this.setIp("localhost");
			this.setPort(5400);
		}finally{
			
		}	
	}

	//setters getters
	@Override
	public boolean isDiag() {
		return Diags;
	}

	@Override
	public void setDiag(boolean diags) {
		Diags=diags;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
