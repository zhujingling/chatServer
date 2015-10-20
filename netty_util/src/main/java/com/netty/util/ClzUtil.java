package com.netty.util;

import net.vidageek.mirror.dsl.Mirror;


public class ClzUtil {
//	  private static final String NameScope = "com.netty";
	  @SuppressWarnings("rawtypes")
	    private static Class getFNClass(String moName) {
	        Class c = null;

	        try {
	            String sNameSpace =moName;
	            c = new Mirror().reflectClass(sNameSpace);
	        }
	        catch (Exception e) {
	        }

	        return c;
	    }
	  
	  @SuppressWarnings("rawtypes")
	    public static Object getFNObject(String moName) throws ClassNotFoundException {

//		    Class c = Class.forName(moName);
	        Class c = getFNClass(moName);

	        Object oFN = null;

	        try {
	            oFN = c.newInstance();
	        }
	        catch (InstantiationException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        catch (IllegalAccessException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        return oFN;
	    }
}
