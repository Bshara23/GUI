package Protocol;

import java.io.Serializable;

public class SeriObject implements Serializable {

	private String str;
	private long longNum;
	private int integer;
	
	
	
	public SeriObject(String str) {
		this.str = str;
	}

	public SeriObject(long longNum) {
		this.longNum = longNum;
	}
	public SeriObject(int integer) {
		this.integer = integer;
	}
	
	
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public long getLongNum() {
		return longNum;
	}
	public void setLongNum(long longNum) {
		this.longNum = longNum;
	}
	public int getInteger() {
		return integer;
	}
	public void setInteger(int integer) {
		this.integer = integer;
	}
	
	
	
}
