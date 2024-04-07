CREATE UNIQUE INDEX event_slug_index ON events (slug);
CREATE UNIQUE INDEX attendees_event_id_email_index ON attendees (event_id, email);
CREATE UNIQUE INDEX check_ins_attendees_id_index ON check_ins (attendee_id);