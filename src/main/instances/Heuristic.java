package main.instances;

public interface Heuristic {
    double h(int vx, int vy, int ex, int ey);

    Heuristic EUCLIDEAN = new Heuristic() {
        @Override
        public double h(int vx, int vy, int ex, int ey) {
            return Math.sqrt(Math.pow(vx-ex, 2) + Math.pow(vy-ey, 2));
        }

        @Override
        public String toString() {
            return "euclidean";
        }
    }; //square grid - any direction


    Heuristic MANHATTAN = new Heuristic() {
        @Override
        public double h(int vx, int vy, int ex, int ey) {
            return Math.abs(vx-ex) + Math.abs(vy-ey);
        }

        @Override
        public String toString() {
            return "manhattan";
        }
    }; //square grid - 4 directions

    Heuristic CHEBYSHEV = new Heuristic() {
        @Override
        public double h(int vx, int vy, int ex, int ey) {
            return Math.max(Math.abs(vx-ex), Math.abs(vy-ey));
        }

        @Override
        public String toString() {
            return "chebyshev";
        }
    }; //square grid - 8 directions

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
    }; //square grid - 8 directions, better
}
