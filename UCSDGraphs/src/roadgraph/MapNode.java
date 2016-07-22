package roadgraph;

import static roadgraph.Utils.*;

import geography.GeographicPoint;

import java.util.Objects;

/**
 * Represents an intersection on {@link MapGraph}.
 * It is determined by it's geographic location
 */
class MapNode {
    private final GeographicPoint location;

    MapNode(GeographicPoint location) {
        notNull(location, "location");
        this.location = location;
    }

    GeographicPoint getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapNode mapNode = (MapNode) o;
        return Objects.equals(location, mapNode.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return "{location=" + location + "}";
    }
}
