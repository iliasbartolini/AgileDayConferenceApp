package it.agileday.data;

import it.agileday.utils.Dates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Track {
	public String title;
	private List<Session> sessions = new ArrayList<Session>();
	private List<Session> unmodifiableSessions = Collections.unmodifiableList(sessions);

	public Track addSession(Session session) {
		sessions.add(session);
		return this;
	}

	public List<Session> getSessions() {
		return unmodifiableSessions;
	}

	public Session getPrevious(Session session) {
		int index = sessions.indexOf(session);
		return (index > 0 ? sessions.get(index - 1) : null);
	}

	public boolean hasPrevious(Session session) {
		return (getPrevious(session) != null);
	}

	public Session getNext(Session session) {
		int index = sessions.indexOf(session);
		return (index < sessions.size() - 1 ? sessions.get(index + 1) : null);
	}

	public boolean hasNext(Session session) {
		return (getNext(session) != null);
	}

	public boolean isValid() {
		return validationMessage() == null;
	}

	public String validationMessage() {
		for (int i = 0; i < sessions.size(); i++) {
			Session session = sessions.get(i);
			if (!session.isValid()) {
				return String.format("%s: %s", session, session.validationMessage());
			}
			if (i > 0 && !Dates.equal(sessions.get(i - 1).getEnd(), session.getStart())) {
				return "Session overlap/bad order/hole detected";
			}
		}
		return null;
	}
}