package com.crayons_2_0.service.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;
import com.crayons_2_0.model.graph.UnitNode.UnitType;

public class UnitDAO {

	@Autowired
    JdbcTemplate jdbcTemplate;

    public void createDbTable() {
        jdbcTemplate.execute("create table if not exists units (title varchar(100), unitType varchar(100))");
    }

    public List<UnitNode> findAll() {
        /*String query = "select * from units";
        RowMapper mapper = new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                
            	String title = rs.getString("title");
                UnitType unitType = createUnitType(rs.getString("unitType"));
                Graph graph = getGraphFromAnyway();

                Unit unit = new Unit("myunit", unitType, true, graph);
                return unit;
            }

			
        };
        return jdbcTemplate.query(query, mapper);*/
        return null;
    }
    
    public boolean save(UnitNode unit) {
        return true;
    }

	/*public void save(Course course) {
        String query = "insert into courses (name) values (?)";
        jdbcTemplate.update(query, new Object[]{course.getTitle()});
    }
    
    private UnitType createUnitType(String unitType) {
    	// TODO!!!!!!!!!!!!!!!!!!!!!!!! (Ergänzen)
		if (unitType.equals(UnitType.START)) {
			return UnitType.START;
		} else if (unitType.equals(UnitType.LEARNING)) {
			return UnitType.LEARNING;
		} else {
			return null;
		}
	}
    
    protected Graph getGraphFromAnyway() {
    	// TODO !!!!!!!!!!!!!!!!!!!!!!!!!
		return null;
	}*/
	
}
