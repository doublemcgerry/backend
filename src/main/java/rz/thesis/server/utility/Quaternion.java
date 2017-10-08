package rz.thesis.server.utility;

public class Quaternion {
	private float x;
	private float y;
	private float z;
	private float w;

	public Quaternion() {

	}

	public Quaternion(float x, float y, float z, float w) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getW() {
		return w;
	}

	public static Quaternion DivideByFloat(Quaternion quaternion, float divisor) {
		return new Quaternion(quaternion.x / divisor, quaternion.y / divisor, quaternion.z / divisor,
				quaternion.w / divisor);
	}
}
