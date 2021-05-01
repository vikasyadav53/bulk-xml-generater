package com.vikas.desktopapplication.xmldbbatchprocessor.utils;

import org.springframework.batch.item.ItemProcessor;

import com.vikas.desktopapplication.xmldbbatchprocessor.models.EmployeeD;
import com.vikas.desktopapplication.xmldbbatchprocessor.schemas.Employee;

public class XmlToDBItemProcessor implements ItemProcessor<Employee, EmployeeD>{

	@Override
	public EmployeeD process(Employee item) throws Exception {
		return null;
	}

}
