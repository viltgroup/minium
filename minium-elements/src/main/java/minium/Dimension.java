package minium;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Dimension {
    private final int width;
    private final int height;

    public Dimension(int width, int height) {
      this.width = width;
      this.height = height;
    }

    public int width() {
      return width;
    }

    public int height() {
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
      return MoreObjects.toStringHelper(Dimension.class.getSimpleName())
              .addValue(width)
              .addValue(height)
              .toString();
    }
  }

