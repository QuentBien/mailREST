package rest;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.ejb.EJB;
import javax.naming.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import ejb.entity.Compte;
import ejb.entity.Message;
import ejb.service.IMessagerie;

@Path("/messagerie")
public class mailRessource {
	@EJB
	private IMessagerie MessagerieBean;

	public mailRessource() {
		/*
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
		*/
	}

	@Path("/compte") 
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response creerCompte(Compte c){
		URI uri = null;
		try {
			this.getMessagerie().creerCompte(c.getLogin(), c.getPassword(), c.getName(), c.getBirthday());
			uri = UriBuilder.fromUri("/compte").path(c.getLogin()).build();
			return Response.created(uri).build();
		} catch (Exception e) {
			return Response.status(403).type("text/plain").entity(e.toString()).build();
		}
	}
	
	@Path("/messages/lus/{login}") 
	@DELETE
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response supprMessages(@PathParam("login") String login){
		try {
			this.getMessagerie().supprimerMessagesLus(login);
			return Response.status(200).build();
		} catch (Exception e) {
			return Response.status(404).type("text/plain").entity(e.toString()).build();
		}
	}

	@Path("/compte/{login}")
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response consulterCompte(@PathParam("login") String login) {
		Compte c = null;
		try {
			c = (this.getMessagerie().consulterCompte(login));
			return Response.status(200).entity(c).build();
		} catch (Exception e) {
			return Response.status(404).type("text/plain").entity(e.toString()).build();
		}
	}
	
	@Path("/messages/{login}")
	@GET
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response releverCourrier(@PathParam("login") String login) {
		List<Message> messages = null;
		try {
			messages = (List<Message>) this.getMessagerie().releverCourrier(login);
			return Response.status(200).entity(messages).build();
		} catch (Exception e) {
			return Response.status(404).type("text/plain").entity(e.toString()).build();
		}
	}
	
	@Path("/message/{destinataire}")
	@POST
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response envoyerMessage(@PathParam("destinataire") String destinataire, Message m) {
		try {
			this.getMessagerie().envoyerMessage(m.getEmetteur().getLogin(), destinataire, m.getObjet(), m.getCorps());
;			return Response.status(201).build();
		} catch (Exception e) {
			return Response.status(404).type("text/plain").entity(e.toString()).build();
		}
	}
	
	@Path("/compte/test/{compte}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Compte avoirCompte(@PathParam("compte") String compte) {
		return new Compte(compte, "bien", "Quent", new Date());
	}
	
	public IMessagerie getMessagerie() {return MessagerieBean;}
	public void setMessagerie(IMessagerie messagerie) {this.MessagerieBean = messagerie;}
	
}
