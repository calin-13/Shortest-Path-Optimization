package org.example;

import org.example.drawing.EdgeEnd;

import java.util.*;
import java.util.List;

import org.example.drawing.Node;

public class Algorithms {
    private static final int INFINITY = 1_000_000_000;

    public static List<Integer> Dijkstra(List<Node> nodes, List<List<EdgeEnd>> adjLists, int start, int end, boolean printPath) {
        int[] minimumDistance = new int[nodes.size()];
        int[] previousNode = new int[nodes.size()];
        boolean[] visited = new boolean[nodes.size()];
        PriorityQueue<PQElement> pq = new PriorityQueue<>();

        for (int i = 0; i < nodes.size(); i++) {
            minimumDistance[i] = INFINITY;
            previousNode[i] = -1;
            visited[i] = false;
        }

        minimumDistance[start] = 0;
        pq.add(new PQElement(start, 0));

        while (!pq.isEmpty()) {
            PQElement current = pq.poll();
            visited[current.nodeIndex] = true;

            if (minimumDistance[current.nodeIndex] < current.distance) {
                continue;
            }

            for (EdgeEnd edge : adjLists.get(current.nodeIndex)) {
                if (!visited[edge.end]) {
                    int newDistance = minimumDistance[current.nodeIndex] + edge.length;
                    if (newDistance < minimumDistance[edge.end]) {
                        minimumDistance[edge.end] = newDistance;
                        previousNode[edge.end] = current.nodeIndex;
                        pq.add(new PQElement(edge.end, newDistance));
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        int currentNode = end;
        while (currentNode != -1) {
            path.add(0, currentNode);
            currentNode = previousNode[currentNode];
        }

        System.out.println("[Dijkstra]");
        if(printPath) {
            System.out.println("Path from " + start + " to " + end);
            System.out.println(path);
        }
        System.out.println("Length: " + minimumDistance[end]);
        System.out.println();
        return path;
    }

    public static List<Integer> Bellman_Ford(List<Node> nodes, List<List<EdgeEnd>> adjLists, int start, int end, boolean printPath) {

        int[] minimumDistance = new int[nodes.size()];
        int[] previousNode = new int[nodes.size()];

        for (int i = 0; i < nodes.size(); i++) {
            minimumDistance[i] = INFINITY;
            previousNode[i] = -1;
        }
        minimumDistance[start] = 0;

        for (int i = 0; i < nodes.size() - 1; i++) {
            boolean distanceImproved = false;

            for (Node startNode : nodes) {
                for (EdgeEnd edge : adjLists.get(startNode.id)) {

                    if (minimumDistance[startNode.id] + edge.length < minimumDistance[edge.end]) {
                        minimumDistance[edge.end] = minimumDistance[startNode.id] + edge.length;
                        previousNode[edge.end] = startNode.id;
                        distanceImproved = true;
                    }
                }
            }

            if(!distanceImproved) {
                break;
            }
        }

        for (int i = 0; i < nodes.size() - 1; i++) {
            boolean distanceImproved = false;

            for (Node startNode : nodes) {
                for (EdgeEnd edge : adjLists.get(startNode.id)) {

                    if (minimumDistance[startNode.id] + edge.length < minimumDistance[edge.end]) {
                        minimumDistance[edge.end] = Integer.MIN_VALUE;
                        previousNode[edge.end] = startNode.id;
                        distanceImproved = true;
                    }
                }
            }

            if(!distanceImproved) {
                break;
            }
        }

        List<Integer> path = new ArrayList<>();
        int currentNode = end;
        while (currentNode != -1) {
            if(path.contains(currentNode)) {
                System.out.println("Negative cycle");
                break;
            }
            path.add(0, currentNode);
            currentNode = previousNode[currentNode];
        }

        System.out.println("[Bellman-Ford]");
        if(printPath) {
            System.out.println("Path from " + start + " to " + end);
            System.out.println(path);
        }
        System.out.println("Length: " + minimumDistance[end]);
        System.out.println();
        return path;
    }

    public static List<Integer> Dijkstra(List<Node> nodes, List<List<EdgeEnd>> adjLists, int start, int end) {
        return Dijkstra(nodes, adjLists, start, end, true);
    }

    public static List<Integer> Bellman_Ford(List<Node> nodes, List<List<EdgeEnd>> adjLists, int start, int end) {
        return Bellman_Ford(nodes, adjLists, start, end, true);
    }
}

class PQElement implements Comparable<PQElement> {
    public int nodeIndex;
    public int distance;

    @Override
    public int compareTo(PQElement o) {
        return Integer.compare(this.distance, o.distance);
    }

    public PQElement(int nodeIndex, int distance) {
        this.nodeIndex = nodeIndex;
        this.distance = distance;
    }
}
