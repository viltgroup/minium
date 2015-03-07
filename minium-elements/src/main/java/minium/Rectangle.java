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

public class Rectangle {

    private final int left;
    private final int top;
    private final int width;
    private final int height;

    public Rectangle(int left, int top, int width, int height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    public int top() {
        return top;
    }

    public int bottom() {
        return top + height;
    }

    public int left() {
        return left;
    }

    public int right() {
        return left + width;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public Point topLeft() {
        return new Point(left(), top());
    }

    public Dimension dimension() {
        return new Dimension(width, height);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Rectangle.class)
                .add("top", top())
                .add("bottom", bottom())
                .add("left", left())
                .add("right", right())
                .add("width", width())
                .add("height", height())
                .toString();
    }

}