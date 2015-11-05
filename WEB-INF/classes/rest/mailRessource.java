package rest;

import java.util.Date;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
/*
import org.glassfish.internal.api.Globals;

import com.sun.appserv.jdbc.DataSource;
import com.sun.enterprise.module.ModulesRegistry;
import com.sun.enterprise.module.bootstrap.StartupContext;
import com.sun.enterprise.module.single.StaticModulesRegistry;
*/
import ejb.entity.*;
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
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void creerCompte(Compte c){
		try {
			mailRessource.getMessagerie().creerCompte(c.getLogin(), c.getName(), c.getPassword(), c.getBirthday());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Path("/compte/tutu/{login}")
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Compte consulterCompte(@PathParam("login") String login) {
		Compte c = null;
		String s = "{\"bien\":\"cool\"}";
		try {
			c = new Compte("lui", "bien", "Quent", new Date());
			c = mailRessource.getMessagerie().consulterCompte(login);
			String t = c.getLogin();
			s = "{\"login\":\"" + t +"\",";
			s += "\"messagerie\":\"" + mailRessource.getMessagerie().toString() +"\"}";
		} catch (Exception e) {
			s = "{\"exception\":\"" + e.toString() + "\"}";
			e.printStackTrace();
		}
		//return s;
		return c;
	}
	
	@GET
	@Path("/compte/test/{compte}")
	@Produces(MediaType.APPLICATION_JSON)
	public Compte avoirCompte(@PathParam("compte") String compte) {
		return new Compte(compte, "bien", "Quent", new Date());
	}
	
	@Path("/message")
	@POST
	@Produces ({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void envoyerMessage(Message m) throws Exception {
		mailRessource.getMessagerie().envoyerMessage(m.getObjet(), m.getCorps(), m.getEmetteur().getLogin(), m.getDestinataire().getLogin());
	}
	
	@GET
	@Path("/accueil/{nomPromo}")
	@Produces(MediaType.APPLICATION_XML)
	public String direBonjour(@PathParam("nomPromo") String promo) {
		return "<accueil> Bonjour la promo de : " + promo + " ! </accueil>";
	}
	
	public static IMessagerie getMessagerie() {
		return messagerie;
	}

	public static void setMessagerie(IMessagerie messagerie) {
		mailRessource.messagerie = messagerie;
	}
	
}
