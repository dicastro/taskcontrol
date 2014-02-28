/**
 * This class is generated by jOOQ
 */
package com.qopuir.taskcontrol.model.h2.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Controls extends org.jooq.impl.TableImpl<com.qopuir.taskcontrol.model.h2.tables.records.ControlsRecord> {

	private static final long serialVersionUID = 912858875;

	/**
	 * The singleton instance of <code>PUBLIC.CONTROLS</code>
	 */
	public static final com.qopuir.taskcontrol.model.h2.tables.Controls CONTROLS = new com.qopuir.taskcontrol.model.h2.tables.Controls();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.qopuir.taskcontrol.model.h2.tables.records.ControlsRecord> getRecordType() {
		return com.qopuir.taskcontrol.model.h2.tables.records.ControlsRecord.class;
	}

	/**
	 * The column <code>PUBLIC.CONTROLS.NAME</code>.
	 */
	public final org.jooq.TableField<com.qopuir.taskcontrol.model.h2.tables.records.ControlsRecord, java.lang.String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "");

	/**
	 * The column <code>PUBLIC.CONTROLS.DESCRIPTION</code>.
	 */
	public final org.jooq.TableField<com.qopuir.taskcontrol.model.h2.tables.records.ControlsRecord, java.lang.String> DESCRIPTION = createField("DESCRIPTION", org.jooq.impl.SQLDataType.VARCHAR.length(2000), this, "");

	/**
	 * Create a <code>PUBLIC.CONTROLS</code> table reference
	 */
	public Controls() {
		this("CONTROLS", null);
	}

	/**
	 * Create an aliased <code>PUBLIC.CONTROLS</code> table reference
	 */
	public Controls(java.lang.String alias) {
		this(alias, com.qopuir.taskcontrol.model.h2.tables.Controls.CONTROLS);
	}

	private Controls(java.lang.String alias, org.jooq.Table<com.qopuir.taskcontrol.model.h2.tables.records.ControlsRecord> aliased) {
		this(alias, aliased, null);
	}

	private Controls(java.lang.String alias, org.jooq.Table<com.qopuir.taskcontrol.model.h2.tables.records.ControlsRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.qopuir.taskcontrol.model.h2.Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.qopuir.taskcontrol.model.h2.tables.records.ControlsRecord> getPrimaryKey() {
		return com.qopuir.taskcontrol.model.h2.Keys.CONSTRAINT_C;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.qopuir.taskcontrol.model.h2.tables.records.ControlsRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.qopuir.taskcontrol.model.h2.tables.records.ControlsRecord>>asList(com.qopuir.taskcontrol.model.h2.Keys.CONSTRAINT_C);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.qopuir.taskcontrol.model.h2.tables.Controls as(java.lang.String alias) {
		return new com.qopuir.taskcontrol.model.h2.tables.Controls(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.qopuir.taskcontrol.model.h2.tables.Controls rename(java.lang.String name) {
		return new com.qopuir.taskcontrol.model.h2.tables.Controls(name, null);
	}
}
