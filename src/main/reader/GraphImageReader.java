package main.reader;

import main.model.WeightedGraph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * A concrete implementation of {@link GraphReader} that reads graph data from an image file.
 * This reader handles images that represent a graph (see examples in `assets/examples` folder).
 * <p>
 * It uses a {@link javax.imageio.ImageReader} for parsing image's pixels into a graph.
 */
public class GraphImageReader extends GraphReader {

    /**
     * Constructs a {@link GraphImageReader} for the specified image file path.
     *
     * @param path the path to the image file containing the graph data
     */
    public GraphImageReader(String path) {
        super(path);
    }

    /**
     * Reads the graph from the image, initializes its vertices and neighbors.
     * By default, sets the first and last pixels as the start and end vertices
     * (but this can be modified by the user though {@link main.gui.launcher.Configuration}).
     *
     * @throws IOException if an error occurs during image reading
     * @throws IllegalArgumentException if the image file does not exist
     */
    @Override
    public void read() throws IOException {
        if(!file.exists()) throw new IllegalArgumentException("Cannot read this image : it doesn't exist");

        BufferedImage image = ImageIO.read(file);
        this.lines = image.getHeight();
        this.columns = image.getWidth();
        this.weightedGraph = new WeightedGraph();
        setVertices(image);
        setNeighbors();
        this.start = weightedGraph.getVertices().getFirst();
        this.end = weightedGraph.getVertices().getLast();
    }

    /**
     * Decodes image pixels and converts their RGB representation to HSV.
     * The value property defined by HSV is then normalized and inverted: the result is the vertex weight.
     * @param image the image read by this reader
     */
    private void setVertices(BufferedImage image) {
        for (int y=0; y < lines; y++) {
            for (int x=0; x < columns; x++) {
                int pixel = image.getRGB(x,y);
                Color color = new Color(pixel, true);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int value = 100-(int)(Color.RGBtoHSB(red, green, blue, null)[2]*100);
                weightedGraph.addVertex(new WeightedGraph.Type(
                        color.toString(), value, String.format("#%02x%02x%02x", red, green, blue)));
            }
        }
    }

    /**
     * Retrieves and sets neighbors for all vertices of this graph.
     * Graphs generated from image files doesn't allow diagonal neighbors (each vertex has up to 4 neighbors).
     */
    private void setNeighbors() {
        List<WeightedGraph.Vertex> vertices = weightedGraph.getVertices();
        for(int line = 0; line < lines; line++) {
            for(int col = 0; col < columns; col++) {
                int current = line * columns + col;
                WeightedGraph.Vertex v = vertices.get(current);
                if(line < lines-1) v.addNeighbor(vertices.get((line+1)*columns+(col)), false);
                if(col < columns-1) v.addNeighbor(vertices.get((line)*columns+(col+1)), false);
                if(col > 0) v.addNeighbor(vertices.get((line)*columns+(col-1)), false);
                if(line > 0) v.addNeighbor(vertices.get((line-1)*columns+(col)), false);
            }
        }
    }
}
