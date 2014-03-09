package com.qopuir.taskcontrol.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.ninja_squad.dbsetup.bind.Binder;
import com.qopuir.taskcontrol.entities.enums.EnumType;

public class EnumTypeBinder implements Binder {
	@Override
	public void bind(PreparedStatement statement, int param, Object value) throws SQLException {
		if (value == null) {
			statement.setNull(param, Types.VARCHAR);
		} else {
			EnumType enumLiteral = (EnumType) value;
			
			statement.setString(param, enumLiteral.getLiteral());
		}
	}
}