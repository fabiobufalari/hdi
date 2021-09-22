package br.com.bufalari.hdi.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.bufalari.hdi.controller.dto.CorretorDto;
import br.com.bufalari.hdi.controller.dto.CorretorStatusDto;

@Service
public class CorretorService {

	public CorretorDto buscarStatusCorretor(String document) throws Exception {
		CorretorDto corretoDto = new CorretorDto();
		corretoDto.setDocumento(document);
		
		CorretorDto corretorDto = consultaCorretorPorDocumento(corretoDto);
				
		return corretorDto;
	}
	

	private CorretorDto consultaCorretorPorId(CorretorDto corretorDto, String idCorretor) throws Exception {
		final String URI = "https://607732991ed0ae0017d6a9b0.mockapi.io/insurance/v1/brokerData/"+idCorretor;
		RestTemplate buscaCorretor = new RestTemplate();
		
//		CorretorDto corretoDto = new CorretorDto();
		
		ResponseEntity<CorretorStatusDto> response = buscaCorretor.exchange(URI,
				HttpMethod.GET, 
				null,
				CorretorStatusDto.class);
		try {
			if(response.getBody().isActive()) {
				corretorDto.setActive(response.getBody().isActive());
				corretorDto.setTaxaComissao(response.getBody().getCommissionRate());
				
			}else {
				Exception erro = new Exception();
			      throw erro;
			}
		} catch (Exception e) {
			throw new Exception("Corretor Com Status Inativo");
			
		}
		
		return corretorDto;
	}


	private CorretorDto consultaCorretorPorDocumento(CorretorDto corretorDto) throws Exception {
		
		final String URI ="https://607732991ed0ae0017d6a9b0.mockapi.io/insurance/v1/broker/"+corretorDto.getDocumento();
		RestTemplate buscaCorretor = new RestTemplate();
		
		ResponseEntity<CorretorStatusDto> response = buscaCorretor.exchange(URI,
				HttpMethod.GET, 
				null,
				CorretorStatusDto.class);
				String idCorretor = response.getBody().getCode();
		
				corretorDto.setNome(response.getBody().getName());
				corretorDto.setDocumento(response.getBody().getDocument());
				corretorDto.setCodigo(Integer.parseInt(idCorretor));
				corretorDto.setDataCriação(response.getBody().getCreateDate());
				corretorDto.setTaxaComissao(response.getBody().getCommissionRate());			
			
		consultaCorretorPorId(corretorDto, idCorretor);
		return corretorDto;
	}

}
