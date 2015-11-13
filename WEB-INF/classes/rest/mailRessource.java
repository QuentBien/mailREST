package rest;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import ejb.entity.Compte;
import ejb.entity.Message;
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
			properties.put("org.omg.CORBA.ORBInitialPort","3700");
			mailRessource.setMessagerie((IMessagerie) new InitialContext(properties).lookup("MessagerieBean"));
		} catch (NamingException e) {e.printStackTrace();}
		
	}

	@Path("/compte") 
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Compte creerCompte(Compte c){
		Compte retour = null;
		try {
			mailRessource.getMessagerie().creerCompte(c.getLogin(), c.getPassword(), c.getName(), c.getBirthday());
			retour = mailRessource.getMessagerie().consulterCompte(c.getLogin());
		} catch (Exception e) {
			//s = "{\"exception\":\"" + e.toString() + "\"}";
			e.printStackTrace();
		}
		return retour;
	}
	
	@Path("/messages/lus/{login}") 
	@DELETE
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String supprMessages(@PathParam("login") String login){
		String s = "";
		try {
			mailRessource.getMessagerie().supprimerMessagesLus(login);
		} catch (Exception e) {
			s = "{\"exception\":\"" + e.toString() + "\"}";
			e.printStackTrace();
		}
		return s;
	}

	@Path("/compte/{login}")
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Compte consulterCompte(@PathParam("login") String login) {
		Compte c = null;
		//String s ="";
		try {
			c = mailRessource.getMessagerie().consulterCompte(login);
			//s = new String(c.getLogin());
			//s = mailRessource.getMessagerie().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return "{\"login\" : \""+ s +"\"}";
		return c;
	}
	
	@Path("/messages/{login}")
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Message> releverCourrier(@PathParam("login") String login) {
		List<Message> messages = new LinkedList<>();
		try {
			messages = (List<Message>) mailRessource.getMessagerie().releverCourrier(login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	@Path("/message/{destinataire}")
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String envoyerMessage(@PathParam("destinataire") String destinataire, Message m) {
		String s = "";
		try {
			mailRessource.getMessagerie().envoyerMessage(m.getEmetteur().getLogin(), destinataire, m.getObjet(), m.getCorps());
		} catch (Exception e) {
			s = "{\"exception\":\"" + e.toString() + "\"}";
			e.printStackTrace();
		}
		return s;
	}
	
	@Path("/compte/test/{compte}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Compte avoirCompte(@PathParam("compte") String compte) {
		return new Compte(compte, "bien", "Quent", new Date());
	}
	
	public static IMessagerie getMessagerie() {
		return messagerie;
	}

	public static void setMessagerie(IMessagerie messagerie) {
		mailRessource.messagerie = messagerie;
	}
	
}
