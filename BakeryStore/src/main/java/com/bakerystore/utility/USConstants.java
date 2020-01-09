package com.bakerystore.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class USConstants {
	
	public final static String US = "INDIA";
	
	public final static Map<String, String> mapOfINDIAStates = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("AP", "Andhra Pradesh");
			put("AR", "Arunachal Pradesh");
			put("AS", "Assam");
			put("BR", "Bihar");
			put("CG", "Chattisgarh");
			put("GA", "Goa");
			put("GJ", "Gujarat");
			put("HR", "Harayana");
			put("HP", "Himachal Pradesh");
			put("JK", "Jammu and Kashmir");
			put("JH", "Jharkhand");
			put("KA", "Karnataka");
			put("KL", "Kerala");
			put("MP", "Madhya Pradesh");
			put("MH", "Maharashtra");
			put("MN", "Manipur");
			put("ML", "Meghalaya");
			put("MZ", "Mizoram");
			put("NL", "Nagaland");
			put("OR", "Orissa");
			put("PB", "Punjab");
			put("RJ", "Rajasthan");
			put("SK", "Sikkim");
			put("TN", "Tamil Nadu");
			put("TG", "Telangana");
			put("TR", "Tripura");
			put("UK", "Uttarkhand");
			put("UP", "Uttar Pradesh");
			put("WB", "West Bengal");
			put("AN", "Andaman and Nicobar Islands");
			put("CH", "Chandigarh");
			put("DH", "Dadra and Nagar Haveli");
			put("DD", "Daman and Diu");
			put("DL", "Delhi");
			put("LD", "Lakshadweep");
			put("PY", "Pondicherry");
			
			
		}
	};
	
	public final static List<String> listOfINDIAStatesCode = new ArrayList<>(mapOfINDIAStates.keySet());
	public final static List<String> listOfINDIAStatesName = new ArrayList<>(mapOfINDIAStates.values());

}
