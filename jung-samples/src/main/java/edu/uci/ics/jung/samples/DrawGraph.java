package edu.uci.ics.jung.samples;

import com.google.common.graph.MutableNetwork;
import edu.uci.ics.jung.layout.algorithms.KKLayoutAlgorithm;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.renderers.BasicNodeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.DefaultNodeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.GradientNodeRenderer;
import edu.uci.ics.jung.visualization.renderers.NodeLabelAsShapeRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Point2D;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class DrawGraph<Node, Edge> {

  public interface IExtractor<Node, Edge> {
    String getId(Node node);
  }

  public void draw(MutableNetwork<Node, Edge> graph, IExtractor extractor) {
    VisualizationImageServer<Node, Edge> vv =
        new VisualizationImageServer<>(graph, new KKLayoutAlgorithm<>(), new Dimension(1200, 1200));
    vv.getRenderer()
        .setNodeRenderer(
            new GradientNodeRenderer<>(
                vv, Color.white, Color.green, Color.white, Color.blue, false));
    vv.getRenderContext().setEdgeDrawPaintFunction(e -> Color.lightGray);
    vv.getRenderContext().setArrowFillPaintFunction(e -> Color.lightGray);
    vv.getRenderContext().setArrowDrawPaintFunction(e -> Color.lightGray);

    NodeLabelAsShapeRenderer<Node, Edge> vlasr =
        new NodeLabelAsShapeRenderer<>(vv.getModel(), vv.getRenderContext());
    vv.getRenderContext().setNodeShapeFunction(vlasr);
    vv.getRenderContext().setNodeLabelRenderer(new DefaultNodeLabelRenderer(Color.green));
    vv.getRenderContext().setEdgeDrawPaintFunction(e -> Color.black);
    vv.getRenderContext().setEdgeStrokeFunction(e -> new BasicStroke(0f));
    vv.getRenderContext()
        .setNodeLabelFunction(
            node -> {
              return "<html><center>" + extractor.getId(node) + "</center></html>";
            });

    vv.getRenderer()
        .getNodeLabelRenderer()
        .setPositioner(new BasicNodeLabelRenderer.InsidePositioner());
    vv.getRenderer().getNodeLabelRenderer().setPosition(Renderer.NodeLabel.Position.CNTR);

    // create a frome to hold the graph
    final JFrame frame = new JFrame();
    Container content = frame.getContentPane();

    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    Image im = vv.getImage(new Point2D.Double(1200, 1200), new Dimension(1200, 1200));
    Icon icon = new ImageIcon(im);
    JLabel label = new JLabel(icon);
    content.add(label);
    frame.pack();
    frame.setVisible(true);
  }
}
