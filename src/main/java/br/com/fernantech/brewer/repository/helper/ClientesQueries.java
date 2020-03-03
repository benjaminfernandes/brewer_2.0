package br.com.fernantech.brewer.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fernantech.brewer.model.Cliente;
import br.com.fernantech.brewer.repository.filter.ClienteFilter;

public interface ClientesQueries {

	public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageAble);
	
	public Cliente buscaClienteComEnderecoCompleto(Long codigo);
}
