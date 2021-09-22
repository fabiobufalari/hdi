package br.com.bufalari.hdi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bufalari.hdi.controller.dto.CorretorDto;
import br.com.bufalari.hdi.service.CorretorService;

@RestController
@RequestMapping("/corretor")
public class CorretorController {

	@Autowired
	private CorretorService corretorService;
	
	@GetMapping("/{document}")
	public ResponseEntity<CorretorDto>  buscarStatusCorretor(@PathVariable String document) throws Exception {
		CorretorDto retorno = corretorService.buscarStatusCorretor(document);
		
		return ResponseEntity.ok(retorno);
	}
}
