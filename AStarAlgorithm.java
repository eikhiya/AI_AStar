package AStar;

/**
 * Author: Eikhiya Khaing
 * Version: 1.0
 * Date: 3/24/2023
 * 
 * AStarAlgorithm.java
 * This program uses the A* algorithm to find the shortest path
 * from a given starting node to the goal node.
 */

import java.util.*;

public class AStarAlgorithm {

    // initializing and declaring variables
    char[][] world = new char[15][15]; // declare an array to store the world
    Random rand = new Random(); // declare a Random object

    /**
     * This method creates a new world with about 10% of the tiles being unpathable
     * no parameter and no return value
     */
    public void newWorld() {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length; j++) {
                // for each tile, pick a random number between 1 and 100.
                // if the number is less than 10, then the tile is become an obstacle
                if (rand.nextInt(100) < 10) {
                    world[i][j] = 'O'; // O represents a rock (obstacle)
                } 
                else {
                    world[i][j] = '_'; // save path
                }
            }
        }
    }

    /**
     * This method prints out the world without the path
     * no parameter and no return value
     */
    public void printWorld() {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length; j++) {
                System.out.print(world[i][j] + " ");
            }
            System.out.println(); // print a new row
        }
    }

    /**
     * This class represents a node
     * It has 5 fields: x, y, gCost, gCost, and parent
     * It has 1 constructor
     * It has 1 method: getFCost()
     * 
     * x and y represent the coordinates of the node
     * gCost represents the cost to move from the starting node to this node
     * hCost represents the heuristic cost to move from this node to the goal node
     * parent represents the parent node of this node
     * 
     */
    static class Node {
        int x;
        int y;
        int gCost; // cost to move from starting node to current node
        int hCost; // heuristic cost to move from current node to goal node
        Node parent;

        // Constructor for the Node class
        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Calculate the total cost of moving from the starting node to the goal node
        int getFCost() {
            return gCost + hCost;
        }
    }

    /**
     * This is a heuristic function that uses the Manhattan method to calculate 
     * the cost to move from the starting node to the goal node
     * @param startNode the starting node
     * @param goalNode the goal node
     * @return the cost to move from the starting node to the goal node
     */
    public int manhattanMethod(Node startNode, Node goalNode) {
        int x = Math.abs(startNode.x - goalNode.x);
        int y = Math.abs(startNode.y - goalNode.y);
        return x + y;
    }

    /**
     * This method returns a list of adjacent nodes of the given node (neighbors)
     * @param node the given node
     * @return a list of neighbors of the given node
     */
    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>(); // create a list to store the neighbors
        int x = node.x; // getting the x coordinate of the given node
        int y = node.y; // getting the y coordinate of the given node

        // check left
        if (x > 0) {
            neighbors.add(new Node(x - 1, y)); // add the left neighbor to the list
        }

        // check right
        if (x < world.length - 1) {
            neighbors.add(new Node(x + 1, y)); // add the right neighbor to the list
        }

        // check above
        if (y > 0) {
            neighbors.add(new Node(x, y - 1)); // add the above neighbor to the list
        }

        // check below
        if (y < world[0].length - 1) {
            neighbors.add(new Node(x, y + 1)); // add the below neighbor to the list
        }

        return neighbors;
    }

    /**
     * This method gets the path from the starting node to the goal node
     * @param endNode the goal node
     * @return a list of nodes that represent the path from the starting node to 
     * the goal node
     */
    public List<Node> getPath(Node endNode) {
        List<Node> path = new ArrayList<>(); // create a list to store the path
        Node currentNode = endNode; // set the current node to the goal node

        // loop through the parent of each node until we reach the starting node
        while (currentNode != null) {
            path.add(0, currentNode); // add the current node to the beginning of the list
            currentNode = currentNode.parent; // set the current node to its parent
        }
        return path;
    }

    /**
     * using the A* algorithm to solve the path with the lowest cost
     * @param startNode the starting node
     * @param goalNode the goal node
     * @return a list of nodes that represent the path from the starting node to
     */
    public List<Node> findPath(Node startNode, Node goalNode) {

        // openList is a list of nodes that have not been checked yet
        // closeList is a list of nodes that have been checked
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();

        startNode.gCost = 0;
        startNode.hCost = manhattanMethod(startNode, goalNode);
        openList.add(startNode); // add the starting node to the open list

        // loop through the open list until it is empty
        while (!openList.isEmpty()) {
            // Sort the open list by the fCost
            Node currentNode = openList.get(0);

            // Check if we have reached the goal node
            if (currentNode.x == goalNode.x && currentNode.y == goalNode.y) {
                return getPath(currentNode);
            }

            // Remove the current node from the open list and add it to the closed list
            openList.remove(currentNode);
            closedList.add(currentNode);

            // Get the neighbors of the current node
            List<Node> neighbors = getNeighbors(currentNode);

            // Loop through the neighbors
            for (Node neighbor : neighbors) {
                // Check if the neighbor is unpathable or in the closed list
                if (world[neighbor.x][neighbor.y] == 'O' || closedList.contains(neighbor)) {
                    // skip to the next neighbor if the current neighbor is unpathable or in the closed list
                    continue;
                }

                // Calculate the cost to move to the neighbor
                // The cost to move to the neighbor is always 1 in this implementation
                int CurrentGCost = currentNode.gCost + 1;

                // Check if the neighbor is already in the open list and has a lower gCost
                if (openList.contains(neighbor) && CurrentGCost >= neighbor.gCost) {
                    // skip to the next neighbor if the neighbor is already in the open list and has a lower gCost
                    continue;
                }

                // Otherwise, update the neighbor's gCost and hCost 
                neighbor.parent = currentNode;
                neighbor.gCost = CurrentGCost;
                neighbor.hCost = manhattanMethod(neighbor, goalNode);

                // If the neighbor is not in the open list, add it
                if (!openList.contains(neighbor)) {
                    openList.add(neighbor);
                }
            }
            // Sort the open list by fCost (gCost + hCost) in ascending order
            Collections.sort(openList, Comparator.comparingInt(Node::getFCost));
        }
        // return null if there's no path found
        return null;
    }

    /**
     * This method prints world with the path
     * @param path the path to print
     * @return no return value
     */
    public void printPath(List<Node> path) { 
        
        // Check if there's no path found
        if (path == null) {
            System.out.println("No path found");
            return;
        }

        // Loop through the world and print the path
        for (int i = 0; i < world.length; i++) { // loop through rows
            for (int j = 0; j < world[i].length; j++) { // loop through columns
                if (world[i][j] == 'O') { // if the node is unpathable (if rocks are in the way!)
                    System.out.print("O ");
                } 
                else {
                    boolean isPath = false;
                    for (Node node : path) { // loop through the path
                        if (node.x == i && node.y == j) { // if the node is in the path
                            System.out.print("* "); // "*" represent the nodes on the path
                            isPath = true;
                            break;
                        }
                    }
                    if (!isPath) {
                        System.out.print("_ "); // "_" represent the nodes that are not on the path
                    }
                }
            }
            System.out.println(); // print a new row
        }
    }
} // end of class