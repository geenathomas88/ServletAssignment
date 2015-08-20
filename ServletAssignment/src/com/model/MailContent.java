package com.model;

public class MailContent {

	int id;
	String message_content;
	int mail_id;
	String subject;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public int getMail_id() {
		return mail_id;
	}
	public void setMail_id(int mail_id) {
		this.mail_id = mail_id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public MailContent() {
		super();
	}
	public MailContent(int id, String message_content, int mail_id, String subject) {
		super();
		this.id = id;
		this.message_content = message_content;
		this.mail_id = mail_id;
		this.subject = subject;
	}
	
}
