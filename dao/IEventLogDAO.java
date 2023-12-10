package com.ms.datawise.distn.dao;
import com.ms.datawise.distn.entity.EventLog;
public interface IEventLogDAO {
	public EventLog saveEventLog (Event Log eventLogEntity); 
	public void saveEventLogAsync (EventLog event LogEntity); 
	public EventLog addEventLog (EventLog eventLogEntity); 
	public void addEventLogAsync (EventLog eventLogEntity);
}