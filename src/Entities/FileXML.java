package Entities;

import java.io.Serializable;

public class FileXML  implements Serializable{
	private String Active,Closed,Canceled,Locked;
	private double Dac,Dcl,Dca,Dl;
	public FileXML() {
		
	}
	public void setAll(String a,String cl,String ca,String l,double dac,double dcl,double dca,double dl) {
		this.Active=a;
		this.Dac=dac;
		this.Closed=cl;
		this.Dcl=dcl;
		this.Canceled=ca;
		this.Dca=dca;
		this.Locked=l;
		this.Dl=dl;
	}

	public String getActive() {
		return Active;
	}

	public void setActive(String active) {
		Active = active;
	}

	public String getClosed() {
		return Closed;
	}

	public void setClosed(String closed) {
		Closed = closed;
	}

	public String getCanceled() {
		return Canceled;
	}

	public void setCanceled(String canceled) {
		Canceled = canceled;
	}

	public String getLocked() {
		return Locked;
	}

	public void setLocked(String locked) {
		Locked = locked;
	}

	public double getDac() {
		return Dac;
	}

	public void setDac(double dac) {
		Dac = dac;
	}

	public double getDcl() {
		return Dcl;
	}

	public void setDcl(double dcl) {
		Dcl = dcl;
	}

	public double getDca() {
		return Dca;
	}

	public void setDca(double dca) {
		Dca = dca;
	}

	public double getDl() {
		return Dl;
	}

	public void setDl(double dl) {
		Dl = dl;
	}
}
