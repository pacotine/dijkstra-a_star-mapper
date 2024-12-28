package main.instances;

/**
 * Represents a heuristic function used in pathfinding algorithms such as A*.
 * A heuristic function estimates the cost from a given node to a target node, guiding the algorithm
 * to the shortest path more efficiently. This interface provides several common heuristic strategies.
 */
public interface Heuristic {

    /**
     * Calculates the heuristic cost estimate between two points on a grid.
     *
     * @param vx the x-coordinate of the current point
     * @param vy the y-coordinate of the current point
     * @param ex the x-coordinate of the target point (end point)
     * @param ey the y-coordinate of the target point (end point)
     * @return the heuristic cost estimate between the current point and the target point
     */
    double h(int vx, int vy, int ex, int ey);

    /**
     * Represents the Euclidean distance heuristic.
     * Suitable for a square grid where movement is allowed in any direction.
     */
    Heuristic EUCLIDEAN = new Heuristic() {
        @Override
        public double h(int vx, int vy, int ex, int ey) {
            return Math.sqrt(Math.pow(vx-ex, 2) + Math.pow(vy-ey, 2));
        }

        @Override
        public String toString() {
            return "euclidean";
        }
    };

    /**
     * Represents the Manhattan distance heuristic.
     * Suitable for a square grid where movement is restricted to 4 directions (up, down, left, right).
     */
    Heuristic MANHATTAN = new Heuristic() {
        @Override
        public double h(int vx, int vy, int ex, int ey) {
            return Math.abs(vx-ex) + Math.abs(vy-ey);
        }

        @Override
        public String toString() {
            return "manhattan";
        }
    };

    /**
     * Represents the Chebyshev distance heuristic.
     * Suitable for a square grid where movement is allowed in 8 directions (diagonals included).
     */
    Heuristic CHEBYSHEV = new Heuristic() {
        @Override
        public double h(int vx, int vy, int ex, int ey) {
            return Math.max(Math.abs(vx-ex), Math.abs(vy-ey));
        }

        @Override
        public String toString() {
            return "chebyshev";
        }
    };

    /**
     * Represents the Octile distance heuristic.
     * Suitable for a square grid where movement is allowed in 8 directions,
     * and diagonal movement has a higher cost than moving in straight lines.
     */
    Heuristic OCTILE = new Heuristic() {
        @Override
        public double h(int vx, int vy, int ex, int ey) {
            int dx = Math.abs(vx-ex);
            int dy = Math.abs(vy-ey);
            return Math.sqrt(2)*Math.min(dx, dy) + Math.abs(dx - dy);
        }

        @Override
        public String toString() {
            return "octile";
        }
    };
}
