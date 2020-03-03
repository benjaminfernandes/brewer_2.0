package br.com.fernantech.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.fernantech.brewer.model.Cidade;
import br.com.fernantech.brewer.model.Estado;
import br.com.fernantech.brewer.repository.helper.CidadesQueries;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long>, CidadesQueries {

	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
	
	public Optional<Cidade> findByNomeIgnoreCase(String nome);
	
	public Optional<Cidade> findByNomeIgnoreCaseAndEstado(String nome, Estado estado);

	@Query("select c from Cidade c join fetch c.estado where c.codigo = :codigo")
	public Cidade findByCodigoFetchingEstado(@Param("codigo") Long codigo);
}
