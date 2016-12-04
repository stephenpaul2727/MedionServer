package com.starters.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Event {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY,generator="user_id_seq")
		private int id;
		@Column(name = "eventname", nullable = false, length =512)
	 	private String eventName;
	    @Column(name = "eventdate", nullable = false)
		private String eventDate;
	    @Column(name = "eventtime", nullable = false)
	    private String eventTime;
	    @Column(name = "memberlist")
	    private int memberList;
	
	    public String getEventName() {
	        return eventName;
	    }
	
	    public void setEventName(String eventName) {
	        this.eventName = eventName;
	    }
	
	    public String getEventDate() {
	        return eventDate;
	    }
	
	    public void setEventDate(String eventData) {
	        this.eventDate = eventData;
	    }
	
	    public String getEventTime() {
	        return eventTime;
	    }
	
	    public void setEventTime(String eventTime) {
	        this.eventTime = eventTime;
	    }
	
	    public int getMemberList() {
	        return memberList;
	    }
	
	    public void setMemberList(int memberList) {
	        this.memberList = memberList;
	    } 
}