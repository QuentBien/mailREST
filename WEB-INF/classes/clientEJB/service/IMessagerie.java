package clientEJB.service;

import java.util.*;
import javax.ejb.Remote;
import clientEJB.entity.*;

@Remote
public interface IMessagerie {
	//retiré Date signup car auto
	public void creerCompte(String login, String nom, String password, Date birthday);
	//peut être vérifier si un compte avec ce login exiqte déjà (clé primaire BDD)
	public Compte consulterCompte(String login);
	//récupérer compte destinataire et compte emetteur en recherchant avec les String
	public void envoyerMessage(String objet, String corps, String emetteur, String destinataire) throws Exception;
	//relever courrier d'un compte -> attribut pour recherche et afficher lu et non lu
	public Collection<Message> releverCourrier(/*String login*/);
	//ajouter attribut pour supprimer courier d'un seul compte (tester si message.getDestinataire().getLogin()==login)
	public void supprimerMessagesLus(/*String login*/);
}
/*
 * modifications envisagées sur MessagerieBean sur le serveur ejb glassfish :
 * 
package ejb.service;

import java.util.*;
import javax.ejb.Stateless;
import javax.persistence.*;

import ejb.entity.*;

@Stateless (mappedName="ejb/mail")
public class MessagerieBean implements IMessagerie {
	
	@PersistenceContext (unitName="persistanceMessagerie")
	EntityManager em;
	
	@Override
	public void creerCompte(String login, String password, String nom, Date birthday) {
		Compte compte = new Compte(login, password, nom, birthday);
		em.persist(compte);
	}

	@Override
	public Compte consulterCompte(String login) {
		Compte compte = em.find(Compte.class, login);
		return compte;
	}

	@Override
	public void envoyerMessage(String loginEmetteur, String loginDestinataire, String objetMessage, String corpsMessage) throws Exception {
		if(d = consulterCompte(loginDestinataire) != null && e = consulterCompte(loginEmetteur) != null){
			Message message = new Message(objetMessage, corpsMessage, e, d);
			em.persist(message);
		}
		else{
			throw new Exception("Le destinataire n'existe pas.");
		}
	}

	@Override
	public List<Message> releverCourrier(String login) {
		// TODO - Mettre à jour date de réception des messages
	    Query query = em.createQuery("SELECT * FROM Message WHERE destinataire = :login").setParameter("login", login);
	    List<?> messagesTmp = null;
	    List<Message> messages = new LinkedList<>();
	    //à tester -> Message devrait contenir des Comptes... Sinon faire des new Message avec les résultats de la requête et consulterCompte(...) pour emetteur et destinataire
	    //caster un par un les items de la liste permet d'éviter un cast unsafe de la liste
	    try{
	    	messagesTmp = (List<?>) query.getResultList();
	    	for(Object objMessage : messagesTmp){
	    		messages.add((Message) objMessage);
	    	}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return messagesNonLus;
	}

	//même idée que releverCourrier(String login)
	@Override
	public void supprimerMessagesLus() {
		Message message = em.find(Message.class, EtatMessage.LU);
		while(message != null){
			em.remove(message);
			em.find(Message.class, EtatMessage.LU);
		}
	}
} */
