package com.starters.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.starters.inf.AddEventInterface;
import com.starters.inf.AddUserEventInterface;
import com.starters.inf.AddUserInterface;
//import com.starters.inf.AddEventInterface;
import com.starters.model.Event;
import com.starters.model.User;
import com.starters.model.UserEvent;
import com.starters.service.FcmNotificationService;

@RestController
public class EventManagementController {
	
	
	@Autowired
	private AddUserInterface addUserInterface;
	
	@Autowired
	private AddEventInterface addEventInterface;
	
	@Autowired
	private AddUserEventInterface addUserEventInterface;
	
//	@Autowired
//	FcmNotificationService fcmNotificationService;
	
	private List<String> memberFcmTokenList;
	private List<String> memberList;
	private Event tempEvent;
	
//	public <User> ArrayList<User> makeCollection(Iterable<User> iter) {
//	    ArrayList<User> list = new ArrayList<User>();
//	    for (User item : iter) {
//	        list.add(item);
//	    }
//	    return list;
//	}
	
	
	public List<String> findAllFcmTokens(List<String> memberList)
	{
		List<String> fcms = new ArrayList<String>();
//		ArrayList<User> users;
//		users = makeCollection(addUserInterface.findAll());
		for(int i=0; i<memberList.size(); i++){
//			for(User user: users)
			for(User user: addUserInterface.findAll())
			{
				if(memberList.get(i).equals(user.getPhone())){
					fcms.add(user.getFcmToken());
				}
			}
		}
		return fcms;
	}
	
	@RequestMapping(value="/api/notifyMembers", method=RequestMethod.POST, produces=MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String notifyMembers(@RequestBody Event event){
		
		tempEvent = addEventInterface.save(event);
		
		//invoke Google FCM server to notify users
		String members = event.getMemberList();
		memberList = new ArrayList<String>(Arrays.asList(members.split(",")));
		memberFcmTokenList = findAllFcmTokens(memberList);
		
		//Send EventID along with Event details
		
		StringBuilder message = new StringBuilder();
		FcmNotificationService fcmNotificationService = new FcmNotificationService();
		for(int i=0; i<memberFcmTokenList.size(); i++){
			message.setLength(0);
			message.append("EventCreated"+",");
			//Fetching Event ID of the event stored in DB just above.
			message.append(Integer.toString(tempEvent.getId())+",");
			message.append(event.getEventName()+",");
			message.append(event.getEventDate()+",");
			message.append(event.getEventTime()+",");
			message.append(members);
			System.out.println(message.toString());
			System.out.println(memberFcmTokenList.get(i));
			fcmNotificationService.notify(message.toString(), memberFcmTokenList.get(i));
			
		}
		String responseFromServer = Integer.toString(tempEvent.getId());
		return responseFromServer;
	}
	
	@RequestMapping(value="/api/addUserEvent", method=RequestMethod.POST, produces=MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String addUserEventLocation(@RequestBody UserEvent userEvent){
		
		//code to save UserEvent object to UserEvent table
		addUserEventInterface.save(userEvent);
		
		
		return "Saved To UserEvent Table";
	}
	
	
}

