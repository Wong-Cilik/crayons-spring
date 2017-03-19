package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.crayons_2_0.component.Unit;
import com.crayons_2_0.model.graph.UnitNode.UnitType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Class for Unit Data Access Object
 *
 */

@Component
public class UnitDAO {

	private @Autowired JdbcTemplate jdbcTemplate;

	/**
	 * Returns all units of DB
	 * 
	 * @return all units of DB
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Unit> findAll() {
		String query = "select * from units";
		RowMapper mapper = new RowMapper<Object>() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String unitTitle = rs.getString("unittitle");
				String courseTitle = rs.getString("coursetitle");
				Unit unit = new Unit(unitTitle, courseTitle);
				return unit;
			}

		};
		return jdbcTemplate.query(query, mapper);

	}

	@SuppressWarnings("unused")
	private UnitType createUnitType(String string) {
		return UnitType.LEARNING;
	}

	public void saveData(File file, String titleUnit, String titleCourse)
			throws IOException {
		FileInputStream fis = new FileInputStream(file);
		jdbcTemplate.update("UPDATE units SET data=? WHERE unitTitle=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setBinaryStream(1, fis, (int) file.length());
						ps.setString(2, titleUnit);
					}
				});
		fis.close();
	}

	public void insertUnit(String unitTitle, String courseTitle) {
		List<Unit> unitList = findAll();
		for (Unit tmpUnit : unitList) {
			if (tmpUnit.getTitle().equals(unitTitle)) {
				return;
			}
		}
		jdbcTemplate.update(
				"insert into units (coursetitle, unittitle) VALUES (?, ?)",
				courseTitle, unitTitle);
	}

	public void getData(String unitTitle, String courseTitle)
			throws IOException {
		File file = new File(unitTitle + ".bin");
		FileOutputStream fos = new FileOutputStream(file);
		byte[] data = jdbcTemplate.queryForObject(
				"SELECT data FROM units WHERE unittitle = ?", byte[].class,
				unitTitle);
		fos.write(data, 0, data.length);
		fos.flush();
		fos.close();
	}

	public boolean hasData(String unitTitle, String courseTitle) {
		byte[] data = jdbcTemplate.queryForObject(
				"SELECT data FROM units WHERE unittitle = ?", byte[].class,
				unitTitle);
		if (data == null) {
			return false;
		}
		return true;
	}
}
