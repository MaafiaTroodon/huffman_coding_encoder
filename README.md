# Huffman Coding Implementation

### Author: Malhar Datta Mahajan

This project is a Java implementation of Huffman Coding, a compression algorithm that assigns variable-length codes to characters based on their frequencies. This program reads character probabilities, builds a Huffman tree, and provides both encoding and decoding functionalities for user-inputted text.

### Features
- Reads letter probabilities from a text file (`LettersProbability.txt`).
- Constructs a Huffman tree to assign optimal codes to characters.
- Encodes and decodes lines of uppercase text provided by the user.

### Usage
1. Prepare a file named `LettersProbability.txt` containing letter frequencies, with each line in the format:
   ```
   A 0.0812
   B 0.0149
   ...
   ```

2. Compile and run the program:
   ```
   javac Huffman.java
   java Huffman
   ```

3. Enter a line of text (uppercase letters only) to see its encoded form and verify the decoding.

### Example
Input: `HELLO`
Encoded: `0101 1011 ...` (actual encoding will vary)
Decoded: `HELLO`

### Requirements
- Java Development Kit (JDK) version 8 or higher.

