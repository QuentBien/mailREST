package ejb.service;

import java.util.*;
import javax.ejb.Remote;

import ejb.entity.Compte;
import ejb.entity.Message;

@Remote
public interface IMessagerie {
	public void creerCompte(String login, String nom, String password, Date birthday) throws Exception;
	public Compte consulterCompte(String login) throws Exception;
	public void envoyerMessage(String loginEmetteur, String loginDestinataire, String objetMessage, String corpsMessage) throws Exception;
	public Collection<Message> releverCourrier(String login) throws Exception;
	public void supprimerMessagesLus(String login) throws Exception;
}
