package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}
	
	public Collection<Meeting> getMeeting() {
		return connector.getSession().createCriteria(Meeting.class).list();
	}

	public Meeting findById(long id) {
		return (Meeting) connector.getSession().get(Meeting.class, id);
	}
	
	public Meeting add(Meeting meeting) {
		Transaction transaction = this.connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();

		return meeting;
	}
}
