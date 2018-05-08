package br.senai.sp.info.pweb.ianes.ws.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class MapHelper {
	
	public static Map<String, String> mapaDe(BindingResult bindingResult){
		Map<String, String> erros = new HashMap<>();
		for (FieldError	 erro : bindingResult.getFieldErrors()) {
			
			erros.put(erro.getField(), erro.getDefaultMessage());
			
		}
		
		return erros;
	}

}
