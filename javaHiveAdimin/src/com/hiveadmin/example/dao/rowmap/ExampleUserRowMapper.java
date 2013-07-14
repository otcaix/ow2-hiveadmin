package com.hiveadmin.example.dao.rowmap;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hiveadmin.example.beans.UserExample;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

public class ExampleUserRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int index) throws SQLException {
		return new UserExample(rs.getInt("id"),rs.getString("name"));	
		
	}

}
