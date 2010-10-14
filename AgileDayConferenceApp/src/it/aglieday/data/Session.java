/*
   Copyright 2010 Ilias Bartolini, Andrea Chiarini, Luigi Bozzo

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

package it.aglieday.data;

import it.agileday.utils.Dates;
import java.util.Date;

public class Session {
	private static final String TAG = Session.class.getSimpleName();

	private int id;
	public String title;
	public String description;
	public String speakers;
	private Date start;
	private Date end;

	public Session() {
	}

	public Session setStart(Date start) {
		this.start = start;
		return this;
	}

	public Session setEnd(Date end) {
		this.end = end;
		return this;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public boolean isValid() {
		return validationMessage() == null;
	}

	public String validationMessage() {
		if (Dates.afterOrEqual(start, end)) {
			return "End date must be greater than start date";
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("%s#%s", TAG, id);
	}

	public Session setId(int id) {
		this.id = id;
		return this;
	}

	public long getId() {
		return id;
	}
}
