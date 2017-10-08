package rz.thesis.server.utility;

import java.io.Serializable;
import java.util.Map;

public class SensorData implements Serializable {

	private static final long serialVersionUID = 8885148327492054159L;
	private long timestamp;
	private String dataType;
	private Map<String, String> data;

	public long getTimestamp() {
		return timestamp;
	}

	public String getDataType() {
		return dataType;
	}

	public Map<String, String> getData() {
		return data;
	}

	@Override
	public String toString() {
		return "type:" + dataType + " values:" + data.toString();
	}

}
