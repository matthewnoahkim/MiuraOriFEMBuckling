import javax.swing.*;
import java.awt.*;

public class MainApp {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Miura Ori FEM Buckling");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(1000, 800);

			MiuraMesh mesh = new MiuraMesh(5, 5, 1.0, 1.0, 60);
			FEMSolver solver = new FEMSolver(mesh);
			Visualizer3D visualizer = new Visualizer3D(mesh);
			ControlPanel controls = new ControlPanel(mesh, solver, visualizer);

			frame.setLayout(new BorderLayout());
			frame.add(controls, BorderLayout.EAST);
			frame.add(visualizer, BorderLayout.CENTER);
			frame.setVisible(true);
		});
	}
}