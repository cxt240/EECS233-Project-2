import java.io.*;
import java.util.*;
/**
 * takes in a text file and creates a text file containing the frequency of each character
 * @author Chris Tsuei
 */
public class HuffmanCompressor {
	
	// results holds an array containg the character, huffman code and frequency. resultIndex is how many results there are
	private static Object[][] results = new Object[200][3];
	private static int resultIndex = 0;
	
	// the main method of this program
	public static void main(String[] args) throws Exception {
		try {
			HuffmanCompressor.HuffmanTree(args[0], args[1]);
		}
		catch (IndexOutOfBoundsException c){
			System.out.println("Please input valid files");
		}
	}
	
	/**
	 * Creates a huffman tree based on the characters
	 * Also recodes the given file into huffman encoding and creates a file with statistics on the input file
	 * @param input the input file to be coded/translated
	 * @param output output file that is translated
	 * @throws Exception if there is no file or the file has nothing
	 */
	public static void HuffmanTree(String input, String output) throws Exception {
		FileReader inputFile = new FileReader(input);
		ArrayList<HuffmanNode> tree = new ArrayList<HuffmanNode>(200);
		char current;
		
		// loops through the file and creates the nodes containing all present characters and frequencies
		while((current = (char)(inputFile.read())) != (char)-1) {
			int search = 0;
			boolean found = false;
			while(search < tree.size() && found != true) {
				if(tree.get(search).inChar == (char)current) {
					tree.get(search).frequency++; 
					found = true;
				}
				search++;
			}
			if(found == false) {
				tree.add(new HuffmanNode(current));
			}
		}
		inputFile.close();
		
		//creates the huffman tree
		createTree(tree);
		
		//the huffman tree
		HuffmanNode sortedTree = tree.get(0);
		
		//prints out the statistics of the input file and the space saved
		FileWriter newVersion = new FileWriter("C:\\Users\\Chris\\workspace\\P2\\src\\P2_cxt240_Tsuei_statistics.txt");
		printTree(sortedTree, "", newVersion);
		spaceSaver(newVersion);
		newVersion.close();
		
		// codes the file using huffman encoding
		FileWriter outputFile = new FileWriter(output);
		translate(input, outputFile);
	}
	
	/**
	 * creates a huffman tree
	 * @param tree the arraylist of individual characters to be sorted
	 */
	public static void createTree(ArrayList<HuffmanNode> tree) {
	
		//loops through until the tree.
		while(tree.size() > 1) {
			Collections.sort(tree);
			HuffmanNode left = tree.get(0);
			HuffmanNode right = tree.get(1);
			
			/*creates the new node and adds it to the arrayList, and removes the two lowest frequency nodes
			 * then recursively calls the create method again
			 */
			tree.add(new HuffmanNode((right.frequency + left.frequency), left, right));
			tree.remove(0);
			tree.remove(0);
			createTree(tree);
		}
	}
	
	/**
	 * prints the tree into the results array
	 * @param tree the Huffman node to be checked
	 * @param direction the huffman code to be inserted
	 * @param newVersion the filewriter editing the statistics file
	 * @throws IOException if there is no filewriter
	 */
	private static void printTree(HuffmanNode tree, String direction, FileWriter newVersion) throws IOException {
		if(tree.inChar != null) {
			newVersion.write(tree.inChar + ": " + direction + " | " + tree.frequency + System.getProperty("line.separator"));
			results[resultIndex][0] = tree.inChar;
			results[resultIndex][1] = direction;
			results[resultIndex][2] = tree.frequency;
			resultIndex++;
		}
		else {
			printTree(tree.right, direction + "1", newVersion);
			printTree(tree.left, direction + "0", newVersion);
		}
	}
	
	/**
	 * computes the amount of space saved
	 * @param output filewriter adding to the file to write to
	 * @throws IOException if there is no filewriter
	 */
	private static void spaceSaver(FileWriter output) throws IOException {
		int totalFrequency = 0;
		int modifiedCode = 0;
		
		for(int i = 0; i < resultIndex; i++) {
			modifiedCode += (((String) results[i][1]).length()) * (Integer)(results[i][2]);
			totalFrequency += (Integer)results[i][2];
		}
		
		int saved = (totalFrequency * 8) - modifiedCode;
		output.write("Space Saved: " + saved + "bits");
	}
	
	/**
	 * translates the input file into 
	 * @param input the file to be translated
	 * @param write the fileWriter that will create the output file encoded with the huffman encoding
	 * @throws IOException if the input file has nothing
	 */
	private static void translate(String input, FileWriter write) throws IOException {
		StringBuilder translation = new StringBuilder();
		FileReader inputString = new FileReader(input);
		
		char read;
		
		//loops till the end
		while((read = (char)(inputString.read())) != (char)-1) {
			int searchChar = 0;
			//finds the character that is currently being read in the results array
			while(((Character)results[searchChar][0]) != ((Character)read)) {
				searchChar++;
				
			}
			
			translation.append((String)results[searchChar][1]);
		}
		inputString.close();
		write.write(translation.toString());
	}
}