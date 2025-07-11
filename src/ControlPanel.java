import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
	public ControlPanel(MiuraMesh mesh, FEMSolver solver, Visualizer3D visualizer) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JSlider angleSlider = new JSlider(30, 150, 60);
		JSlider loadSlider = new JSlider(0, 100, 10);

		angleSlider.setPaintTicks(true);
		angleSlider.setMajorTickSpacing(30);
		angleSlider.setPaintLabels(true);
		loadSlider.setPaintTicks(true);
		loadSlider.setMajorTickSpacing(20);
		loadSlider.setPaintLabels(true);

		angleSlider.addChangeListener(e -> {
			mesh.updateAngle(angleSlider.getValue());
			visualizer.repaint();
		});

		loadSlider.addChangeListener(e -> {
			solver.setLoad(loadSlider.getValue());
			solver.solve();
			visualizer.repaint();
		});

		add(new JLabel("Fold Angle (degrees):"));
		add(angleSlider);
		add(new JLabel("Load Weight:"));
		add(loadSlider);
	}
}
