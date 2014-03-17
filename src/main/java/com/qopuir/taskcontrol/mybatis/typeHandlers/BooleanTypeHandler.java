package com.qopuir.taskcontrol.mybatis.typeHandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class BooleanTypeHandler implements TypeHandler<Boolean> {
	@Override
	public void setParameter(PreparedStatement ps, int columnIndex, Boolean parameter, JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			ps.setBoolean(columnIndex, parameter);
		} else {
			ps.setNull(columnIndex, jdbcType.TYPE_CODE);
		}
	}

	@Override
	public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getBoolean(columnName);
	}

	@Override
	public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getBoolean(columnIndex);
	}

	@Override
	public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getBoolean(columnIndex);
	}
}