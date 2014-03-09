package com.qopuir.taskcontrol.mybatis.typeHandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import com.qopuir.taskcontrol.entities.enums.ControlName;

@MappedTypes(ControlName.class)
public class ControlNameTypeHandler implements TypeHandler<ControlName> {

	public void setParameter(PreparedStatement ps, int columnIndex, ControlName parameter, JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			ps.setString(columnIndex, parameter.getLiteral());
		} else {
			ps.setNull(columnIndex, jdbcType.TYPE_CODE);
		}
	}

	public ControlName getResult(ResultSet rs, String columnName) throws SQLException {
		String controlNameLiteral = rs.getString(columnName);
		
		if (controlNameLiteral != null) {
			return ControlName.valueOf(controlNameLiteral);
		} else {
			return null;
		}
	}

	@Override
	public ControlName getResult(ResultSet rs, int columnIndex) throws SQLException {
		String controlNameLiteral = rs.getString(columnIndex);
		
		if (controlNameLiteral != null) {
			return ControlName.valueOf(controlNameLiteral);
		} else {
			return null;
		}
	}

	public ControlName getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String controlNameLiteral = cs.getString(columnIndex);
		
		if (controlNameLiteral != null) {
			return ControlName.valueOf(controlNameLiteral);
		} else {
			return null;
		}
	}
}