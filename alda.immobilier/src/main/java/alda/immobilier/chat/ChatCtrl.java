package alda.immobilier.chat;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

@ManagedBean(name="chatCtrl", eager=true)
@ViewScoped
public class ChatCtrl implements Serializable{
	private static final long serialVersionUID = 1148132624820376439L;
	
	@ManagedProperty(value="#{chat}")
	private Chat chat;
	private String expediteurMsg, contenuMsg, conversation;

	public ChatCtrl(){
		expediteurMsg = new String("Visiteur");
		contenuMsg = new String();
		conversation = new String();
	}
	
	@PostConstruct
	public void init(){
		chat.ajouterChatCtrl(this);
	}
	
	@Override
	public void finalize() throws Throwable{
		super.finalize();
		chat.retirerChatCtrl(this);
	}
	
	public void posterMessage(){
		chat.posterMessage(expediteurMsg, contenuMsg);
		contenuMsg = "";
	}

	public Chat getChat(){
		return chat;
	}
	public void setChat(Chat chat){
		this.chat = chat;
	}
	public String getExpediteurMsg() {
		return expediteurMsg;
	}
	public void setExpediteurMsg(String expediteurMsg) {
		this.expediteurMsg = expediteurMsg;
	}
	public String getContenuMsg() {
		return contenuMsg;
	}
	public void setContenuMsg(String contenuMsg) {
		this.contenuMsg = contenuMsg;
	}
	public String getConversation() {
		return conversation;
	}
	public void setConversation(String conversation) {
		this.conversation = conversation;
	}
}
