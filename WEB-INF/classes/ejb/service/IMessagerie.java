package ejb.service;

import java.util.*;
import ejb.entity.*;

public interface IMessagerie {
	public void creerCompte(String login, String nom, String password, String birthday) throws Exception;
	public Compte consulterCompte(String login) throws Exception;
	public void envoyerMessage(String loginEmetteur, String loginDestinataire, String objetMessage, String corpsMessage) throws Exception;
	public Collection<Message> releverCourrier(String login) throws Exception;
	public void supprimerMessagesLus(String login) throws Exception;
	public String toString();
}
