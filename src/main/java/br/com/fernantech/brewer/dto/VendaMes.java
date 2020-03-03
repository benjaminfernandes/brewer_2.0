package br.com.fernantech.brewer.dto;

public class VendaMes {

	public VendaMes(String mes, Integer total) {
		this.mes = mes;
		this.total = total;
	}
	
	public VendaMes() {
	}
	
	private String mes;
	private Integer total;
	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
	
}
