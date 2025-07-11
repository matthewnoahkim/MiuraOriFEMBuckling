public class Node {
	double x, y, z;
	double ux, uy, uz;
	boolean fixed;

	public Node(double x, double y, double z) {
		this.x = x; this.y = y; this.z = z;
		this.ux = this.uy = this.uz = 0;
		this.fixed = false;
	}

	public double[] getPosition() {
		return new double[]{x + ux, y + uy, z + uz};
	}

	public double displacementMagnitude() {
		return Math.sqrt(ux * ux + uy * uy + uz * uz);
	}
}