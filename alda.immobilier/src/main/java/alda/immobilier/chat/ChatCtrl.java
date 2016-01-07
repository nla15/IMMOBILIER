package alda.immobilier.chat;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

@ManagedBean(name="chatCtrl", eager=true)
@ViewScoped
public class ChatCtrl implements Serializable{
	private static final long serialVersionUID = 1148132624820376439L;
	
	@ManagedProperty(value="#{chatSession}")
	private ChatSession chatSession;
	private String contenuMsg;
	
	public ChatCtrl(){
		contenuMsg = new String();
	}
	
	@PostConstruct
	public void init(){
		chatSession.ajouterChatCtrl(this);
	}
	
	@PreDestroy
	public void destroy(){
		chatSession.retirerChatCtrl(this);
	}
	
	public void posterMessage(){
		chatSession.posterMessage(contenuMsg);
		contenuMsg = "";
	}
	
	public String bienvenue(){
		return "Bienvenue, " + getExpediteurMsg() + " !";
	}
	
	public String getExpediteurMsg(){
		return chatSession.getExpediteurMsg();
	}
	public ChatSession getChatSession() {
		return chatSession;
	}
	public void setChatSession(ChatSession chatSession) {
		this.chatSession = chatSession;
	}
	public String getContenuMsg() {
		return contenuMsg;
	}
	public void setContenuMsg(String contenuMsg) {
		this.contenuMsg = contenuMsg;
	}
	public String getConversation() {
		return chatSession.getConversation();
	}
	public void setConversation(String conversation) {
		chatSession.setConversation(conversation);
	}
}
