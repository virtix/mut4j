package edu.gmu.mut4j;

public class DBMutProxy {
	
	Class cut;
	public DBMutProxy(Class cut){
		this.cut = cut;
	}
	
	public static Object createProxy(Object cut){
		return cut;
	}

}
