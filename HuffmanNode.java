/**
 * the huffman encoding for the characters of project 2
 * @author Chris Tsuei
 */
public class HuffmanNode implements Comparable<HuffmanNode> {

	//the character the node represents
	public Character inChar;
	
	//frequency of the character represented
	public int frequency;
	
	//the left and right children of this node
	public HuffmanNode left;
	public HuffmanNode right;
	
	/**
	 * the constructor of this class that takes in a character
	 * @param c the unique character in this HuffmanNode tree
	 */
	public HuffmanNode(char c) {
		this.inChar = c;
		this.frequency = 1;
	}

	/**
	 * a constructor
	 */
	public HuffmanNode(int times, HuffmanNode left, HuffmanNode right) {
		this.frequency = times;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * writing the comparable method
	 * @param o the HuffmanNode whose frequency is to be compared
	 * @return the result of the compare method
	 */
	public int compareTo(HuffmanNode compare) {
		return (this.frequency - compare.frequency);
	}
}
