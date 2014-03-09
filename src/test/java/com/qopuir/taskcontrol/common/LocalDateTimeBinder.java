package com.qopuir.taskcontrol.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.joda.time.LocalDateTime;

import com.ninja_squad.dbsetup.bind.Binder;

public class LocalDateTimeBinder implements Binder {

	@Override
	public void bind(PreparedStatement statement, int param, Object value) throws SQLException {
		if (value == null) {
			statement.setNull(param, Types.TIMESTAMP);
		} else {
			LocalDateTime date = (LocalDateTime) value;
			
			statement.setTimestamp(param, new Timestamp(date.toDateTime().getMillis()));
		}
	}
}