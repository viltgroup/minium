/*
 * Copyright (C) 2013 The Minium Authors
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
package com.vilt.minium;

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

