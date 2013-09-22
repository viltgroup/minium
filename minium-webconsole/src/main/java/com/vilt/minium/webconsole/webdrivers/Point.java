package com.vilt.minium.webconsole.webdrivers;

import com.google.common.base.Objects;

public class Point {
    
    public int x;
    public int y;

    public Point() {
    }
    
    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Point)) {
        return false;
      }

      Point other = (Point) o;
      return other.x == x && other.y == y;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(x, y);
    }

    @Override
    public String toString() {
      return Objects.toStringHelper(Point.class)
              .addValue(x)
              .addValue(y)
              .toString();
    }
}
