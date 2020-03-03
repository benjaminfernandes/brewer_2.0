package br.com.fernantech.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fernantech.brewer.model.Grupo;

@Repository
public interface Grupos extends JpaRepository<Grupo, Long>{

}
