import java.io.*;
import java.util.*;


public class Huffman {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // file for probabilities
        String filename = "LettersProbability.txt";

        // Read probabilities from the file
        List<Pair> probabilities = readProbabilities(filename);


        // Build Huffman tree
        BinaryTree<Pair> huffmanTree = buildHuffmanTree(probabilities);

        // Prompt for text input
        System.out.print("Enter a line of text (uppercase letters only): ");
        String input = scanner.nextLine();

        // Validate input (uppercase letters only)
        if (!input.matches("[A-Z ]*")) {
            System.out.println("Error: Please enter uppercase letters only.");
            return;
        }

        // Encode the input
        String encoded = encode(input, huffmanTree);
        System.out.println("Here’s the encoded line: " + encoded);

        // Decode the encoded string
        String decoded = decode(encoded, huffmanTree);
        System.out.println("The decoded line is: " + decoded);
    }

    private static List<Pair> readProbabilities(String filename) throws IOException {
        List<Pair> probabilities = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            String[] parts = line.split(" +|\t");
            if (parts.length == 2) {
                char symbol = parts[0].charAt(0);
                double probability = Double.parseDouble(parts[1].trim());
                probabilities.add(new Pair(symbol, probability));
            } else {
                throw new IOException("Invalid file format. Each line must contain a letter and its probability separated by a space or tab.");
            }
        }
        br.close();
        return probabilities;
    }

    private static BinaryTree<Pair> buildHuffmanTree(List<Pair> probabilities) {
        // Two separate queues: S for initial nodes, T for intermediate nodes
        Queue<BinaryTree<Pair>> S = new LinkedList<>();
        Queue<BinaryTree<Pair>> T = new LinkedList<>();

        // Populate Queue S with BinaryTree<Pair> objects sorted by probabilities
        probabilities.sort(Comparator.comparingDouble(Pair::getProb));
        for (Pair pair : probabilities) {
            BinaryTree<Pair> tree = new BinaryTree<>();
            tree.makeRoot(pair);
            S.add(tree);
        }

        // Build the Huffman Tree
        while (S.size() + T.size() > 1) {
            // Pick the two smallest trees A and B
            BinaryTree<Pair> A = pickSmallest(S, T);
            BinaryTree<Pair> B = pickSmallest(S, T);

            // Combine A and B into a new tree P
            BinaryTree<Pair> P = new BinaryTree<>();
            P.makeRoot(new Pair('\0', A.getData().getProb() + B.getData().getProb()));
            P.attachLeft(A);
            P.attachRight(B);

            // Enqueue P into T
            T.add(P);
        }

        // The last tree remaining in T is the Huffman tree
        return T.poll();
    }

    private static BinaryTree<Pair> pickSmallest(Queue<BinaryTree<Pair>> S, Queue<BinaryTree<Pair>> T) {
        if (S.isEmpty()) return T.poll();
        if (T.isEmpty()) return S.poll();

        BinaryTree<Pair> frontS = S.peek();
        BinaryTree<Pair> frontT = T.peek();

        if (frontS.getData().getProb() < frontT.getData().getProb() ||
                (frontS.getData().getProb() == frontT.getData().getProb() &&
                        frontS.getData().getValue() < frontT.getData().getValue())) {
            return S.poll();
        } else {
            return T.poll();
        }
    }

    private static String encode(String text, BinaryTree<Pair> huffmanTree) {
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c == ' ') {
                encoded.append(" ");
            } else {
                String code = findCode(huffmanTree, c, "");
                encoded.append(code);
            }
        }
        return encoded.toString();
    }

    private static String findCode(BinaryTree<Pair> bt, char target, String prefix) {
        if (bt.getLeft() == null && bt.getRight() == null) {
            if (bt.getData().getValue() == target) {
                return prefix;
            }
            return "";
        } else {
            if (bt.getLeft() != null) {
                String left = findCode(bt.getLeft(), target, prefix + "0");
                if (!left.isEmpty()) {
                    return left;
                }
            }
            if (bt.getRight() != null) {
                String right = findCode(bt.getRight(), target, prefix + "1");
                if (!right.isEmpty()) {
                    return right;
                }
            }
        }
        return "";
    }

    private static String decode(String encodedText, BinaryTree<Pair> huffmanTree) {
        StringBuilder decoded = new StringBuilder();
        BinaryTree<Pair> current = huffmanTree;
        for (char c : encodedText.toCharArray()) {
            if (c == ' ') {
                decoded.append(" ");
                current = huffmanTree;
            } else {
                if (c == '0') {
                    current = current.getLeft();
                } else {
                    current = current.getRight();
                }
                if (current.getLeft() == null && current.getRight() == null) {
                    decoded.append(current.getData().getValue());
                    current = huffmanTree;
                }
            }
        }
        return decoded.toString();
    }
}

