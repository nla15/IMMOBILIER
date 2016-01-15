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
		
		for ( ChatSession cs : sessions ){
			cs.ajouterMessageConversation(genChaineDernierMessage());
		}
	}
	
	public String genChaineConversation(){
		String res = "";
		
		for ( Message msg : messages )
			res += msg.toString() + "\n\n";
		
		return res;
	}
	
	public String genChaineDernierMessage(){
		return messages.get( messages.size() -1).toString() + "\n\n";
	}
	
	public void ajouterChatSession(ChatSession cs){
		sessions.add(cs);
		cs.setConversation(genChaineConversation());
	}
	
	public void retirerChatSession(ChatSession cs){
		sessions.remove(cs);
	}
	
	public int getNbSessions(){
		return sessions.size();
	}
}
