package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.crayons_2_0.component.Unit;
import com.crayons_2_0.model.graph.UnitNode.UnitType;

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

	@Autowired
	JdbcTemplate jdbcTemplate;

	// public void createDbTable() {
	// jdbcTemplate.execute("create table if not exists units (title varchar(100), unitType varchar(100))");
	// }

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

				String title = rs.getString("title");
				UnitType unitType = createUnitType(rs.getString("unitType"));
				String content = rs.getString("content");
				String courseTitel = rs.getString("course");
				Unit unit = new Unit(title, unitType, courseTitel, content);
				return unit;
			}

		};
		return jdbcTemplate.query(query, mapper);

	}

	private UnitType createUnitType(String string) {
		// TODO
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return UnitType.LEARNING;
	}

	public boolean save(Unit unit) {
		return true;
	}

	/*
	 * public void save(Course course) { String query =
	 * "insert into courses (name) values (?)"; jdbcTemplate.update(query, new
	 * Object[]{course.getTitle()}); }
	 * 
	 * private UnitType createUnitType(String unitType) { //
	 * TODO!!!!!!!!!!!!!!!!!!!!!!!! (Ergänzen) if
	 * (unitType.equals(UnitType.START)) { return UnitType.START; } else if
	 * (unitType.equals(UnitType.LEARNING)) { return UnitType.LEARNING; } else {
	 * return null; } }
	 * 
	 * protected Graph getGraphFromAnyway() { // TODO !!!!!!!!!!!!!!!!!!!!!!!!!
	 * return null; }
	 */

}
