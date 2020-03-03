package br.com.fernantech.brewer.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fernantech.brewer.model.Estilo;
import br.com.fernantech.brewer.repository.filter.EstiloFilter;

public interface EstilosQueries {

	public Page<Estilo> filtrar(EstiloFilter filtro, Pageable pageable );
}
