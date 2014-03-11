package com.qopuir.taskcontrol.workflow;

import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleAction;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;

@Component
public class ControlScheduleWorkflow {
	public boolean checkAction(ControlScheduleVO controlSchedule, ControlScheduleAction action) {
		LocalDateTime now = new LocalDateTime().withMillisOfSecond(0);
		
		switch (controlSchedule.getStatus()) {
			case PENDING: {
				switch (action) {
					case START: {
						return isWithinPeriod(controlSchedule.getStart(), controlSchedule.getEnd(), now);
					}
					case STOP: {
						return true;
					}
					case PAUSE: {
						return false;
					}
					default: {
						return false;
					}
				}
			}
			case RUNNING: {
				switch (action) {
					case START: {
						return false;
					}
					case STOP: {
						return true;
					}
					case PAUSE: {
						return isWithinPeriod(controlSchedule.getStart(), controlSchedule.getEnd(), now);
					}
					default: {
						return false;
					}
				}
			}
			case PAUSED: {
				switch (action) {
					case START: {
						return isWithinPeriod(controlSchedule.getStart(), controlSchedule.getEnd(), now);
					}
					case STOP: {
						return true;
					}
					case PAUSE: {
						return false;
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
	
	public ControlScheduleStatus getNextStatus(ControlScheduleStatus currentStatus, ControlScheduleAction action) {
		switch (currentStatus) {
			case PENDING: {
				switch (action) {
					case START: {
						return ControlScheduleStatus.RUNNING;
					}
					case STOP: {
						return ControlScheduleStatus.FINISHED;
					}
					case PAUSE: {
						return null;
					}
					default: {
						return null;
					}
				}
			}
			case RUNNING: {
				switch (action) {
					case START: {
						return null;
					}
					case STOP: {
						return ControlScheduleStatus.FINISHED;
					}
					case PAUSE: {
						return ControlScheduleStatus.PAUSED;
					}
					default: {
						return null;
					}
				}
			}
			case PAUSED: {
				switch (action) {
					case START: {
						return ControlScheduleStatus.RUNNING;
					}
					case STOP: {
						return ControlScheduleStatus.FINISHED;
					}
					case PAUSE: {
						return null;
					}
					default: {
						return null;
					}
				}
			}
			case FINISHED: {
				return null;
			}
			default: {
				return null;
			}
		}
	}
	
	private boolean isWithinPeriod(LocalDateTime periodStart, LocalDateTime periodEnd, LocalDateTime now) {
		return (now.equals(periodStart) || now.isAfter(periodStart)) && (now.equals(periodEnd) || now.isBefore(periodEnd));
	}
}