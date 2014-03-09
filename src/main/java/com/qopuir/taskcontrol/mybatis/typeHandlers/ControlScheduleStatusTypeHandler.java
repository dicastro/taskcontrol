package com.qopuir.taskcontrol.mybatis.typeHandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;

@MappedTypes(ControlScheduleStatus.class)
public class ControlScheduleStatusTypeHandler implements TypeHandler<ControlScheduleStatus> {

	public void setParameter(PreparedStatement ps, int columnIndex, ControlScheduleStatus parameter, JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			ps.setString(columnIndex, parameter.getLiteral());
		} else {
			ps.setNull(columnIndex, jdbcType.TYPE_CODE);
		}
	}

	public ControlScheduleStatus getResult(ResultSet rs, String columnName) throws SQLException {
		String controlNameLiteral = rs.getString(columnName);
		
		if (controlNameLiteral != null) {
			return ControlScheduleStatus.valueOf(controlNameLiteral);
		} else {
			return null;
		}
	}

	@Override
	public ControlScheduleStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
		String controlNameLiteral = rs.getString(columnIndex);
		
		if (controlNameLiteral != null) {
			return ControlScheduleStatus.valueOf(controlNameLiteral);
		} else {
			return null;
		}
	}

	public ControlScheduleStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String controlNameLiteral = cs.getString(columnIndex);
		
		if (controlNameLiteral != null) {
			return ControlScheduleStatus.valueOf(controlNameLiteral);
		} else {
			return null;
		}
	}
}