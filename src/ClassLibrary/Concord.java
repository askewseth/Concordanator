package ClassLibrary;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;


/**
 * @author seth
 */
public class Concord implements Serializable{
    private int number_of_lines;
    private String file_name,flat_words_full, book_title, book_author;
    private String[] file_lines, flat_words;
    private ArrayList[] file_words;
    private HashMap<String, Word> concord;
    private ArrayList<String> unique_words, common_words;
    private HashMap<String, Integer> all_appearances,appearance_ranks;
    
  
    /**
     * 
     * @param file_name name of the file to make the concordance from
     * @throws IOException 
     */
    public Concord(String title, String author, String file_name) throws IOException{
        this.book_title = title;
        this.book_author = author;
        this.file_name = file_name;
        //System.out.println("Stage 1: Setting number of lines.");
        this.number_of_lines = set_number_lines();
        //System.out.println("Stage 2: Setting file lines.");
        this.file_lines = this.set_file_lines();
        //System.out.println("Stage 3: Setting file words.");
        this.file_words = this.set_file_words();
        System.out.print("\r|=        | Stage 1 of 9");
        this.flat_words_full = Arrays.toString(this.file_lines).toLowerCase();
        System.out.print("\r|==       | Stage 2 of 9");
        this.flat_words = flat_words_full.split("[\\s--.,;\\n\\t]");
        System.out.print("\r|===      | Stage 3 of 9");
        this.unique_words = this.set_unique_words();
        System.out.print("\r|====     | Stage 4 of 9");
        this.all_appearances = this.set_all_appearances();
        System.out.print("\r|=====    | Stage 5 of 9");
        this.appearance_ranks = this.set_appearance_ranks();
        System.out.print("\r|======   | Stage 6 of 9");
        this.concord = this.set_concord();
        System.out.print("\r|=======  | Stage 7 of 9");
        this.common_words = this.set_common_words();
        System.out.print("\r|======== | Stage 8 of 9");
        this.save();
        System.out.print("\r|=========| Stage 9 of 9\n");
    }
    
    public class Word implements Serializable{
        private String word;
        private ArrayList<Integer> list_lines;
        private int number_occurrences, number_lines, appearance_rank;
        
        /**
         * @param w String containing the word itself
         * @param ll ArrayList of line numbers word's found on
         * @param no Number of occurrences for the word
         * @param a  Appearance rank of the word
         */
        public Word(String w, ArrayList<Integer> ll, int no, int a){
            this.word = w;
            this.list_lines = ll;
            this.number_lines = this.list_lines.size();
            this.number_occurrences = no;
            this.appearance_rank = a;
        }
        
        /**
         * @return string representation of word
         */
        public String getWord(){return this.word;}

        /***
         * @return list of line numbers word found on
         */
        public ArrayList<Integer> getListLines(){return this.list_lines;}
        
        /**
         * @return number of occurances for word
         */
        public int getNumberOccurances(){return this.number_occurrences;}
        
        /**
         * @return get the number of lines the word's found on
         */
        public int getNumberLines(){return this.number_lines;}
        
        /***
         * @return get the appearance rank for the word
         */
        public int getAppearanceRank(){return this.appearance_rank;}
    }
    
    
    /***
     * Get file name for text
     * @return String name of file
     */
    public String getFileName(){return this.file_name;}
    
    /***
     * gets String array containing each line in the file,
     * each line is one long string 
     * @return String array of lines in the file
     */
    public String[] getFileLines(){return this.file_lines;}
    
    /***
     * gets String array containing each word in the file, split by line and
     * other common delimiters 
     * @return String array flat words
     */
    public String[] getFlatWords(){return this.flat_words;}
    
    /**
     * gets ArrayList with each element corresponding to a line in the file,
     * each element is a String ArrayList containing each line in the file
     * @return 
     */
    public ArrayList[] getFileWords(){return this.file_words;}

