/* Short Description
 * Group 5
 * Authors: Daclison, Trisha Jamaica U.  (Leader)
 *          Banzuela, Francine Hope G.   (Members)
 *          Cainday, Katrina Camille B.
 * Laboratory Exercise 5
 * Date: November 15, 2025
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class IT2A_Group5_Lab5 {

    static Scanner sc = new Scanner(System.in);
    static BST tree = new BST();

    // Main Menu
    public static void main(String[] args) {
        char choice;

        do {
            clearScreen();
            System.out.println("-------------------------------------");
            System.out.println("                Menu                 ");
            System.out.println("         BST Tree Operations         ");
            System.out.println("-------------------------------------");
            System.out.println("[S] Show");
            System.out.println("[I] Insert");
            System.out.println("[D] Delete");
            System.out.println("[T] Traverse");
            System.out.println("[Q] Quit");
            System.out.println();
            System.out.print("Enter First Letter of Your Choice: ");
            choice = sc.next().toUpperCase().charAt(0);
            sc.nextLine();

            switch (choice) {

                // Case 1: Show
                case 'S':
                    if (tree.root == null)
                        System.out.println("The Tree is Empty!");
                    else
                        tree.show(tree.root, 0);
                    pause(sc);
                    break;

                // Case 2: Insert
                case 'I':
                    char insertI;
                    do { 
                        System.out.print("Enter Value to Insert: ");
                        int val = getInt(sc);
                        sc.nextLine();

                        if (tree.insert(val))
                            System.out.println(val + " successfully inserted!");
                        else
                            System.out.println("Error! Duplicate Value. Please Enter New Value.");
                        
                        insertI = askTryAgain(sc); // <-- Call the new try again method
                    } while (insertI == 'Y');
                    break;

                // Case 3: Delete
                case 'D':
                    char deleteD;
                    do { 
                        System.out.print("Enter value to delete: ");
                        int valD = getInt(sc);
                        sc.nextLine();

                        if (tree.delete(valD))
                            System.out.println(valD + " successfully deleted!");
                        else
                            System.out.println("Error! Value Not Found. Please Enter an Existing Value");
                        
                        deleteD = askTryAgain(sc); // <-- Call the new try again method
                    } while (deleteD == 'Y');
                    break;

                // Case 4: Traverse
                case 'T':
                    char choiceT;
                    do {
                        clearScreen();
                        System.out.println("---------- Tree Traversal ----------");
                        System.out.println("1 - InOrder");
                        System.out.println("2 - PreOrder");
                        System.out.println("3 - PostOrder");
                        System.out.println("4 - Exit");
                        System.out.print("Enter Your Choice: ");
                        choiceT = sc.next().charAt(0);
                        sc.nextLine();

                        // Traversal Choices
                        switch (choiceT) {
                            // Choice 1: In Order
                            case '1':
                                System.out.print("InOrder: ");
                                tree.inorder(tree.root);
                                System.out.println();
                                pause(sc);
                                break;

                            // Choice 2: Pre Order
                            case '2':
                                System.out.print("PreOrder: ");
                                tree.preorder(tree.root);
                                System.out.println();
                                pause(sc);
                                break;

                            // Choice 3: Post Order
                            case '3':
                                System.out.print("PostOrder: ");
                                tree.postorder(tree.root);
                                System.out.println();
                                pause(sc);
                                break;

                            // Choice 4: Exit
                            case '4':
                                break;

                            default:
                                System.out.println("Invalid choice!");
                                pause(sc);
                        }

                    } while (choiceT != '4');
                    break;

                // QUIT
                case 'Q':
                    System.out.println("Program terminated. Thank you!");
                    break;

                default:
                    System.out.println("Invalid menu choice!");
                    pause(sc);
            }

        } while (choice != 'Q');

        sc.close();
    }

    // BST Node Class
    static class Node {
        int data;
        Node left, right;

        Node(int value) {
            data = value;
        }
    }

    // BST Structure
    static class BST {
        Node root;

        // Insert (No Duplication)
        boolean insert(int value) {
            if (search(value)) return false; 
            root = insertVal(root, value);
            return true;
        }

        private Node insertVal(Node root, int value) {
            if (root == null)
                return new Node(value);

            if (value < root.data)
                root.left = insertVal(root.left, value);
            else 
                root.right = insertVal(root.right, value);

            return root;
        }

        // Search
        boolean search(int value) {
            return searchVal(root, value);
        }

        private boolean searchVal(Node root, int value) {
            if (root == null)
                return false;
            if (root.data == value)
                return true;
            
            if (value < root.data)
                return searchVal(root.left, value);
            else
                return searchVal(root.right, value);
        }

        // Delete Using Inorder Successor
        boolean delete(int value) {
            if (!search(value))
                return false;

            root = deleteVal(root, value);
            return true;
        }

        private Node deleteVal(Node root, int value) {
            if (root == null)
                return null;

            if (value < root.data)
                root.left = deleteVal(root.left, value);
            else if (value > root.data)
                root.right = deleteVal(root.right, value);
            else {
                if (root.left == null)
                    return root.right;
                if (root.right == null)
                    return root.left;

                root.data = minValue(root.right);
                root.right = deleteVal(root.right, root.data);
            }
            return root;
        }

        private int minValue(Node root){
            int minVal = root.data;
            while (root.left != null) {
                minVal = root.left.data;
                root = root.left;
            }
            return minVal;
        }

        // Traversals
        void inorder(Node r) {
            if (r != null) {
                inorder(r.left);
                System.out.print(r.data + " ");
                inorder(r.right);
            }
        }

        void preorder(Node r){
            if (r != null) {
                System.out.print(r.data + " ");
                preorder(r.left);
                preorder(r.right);
            }
        }

        void postorder(Node r) {
            if (r != null) {
                postorder(r.left);
                postorder(r.right);
                System.out.print(r.data + " "); 
            }
        }

        void show(Node root, int level) {
            if (root == null) return;

            show(root.right, level + 1);
            System.out.println("    ".repeat(level) + root.data);
            show(root.left, level + 1);
        }
    }

    // Clear Screen
    public static void clearScreen() {
        for (int i = 0; i < 10; i++)
            System.out.println();
    }

    // Pause Screen
    public static void pause(Scanner sc) {
        System.out.print("\nPress any key to continue...");
        sc.nextLine();
        clearScreen();
    }

    // Get Integer Safely
    public static int getInt(Scanner sc){
        while(true){
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.print("Invalid Input! Enter an Integer: ");
            }
        }
    }

    // Added Method: Ask user to try again (Y/N) and validate input
    public static char askTryAgain(Scanner sc) {
        char input;
        while (true) {
            System.out.print("Try Again (Y/N)? ");
            String line = sc.nextLine().trim().toUpperCase();

            if (line.length() == 1 && (line.charAt(0) == 'Y' || line.charAt(0) == 'N')) {
                input = line.charAt(0);
                break;
            } else {
                System.out.println("Invalid input! Please enter only 'Y' or 'N'.");
            }
        }
        return input;
    }
}