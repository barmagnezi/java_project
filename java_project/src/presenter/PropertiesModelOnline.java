package presenter;

import java.beans.XMLDecoder;
import java.io.InputStream;
import java.io.Serializable;

public class PropertiesModelOnline implements Serializable, PropertiesModel {

	private static final long serialVersionUID = 1L;
	public String ip; // "localhost"
	public Integer port; // 5400
	public Integer AllowedThreads;
	public Boolean Diag;


	public PropertiesModelOnline() {
	}

	/**
	 * Default copy constructor.
	 */
	private void copyConstructor(PropertiesModelOnline prop) {
		ip = prop.ip;
		port = prop.port;
		AllowedThreads=prop.AllowedThreads;
		Diag=prop.Diag;
	}

	/**
	 * Sets the properties model from an inputStream.
	 * 
	 * @param from
	 *            inputStream containing the XML file.
	 */
	public PropertiesModelOnline(InputStream from) {
		try {
			XMLDecoder XML = null;
			XML = new XMLDecoder(from);
			this.copyConstructor((PropertiesModelOnline) XML.readObject());
			XML.close();
		} catch (Exception e) {
			System.out
					.println("no found properties//run default values\n"
							+ "after exit command the properties save in resources/propertiesOnline.xml");
			this.setIp("localhost");
			this.setPort(5401);
			this.setAllowedThreads(5);
			this.setDiag(true);
		} finally {

		}
	}

	// setters getters
	
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getAllowedThreads() {
		return AllowedThreads;
	}

	public void setAllowedThreads(Integer allowedThreads) {
		AllowedThreads = allowedThreads;
	}


	public void setDiag(Boolean diag) {
		Diag = diag;
	}

	@Override
	public boolean isDiag() {
		return Diag;
	}



}
