package com.qopuir.taskcontrol.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qopuir.taskcontrol.entities.ControlParamVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;

public interface ControlParamDAO {
    /**
     * Get list of params of control
     */
    List<ControlParamVO> listControlParams(@Param("controlName") ControlName controlName);
}