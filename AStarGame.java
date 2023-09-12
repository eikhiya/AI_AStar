package AStar;

/**
 * Author: Eikhiya Khaing
 * Version: 1.0
 * Date: 3/24/2023
 * 
 * AStarGame.java
 * This is the main (driver) class that runs the game.
 * It creates a 15x15 world with about 10% of the nodes being unpathable. The game ask
 * the user to input the starting and goal node. The game uses the A Star
 * algorithm to find the shortest path from the starting node to the goal node
 */

import java.util.*;

// importing the Node class
import AStar.AStarAlgorithm.Node;

public class AStarGame {
    public static void main(String[] args) {

        // initializing and declaring variables
        AStarAlgorithm world = new AStarAlgorithm();
        Scanner scanner = new Scanner(System.in);

        // Print the world with the rocks in the way (obstacles)
        world.newWorld();
        world.printWorld();

        System.out.println("\nThis is the world with rocks in the way! Enter the " +
                            "\nstarting node and goal node. The program will find " +
                            "\nthe shortest path without going through the rocks");
        
        // Prompt the user to enter the starting and goal nodes
        System.out.println("\nEnter the x coordinate (row) for the starting node:");
        int startX = scanner.nextInt();
        System.out.println("Enter the y coordinate (column) for the starting node:");
        int startY = scanner.nextInt();
        Node startNode = new Node(startX, startY);
        
        System.out.println("Enter the x coordinate (row) for the goal node:");
        int goalX = scanner.nextInt();
        System.out.println("Enter the y coordinate (column) for the goal node:");
        int goalY = scanner.nextInt();
        Node goalNode = new Node(goalX, goalY);

        // close the scanner
        scanner.close();
        
        // Find the shortest path from the starting node to the goal node using A*
        List<Node> path = world.findPath(startNode, goalNode);
        if (path == null) {
            System.out.println("\nNo path found. Rocks in the way!\n");
        } 
        else {
            System.out.println("\nThe shortest path:");
            for (Node node : path) {
                System.out.print("(" + node.x + "," + node.y + "),");
            }

            // Print the world after the path is found
            System.out.println("\n\nThe path have been found!");
            world.printPath(path);
            System.out.println("\n"); // for spacing. Easier to look at the output
        }
    } // end of main
} // end of class
