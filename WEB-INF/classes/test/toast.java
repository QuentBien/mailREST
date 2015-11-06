package test;

//import java.util.Date;
import java.util.Properties;

import javax.naming.*;

import ejb.entity.Compte;
import ejb.service.IMessagerie;

public class toast {

	public static void main(String[] args) throws NamingException {
		//connexion au serveur glassfish
		Properties properties = new Properties();
		properties.put("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory");
		properties.put("java.naming.factory.url.pkgs","com.sun.enterprise.naming");
		properties.put("java.naming.factory.state","com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
		properties.put("org.omg.CORBA.ORBInitialHost", "localhost");
		properties.put("org.omg.CORBA.ORBInitialPort","3700"); //deuxième argument : port de glassfish
		Context ctx = new InitialContext(properties);
		IMessagerie toto = (IMessagerie) ctx.lookup("MessagerieBean");
		System.out.println(toto.toString());
		try {
			//toto.creerCompte("toto2", "TOTO2", "bien", new Date());
			Compte c = toto.consulterCompte("moi");
			System.out.println(c.getLogin());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}