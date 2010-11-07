/*
	Copyright 2010 
	
	Author: Gian Marco Gherardi
	Author: Ilias Bartolini

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package it.agileday.data;

import it.agileday.utils.Dates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Track implements Comparable<Track>{
	public String title;
	public int order;
	
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
			if (i > 0 && !Dates.beforeOrEqual(sessions.get(i - 1).getEnd(), session.getStart())) {
				return "Session overlap or bad order detected";
			}
		}
		return null;
	}

	@Override
	public int compareTo(Track another) {
		return this.order - another.order;
	}
}
