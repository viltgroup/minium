package minium;

import minium.internal.InternalOffsets;

import com.google.common.base.Objects;

public class Offsets {

    public static class ParseException extends RuntimeException {

        private static final long serialVersionUID = 866185720502410258L;

        public ParseException() {
            super();
        }

        public ParseException(String message, Throwable cause) {
            super(message, cause);
        }

        public ParseException(String message) {
            super(message);
        }

        public ParseException(Throwable cause) {
            super(cause);
        }
    }

    public interface Offset {
        public HorizontalOffset horizontal();
        public VerticalOffset vertical();
        public Point apply(Rectangle rectangle);
        public Point offset(Dimension rectangle);
    }

    public interface HorizontalOffset {
        public UnitHorizontalOffset plus(double val);
        public HorizontalOffset plus(double val, Unit unit);
        public UnitHorizontalOffset minus(double val);
        public HorizontalOffset minus(double val, Unit unit);
        public double apply(double width);
    }

    public interface UnitHorizontalOffset extends HorizontalOffset {
        public HorizontalOffset percent();
        public HorizontalOffset pixels();
    }

    public interface VerticalOffset {
        public UnitVerticalOffset plus(double val);
        public VerticalOffset plus(double val, Unit unit);
        public UnitVerticalOffset minus(double val);
        public VerticalOffset minus(double val, Unit unit);
        public double apply(double height);
    }

    public interface UnitVerticalOffset {
        public VerticalOffset percent();
        public VerticalOffset pixels();
    }

    public static enum Unit {
        PIXEL("px"),
        PERCENT("%");

        private final String name;

        private Unit(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Unit of(String name) {
            for (Unit unit : values()) {
                if (Objects.equal(unit.name, name)) return unit;
            }
            return null;
        }
    }

    public static enum VerticalReference implements VerticalOffset {
        TOP("top") {
            @Override
            public double apply(double height) {
                return 0;
            }
        },
        MIDDLE("center") {
            @Override
            public double apply(double height) {
                return height / 2;
            }
        },
        BOTTOM("bottom") {
            @Override
            public double apply(double height) {
                return height;
            }
        },
        MINUS_INF("-inf") {
            @Override
            public double apply(double height) {
                return Double.NEGATIVE_INFINITY;
            }
            @Override
            public UnitVerticalOffset plus(double val) {
                throw new UnsupportedOperationException();
            }
            @Override
            public VerticalOffset plus(double val, Unit unit) {
                throw new UnsupportedOperationException();
            }
            @Override
            public UnitVerticalOffset minus(double val) {
                throw new UnsupportedOperationException();
            }
            @Override
            public VerticalOffset minus(double val, Unit unit) {
                throw new UnsupportedOperationException();
            }
        },
        PLUS_INF("+inf") {
            @Override
            public double apply(double height) {
                return Double.POSITIVE_INFINITY;
            }
            @Override
            public UnitVerticalOffset plus(double val) {
                throw new UnsupportedOperationException();
            }
            @Override
            public VerticalOffset plus(double val, Unit unit) {
                throw new UnsupportedOperationException();
            }
            @Override
            public UnitVerticalOffset minus(double val) {
                throw new UnsupportedOperationException();
            }
            @Override
            public VerticalOffset minus(double val, Unit unit) {
                throw new UnsupportedOperationException();
            }
        };

        private final String name;

        private VerticalReference(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public static VerticalReference of(String name) {
            for (VerticalReference ref : values()) {
                if (Objects.equal(ref.name, name)) return ref;
            }
            return null;
        }

        @Override
        public UnitVerticalOffset plus(double val) {
            return new InternalOffsets.UnitVerticalOffsetImpl(this, val);
        }

        @Override
        public VerticalOffset plus(double val, Unit unit) {
            return new InternalOffsets.AbstractVerticalOffset(this, val, unit);
        }

        @Override
        public UnitVerticalOffset minus(double val) {
            return plus(-val);
        }

        @Override
        public VerticalOffset minus(double val, Unit unit) {
            return plus(-val, unit);
        }
    }

    public static enum HorizontalReference implements HorizontalOffset {
        LEFT("left") {
            @Override
            public double apply(double width) {
                return 0;
            }
        },
        CENTER("center") {
            @Override
            public double apply(double width) {
                return width / 2;
            }
        },
        RIGHT("right") {
            @Override
            public double apply(double width) {
                return width;
            }
        },
        MINUS_INF("-inf") {
            @Override
            public double apply(double width) {
                return Double.NEGATIVE_INFINITY;
            }
            @Override
            public UnitHorizontalOffset plus(double val) {
                throw new UnsupportedOperationException();
            }
            @Override
            public HorizontalOffset plus(double val, Unit unit) {
                throw new UnsupportedOperationException();
            }
            @Override
            public UnitHorizontalOffset minus(double val) {
                throw new UnsupportedOperationException();
            }
            @Override
            public HorizontalOffset minus(double val, Unit unit) {
                throw new UnsupportedOperationException();
            }
        },
        PLUS_INF("+inf") {
            @Override
            public double apply(double width) {
                return Double.POSITIVE_INFINITY;
            }
            @Override
            public UnitHorizontalOffset plus(double val) {
                throw new UnsupportedOperationException();
            }
            @Override
            public HorizontalOffset plus(double val, Unit unit) {
                throw new UnsupportedOperationException();
            }
            @Override
            public UnitHorizontalOffset minus(double val) {
                throw new UnsupportedOperationException();
            }
            @Override
            public HorizontalOffset minus(double val, Unit unit) {
                throw new UnsupportedOperationException();
            }
        };

        private final String name;

        private HorizontalReference(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public static HorizontalReference of(String name) {
            for (HorizontalReference ref : values()) {
                if (Objects.equal(ref.name, name)) return ref;
            }
            return null;
        }

        @Override
        public UnitHorizontalOffset plus(double val) {
            return new InternalOffsets.UnitHorizontalOffsetImpl(this, val);
        }

        @Override
        public HorizontalOffset plus(double val, Unit unit) {
            return new InternalOffsets.AbstractHorizontalOffset(this, val, unit);
        }

        @Override
        public UnitHorizontalOffset minus(double val) {
            return plus(-val);
        }

        @Override
        public HorizontalOffset minus(double val, Unit unit) {
            return plus(-val, unit);
        }
    }

    public static Offset at(HorizontalOffset horizontal, VerticalOffset vertical) {
        return new InternalOffsets.OffsetImpl(horizontal, vertical);
    }

    public static Offset at(String pos) throws ParseException {
        return new InternalOffsets.Parser().parse(pos);
    }
}
