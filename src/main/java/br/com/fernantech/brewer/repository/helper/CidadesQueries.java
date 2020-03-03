package br.com.fernantech.brewer.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fernantech.brewer.model.Cidade;
import br.com.fernantech.brewer.repository.filter.CidadeFilter;

public interface CidadesQueries {

	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageAble);
}
