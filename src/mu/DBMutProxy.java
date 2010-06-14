package mu;

public class DBMutProxy {
	
	Class cut;
	public DBMutProxy(Class cut){
		this.cut = cut;
	}
	
	public static Object createProxy(Object cut){
		return cut;
	}

}
