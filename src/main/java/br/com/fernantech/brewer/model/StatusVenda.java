package br.com.fernantech.brewer.model;

public enum StatusVenda {

	ORCAMENTO("Orçamento"), EMITIDA("Emitida"), CANCELADA("Cancelada");
	
	private StatusVenda(String descricao) {
		this.descricao = descricao;
	}
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
