package br.com.fernantech.brewer.dto;

import java.math.BigDecimal;

public class ValorItensEstoque {
	
	public ValorItensEstoque(BigDecimal valorEstoque, Long quantidadeEstoque) {
		this.valorEstoque = valorEstoque;
		this.quantidadeEstoque = quantidadeEstoque;
	}
	private BigDecimal valorEstoque;
	private Long quantidadeEstoque;
	
	public BigDecimal getValorEstoque() {
		return valorEstoque;
	}
	public void setValorEstoque(BigDecimal valorEstoque) {
		this.valorEstoque = valorEstoque;
	}
	public Long getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(Long quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	
	
}
