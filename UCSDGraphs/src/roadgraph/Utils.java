package roadgraph;

class Utils {
    static void notNull(Object object, String objectDescription) {
        if (object == null) {
            throw new IllegalArgumentException(objectDescription + " cannot be null!");
        }
    }

}
