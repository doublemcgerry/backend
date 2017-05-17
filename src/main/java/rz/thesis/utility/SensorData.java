package rz.thesis.utility;

import java.io.Serializable;

public class SensorData implements Serializable {

	private static final long serialVersionUID = 8885148327492054159L;
	private long timestamp;
	private MovementType typeMovement;
	
	public SensorData(long timestamp, MovementType typeMovement) {
		super();
		this.timestamp = timestamp;
		this.typeMovement = typeMovement;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public MovementType getTypeMovement() {
		return typeMovement;
	}
	
	public void setTypeMovement(MovementType typeMovement) {
		this.typeMovement = typeMovement;
	}
}
