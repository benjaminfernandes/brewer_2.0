package br.com.fernantech.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fernantech.brewer.model.Estilo;
import br.com.fernantech.brewer.repository.helper.EstilosQueries;

@Repository
public interface Estilos extends JpaRepository<Estilo, Long>, EstilosQueries{

	public Optional<Estilo> findByNomeIgnoreCase(String nome);
	
}
