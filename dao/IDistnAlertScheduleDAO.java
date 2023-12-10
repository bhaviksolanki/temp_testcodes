package com.ms.datawise.distn.dao;

import com.ms.datawise.distn.entity.DistnAlertSchedule; 
public interface IDistnAlertScheduleDAO {
	
	DistnAlertSchedule getAlertSchedule (String productId);
	void setAlertSchedule (DistnAlertSchedule newAlert);
}