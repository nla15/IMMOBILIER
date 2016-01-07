package alda.immobilier.chat;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="chat", eager=true)
@ApplicationScoped
public class Chat implements Serializable {
	private static final long serialVersionUID = 1148132624820376439L;
	private static final int MAX_MSG = 500;

	private ArrayList<ChatSession> sessions;
	private ArrayList<Message> messages;
	
	public Chat(){
		sessions = new ArrayList<>();
		messages = new ArrayList<>();
	}
	
	public void posterMessage(String expediteur, String contenu){
		messages.add(new Message(expediteur, contenu));
		
		if ( messages.size() > MAX_MSG )
			messages.remove(0);
		
		System.out.println("\n*---*");
		for ( ChatSession cs : sessions ){
			System.out.println("Session " + cs.getExpediteurMsg() +", addr " + cs + " : envoi de la conversation...");
			cs.setConversation(chaineConversation());
		}
	}
	
	public String chaineConversation(){
		String res = "";
		
		for ( Message msg : messages )
			res += msg.toString() + "\n\n";
		
		return res;
	}
	
	public void ajouterChatSession(ChatSession cs){
		sessions.add(cs);
		cs.setConversation(chaineConversation());
	}
	
	public void retirerChatSession(ChatSession cs){
		sessions.remove(cs);
	}
	
	public int getNbSessions(){
		return sessions.size();
	}
}
