import java.util.*;

public class MiuraMesh {
	public ArrayList<Node> nodes = new ArrayList<>();
	public ArrayList<SpringElement> springs = new ArrayList<>();
	int rows, cols;
	double a, b;
	double theta;

	public MiuraMesh(int rows, int cols, double a, double b, double thetaDeg) {
		this.rows = rows;
		this.cols = cols;
		this.a = a;
		this.b = b;
		this.theta = Math.toRadians(thetaDeg);
		generate();
	}

	public void generate() {
		nodes.clear();
		springs.clear();
		for (int i = 0; i <= rows; i++) {
			for (int j = 0; j <= cols; j++) {
				double x = j * a + ((i % 2 == 0) ? 0 : a / 2);
				double y = i * b * Math.sin(theta);
				double z = ((i + j) % 2 == 0) ? a * Math.cos(theta) : -a * Math.cos(theta);
				Node node = new Node(x, y, z);
				if (i == 0) node.fixed = true;
				nodes.add(node);
			}
		}

		// Only horizontal and vertical springs for fold lines
		for (int i = 0; i <= rows; i++) {
			for (int j = 0; j <= cols; j++) {
				int idx = i * (cols + 1) + j;
				if (j < cols) {
					Node right = nodes.get(idx + 1);
					springs.add(new SpringElement(nodes.get(idx), right, 10));
				}
				if (i < rows) {
					Node below = nodes.get(idx + (cols + 1));
					springs.add(new SpringElement(nodes.get(idx), below, 10));
				}
			}
		}
	}

	public void updateAngle(double thetaDeg) {
		this.theta = Math.toRadians(thetaDeg);
		generate();
	}
}