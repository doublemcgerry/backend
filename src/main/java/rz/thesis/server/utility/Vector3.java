package rz.thesis.server.utility;

public class Vector3 {
	private float x;
	private float y;
	private float z;

	public Vector3() {
		// TODO Auto-generated constructor stub
	}

	public Vector3(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
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

	public static Vector3 DivideByFloat(Vector3 vector, float divisor) {
		return new Vector3(vector.x / divisor, vector.y / divisor, vector.z / divisor);
	}
}