    /**
     * gets concordance
     * @return hashmap mapping every unique word in file to it's Word object
     */
    public HashMap<String, Word> getConcord(){return this.concord;}
    
    /**
     * gets a hashmap containing #appearances for each word in the file
     * @return hashmap of #apperances for each word
     */
    public HashMap<String, Integer> getAllAppearances(){return this.all_appearances;}
    
    /**
     * gets one long string containing the entire file flattened
     * @return string of entire file
     */
    public String getFlatWordsFull(){return this.flat_words_full;}
    
    /**
     * gets hashmap mapping each word in file to it's appearance rank
     * @return hashmap of all appearance ranks 
     */
    public HashMap<String, Integer> getAppearanceRanks(){return this.appearance_ranks;}
    
    /**
     * gets common words from commonwords.txt and returns them as an ArrayList
     * @return list of common words to exclude from queries an an ArrayList of strings
     */
    public ArrayList<String> getCommonWords(){return this.common_words;}
    /**
     * returns the number of lines in the text file
     * @return number of lines in file
     * @throws IOException 
     */
    private int set_number_lines() throws IOException{
        LineNumberReader lnr = new LineNumberReader( new FileReader( new File(this.file_name)));
        lnr.skip(Long.MAX_VALUE);
        int number_of_lines = lnr.getLineNumber() + 1;
        lnr.close();
        return number_of_lines;
    }
    
    /**
     * Parses the .txt file and writes each line to an array file_lines
     * @return String array containing all lines in file as one long string
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private String[] set_file_lines() throws FileNotFoundException, IOException {
               
            //open file
            FileReader file_reader = new FileReader(this.file_name);
            BufferedReader  buffered_reader = new BufferedReader(file_reader);
            
            //Writes each line in the file to an array 'file_lines'
            String[] file_lines = new String[this.number_of_lines];
            for (int i = 0; i < this.number_of_lines; i++) {
              file_lines[i] = buffered_reader.readLine();
            }
    
        return file_lines;
    }
    
    /**
     * Makes a Word object for each unique word in the text and writes it to a hash table
     * @return hashmap containing the concordance
     * @throws IOException 
     */
    private HashMap<String, Word> set_concord() throws IOException {
        HashMap<String, Word> concord = new HashMap<String, Word>();
        for(String word: this.unique_words){
            ArrayList<Integer> lines = this.get_list_lines(word);
            int num_occurrences = this.all_appearances.get(word);
            int app_rank = this.appearance_ranks.get(word);
            Word tword = new Word(word, lines, num_occurrences, app_rank);
            concord.put(word, tword);
        }
        return concord;
    }
    
    /**
     * Creates a concordance excluding all words in commonwords.txt
     * @return hashmap containing the concordance
     */
    private HashMap<String, Word> set_concord_nocommon() {
        HashMap<String, Word> concord = new HashMap<String, Word>();
        //exclude all words in the common words file
        for(String word: this.unique_words){
            if (!(this.common_words.contains(word))){
                ArrayList<Integer> lines = this.get_list_lines(word);
                int num_occurrences = this.get_number_occurrences(word);
                int app_rank = this.get_appearance_rank(word);
                Word tword = new Word(word, lines, num_occurrences, app_rank);
                concord.put(word, tword);
            }
        }
        return concord;
    }
    
    /**
     * Gets an arraylist with number_of_lines worth's of elements, each containing
     * an arraylist with each word on the line in order
     * @return Arraylist of each word on each line
     */
    private ArrayList[] set_file_words() {
        ArrayList[] file_words = new ArrayList[this.number_of_lines];
        
        //for each line in the file
        for (int i = 0; i < this.number_of_lines; i++) {
            String file_line = file_lines[i];
            if (file_line != null) {
                //split each string line of the file
                String[] split_file_line = file_line.split("[\\s.,;:?!--()'\"\\n\\t]");
                file_words[i] = new ArrayList<String>();
                //add each word to it's respective list
                for (int j = 0; j < split_file_line.length; j++) {
                    if(file_words[i] != null){
                        //make sure words are all lowercase
                        file_words[i].add(split_file_line[j].toLowerCase());
                    }
                }
            }
            
        }
        return file_words;
    }

