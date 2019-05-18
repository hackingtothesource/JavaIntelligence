package Graph;

import java.util.LinkedList;

public class Graph {

    private final int V, X;

    private int E;

    private LinkedList<Integer>[] adj;

    public Graph(int ver_num) {
        V = ver_num;
        E = 0;
        adj = (LinkedList<Integer>[]) new LinkedList[ver_num];
        for (int i = 0; i < ver_num; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int a, int b) {
        adj[a].add(b);
        adj[b].add(a);
        E++;
    }

    public LinkedList<Integer> adj(int x) {
        return adj[x];
    }

    public int degree(int Vertex, Graph g) {
        int Count = 0;
        for (int s : adj(Vertex)) {
            Count++;
        }
        return Count;
    }
}
