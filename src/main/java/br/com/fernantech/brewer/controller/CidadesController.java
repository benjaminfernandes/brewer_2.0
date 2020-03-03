package br.com.fernantech.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fernantech.brewer.controller.page.PageWrapper;
import br.com.fernantech.brewer.model.Cidade;
import br.com.fernantech.brewer.repository.Cidades;
import br.com.fernantech.brewer.repository.Estados;
import br.com.fernantech.brewer.repository.filter.CidadeFilter;
import br.com.fernantech.brewer.service.CadastroCidadeService;
import br.com.fernantech.brewer.service.exception.NomeCidadeJaCadastradaException;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private Cidades cidades;
	@Autowired
	private Estados estados;
	@Autowired
	private CadastroCidadeService cadastroCidadeService;

	@RequestMapping("/novo")
	public ModelAndView novo(Cidade cidade) {
		ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
		mv.addObject("estados", this.estados.findAll());
		return mv;
	}
	
	@PostMapping("/novo")
	@CacheEvict(value = "cidades", key = "#cidade.estado.codigo", condition = "#cidade.temEstado()")
	public ModelAndView cadastrar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(cidade);
		}
		
		try {
			this.cadastroCidadeService.salvar(cidade);
		}catch (NomeCidadeJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(cidade);
		}
		
		attributes.addFlashAttribute("mensagem", "Cidade cadastrada com sucesso!");
		return new ModelAndView("redirect:/cidades/novo");
	}
	
	//aula 16.7
	@Cacheable(value = "cidades", key = "#codigoEstado")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado){
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.cidades.findByEstadoCodigo(codigoEstado);		
	}
	
	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest httpServletRequest, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("cidade/PesquisaCidades");
		mv.addObject("estados", this.estados.findAll());
		
		PageWrapper<Cidade> cidadeWraper = new PageWrapper<>(this.cidades.filtrar(cidadeFilter, pageable), httpServletRequest);
		mv.addObject("pagina", cidadeWraper);
		
		return mv;
	}
}