    /**
     * Gets an arraylist of all of the unique words in the text
     * @return arraylist of all of the unique words in the text
     */
    private ArrayList<String> set_unique_words() {
        ArrayList<String> unique_words = new ArrayList<String>();
        //for line in file
        for (int i = 0; i < this.file_words.length; i++) {
            if (file_words[i]!=null){
                //for word in line
                for (int j = 0; j < file_words[i].size(); j++) {
                    if (!(unique_words.contains((String) file_words[i].get(j)))){
                        unique_words.add((String) file_words[i].get(j));
                    }
                }
            }
        }
        return unique_words;
    }
    
    /**
     * Gets a list of all the line numbers a word appears on
     * @param target_word
     * @return list of all the line numbers a word appears on
     */
    private ArrayList<Integer> get_list_lines(String target_word){
        ArrayList<Integer> list_lines = new ArrayList<Integer>();
        int counter = 1;
        for(ArrayList<String> str: this.file_words){
            if (str!=null){
                for(String word: str){
                    if(target_word.equals(word) && !list_lines.contains(counter)){
                        list_lines.add(counter);
                    }
                }
            }
            counter++;
        }
        return list_lines;
    }
 
    /**
     * Gets total number of lines that a word appears on
     * @param target_word
     * @return 
     */
    //Gets the number of lines that a word appears on
    public int get_number_lines(String target_word){
        return this.get_list_lines(target_word).size();
    }
    
    /**
     *Gets the total number of occurrences for given word 
     * @param target_word
     * @return integer number of occurrences for the given word
     */
    public int get_number_occurrences(String target_word){
        int counter = 0;
        for(ArrayList<String> str: this.file_words){
            if (str!=null){
                for(String word: str){
                    if(target_word.equals(word)){
                    counter++;
                    }
                }
            }
        }
        return counter;
    }
    
    /**
     * Gets number of appearances for each unique word and writes to a hash table
     * @return Hashmap mapping every unique word in text to it's number of appearances
     */
    private HashMap<String, Integer> set_all_appearances(){
        HashMap<String, Integer> all_appearances = new HashMap<String, Integer>();
        for(String word: this.unique_words){
            all_appearances.put(word, this.get_number_occurrences(word)); 
        }
        return all_appearances;
    }
    
    /**
     * gets a words appearance rank within the text file, more accurately
     * it is the number of words which have a higher appearance rank
     * than the target word, case where the rank number after a tie is skipped
     * @param target_word
     * @return rank according to appearance
     */
    public int get_appearance_rank(String target_word){
        //get list of appearances for all words
        int[] appearance_numbers = new int[this.all_appearances.size()];
        int index = 0;
        for(String word: this.all_appearances.keySet()){
            appearance_numbers[index] = this.all_appearances.get(word);     
            index++;
        }
        
        //get the number of appearances for the target word
        int target_num = this.get_number_occurrences(target_word);
        
        int rank = 1;
        
        //check target num against all numbers in list and return number greater than
        for (int i = 0; i < appearance_numbers.length; i++) {
            if (appearance_numbers[i] > target_num){
                rank = rank + 1;
            }
        }
        return rank;
    }
    
    /**
     * gets a hash map of all of the appearance ranks for each word in file
     * @return hash map of all appearance ranks for each unique word in text
     */
    private HashMap<String, Integer> set_appearance_ranks(){
        HashMap<String, Integer> appearance_ranks = new HashMap<String, Integer>();
        for(String word: this.unique_words){
            appearance_ranks.put(word, this.get_appearance_rank(word));
        }
        return appearance_ranks;
    }
    
