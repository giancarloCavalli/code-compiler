package compilador.repositories;

import java.util.HashMap;
import java.util.Map;

import compilador.services.gals.Constants;

public class ConvertedId implements Constants {
	private Map<Integer, String> variables = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;

		{
			put(2, "identificador\t");
			put(3, "constante int\t");
			put(4, "constante float\t");
			put(5, "constante str\t");
			put(6, "constante char\t");	
			put(7, "palavra reservada");	//def
			put(8, "palavra reservada");	//else
			put(9, "palavra reservada");	//end
			put(10, "palavra reservada");	//false
			put(11, "palavra reservada");	//if
			put(12, "palavra reservada");	//input
			put(13, "palavra reservada");	//output
			put(14, "palavra reservada");	//true
			put(15, "palavra reservada");	//until
			put(16, "palavra reservada"); 	//while
			put(17, "s�mbolo especial");	//{%
			put(18, "s�mbolo especial");	//%}
			put(19, "s�mbolo especial");	//=
			put(20, "s�mbolo especial");	//,
			put(21, "s�mbolo especial");	//:
			put(22, "s�mbolo especial");	//+=
			put(23, "s�mbolo especial");	//-=
			put(24, "s�mbolo especial");	//*=
			put(25, "s�mbolo especial");	///=
			put(26, "s�mbolo especial");	//(
			put(27, "s�mbolo especial");	//)
			put(28, "s�mbolo especial");	//&
			put(29, "s�mbolo especial");	//|
			put(30, "s�mbolo especial");	//!
			put(31, "s�mbolo especial");	//==
			put(32, "s�mbolo especial");	//!=
			put(33, "s�mbolo especial");	//<
			put(34, "s�mbolo especial");	//>
			put(35, "s�mbolo especial");	//+
			put(36, "s�mbolo especial");	//-
			put(37, "s�mbolo especial");	//*
			put(38, "s�mbolo especial");	///
		}
	};

	public ConvertedId() {
		
	}

	public String getHashByKey(int key) {
		if (this.variables.containsKey(key)) {
			return this.variables.get(key);
		}
		return "";
	}

}
