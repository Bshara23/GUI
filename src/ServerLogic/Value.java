package ServerLogic;

import java.sql.Date;
// TODO: check if such class is needed, sql probably works fine even if all fields has the ' ' around them!!
public class Value {
	private String str;
	private Long l;
	private Integer i;
	private Date date;
	
	public Value(String str) {
		this.str = str;
	}
	public Value(Long l) {
		this.l = l;
	
	}
	public Value(Integer i) {
		this.i = i;
	}
	public Value(Date date) {	
		this.date = date;
	}
	
	public String toString() {
		
		
		if (str != null)
			return "'"+str+"'";
		
		if (l != null)
			return l.toString();
		
		if (i != null)
			return i.toString();
		
		// TODO: need to validate if this works with queries
		if(date != null)
			return date.toString();
		
		return "NULL";
	}
	
}
