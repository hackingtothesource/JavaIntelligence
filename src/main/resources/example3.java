package Graph;

import java.util.LinkedList;

public class Graph {

    private final int V;

    private int E;

    private LinkedList<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (LinkedList<Integer>[]) new LinkedList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedList<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public LinkedList<Integer> adj(int v) {
        return adj[v];
    }

    public int degree(int v, Graph g) {
        int count = 0;
        for (int s : adj(v)) {
            count++;
        }
        return count;
    }
}
