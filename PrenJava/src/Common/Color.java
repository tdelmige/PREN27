package Common;

/**
 * Created by raffaelsteinmann on 27.05.14.
 */
public enum Color {
    RED(0) {
        public String toString() {
            return "Rot";
        }
    },
    BLUE(1) {
        public String toString() {
            return "Blau";
        }
    },
    YELLOW(2) {
        public String toString() {
            return "Gelb";
        }
    },
    GREEN(3) {
        public String toString() {
            return "Gruen";
        }
    };

    private final int value;

    private Color(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
