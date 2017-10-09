
//Created a Trie from scratch in order to prep for an interview. It fails if there are special characters
public class TriePractice {
	public final static int ALPHABET_SIZE = 27;
	
	//Holds an array of TrieNode children and a letter value.
	public class TrieNode {
		private Character letter;
		private TrieNode[] children;
		public TrieNode(){
			children = new TrieNode[ALPHABET_SIZE];
		}
		public TrieNode(Character c) {
			this.letter = c;
			children = new TrieNode[ALPHABET_SIZE];
		}
		public void setLetter(Character c) {letter = c;}
		public TrieNode[] getChildren() { return children;}
		public Character getLetter() {return this.letter;}
	}
	
	private TrieNode root;
	
	//Root node is special character
	public TriePractice(){root = new TrieNode('*');}
	
	//These functions take a string and call an overloaded function with a charArray as arg
	public boolean search(String word){return search(regularizeString(word), root);}
	public void insertWord(String word){insertWord(root, regularizeString(word));}
	
	//Helper function to convert String to char Array and add terminating character
	public char[] regularizeString(String word){
		String lowerCase = word.toLowerCase() + '*';
		//System.out.println(lowerCase);
		return lowerCase.toCharArray();
	}
	
	//Helper function to pass a word segment to the recursively called functions
	public char[] createWordSeg(char[] word){
		char[]wordSeg = new char[word.length-1];
		int curr = 0;
		for(int j = 1; j < word.length; j++){
			wordSeg[curr] = word[j];
			curr++;
		}
		return wordSeg;
	}
	
	//To insert word to the trie, we first check if the letter is already there. If it is
	//and we've reached the end of the word, we return. If we haven't reached the end of the word,
	//we create the word segment and pass that recursively along with the child node as root.
	//If the letter isn't there already, we construct the TrieNode and add the letter. Again,
	//return if it's the end of the word, else call recursively.
	public void insertWord(TrieNode root, char[]word){
		for(int i = 0; i < root.children.length; i++){
			if(root.children[i] != null && root.children[i].getLetter() == word[0]){
				if(word.length == 1)
					return;
				else{
					char[] wordSeg = createWordSeg(word);
					insertWord(root.children[i], wordSeg);
					return;
				}
			}
			else if(root.children[i] == null){
				root.children[i] = new TrieNode();
				root.children[i].setLetter(word[0]);
				if(word.length == 1)
					return;
				else{
					char[]wordSeg = createWordSeg(word);
					insertWord(root.children[i], wordSeg);
					return;
				}
			}
		}
	}
	
	//search checks if the first letter of the current array is in our Trie. If it is and
	//it's the terminating character, we return true. If it's not the terminating character,
	//create a word segment and call recursively.
	//If we reach null, we return false.
	public boolean search(char[] word, TrieNode root){
		for(int i = 0; i < root.children.length; i++){
			if(root.children[i] != null && root.children[i].getLetter() == word[0]){
				if(word[0] == '*')
					return true;
				else{
					char[] wordSeg = createWordSeg(word);
					return search(wordSeg,root.children[i]);
				}
			}
			if(root.children[i] == null){
				return false;
			}
		} return false;
	}
	
	public static void main(String[] args){
		TriePractice test = new TriePractice();
		test.insertWord("Doggy");
		test.insertWord("damn");
		System.out.println("Success");
		System.out.println(test.search("doGGy"));
		System.out.println(test.search("damn"));
		
	}
}
//Insertion and search time is O(word.length * 27) because it has to check the arrays.
//Space is O(27 * the longest word), since again we are using arrays.
//It will fail if we use special characters because 1. They don't make actual words and 2.
//It's only designed to take the alphabet + the terminating character.
	
	
	

