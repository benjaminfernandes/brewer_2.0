package br.com.fernantech.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fernantech.brewer.controller.page.PageWrapper;
import br.com.fernantech.brewer.model.Cliente;
import br.com.fernantech.brewer.model.TipoPessoa;
import br.com.fernantech.brewer.repository.Clientes;
import br.com.fernantech.brewer.repository.Estados;
import br.com.fernantech.brewer.repository.filter.ClienteFilter;
import br.com.fernantech.brewer.service.CadastroClienteService;
import br.com.fernantech.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import br.com.fernantech.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

	@Autowired
	private Estados estados;
	@Autowired
	private Clientes clientes;
	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estados.findAll());
		
		return mv;
	}
	
	@PostMapping(value = {"/novo", "{\\d+}"})
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return this.novo(cliente);
		}
		try {
			this.cadastroClienteService.salvar(cliente);
		}catch(CpfCnpjClienteJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return this.novo(cliente);
		}
		
		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
		return new ModelAndView("redirect:/clientes/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(ClienteFilter clienteFilter, @PageableDefault(size = 10) Pageable pageAble, 
			HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/cliente/PesquisaCliente");
		
		PageWrapper<Cliente> paginaWrapper = new PageWrapper<>(this.clientes.filtrar(clienteFilter, pageAble), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Cliente> pesquisar(String nome){
		validarTamanhoNome(nome);
		return this.clientes.findByNomeStartingWithIgnoreCase(nome);
	}
	
	//aqui trata o erro gerado Aula 22.3
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cliente cliente){
		
		try {
			this.cadastroClienteService.excluir(cliente);
		}catch(ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
		
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Cliente cliente) {
		
		ModelAndView mv = novo(cliente);
		this.cadastroClienteService.comporDadosEndereco(cliente);		
		mv.addObject(cliente);
	
		return mv;
	}

	private void validarTamanhoNome(String nome) {
		if(StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException(); //aqui vai retornar um erro
		}
	}
	
}
