package com.eda.rest.model.dao;

import com.eda.rest.model.ErrorLog;

public interface ILogDao {
	public boolean createLog(ErrorLog errorLog);

	public boolean deletelog(String model);
}
