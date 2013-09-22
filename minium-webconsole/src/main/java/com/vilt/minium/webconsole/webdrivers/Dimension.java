package com.vilt.minium.webconsole.webdrivers;

import com.google.common.base.Objects;

public class Dimension {
    public int width;
    public int height;

    public Dimension() {
    }
    
    public Dimension(int width, int height) {
      this.width = width;
      this.height = height;
    }

    public int getWidth() {
      return width;
    }

    public int getHeight() {
      return height;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Dimension)) {
        return false;
      }

      Dimension other = (Dimension) o;
      return other.width == width && other.height == height;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(width, height);
    }

    @Override
    public String toString() {
      return Objects.toStringHelper(Dimension.class)
              .addValue(width)
              .addValue(height)
              .toString();
    }
}
