/**
 * This class is generated by jOOQ
 */
package com.qopuir.taskcontrol.model.h2.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ControlsScheduleRecord extends org.jooq.impl.UpdatableRecordImpl<com.qopuir.taskcontrol.model.h2.tables.records.ControlsScheduleRecord> implements org.jooq.Record6<java.lang.Long, java.lang.String, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.String> {

	private static final long serialVersionUID = 1568196172;

	/**
	 * Setter for <code>PUBLIC.CONTROLS_SCHEDULE.ID</code>.
	 */
	public void setId(java.lang.Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>PUBLIC.CONTROLS_SCHEDULE.ID</code>.
	 */
	public java.lang.Long getId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.CONTROLS_SCHEDULE.CONTROL_NAME</code>.
	 */
	public void setControlName(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>PUBLIC.CONTROLS_SCHEDULE.CONTROL_NAME</code>.
	 */
	public java.lang.String getControlName() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>PUBLIC.CONTROLS_SCHEDULE.START</code>.
	 */
	public void setStart(java.sql.Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>PUBLIC.CONTROLS_SCHEDULE.START</code>.
	 */
	public java.sql.Timestamp getStart() {
		return (java.sql.Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>PUBLIC.CONTROLS_SCHEDULE.END</code>.
	 */
	public void setEnd(java.sql.Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>PUBLIC.CONTROLS_SCHEDULE.END</code>.
	 */
	public java.sql.Timestamp getEnd() {
		return (java.sql.Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>PUBLIC.CONTROLS_SCHEDULE.STATUS</code>.
	 */
	public void setStatus(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>PUBLIC.CONTROLS_SCHEDULE.STATUS</code>.
	 */
	public java.lang.String getStatus() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>PUBLIC.CONTROLS_SCHEDULE.CRON</code>.
	 */
	public void setCron(java.lang.String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>PUBLIC.CONTROLS_SCHEDULE.CRON</code>.
	 */
	public java.lang.String getCron() {
		return (java.lang.String) getValue(5);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Long> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record6 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Long, java.lang.String, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row6) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row6<java.lang.Long, java.lang.String, java.sql.Timestamp, java.sql.Timestamp, java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row6) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return com.qopuir.taskcontrol.model.h2.tables.ControlsSchedule.CONTROLS_SCHEDULE.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.qopuir.taskcontrol.model.h2.tables.ControlsSchedule.CONTROLS_SCHEDULE.CONTROL_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field3() {
		return com.qopuir.taskcontrol.model.h2.tables.ControlsSchedule.CONTROLS_SCHEDULE.START;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field4() {
		return com.qopuir.taskcontrol.model.h2.tables.ControlsSchedule.CONTROLS_SCHEDULE.END;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.qopuir.taskcontrol.model.h2.tables.ControlsSchedule.CONTROLS_SCHEDULE.STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field6() {
		return com.qopuir.taskcontrol.model.h2.tables.ControlsSchedule.CONTROLS_SCHEDULE.CRON;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getControlName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value3() {
		return getStart();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value4() {
		return getEnd();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value6() {
		return getCron();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ControlsScheduleRecord value1(java.lang.Long value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ControlsScheduleRecord value2(java.lang.String value) {
		setControlName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ControlsScheduleRecord value3(java.sql.Timestamp value) {
		setStart(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ControlsScheduleRecord value4(java.sql.Timestamp value) {
		setEnd(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ControlsScheduleRecord value5(java.lang.String value) {
		setStatus(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ControlsScheduleRecord value6(java.lang.String value) {
		setCron(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ControlsScheduleRecord values(java.lang.Long value1, java.lang.String value2, java.sql.Timestamp value3, java.sql.Timestamp value4, java.lang.String value5, java.lang.String value6) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ControlsScheduleRecord
	 */
	public ControlsScheduleRecord() {
		super(com.qopuir.taskcontrol.model.h2.tables.ControlsSchedule.CONTROLS_SCHEDULE);
	}

	/**
	 * Create a detached, initialised ControlsScheduleRecord
	 */
	public ControlsScheduleRecord(java.lang.Long id, java.lang.String controlName, java.sql.Timestamp start, java.sql.Timestamp end, java.lang.String status, java.lang.String cron) {
		super(com.qopuir.taskcontrol.model.h2.tables.ControlsSchedule.CONTROLS_SCHEDULE);

		setValue(0, id);
		setValue(1, controlName);
		setValue(2, start);
		setValue(3, end);
		setValue(4, status);
		setValue(5, cron);
	}
}