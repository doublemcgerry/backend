package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import serialization.action.Action;
import websocketecho.SerializerAdapter;


public class StringSerializer {
	private static Gson _gson=null;  
	  static{ 
	    GsonBuilder gb= new GsonBuilder(); 
	    gb.registerTypeAdapter(Action.class,new SerializerAdapter<>()); 
	    _gson=gb.create(); 
	  }
	public static Gson getSerializer() {
		return _gson;
	}
	
}
