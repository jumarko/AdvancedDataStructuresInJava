package roadgraph;

import static roadgraph.Utils.notNull;

import java.util.Objects;

/**
 * Represents a directed edge between two Map nodes ({@link MapNode}.
 */
class MapEdge {
    private final MapNode start;
    private final MapNode end;
    private final String roadName;
    private final String roadType;
    private final double length;

    MapEdge(MapNode start, MapNode end, String roadName, String roadType, double length) {
        notNull(start, "start node");
        notNull(end, "end node");
        notNull(roadName, "road name");
        notNull(roadType, "road type");
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be a positive number");
        }

        this.start = start;
        this.end = end;
        this.roadName = roadName;
        this.roadType = roadType;
        this.length = length;
    }

    public MapNode getStart() {
        return start;
    }

    public MapNode getEnd() {
        return end;
    }

    public String getRoadName() {
        return roadName;
    }

    public String getRoadType() {
        return roadType;
    }

    public double getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapEdge mapEdge = (MapEdge) o;
        return Double.compare(mapEdge.length, length) == 0 &&
                Objects.equals(start, mapEdge.start) &&
                Objects.equals(end, mapEdge.end) &&
                Objects.equals(roadName, mapEdge.roadName) &&
                Objects.equals(roadType, mapEdge.roadType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, roadName, roadType, length);
    }

    @Override
    public String toString() {
        return "{" +
                "start=" + start +
                ", end=" + end +
                ", roadName='" + roadName + '\'' +
                ", roadType='" + roadType + '\'' +
                ", length=" + length +
                '}';
    }
}
