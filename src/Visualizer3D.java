import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Visualizer3D extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener {
    MiuraMesh mesh;
    double scale = 50;
    int centerX, centerY;
    double rotX = Math.PI / 6, rotY = Math.PI / 4;
    int prevX, prevY;
    Node selectedNode = null;

    public Visualizer3D(MiuraMesh mesh) {
        this.mesh = mesh;
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        for (SpringElement s : mesh.springs) {
            int[] p1 = project(s.getNode1Position());
            int[] p2 = project(s.getNode2Position());
            g2.drawLine(p1[0], p1[1], p2[0], p2[1]);
        }

        if (selectedNode != null) {
            double[] pos = selectedNode.getPosition();
            g2.setColor(Color.RED);
            int[] p = project(pos);
            g2.fillOval(p[0]-5, p[1]-5, 10, 10);
            g2.drawString(String.format("Disp: %.4f", selectedNode.displacementMagnitude()), p[0]+10, p[1]);
        }
    }

    private int[] project(double[] p) {
        double x = p[0], y = p[1], z = p[2];
        double cosX = Math.cos(rotX), sinX = Math.sin(rotX);
        double cosY = Math.cos(rotY), sinY = Math.sin(rotY);
        double x1 = x * cosY - z * sinY;
        double z1 = x * sinY + z * cosY;
        double y1 = y * cosX - z1 * sinX;
        return new int[]{(int)(centerX + x1 * scale), (int)(centerY - y1 * scale)};
    }

    public void mouseDragged(MouseEvent e) {
        int dx = e.getX() - prevX;
        int dy = e.getY() - prevY;
        rotY += dx * 0.01;
        rotX += dy * 0.01;
        prevX = e.getX();
        prevY = e.getY();
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
        prevX = e.getX();
        prevY = e.getY();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        scale *= (1 - e.getPreciseWheelRotation() * 0.1);
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        int ex = e.getX(), ey = e.getY();
        double minDist = 15;
        selectedNode = null;
        for (Node n : mesh.nodes) {
            int[] p = project(n.getPosition());
            double dist = Math.hypot(p[0] - ex, p[1] - ey);
            if (dist < minDist) {
                minDist = dist;
                selectedNode = n;
            }
        }
        repaint();
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}