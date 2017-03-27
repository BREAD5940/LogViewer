package org.team5940.log_viewer.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Graph extends JPanel {
	private final int padding = 25;
	private final int labelPadding = 25;
	private final Color lineColor = new Color(44, 102, 230, 180);
	private final Color pointColor = new Color(100, 100, 100, 100);
	private final Color gridColor = new Color(150, 150, 150, 200);
	private final Color gridLabelColor = new Color(75, 75, 75, 255);
	private final Color borderColor = new Color(75, 75, 75, 255);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private final int pointWidth = 4;
	private final int divisionSize = 100;
	private ArrayList<DoublePoint> data;
	
	public Graph(ArrayList<DoublePoint> data) {
		this.data = data;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//GET GRAPH BOUNDS
		//x
		int xStart = this.padding + this.labelPadding;
		int xEnd = this.getWidth() - this.padding;
		int xWidth = xEnd - xStart;
		//y
		int yStart = this.padding;
		int yEnd = this.getHeight() - this.padding - this.labelPadding;
		int yHeight = yEnd - yStart;
		
		//DRAW GRAPH BORDER
		g2.setColor(this.borderColor);
		g2.drawLine(xStart, yStart, xStart, yEnd);
		g2.drawLine(xStart, yEnd, xEnd, yEnd);
		g2.drawLine(xEnd, yEnd, xEnd, yStart);
		g2.drawLine(xEnd, yStart, xStart, yStart);
		
		//MAP DATA TO GRAPH
		DoublePoint mins = this.getMinimums();
		DoublePoint maxs = this.getMaximums();
		double xRatio = xWidth/(maxs.x-mins.x);
		double yRatio = -yHeight/(maxs.y-mins.y);
		System.out.println(maxs.y);
		System.out.println(mins.y);
		ArrayList<Point> graphData = new ArrayList<Point>();
		for(DoublePoint p : this.data) {
			double mappedX = ((p.x-mins.x) * xRatio)+xStart;
			double mappedY = yEnd + ((p.y-mins.y) * yRatio);
			graphData.add(new Point((int) mappedX, (int) mappedY));
		}
		
		//DRAW LINES
		g2.setColor(this.lineColor);
		for(int i = 1; i < graphData.size(); i++) {
			Point p0 = graphData.get(i-1);
			Point p1 = graphData.get(i);
			g2.drawLine(p0.x, p0.y, p1.x, p1.y);
		}
		
		//DRAW POINTS
		g2.setColor(this.pointColor);
		for(Point p : graphData) {
			g2.fillOval(p.x-(pointWidth/2), p.y-(pointWidth/2), pointWidth, pointWidth);
		}
		
		//DRAW VERTICAL AXES AND LABELS
		for(int i = xStart; i < xEnd; i+=this.divisionSize) {
			g2.setColor(this.gridColor);
			g2.drawLine(i, yStart, i, yEnd);
			g2.setColor(this.gridLabelColor);
			g2.drawString(String.valueOf(Math.round((((i-xStart)/xRatio)+mins.x)*1000)/1000d), i, this.getHeight()-this.padding);
		}
		
		//DRAW HORIZONTAL AXES AND LABELS
		for(int i = yEnd; i > yStart; i-=this.divisionSize) {
			g2.setColor(this.gridColor);
			g2.drawLine(xStart, i, xEnd, i);
			g2.setColor(this.gridLabelColor);
			g2.drawString(String.valueOf(Math.round((((i-yEnd)/yRatio)+mins.y)*1000)/1000d), this.padding, i);
		}
		
//		super.paintComponent(g);
//		Graphics2D g2 = (Graphics2D) g;
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / data.get(data.size() - 1).x;
//        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMax() - getMin());
//		
//		ArrayList<Point> graphPoints = new ArrayList<>();
//		for (int i = 0; i < data.size(); i++) {
//			int x1 = (int) (data.get(data.size() - 1).x * xScale + padding + labelPadding);
//			int y1 = (int) ((getMax() - data.get(i).y) * yScale + padding);
//			graphPoints.add(new Point(x1, y1));
//		}
//		g2.setColor(Color.WHITE);
//		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
//		g2.setColor(Color.BLACK);
//		
//		for (int i = 0; i < numberYDivisions + 1; i++) {
//            int x0 = padding + labelPadding;
//            int x1 = pointWidth + padding + labelPadding;
//            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
//            int y1 = y0;
//            if (data.size() > 0) {
//                g2.setColor(gridColor);
//                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
//                g2.setColor(Color.BLACK);
//                String yLabel = ((int) ((getMin() + (getMax() - getMin()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
//                FontMetrics metrics = g2.getFontMetrics();
//                int labelWidth = metrics.stringWidth(yLabel);
//                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
//            }
//            g2.drawLine(x0, y0, x1, y1);
//        }
//		
//		for (int i = 0; i < data.size(); i++) {
//            if (data.size() > 1) {
//                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (data.size() - 1) + padding + labelPadding;
//                int x1 = x0;
//                int y0 = getHeight() - padding - labelPadding;
//                int y1 = y0 - pointWidth;
//                if ((i % ((int) ((data.size() / 20.0)) + 1)) == 0) {
//                    g2.setColor(gridColor);
//                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
//                    g2.setColor(Color.BLACK);
//                    String xLabel = i + "";
//                    FontMetrics metrics = g2.getFontMetrics();
//                    int labelWidth = metrics.stringWidth(xLabel);
//                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
//                }
//                g2.drawLine(x0, y0, x1, y1);
//            }
//        }
//		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
//        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);
//
//        Stroke oldStroke = g2.getStroke();
//        g2.setColor(lineColor);
//        g2.setStroke(GRAPH_STROKE);
//        for (int i = 0; i < graphPoints.size() - 1; i++) {
//            int x1 = graphPoints.get(i).x;
//            int y1 = graphPoints.get(i).y;
//            int x2 = graphPoints.get(i + 1).x;
//            int y2 = graphPoints.get(i + 1).y;
//            g2.drawLine(x1, y1, x2, y2);
//        }
//
//        g2.setStroke(oldStroke);
//        g2.setColor(pointColor);
//        for (int i = 0; i < graphPoints.size(); i++) {
//            int x = graphPoints.get(i).x - pointWidth / 2;
//            int y = graphPoints.get(i).y - pointWidth / 2;
//            int ovalW = pointWidth;
//            int ovalH = pointWidth;
//            g2.fillOval(x, y, ovalW, ovalH);
//        }
	}

	DoublePoint getMinimums() {
		double minX = this.data.get(0).x;
		double minY = this.data.get(0).y;
		for(DoublePoint d : this.data) {
			if(d.x < minX) minX = d.x;
			if(d.y < minY) minY = d.y;
		}
		return new DoublePoint(minX, minY);
	}
	
	DoublePoint getMaximums() {
		double maxX = this.data.get(0).x;
		double maxY = this.data.get(0).y;
		for(DoublePoint d : this.data) {
			if(d.x > maxX) maxX = d.x;
			if(d.y > maxY) maxY = d.y;
		}
		return new DoublePoint(maxX, maxY);
	}
}