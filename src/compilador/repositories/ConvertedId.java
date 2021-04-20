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
			put(17, "símbolo especial");	//{%
			put(18, "símbolo especial");	//%}
			put(19, "símbolo especial");	//=
			put(20, "símbolo especial");	//,
			put(21, "símbolo especial");	//:
			put(22, "símbolo especial");	//+=
			put(23, "símbolo especial");	//-=
			put(24, "símbolo especial");	//*=
			put(25, "símbolo especial");	///=
			put(26, "símbolo especial");	//(
			put(27, "símbolo especial");	//)
			put(28, "símbolo especial");	//&
			put(29, "símbolo especial");	//|
			put(30, "símbolo especial");	//!
			put(31, "símbolo especial");	//==
			put(32, "símbolo especial");	//!=
			put(33, "símbolo especial");	//<
			put(34, "símbolo especial");	//>
			put(35, "símbolo especial");	//+
			put(36, "símbolo especial");	//-
			put(37, "símbolo especial");	//*
			put(38, "símbolo especial");	///
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
