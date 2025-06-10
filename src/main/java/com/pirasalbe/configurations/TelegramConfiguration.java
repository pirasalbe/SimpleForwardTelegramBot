package com.pirasalbe.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bot")
public class TelegramConfiguration {

	// Phone number
	private String phoneNumber;

	// Application ID obtained from Telegram
	private Integer apiId;

	// Application hash obtained from Telegram
	private String apiHash;

	// Chat to monitor
	private Long chatId;

	// Destination for photos
	private Long photoDestinationChatId;

	// Thread name in the photo destination chat
	private String photoDestinationThreadName;

	// Destination for documents
	private Long documentDestinationChatId;

	// Thread name in the document destination chat
	private String documentDestinationThreadName;

	// Destination for video messages
	private Long videoMessageDestinationChatId;

	// Thread name in the video message destination chat
	private String videoMessageDestinationThreadName;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getApiId() {
		return apiId;
	}

	public void setApiId(Integer apiId) {
		this.apiId = apiId;
	}

	public String getApiHash() {
		return apiHash;
	}

	public void setApiHash(String apiHash) {
		this.apiHash = apiHash;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public Long getPhotoDestinationChatId() {
		return photoDestinationChatId;
	}

	public void setPhotoDestinationChatId(Long photoDestinationChatId) {
		this.photoDestinationChatId = photoDestinationChatId;
	}

	public String getPhotoDestinationThreadName() {
		return photoDestinationThreadName;
	}

	public void setPhotoDestinationThreadName(String photoDestinationThreadName) {
		this.photoDestinationThreadName = photoDestinationThreadName;
	}

	public Long getDocumentDestinationChatId() {
		return documentDestinationChatId;
	}

	public void setDocumentDestinationChatId(Long documentDestinationChatId) {
		this.documentDestinationChatId = documentDestinationChatId;
	}

	public String getDocumentDestinationThreadName() {
		return documentDestinationThreadName;
	}

	public void setDocumentDestinationThreadName(String documentDestinationThreadName) {
		this.documentDestinationThreadName = documentDestinationThreadName;
	}

	public Long getVideoMessageDestinationChatId() {
		return videoMessageDestinationChatId;
	}

	public void setVideoMessageDestinationChatId(Long videoMessageDestinationChatId) {
		this.videoMessageDestinationChatId = videoMessageDestinationChatId;
	}

	public String getVideoMessageDestinationThreadName() {
		return videoMessageDestinationThreadName;
	}

	public void setVideoMessageDestinationThreadName(String videoMessageDestinationThreadName) {
		this.videoMessageDestinationThreadName = videoMessageDestinationThreadName;
	}

}
