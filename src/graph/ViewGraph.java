package graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.TransformerUtils;


import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

public class ViewGraph {

	
	public static <V, E> void visualizeGraph(Graph<V,E> g) {
		
		Layout<Integer, String> layout = new KKLayout(g);
        layout.setSize(new Dimension(800,800)); // sets the initial size of the layout space
        // The BasicVisualizationServer<V,E> is parameterized by the vertex and edge types
        VisualizationViewer<Integer,String> vv = new VisualizationViewer<Integer,String>(layout);
        
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(DefaultModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);
        
       
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.setPreferredSize(new Dimension(800,800)); //Sets the viewing area size
        
        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv); 
        frame.pack();
        frame.setVisible(true);   
	}
	
//	public static <V, E> void visualizeGraph(final Graph<V,E> g, final ArrayList<Route<V>> routes, final KMResults results, boolean spatial) {
//		
//		Layout<V, E> layout;
//		
//		if(spatial){
//			
//			HashMap<SpatialNode,Point2D> map = new HashMap<SpatialNode,Point2D>();
//			
//			for(V n : g.getVertices()){
//				SpatialNode node = (SpatialNode) n;
//				map.put(node, new Point2D.Double(Math.sqrt(150+node.getX())*10, Math.sqrt(node.getY())*10));
//			}
//			
//			Transformer<SpatialNode, Point2D> vertexLocations = TransformerUtils.mapTransformer(map);
//			
//			layout = new StaticLayout(g, vertexLocations);
//			
//			
//			
//		} else {
//			layout = new KKLayout(g);
//			layout.setSize(new Dimension(5000,5000)); // sets the initial size of the layout space
//		}
//		
//		
//		
//	
//        
//        // The BasicVisualizationServer<V,E> is parameterized by the vertex and edge types
//        VisualizationViewer<V,E> vv = new VisualizationViewer<V,E>(layout);
//        
//        
//        
//        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
//        gm.setMode(DefaultModalGraphMouse.Mode.TRANSFORMING);
//        vv.setGraphMouse(gm);
//        
//        
//        
//        
//        vv.getRenderContext().setEdgeDrawPaintTransformer(new Transformer<E, Paint>() {
//			@Override
//			public Paint transform(E arg0) {
//				// TODO Auto-generated method stub
//				ArrayList<LinkedList<Route<V>>> clusters = results.getClusters();
//				
//				
//				int colorCount = 0;
//			
//				int blue = 0;
//				int green = 0;
//				
//				for(LinkedList<Route<V>> clus : clusters){
//					
//					for(Route<V> r : clus){
//						for(V i : r.getRoute()){
//							for(E e : g.getIncidentEdges((V) i)){
//								if(e.equals(arg0)){
//									colorCount++;
//									if(clusters.indexOf(clus) == 0){
//										blue += 25;
//									} 
//									if (clusters.indexOf(clus) == 1){
//										green += 50;
//									}
//								}
//							}
//						}
//					}
//				}
//				
//			
//				return new Color(0,Math.min(green, 255),Math.min(blue,255),Math.min(colorCount*25+50, 255));
//				
//	
//			}
//        
//        	
//        
//        });
//        
//        vv.getRenderContext().setVertexLabelTransformer(new Transformer<V, String>() {
//
//			@Override
//			public String transform(V arg0) {
//				String ret = arg0 + ": ";
//				int rCount = 0;
//				for(Route<V> r : routes){
//					if(r.getRoute().contains(arg0)){
//						rCount++;
//					}
//				}
//				
//				return "";//ret + rCount;
//			}
//		});
//        vv.getRenderContext().setVertexFillPaintTransformer( 
//								        	    new Transformer<V, Paint>() {
//								            private final Color[] palette = { Color.blue,  Color.GREEN, Color.CYAN,
//								                 Color.RED,  Color.ORANGE, Color.PINK
//								                };
//								
//								            public Paint transform(V i) {
//								            	Color newCol = Color.WHITE;
//								            	
//								            	ArrayList<Route<Integer>> centers = results.getCenters();
//								            	
//								            	for(int j = 0; j < centers.size(); j++){
//								            		Route<Integer> r = centers.get(j);
//								            		
//							            			if(r.getRoute().contains(i)){
//							            				if(newCol == Color.WHITE || newCol == palette[j % palette.length]){
//							            					newCol = palette[j % palette.length];
//							            				} else {
//							            					return Color.YELLOW;
//							            				}
//							            			}
//								            		
//        										}
//								                return newCol;
//								            }
//								        });
//        
//        vv.setPreferredSize(new Dimension(1200,800)); //Sets the viewing area size
//        
//        JFrame frame = new JFrame("Simple Graph View");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(vv); 
//        frame.pack();
//        frame.setVisible(true);   
//	}
//	
//	public static void main(String[] args) {
//		Graph<Integer, WeightedEdge> g = GraphMLImport.readGraph("500_nodes.gml");
//		ViewGraph.visualizeGraph(g);
//	}
	
}
