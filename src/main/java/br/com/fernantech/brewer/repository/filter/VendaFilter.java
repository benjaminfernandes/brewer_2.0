package br.com.fernantech.brewer.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.fernantech.brewer.model.StatusVenda;

public class VendaFilter {

	private Long codigo;
	private StatusVenda statusVenda;
	
	private String nomeCliente;
	private String cpfOuCnpjCliente;
	
	private LocalDate dataComeco;
	private LocalDate dataFim;
	
	private BigDecimal valorMinimo;
	private BigDecimal valorMaximo;
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public StatusVenda getStatusVenda() {
		return statusVenda;
	}
	public void setStatusVenda(StatusVenda statusVenda) {
		this.statusVenda = statusVenda;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getCpfOuCnpjCliente() {
		return cpfOuCnpjCliente;
	}
	public void setCpfOuCnpjCliente(String cpfOuCnpjCliente) {
		this.cpfOuCnpjCliente = cpfOuCnpjCliente;
	}
	public LocalDate getDataComeco() {
		return dataComeco;
	}
	public void setDataComeco(LocalDate dataComeco) {
		this.dataComeco = dataComeco;
	}
	public LocalDate getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}
	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}
	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	
	
}
