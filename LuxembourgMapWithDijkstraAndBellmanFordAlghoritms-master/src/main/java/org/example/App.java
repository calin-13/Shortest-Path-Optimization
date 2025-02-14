package org.example;

import org.example.drawing.EdgeEnd;
import org.example.drawing.MainFrame;
import org.example.drawing.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        String filePath = App.class.getClassLoader().getResource("Harta_Luxemburg.xml").getPath();
        MapReader xmlReader = new MapReader(filePath);

        List<Node> nodes = new ArrayList<>();
        List<List<EdgeEnd>> adjLists = new ArrayList<>();
        xmlReader.readGraph(nodes, adjLists);

        MainFrame mainFrame = new MainFrame(nodes, adjLists);

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Enter the starting index");
            int start = scanner.nextInt();
            if (start == -1) {
                break;
            }

            System.out.println("Enter the ending index");
            int end = scanner.nextInt();
            if (end == -1) {
                break;
            }

            if (start < 0 || start >= nodes.size() || end < 0 || end >= nodes.size()) {
                System.out.println("Invalid indecies");
                continue;
            }

            mainFrame.path = Algorithms.Dijkstra(nodes, adjLists, start, end);
            mainFrame.repaint();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            mainFrame.path = Algorithms.Bellman_Ford(nodes, adjLists, start, end);
            mainFrame.repaint();

        } while (true);
    }
}
