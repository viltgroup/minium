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
package com.vilt.minium.impl;

import static java.lang.String.format;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.vilt.minium.Dimension;
import com.vilt.minium.Offsets.HorizontalOffset;
import com.vilt.minium.Offsets.HorizontalReference;
import com.vilt.minium.Offsets.Offset;
import com.vilt.minium.Offsets.Unit;
import com.vilt.minium.Offsets.UnitHorizontalOffset;
import com.vilt.minium.Offsets.UnitVerticalOffset;
import com.vilt.minium.Offsets.VerticalOffset;
import com.vilt.minium.Offsets.VerticalReference;
import com.vilt.minium.Point;
import com.vilt.minium.Rectangle;

public class InternalOffsets {

    private static final DecimalFormat DECIMAL_FORMAT;
    static {
        DECIMAL_FORMAT = new DecimalFormat("#.####");
        DECIMAL_FORMAT.setPositivePrefix("+");
    }

    public static class AbstractHorizontalOffset implements HorizontalOffset {

        protected final HorizontalOffset parent;
        protected final double val;
        protected final Unit unit;

        public AbstractHorizontalOffset(HorizontalOffset parent, double val, Unit unit) {
            this.parent = parent;
            this.val = val;
            this.unit = unit == null ? Unit.PIXEL : unit;
        }

        @Override
        public UnitHorizontalOffset plus(double val) {
            return new UnitHorizontalOffsetImpl(this, val);
        }

        @Override
        public HorizontalOffset plus(double val, Unit unit) {
            return new AbstractHorizontalOffset(this, val, unit);
        }

        @Override
        public UnitHorizontalOffset minus(double val) {
            return plus(-val);
        }

        @Override
        public HorizontalOffset minus(double val, Unit unit) {
            return plus(-val, unit);
        }

        @Override
        public double apply(double width) {
            double offset;
            switch (unit) {
            case PIXEL:
                offset = val;
                break;
            default:
                offset = width * val / 100.0;
                break;
            }
            return parent.apply(width) + offset;
        }

        @Override
        public String toString() {
            return format("%s%s%s", parent, DECIMAL_FORMAT.format(val), unit);
        }
    }

    public static class UnitHorizontalOffsetImpl extends AbstractHorizontalOffset implements UnitHorizontalOffset {

        public UnitHorizontalOffsetImpl(HorizontalOffset parent, double val) {
            super(parent, val, Unit.PIXEL);
        }

        @Override
        public HorizontalOffset percent() {
            return new AbstractHorizontalOffset(parent, val, Unit.PERCENT);
        }

        @Override
        public HorizontalOffset pixels() {
            return new AbstractHorizontalOffset(parent, val, Unit.PIXEL);
        }
    }

    public static class AbstractVerticalOffset implements VerticalOffset {

        protected final VerticalOffset parent;
        protected final double val;
        protected final Unit unit;

        public AbstractVerticalOffset(VerticalOffset parent, double val, Unit unit) {
            this.parent = parent;
            this.val = val;
            this.unit = unit == null ? Unit.PIXEL : unit;
        }

        @Override
        public UnitVerticalOffset plus(double val) {
            return new UnitVerticalOffsetImpl(this, val);
        }

        @Override
        public VerticalOffset plus(double val, Unit unit) {
            return new AbstractVerticalOffset(this, val, unit);
        }

        @Override
        public UnitVerticalOffset minus(double val) {
            return plus(-val);
        }

        @Override
        public VerticalOffset minus(double val, Unit unit) {
            return plus(-val, unit);
        }

        @Override
        public double apply(double height) {
            double offset;
            switch (unit) {
            case PIXEL:
                offset = val;
                break;
            default:
                offset = height * val / 100.0;
                break;
            }
            return parent.apply(height) + offset;
        }

        @Override
        public String toString() {
            return format("%s%s%s", parent, DECIMAL_FORMAT.format(val), unit);
        }
    }

    public static class UnitVerticalOffsetImpl extends AbstractVerticalOffset implements UnitVerticalOffset {

        public UnitVerticalOffsetImpl(VerticalOffset parent, double val) {
            super(parent, val, Unit.PIXEL);
        }

        @Override
        public VerticalOffset percent() {
            return new AbstractVerticalOffset(parent, val, Unit.PERCENT);
        }

        @Override
        public VerticalOffset pixels() {
            return new AbstractVerticalOffset(parent, val, Unit.PIXEL);
        }
    }

    public static class OffsetImpl implements Offset {

        private final HorizontalOffset horizontal;
        private final VerticalOffset vertical;

        public OffsetImpl(HorizontalOffset horizontal, VerticalOffset vertical) {
            this.horizontal = horizontal;
            this.vertical = vertical;
        }

        @Override
        public HorizontalOffset horizontal() {
            return horizontal;
        }

        @Override
        public VerticalOffset vertical() {
            return vertical;
        }

        @Override
        public Point apply(Rectangle rectangle) {
            Point offset = offset(rectangle.dimension());
            return rectangle.topLeft().moveBy(offset.x(), offset.y());
        }

        @Override
        public Point offset(Dimension rectangle) {
            double x = horizontal.apply(rectangle.width());
            double y = vertical.apply(rectangle.height());
            return new Point(Math.round((float) x), Math.round((float) y));
        }

        @Override
        public String toString() {
            return format("%s %s", horizontal, vertical);
        }
    }

    public static class Parser {
        private static final Pattern POS_REGEX = Pattern.compile("(left|right|center)(?:([+-]\\d+(?:\\.\\d+)?)(%|px)?)?\\s+(top|bottom|center|middle)(?:([+-]?\\d+(?:\\.\\d+)?)(%|px)?)?");

        public Offset parse(String positionString) {
            Matcher matcher = POS_REGEX.matcher(positionString);
            Preconditions.checkArgument(matcher.matches(), "Not a valid position");

            HorizontalOffset horizontal = HorizontalReference.of(matcher.group(1));
            horizontal = horizontal.plus(parseVal(matcher.group(2)), Unit.of(matcher.group(3)));

            VerticalOffset vertical = VerticalReference.of(matcher.group(4));
            vertical = vertical.plus(parseVal(matcher.group(5)), Unit.of(matcher.group(6)));

            return new OffsetImpl(horizontal, vertical);
        }

        protected double parseVal(String valStr) {
            if (Strings.isNullOrEmpty(valStr)) return 0;

            if (valStr.startsWith("+")) valStr = valStr.substring(1);
            return Double.parseDouble(valStr);
        }
    }

}
