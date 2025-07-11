public class SpringElement {
	Node n1, n2;
	double restLength;
	double stiffness;
	double lastForce;

	public SpringElement(Node n1, Node n2, double k) {
		this.n1 = n1;
		this.n2 = n2;
		this.restLength = distance();
		this.stiffness = k;
		this.lastForce = 0;
	}

	private double distance() {
		return Math.sqrt(Math.pow(n2.x - n1.x, 2) + Math.pow(n2.y - n1.y, 2) + Math.pow(n2.z - n1.z, 2));
	}

	public void applyForce() {
		double[] p1 = n1.getPosition();
		double[] p2 = n2.getPosition();
		double dx = p2[0] - p1[0];
		double dy = p2[1] - p1[1];
		double dz = p2[2] - p1[2];
		double L = Math.sqrt(dx*dx + dy*dy + dz*dz);
		double f = stiffness * (L - restLength);
		lastForce = f;

		if (L == 0) return;
		double fx = f * dx / L;
		double fy = f * dy / L;
		double fz = f * dz / L;

		if (!n1.fixed) {
			n1.ux += fx * 0.001;
			n1.uy += fy * 0.001;
			n1.uz += fz * 0.001;
		}
		if (!n2.fixed) {
			n2.ux -= fx * 0.001;
			n2.uy -= fy * 0.001;
			n2.uz -= fz * 0.001;
		}
	}

	public double[] getNode1Position() { return n1.getPosition(); }
	public double[] getNode2Position() { return n2.getPosition(); }
}
