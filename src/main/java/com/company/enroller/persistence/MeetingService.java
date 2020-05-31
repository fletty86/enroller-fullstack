package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;


import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;

@Component("meetingService")
public class MeetingService {

    Session session;

	public MeetingService() {
		session = DatabaseConnector.getInstance().getSession();
	}
    
    public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = this.session.createQuery(hql);
		return query.list();
	}

	public Collection<Meeting> findMeetings(String title, String description, Participant participant, String sortMode) {
		String hql = "FROM Meeting as meeting WHERE title LIKE :title AND description LIKE :description ";
		if (participant!=null) {
			hql += " AND :participant in elements(participants)";
		}
		if (sortMode.equals("title")) {
			hql += " ORDER BY title";
		}
		Query query = this.session.createQuery(hql);
		query.setParameter("title", "%" + title + "%").setParameter("description", "%" + description + "%");
		if (participant!=null) {
			query.setParameter("participant", participant);
		} 
		
		return query.list();
	}
}
