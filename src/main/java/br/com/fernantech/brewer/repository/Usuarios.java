package br.com.fernantech.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fernantech.brewer.model.Usuario;
import br.com.fernantech.brewer.repository.helper.UsuariosQueries;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries{

	public Optional<Usuario> findByEmailIgnoreCase(String email);

	public List<Usuario> findByCodigoIn(Long[] codigos);
}