    /**
     * parses commonwords.txt and returns an arraylist containing all the words
     * that should be excluded from the concord
     * @return arraylist containing all the words that should be excluded from the concord
     * @throws FileNotFoundException
     * @throws IOException 
     */
    //parses commonwords.txt and returns an arraylist containing all the words
    //that should be excluded from the concord
    private ArrayList<String> set_common_words() throws FileNotFoundException, IOException{
            ArrayList<String> common_words = new ArrayList<String>();
            File comWordFile = null;
            if (new File("ClassLibrary" + File.separator + "commonwords.txt").isFile()){
                comWordFile = new File("ClassLibrary" + File.separator + "commonwords.txt");
            }
            else if (new File("src" + File.separator + "ClassLibrary" + File.separator + "commonwords.txt").isFile()){
                comWordFile = new File("src" + File.separator + "ClassLibrary" + File.separator + "commonwords.txt");
            }
            //open file
            FileReader file_reader = new FileReader(comWordFile);
            BufferedReader  buffered_reader = new BufferedReader(file_reader);
            String line;
            //Write each line in file to element in array
            while((line = buffered_reader.readLine())!=null){
                common_words.add(line);
            }
            return common_words;
    }

    /**
     * Get's all the words within some given distance of all occurrences of a target_word
     * @param target_word 
     * @param dist
     * @return ArrayList of all of the words in the file that are 
     * found within the given distance of the target word
     */
    public ArrayList<String> get_words_within_distance(String target_word, int dist){
        ArrayList<String> words = new ArrayList<String>();
        
        //for word in array of flat words (leaving off 'dist' worth of space on both ends)
        for(int i=dist; i < this.flat_words.length-dist; i++){
            if (this.flat_words[i].equals(target_word)){
                //add all words within distance on either side to arraylist
                for(int j=i-dist; j <= i+dist; j++){
                    //exclude the target word
                    if(i!=j && !words.contains(this.flat_words[j])){
                        words.add(this.flat_words[j]);
                    }
                }
            }
        }
        return words;
    }
    
    /**
     * saves the seralized concord to filename.con
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void save() throws FileNotFoundException, IOException{
        /*String name = this.file_name.substring(0, this.file_name.length()-4) + ".con";
        FileOutputStream fileOut = new FileOutputStream(name);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();*/
        IO<Concord> io = new IO<Concord>(this.file_name);
        io.serialize(this);
    }
    /**
     * Currently only return line numbers that contain the entire phrase,
     * doesn't cover the case where the phrase breaks over lines
     * @param phrase
     * @return ArrayList of lines containing line numbers of phrase occurrences
     */
    public ArrayList<Integer> find_phrase(String phrase){
        ArrayList<Integer> phrase_lines = new ArrayList<Integer>();
        
        //if phrase not found in file, return empty arraylist
        if (!this.flat_words_full.contains(phrase)){
            return phrase_lines;
        }
        
        //get words in phrase
        String[] words = phrase.split("[\\s.,;\\n\\t]");
        
        //get line numbers for each word in phrase
        for (int i = 0; i < words.length; i++) {
            phrase_lines.add(this.get_number_lines(words[i]));
        }
        
        //find numbers within tolerance
        phrase_lines.get(0);
        
        //lines that completely contain phrase
        ArrayList<Integer> full_phrase_lines = new ArrayList<Integer>();
        
        //parse this.file_lines and see what lines contain whole phrase
        for(int line = 0; line < this.file_lines.length; line++){
            if(this.file_lines[line] != null){
                if(this.file_lines[line].contains(phrase) && !full_phrase_lines.contains(line+1)){
                    full_phrase_lines.add(line+1);
                }
            }
        }
        
        return full_phrase_lines;
    }

	/**
	 * @param String word for which to get array of line numbers associated with it.
	 * @return ArrayList<Integer> array containing line number on which word occurs.
	 */
	public ArrayList<Integer> getWordLines(String word) {
		ArrayList<Integer> lines = this.getConcord().get(word).getListLines();

		return lines;
	}
}
