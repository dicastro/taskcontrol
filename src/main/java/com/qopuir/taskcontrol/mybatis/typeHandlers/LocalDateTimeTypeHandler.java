package com.qopuir.taskcontrol.mybatis.typeHandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

public class LocalDateTimeTypeHandler implements TypeHandler<LocalDateTime> {
	@Override
	public void setParameter(PreparedStatement ps, int columnIndex, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			ps.setTimestamp(columnIndex, new Timestamp(parameter.toDateTime(DateTimeZone.UTC).toDate().getTime()));
		} else {
			ps.setNull(columnIndex, jdbcType.TYPE_CODE);
		}
	}

	@Override
	public LocalDateTime getResult(ResultSet rs, String columnName) throws SQLException {
		Timestamp timestamp = rs.getTimestamp(columnName);
		
		if (timestamp != null) {
			return new LocalDateTime(timestamp.getTime(), DateTimeZone.UTC);
		} else {
			return null;
		}
	}

	@Override
	public LocalDateTime getResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp timestamp = rs.getTimestamp(columnIndex);
		
		if (timestamp != null) {
			return new LocalDateTime(timestamp.getTime(), DateTimeZone.UTC);
		} else {
			return null;
		}
	}

	@Override
	public LocalDateTime getResult(CallableStatement cs, int columnIndex) throws SQLException {
		Timestamp timestamp = cs.getTimestamp(columnIndex);
		
		if (timestamp != null) {
			return new LocalDateTime(timestamp.getTime(), DateTimeZone.UTC);
		} else {
			return null;
		}
	}
}