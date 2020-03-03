package br.com.fernantech.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fernantech.brewer.model.Cidade;
import br.com.fernantech.brewer.model.Cliente;
import br.com.fernantech.brewer.repository.Cidades;
import br.com.fernantech.brewer.repository.Clientes;
import br.com.fernantech.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import br.com.fernantech.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroClienteService {

	@Autowired
	private Clientes clientes;
	@Autowired
	private Cidades cidades;
	
	@Transactional
	public void salvar(Cliente cliente) {
		
		if(cliente.isNovo()) {
			this.clientes.findByCpfOuCnpj(cliente.getCpfCnpjSemFormatacao()).ifPresent(
						c -> {
							throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado!");
					});
		}
		
		Optional<Cliente> clienteExistente = this.clientes.findByCpfOuCnpj(cliente.getCpfCnpjSemFormatacao());
		
		if(clienteExistente.isPresent() && cliente.isNovo()) {
			
		}
		
		this.clientes.save(cliente);
	}

	@Transactional
	public void excluir(Cliente cliente) {
		
		try {
			this.clientes.delete(cliente);
			this.clientes.flush();
		}catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir este cliente");
		}
	}

	@Transactional(readOnly = true)
	public void comporDadosEndereco(Cliente cliente) {
		
		if(cliente.getEndereco() == null || cliente.getEndereco().getCidade() == null
				|| cliente.getEndereco().getCidade().getCodigo() == null) {
			return;
		}
		
		Cidade cidade = this.cidades.findByCodigoFetchingEstado(cliente.getEndereco().getCidade().getCodigo());
		
		cliente.getEndereco().setCidade(cidade);
		cliente.getEndereco().setEstado(cidade.getEstado());
	}
}
