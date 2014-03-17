package com.qopuir.taskcontrol.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qopuir.taskcontrol.entities.ControlScheduleParamVO;

public interface ControlScheduleParamDAO {
    /**
     * Get list of params of control schedule
     */
    List<ControlScheduleParamVO> listControlParams(@Param("controlScheduleId") Long controlScheduleId);
}