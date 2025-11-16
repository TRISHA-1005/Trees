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

public class IT2A_Group1_Lab5 {

    // ======== BST Node Class ========
    static class Node {
        int data;
        Node left, right;
        Node(int value) {
            data = value;
        }
    }

    // ======== BST Structure ========
    static class BST {
        Node root;

        // Insert (no duplicates)
        boolean insert(int value) {
            if (search(value)) return false;   // duplicate
            root = insertRec(root, value);
            return true;
        }

        private Node insertRec(Node root, int value) {
            if (root == null) return new Node(value);

            if (value < root.data)
                root.left = insertRec(root.left, value);
            else
                root.right = insertRec(root.right, value);

            return root;
        }

        // Search
        boolean search(int value) {
            return searchRec(root, value);
        }

        private boolean searchRec(Node root, int value) {
            if (root == null) return false;
            if (root.data == value) return true;

            return (value < root.data)
                    ? searchRec(root.left, value)
                    : searchRec(root.right, value);
        }

        // Delete using inorder successor
        boolean delete(int value) {
            if (!search(value)) return false;
            root = deleteRec(root, value);
            return true;
        }

        private Node deleteRec(Node root, int value) {
            if (root == null) return null;

            if (value < root.data)
                root.left = deleteRec(root.left, value);
            else if (value > root.data)
                root.right = deleteRec(root.right, value);
            else {
                // Case 1 & 2: 0 or 1 child
                if (root.left == null) return root.right;
                if (root.right == null) return root.left;

                // Case 3: 2 children – inorder successor
                root.data = minValue(root.right);
                root.right = deleteRec(root.right, root.data);
            }
            return root;
        }

        private int minValue(Node root) {
            int minV = root.data;
            while (root.left != null) {
                minV = root.left.data;
                root = root.left;
            }
            return minV;
        }

        // Traversals
        void inorder(Node r) {
            if (r != null) {
                inorder(r.left);
                System.out.print(r.data + " ");
                inorder(r.right);
            }
        }

        void preorder(Node r) {
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

        // Pretty-print BST sideways
        void show(Node root, int level) {
            if (root == null) return;

            show(root.right, level + 1);
            System.out.println("    ".repeat(level) + root.data);
            show(root.left, level + 1);
        }
    }

    // Clear Screen (Windows-friendly)
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Pause Screen
    public static void pause(Scanner sc) {
        System.out.print("\nPress any key to continue...");
        sc.nextLine();
    }

    // Get Integer Safely
    public static int getInt(Scanner sc) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.print("Invalid input! Enter an integer: ");
            }
        }
    }

    // ===============================
    //          MAIN PROGRAM
    // ===============================
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BST tree = new BST();
        char choice;

        do {
            clearScreen();
            System.out.println("======================================");
            System.out.println("                Menu");
            System.out.println("       BST Tree Operations");
            System.out.println("--------------------------------------");
            System.out.println("[ S ]   Show");
            System.out.println("[ I ]   Insert");
            System.out.println("[ D ]   Delete");
            System.out.println("[ T ]   Traverse");
            System.out.println("[ Q ]   Quit");
            System.out.println("--------------------------------------");
            System.out.print("Enter First Letter of Your Choice: ");
            choice = sc.next().toUpperCase().charAt(0);
            sc.nextLine(); // clear buffer

            switch (choice) {

                // ===== SHOW =====
                case 'S':
                    clearScreen();
                    if (tree.root == null)
                        System.out.println("Tree is empty!");
                    else
                        tree.show(tree.root, 0);
                    pause(sc);
                    break;

                // ===== INSERT =====
                case 'I':
                    char againI;
                    do {
                        System.out.print("Enter value to insert: ");
                        int val = getInt(sc);
                        sc.nextLine();

                        if (tree.insert(val))
                            System.out.println(val + " successfully inserted!");
                        else
                            System.out.println("Error: Duplicate value!");

                        System.out.print("Try Again (Y/N)? ");
                        againI = sc.next().toUpperCase().charAt(0);
                        sc.nextLine();
                    } while (againI == 'Y');
                    break;

                // ===== DELETE =====
                case 'D':
                    char againD;
                    do {
                        System.out.print("Enter value to delete: ");
                        int valD = getInt(sc);
                        sc.nextLine();

                        if (tree.delete(valD))
                            System.out.println(valD + " successfully deleted!");
                        else
                            System.out.println("Error: Value not found!");

                        System.out.print("Try Again (Y/N)? ");
                        againD = sc.next().toUpperCase().charAt(0);
                        sc.nextLine();
                    } while (againD == 'Y');
                    break;

                // ===== TRAVERSE =====
                case 'T':
                    char tchoice;
                    do {
                        clearScreen();
                        System.out.println("========= TREE TRAVERSAL =========");
                        System.out.println("1 - InOrder");
                        System.out.println("2 - PreOrder");
                        System.out.println("3 - PostOrder");
                        System.out.println("4 - Exit");
                        System.out.print("Enter Your Choice: ");
                        tchoice = sc.next().charAt(0);
                        sc.nextLine();

                        switch (tchoice) {
                            case '1':
                                System.out.print("InOrder: ");
                                tree.inorder(tree.root);
                                System.out.println();
                                pause(sc);
                                break;

                            case '2':
                                System.out.print("PreOrder: ");
                                tree.preorder(tree.root);
                                System.out.println();
                                pause(sc);
                                break;

                            case '3':
                                System.out.print("PostOrder: ");
                                tree.postorder(tree.root);
                                System.out.println();
                                pause(sc);
                                break;

                            case '4':
                                break;

                            default:
                                System.out.println("Invalid choice!");
                                pause(sc);
                        }

                    } while (tchoice != '4');
                    break;

                // ===== QUIT =====
                case 'Q':
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid menu choice!");
                    pause(sc);
            }

        } while (choice != 'Q');

        sc.close();
    }
}