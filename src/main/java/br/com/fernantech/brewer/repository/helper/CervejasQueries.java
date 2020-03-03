package br.com.fernantech.brewer.repository.helper;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fernantech.brewer.dto.CervejaDTO;
import br.com.fernantech.brewer.dto.ValorItensEstoque;
import br.com.fernantech.brewer.model.Cerveja;
import br.com.fernantech.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {

	public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable);
	
	public List<CervejaDTO> porSkuOuNome(String skuOuNome);
	
	public ValorItensEstoque valorItensEstoque();
}
