package com.qopuir.taskcontrol.mybatis.typeHandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.entities.enums.ParamName;

@MappedTypes(ControlName.class)
public class ParamNameTypeHandler implements TypeHandler<ParamName> {

	public void setParameter(PreparedStatement ps, int columnIndex, ParamName parameter, JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			ps.setString(columnIndex, parameter.getLiteral());
		} else {
			ps.setNull(columnIndex, jdbcType.TYPE_CODE);
		}
	}

	public ParamName getResult(ResultSet rs, String columnName) throws SQLException {
		String controlNameLiteral = rs.getString(columnName);
		
		if (controlNameLiteral != null) {
			return ParamName.valueOf(controlNameLiteral);
		} else {
			return null;
		}
	}

	@Override
	public ParamName getResult(ResultSet rs, int columnIndex) throws SQLException {
		String controlNameLiteral = rs.getString(columnIndex);
		
		if (controlNameLiteral != null) {
			return ParamName.valueOf(controlNameLiteral);
		} else {
			return null;
		}
	}

	public ParamName getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String controlNameLiteral = cs.getString(columnIndex);
		
		if (controlNameLiteral != null) {
			return ParamName.valueOf(controlNameLiteral);
		} else {
			return null;
		}
	}
}