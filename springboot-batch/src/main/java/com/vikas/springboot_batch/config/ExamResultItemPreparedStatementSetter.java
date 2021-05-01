package com.vikas.springboot_batch.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.vikas.springboot_batch.schemas.ExamResult;

public class ExamResultItemPreparedStatementSetter implements ItemPreparedStatementSetter<ExamResult> {
	 
    public void setValues(ExamResult result, PreparedStatement ps) throws SQLException {
        ps.setString(1, result.getStudentName());
        //ps.setDate(2, new java.sql.Date(result.getDob().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())));
        ps.setDate(2, java.sql.Date.valueOf(result.getDob()));
        ps.setDouble(3, result.getPercentage());
    }
 
}
