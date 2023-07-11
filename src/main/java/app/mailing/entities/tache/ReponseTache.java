package app.mailing.entities.tache;

import java.util.ArrayList;

public class ReponseTache {
	private int size;
	private int total;
	private int start;
	private ArrayList<Tache> data;
	public ReponseTache(int size, int total, int start, ArrayList<Tache> data) {
		super();
		this.size = size;
		this.total = total;
		this.start = start;
		this.data = data;
	}
	public ReponseTache() {
		super();
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public ArrayList<Tache> getData() {
		return data;
	}
	public void setData(ArrayList<Tache> data) {
		this.data = data;
	}
	
}
