import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class FEMSolver {
	MiuraMesh mesh;
	double loadWeight;

	public FEMSolver(MiuraMesh mesh) {
		this.mesh = mesh;
	}

	public void setLoad(double w) {
		this.loadWeight = w;
	}

	public void solve() {
		for (int step = 0; step < 300; step++) {
			for (SpringElement s : mesh.springs) s.applyForce();
			for (Node n : mesh.nodes) if (!n.fixed) n.uz -= loadWeight * 0.0001;
		}
	}

	public void exportCSV(String filename) {
		try (FileWriter writer = new FileWriter(filename)) {
			writer.write("x,y,z,ux,uy,uz,displacement\n");
			for (Node n : mesh.nodes) {
				writer.write(String.format(Locale.US, "%.5f,%.5f,%.5f,%.5f,%.5f,%.5f,%.5f\n",
						n.x, n.y, n.z, n.ux, n.uy, n.uz, n.displacementMagnitude()));
			}
			writer.write("n1x,n1y,n1z,n2x,n2y,n2z,force\n");
			for (SpringElement s : mesh.springs) {
				double[] p1 = s.getNode1Position();
				double[] p2 = s.getNode2Position();
				writer.write(String.format(Locale.US, "%.5f,%.5f,%.5f,%.5f,%.5f,%.5f,%.5f\n",
						p1[0], p1[1], p1[2], p2[0], p2[1], p2[2], s.lastForce));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}