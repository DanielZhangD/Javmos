package javmos.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;

import javmos.JavmosComponent;
import javmos.JavmosGUI;
import javmos.components.JavmosPanel;
import javmos.components.functions.Function;
import javmos.components.Point;

public class ZoomListener implements ActionListener {
    
    private final int ZOOM_FACTOR = 10;
    private final JavmosGUI gui;
    private final JavmosPanel panel;

    public ZoomListener(JavmosGUI gui, JavmosPanel panel) {
        this.gui = gui;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("+")) {
            gui.setZoom(gui.getZoom() + ZOOM_FACTOR);
        } else if (gui.getZoom() - ZOOM_FACTOR > 0) {
            gui.setZoom(gui.getZoom() - ZOOM_FACTOR);
			double min = -gui.getPlaneWidth() / (2 * gui.getZoom());
			double max = gui.getPlaneWidth() / (2 * gui.getZoom());
			Function function = panel.getFunction();
			LinkedList<JavmosComponent>components = panel.components;
			HashSet<Point> xInts = function.getXIntercepts(min, max);
			if (xInts != null) {
				components.addAll(xInts);
			}
			HashSet<Point> critPoints = function.getCriticalPoints(min, max);
			if (critPoints != null) {
				components.addAll(critPoints);
			}
			HashSet<Point> infPoints = function.getInflectionPoints(min, max);
			if (infPoints != null) {
				components.addAll(infPoints);
			}
        }
        panel.repaint();
    }
}
