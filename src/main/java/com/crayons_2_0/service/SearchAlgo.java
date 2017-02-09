package com.crayons_2_0.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchAlgo {
	public Collection<CourseDisplay> getSeachResult(String input){
		
		CourseDisplay a = new CourseDisplay("28.05.2016", "Algorithmen I", "Peter Sachert", "Beigetreten");
		CourseDisplay b = new CourseDisplay("03.02.2014", "Betriebswirtschaft", "Simon Jäger", "Beigetreten");
		CourseDisplay c = new CourseDisplay("22.07.2015", "Algorithmen II", "Peter Sachert", "Privat");
		CourseDisplay d = new CourseDisplay("01.2.1016", "Höhere Mathematik", "Sahra Remmert", "Beigetreten");
		CourseDisplay e = new CourseDisplay("28.5.1016", "Physik I", "Björn Siemann", "Autor");
		
		List<CourseDisplay> collection = new ArrayList<CourseDisplay>();
		collection.add(a);
		collection.add(b);
		collection.add(c);
		collection.add(d);
		collection.add(e);
		return collection;
	}
}
