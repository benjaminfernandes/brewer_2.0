package br.com.fernantech.brewer.model;

public enum Origem {

	NACIONAL("Nacional"), INTERNACIONAL("Internacional");
	
	private Origem(String descricao) {
		this.descricao = descricao;
	}
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}
}
