package minium.visual;

import minium.Point;

public interface Pattern {

    public float getSimilar();
    public Point getTargetOffset();

    public static class AbstractPattern<T extends Pattern> implements Pattern {

        protected float similarity = 0.8f;
        protected Point offset = new Point(0, 0);

        public AbstractPattern() {
        }

        public AbstractPattern(Pattern p) {
            this.similarity = p.getSimilar();
            this.offset = p.getTargetOffset();
        }

        /**
         * sets the minimum Similarity to use with findX
         *
         * @param sim
         * @return the Pattern object itself
         */
        @SuppressWarnings("unchecked")
        public T similar(float sim) {
            similarity = sim;
            return (T) this;
        }

        /**
         * sets the minimum Similarity to 0.99 which means exact match
         *
         * @return the Pattern object itself
         */
        @SuppressWarnings("unchecked")
        public T exact() {
            similarity = 0.99f;
            return (T) this;
        }

        /**
         *
         * @return the current minimum similarity
         */
        @Override
        public float getSimilar() {
            return this.similarity;
        }

        /**
         * set the offset from the match's center to be used with mouse actions
         *
         * @param dx
         * @param dy
         * @return the Pattern object itself
         */
        @SuppressWarnings("unchecked")
        public T targetOffset(int dx, int dy) {
            offset = new Point(dx, dy);
            return (T) this;
        }

        /**
         * set the offset from the match's center to be used with mouse actions
         *
         * @param point
         * @return the Pattern object itself
         */
        @SuppressWarnings("unchecked")
        public T targetOffset(Point point) {
            offset = point;
            return (T) this;
        }

        /**
         *
         * @return the current offset
         */
        @Override
        public Point getTargetOffset() {
            return offset;
        }
    }
}
