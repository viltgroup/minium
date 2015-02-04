package minium.visual;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Drawing {

    public static class DoublePoint {
        private double x;
        private double y;

        public DoublePoint() {
        }

        public DoublePoint(double x, double y) {
            super();
            this.x = x;
            this.y = y;
        }

        public double x() {
            return x;
        }

        public double y() {
            return y;
        }

        public double distance(Drawing.DoublePoint point) {
            double diffX = point.x() - this.x();
            double diffY = point.y() - this.y();
            return Math.sqrt(diffX * diffX + diffY * diffY);
        }
    }

    public static class Polygon {
        private List<Drawing.DoublePoint> points = Lists.newArrayList();

        public Polygon() {
        }

        public List<Drawing.DoublePoint> points() {
            return ImmutableList.copyOf(points);
        }

        public Drawing.Polygon addPoint(Drawing.DoublePoint point) {
            points.add(point);
            return this;
        }
    }

    public static Drawing read(String path) {
        try {
            InputStream in = Drawing.class.getClassLoader().getResourceAsStream(path);
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                            .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                            .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                            .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                            .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
            return mapper.readValue(in, Drawing.class);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    private int width;
    private int height;
    private List<Drawing.Polygon> polygons = Lists.newArrayList();

    public Drawing() {
    }

    public Drawing(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void addPolygons(Collection<Drawing.Polygon> polygons) {
        this.polygons.addAll(polygons);
    }

    public List<Drawing.Polygon> polygons() {
        return ImmutableList.copyOf(polygons);
    }

    public Drawing addPolygon(Drawing.Polygon polygon) {
        polygons.add(polygon);
        return this;
    }

}