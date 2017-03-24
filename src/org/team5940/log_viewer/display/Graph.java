package org.team5940.log_viewer.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Graph extends JPanel {
	private final int width = 800;
	private final int height = 400;
	private final int padding = 25;
	private final int labelPadding = 25;
	private final Color lineColor = new Color(44, 102, 230, 180);
	private final Color pointColor = new Color(100, 100, 100, 100);
	private final Color gridColor = new Color(200, 200, 200, 200);	
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private final int pointWidth = 4;
	private final int numberYDivisions = 10;
	private ArrayList<Double> data;
	private final String xLabel;
	private final String yLabel;
	private final String title;
	
	public Graph(ArrayList<Double> data, String title, String xLabel, String yLabel) {
		this.data = data;
		this.title = title;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double xScale = (((double) getWidth() - (2 * padding) - labelPadding) / data.size() - 1);
		double yScale = (((double) getHeight() - 2 * padding - labelPadding) / 	(getMax() - getMin()) - 1);
		
		ArrayList<Point> graphPoints = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			int x1 = (int) (i * xScale + padding + labelPadding);
			int y1 = (int) ((getMax() - data.get(i)) * yScale + padding);
			graphPoints.add(new Point(x1, y1));
		}
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);
		
		for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMin() + (getMax() - getMin()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

	}

	private int getMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getMin() {
		// TODO Auto-generated method stub
		return 0;
	}

}
