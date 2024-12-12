package reader;

import model.WeightedGraph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class GraphImageReader extends GraphReader {

    public GraphImageReader(String path) {
        super(path);
    }

    @Override
    public void read() throws IOException {
        if(!file.exists()) throw new IllegalArgumentException("Cannot read this file : it doesn't exist");

        BufferedImage image = ImageIO.read(file);
        this.lines = image.getHeight();
        this.columns = image.getWidth();
        this.weightedGraph = new WeightedGraph();
        setVertices(image);
        setNeighbors();
        this.start = weightedGraph.getVertices().getFirst(); //temp
        this.end = weightedGraph.getVertices().getLast(); //temp
    }

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
