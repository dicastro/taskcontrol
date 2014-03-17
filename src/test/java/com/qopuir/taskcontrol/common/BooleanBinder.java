package com.qopuir.taskcontrol.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.ninja_squad.dbsetup.bind.Binder;

public class BooleanBinder implements Binder {
	@Override
	public void bind(PreparedStatement statement, int param, Object value) throws SQLException {
		if (value == null) {
			statement.setNull(param, Types.BIT);
		} else {
			Boolean booleanValue = (Boolean) value;
			
			statement.setBoolean(param, booleanValue);
		}
	}
}