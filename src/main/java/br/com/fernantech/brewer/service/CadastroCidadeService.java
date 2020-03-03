package br.com.fernantech.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fernantech.brewer.model.Cidade;
import br.com.fernantech.brewer.repository.Cidades;
import br.com.fernantech.brewer.service.exception.NomeCidadeJaCadastradaException;

@Service
public class CadastroCidadeService {

	@Autowired
	private Cidades cidades;
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		
		Optional<Cidade> cidadeOptional = this.cidades.findByNomeIgnoreCaseAndEstado(cidade.getNome(), cidade.getEstado());
		
		if(cidadeOptional.isPresent()) {
			throw new NomeCidadeJaCadastradaException("Nome já cadastrado");
		}
		
		return this.cidades.saveAndFlush(cidade);
	}
}
