package common;

import java.util.Objects;

/**
 * Denotes the nodes of the graph.
 */
public class Node {
    private final int id;
    private final String name;
    // optional coordinates
    private final int x;
    private final int y;

    public Node(int id, String name) {
        this(id, name, -1, -1);
    }
    public Node(int id, String name, int x, int y) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }

        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
