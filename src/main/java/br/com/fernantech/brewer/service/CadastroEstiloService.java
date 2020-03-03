package br.com.fernantech.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fernantech.brewer.model.Estilo;
import br.com.fernantech.brewer.repository.Estilos;
import br.com.fernantech.brewer.service.exception.ImpossivelExcluirEntidadeException;
import br.com.fernantech.brewer.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {

	@Autowired
	private Estilos estilos;

	@Transactional
	public Estilo salvar(Estilo estilo) {

		Optional<Estilo> estiloOptional = estilos.findByNomeIgnoreCase(estilo.getNome());

		if (estiloOptional.isPresent()) {
			throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado!");
		}

		return this.estilos.saveAndFlush(estilo);
	}

	@Transactional
	public void excluir(Estilo estilo) {

		try {
			this.estilos.delete(estilo);
			this.estilos.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException(
					"Não foi possível excluir o estilo! Este estilo foi utilizado!");
		}
	}
}
