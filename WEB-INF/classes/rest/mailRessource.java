package rest;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import ejb.entity.*;
import ejb.service.IMessagerie;

@Path("/messagerie")
public class mailRessource {
	private static IMessagerie messagerie;

	public mailRessource() {
		try {
			//connexion au serveur glassfish
			Properties properties = new Properties();
			properties.put("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory");
			properties.put("java.naming.factory.url.pkgs","com.sun.enterprise.naming");
			properties.put("java.naming.factory.state","com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
			properties.put("org.omg.CORBA.ORBInitialHost", "localhost");
			properties.put("org.omg.CORBA.ORBInitialPort","3700"); //deuxième argument : port de glassfish
			mailRessource.setMessagerie((IMessagerie) new InitialContext(properties).lookup("ejb/messagerie"));
		} catch (NamingException e) {e.printStackTrace();}
	}

	@Path("/compte")
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void creerCompte(Compte c){
		try {
			mailRessource.getMessagerie().creerCompte(c.getLogin(), c.getName(), c.getPassword(), c.getBirthday());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Path("/{login}")
	@GET
	@Produces ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Compte consulterCompte(@PathParam("login") String login) {
		Compte c = null;
		try {
			c = mailRessource.getMessagerie().consulterCompte(login);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	
	@Path("/message")
	@POST
	@Produces ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void envoyerMessage(Message m) throws Exception {
		mailRessource.getMessagerie().envoyerMessage(m.getObjet(), m.getCorps(), m.getEmetteur().getLogin(), m.getDestinataire().getLogin());
	}
	

	
	public static IMessagerie getMessagerie() {
		return messagerie;
	}

	public static void setMessagerie(IMessagerie messagerie) {
		mailRessource.messagerie = messagerie;
	}
	
}
