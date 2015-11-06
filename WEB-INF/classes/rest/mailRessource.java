package rest;

import java.util.Date;
//import java.util.Properties;
import java.util.List;

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
			/*
			Properties properties = new Properties();
			properties.put("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory");
			properties.put("java.naming.factory.url.pkgs","com.sun.enterprise.naming");
			properties.put("java.naming.factory.state","com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
			properties.put("org.omg.CORBA.ORBInitialHost", "localhost");
			properties.put("org.omg.CORBA.ORBInitialPort","3700"); //deuxième argument : port de glassfish
			*/
			mailRessource.setMessagerie((IMessagerie) new InitialContext().lookup("MessagerieBean"));
			
		} catch (NamingException e) {e.printStackTrace();}
		
	}

	@Path("/compte") 
	@POST
	@Produces (MediaType.APPLICATION_JSON)
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String creerCompte(Compte c){
		String s = "";
		try {
			mailRessource.getMessagerie().creerCompte(c.getLogin(), c.getName(), c.getPassword(), c.getBirthday());
			s = "{\"login\":\""+mailRessource.getMessagerie().consulterCompte(c.getLogin()).getLogin()+"\"}";
		} catch (Exception e) {
			s = "{\"exception\":\"" + e.toString() + "\"}";
			e.printStackTrace();
		}
		return s;
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
		String s = "";
		try {
			//c = new Compte("lui", "bien", "Quent", new Date());
			c = mailRessource.getMessagerie().consulterCompte(login);
			String t = c.getLogin() + "ok";
			s = "{\"login\":\"" + t +"\",";
			s += "\"messagerie\":\"" + mailRessource.getMessagerie().toString() +"\"}";
		} catch (Exception e) {
			s = "{\"exception\":\"" + e.toString() + "\"}";
			e.printStackTrace();
		}
		//return s;
		return c;
	}
	
	@Path("/messages/{login}")
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Message> releverCourrier(@PathParam("login") String login) {
		List<Message> messages = null;
		String s = "";
		try {
			messages = (List<Message>) mailRessource.getMessagerie().releverCourrier(login);
			s = "{\"objet\":\"" + messages.get(0).getObjet() + "\"}";
		} catch (Exception e) {
			s = "{\"exception\":\"" + e.toString() + "\"}";
			e.printStackTrace();
		}
		//return s;
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
