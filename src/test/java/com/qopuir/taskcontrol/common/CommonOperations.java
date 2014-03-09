package com.qopuir.taskcontrol.common;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

import com.ninja_squad.dbsetup.operation.Operation;
import com.qopuir.taskcontrol.entities.enums.ControlName;

public class CommonOperations {
	public static final Operation DELETE_ALL = deleteAllFrom("USER_CONTROLS", "USERS", "CONTROL_SCHEDULES", "CONTROLS");
	
	public static final Operation INSERT_REFERENCE_DATA =
	        sequenceOf(
	            insertInto("USERS")
	                .columns("USERNAME", "PASSWORD", "EMAIL")
	                .values("d_castro", "d_castro", "diego.castro@sopragroup.com")
	                .useMetadata(false)
	                .build(),
                insertInto("CONTROLS")
	                .columns("NAME", "DESCRIPTION")
	                .withBinder(new EnumTypeBinder(), "NAME")
	                .row().column("NAME", ControlName.PROJECTION_SEMANAL).column("DESCRIPTION", "Comprueba que los usuarios hayan imputado las horas en el projection hasta un determinado día de la semana. Envía un email a los que no lo hayan hecho.").end()
	                .row().column("NAME", ControlName.PROJECTION_MENSUAL).column("DESCRIPTION", "Comprueba que los usuarios hayan imputado las horas de todo el mes en curso. Envía un email a los que no lo hayan hecho.").end()
	                .useMetadata(false)
	                .build()
	        );
}