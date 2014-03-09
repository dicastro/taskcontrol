package com.qopuir.taskcontrol.workflow;

import org.springframework.stereotype.Component;

import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;

@Component
public class ControlScheduleWorkflow {
	public boolean checkAction(ControlScheduleStatus currentStatus, ControlScheduleStatus newStatus) {
		switch (currentStatus) {
			case PENDING: {
				switch (newStatus) {
					case RUNNING:
					case PAUSED:
					case FINISHED: {
						return true;
					}
					default: {
						return false;
					}
				}
			}
			case RUNNING: {
				switch (newStatus) {
					case PAUSED:
					case FINISHED: {
						return true;
					}
					default: {
						return false;
					}
				}
			}
			case PAUSED: {
				switch (newStatus) {
					case RUNNING:
					case FINISHED: {
						return true;
					}
					default: {
						return false;
					}
				}
			}
			case FINISHED: {
				return false;
			}
			default: {
				return false;
			}
		}
	}
}