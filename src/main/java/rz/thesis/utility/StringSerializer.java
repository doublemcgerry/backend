package rz.thesis.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rz.thesis.core.serialization.SerializerAdapter;
import rz.thesis.serialization.action.Action;

public class StringSerializer {
	private static Gson _gson = null;
	static {
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Action.class, new SerializerAdapter<>());
		_gson = gb.create();
	}

	public static Gson getSerializer() {
		return _gson;
	}

}
