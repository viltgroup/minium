/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.primitives.Ints;

public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int x() {
      return x;
    }

    public int y() {
      return y;
    }

    public Point moveBy(int xOffset, int yOffset) {
      return new Point(safeSum(x, xOffset), safeSum(y, yOffset));
    }

    public boolean isNull() {
        return x == 0 && y == 0;
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
      return MoreObjects.toStringHelper(Point.class.getSimpleName())
              .addValue(x)
              .addValue(y)
              .toString();
    }

    private int safeSum(int a, int b) {
        if ((a == Integer.MAX_VALUE || a == Integer.MIN_VALUE) && b != Integer.MAX_VALUE && b != Integer.MIN_VALUE) {
            return a;
        }
        if ((b == Integer.MAX_VALUE || b == Integer.MIN_VALUE) && a != Integer.MAX_VALUE && a != Integer.MIN_VALUE) {
            return b;
        }
        return Ints.saturatedCast((long) a + (long) b);
    }
}
