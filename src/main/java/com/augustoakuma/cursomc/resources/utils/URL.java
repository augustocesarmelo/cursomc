package com.augustoakuma.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	public static List<Integer> decodeIntList(String lista){
		String[] vet = lista.split(",");
		List<Integer> retorno = new ArrayList<>();
		for(int i =0; i<vet.length; i++) {
			retorno.add( Integer.parseInt(vet[i]));
		}
		return retorno;
	}
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

}
