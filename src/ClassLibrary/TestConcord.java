import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * *
 * @author seth
 */
public class TestConcord implements Serializable {

    public int number_of_lines;
    public String file_name, flat_words_full, book_title, book_author;
    public String[] file_lines, flat_words;
    public ArrayList[] file_words;
    public HashMap<String, Word> concord;
    public ArrayList<String> unique_words, common_words;
    public HashMap<String, Integer> all_appearances, appearance_ranks;

    public ArrayList[] file_words_index;
    public String[][] file_words_index2;
    public ArrayList<String[]> file_words_index3, index;
            
    /**
     *
     * @param file_name name of the file to make the Concordance from
     * @throws IOException
     */
    public TestConcord(String title, String author, String file_name) throws IOException {
        this.book_title = title;
        this.book_author = author;
        this.file_name = file_name;
        //System.out.println("Stage 1: Setting number of lines.");
        this.number_of_lines = set_number_lines();
        //System.out.println("Stage 2: Setting file lines.");
        this.file_lines = this.set_file_lines();
        
        this.index = this.get_index();

        
        //System.out.println("Stage 3: Setting file words.");
        this.file_words = this.set_file_words();
        System.out.print("\r|=        | Stage 1 of 9");
        this.flat_words_full = Arrays.toString(this.file_lines).toLowerCase();
        System.out.print("\r|==       | Stage 2 of 9");
        this.flat_words = flat_words_full.split("[\\s--.,;\\n\\t]");
        System.out.print("\r|===      | Stage 3 of 9");
        this.common_words = this.set_common_words();
        System.out.print("\r|======   | Stage 4 of 9");
        this.unique_words = this.set_unique_words();
        System.out.print("\r|====     | Stage 5 of 9");
        this.all_appearances = this.set_all_appearances();
        System.out.print("\r|=====    | Stage 6 of 9");
        this.appearance_ranks = this.set_appearance_ranks();
        System.out.print("\r|=======  | Stage 7 of 9");
        this.concord = this.set_concord();
        System.out.print("\r|======== | Stage 8 of 9");
        this.save();
        System.out.print("\r|=========| Stage 9 of 9\n");
    }

    public class Word implements Serializable {

        public String word;
        public ArrayList<String> list_lines;
        public int number_occurrences, number_lines, appearance_rank;

        /**
         * @param w String containing the word itself
         * @param ll ArrayList of line numbers word's found on
         * @param no Number of occurrences for the word
         * @param a Appearance rank of the word
         */
        public Word(String w, ArrayList<String> ll, int no, int a) {
            this.word = w;
            this.list_lines = ll;
            this.number_lines = this.list_lines.size();
            this.number_occurrences = no;
            this.appearance_rank = a;
        }
    }

    /**
     * returns the number of lines in the text file
     *
     * @return number of lines in file
     * @throws IOException
     */
    public int set_number_lines() throws IOException {
        LineNumberReader lnr = new LineNumberReader(new FileReader(new File(this.file_name)));
        lnr.skip(Long.MAX_VALUE);
        int number_of_lines = lnr.getLineNumber() + 1;
        lnr.close();
        return number_of_lines;
    }

    /**
     * Parses the .txt file and writes each line to an array file_lines
     *
     * @return String array containing all lines in file as one long string
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String[] set_file_lines() throws FileNotFoundException, IOException {

        //open file
        FileReader file_reader = new FileReader(this.file_name);
        BufferedReader buffered_reader = new BufferedReader(file_reader);

        //Writes each line in the file to an array 'file_lines'
        String[] file_lines = new String[this.number_of_lines];
        //only include text after line with *** start of gb... ***
        Boolean started = false, ended = false;
        for (int i = 0; i < this.number_of_lines; i++) {
            String tmp_line = buffered_reader.readLine();
            if (tmp_line == null) {
                tmp_line = "";
            }
            if (tmp_line.contains("*** END")) {
                ended = true;
            } //check end condition before writing
            if (started && !ended) {
                file_lines[i] = tmp_line;
            }
            if (tmp_line.contains("*** START")) {
                started = true;
            } //check for start condition after checking                    
        }

        return file_lines;
    }

    /**
     * Makes a Word object for each unique word in the text and writes it to a
     * hash table
     *
     * @return hashmap containing the concordance
     * @throws IOException
     */
    public HashMap<String, Word> set_concord() throws IOException {
        HashMap<String, Word> concord = new HashMap<String, Word>();
        for (String word : this.unique_words) {
            ArrayList<String> lines = this.get_list_lines(word);
            int num_occurrences = this.all_appearances.get(word);
            int app_rank = this.appearance_ranks.get(word);
            Word tword = new Word(word, lines, num_occurrences, app_rank);
            concord.put(word, tword);
        }
        return concord;
    }

    /**
     * Creates a concordance excluding all words in commonwords.txt
     *
     * @return hashmap containing the concordance
     */
    public HashMap<String, Word> set_concord_nocommon() {
        HashMap<String, Word> concord = new HashMap<String, Word>();
        //exclude all words in the common words file
        for (String word : this.unique_words) {
            if (!(this.common_words.contains(word))) {
                ArrayList<String> lines = this.get_list_lines(word);
                int num_occurrences = this.get_number_occurrences(word);
                int app_rank = this.get_appearance_rank(word);
                Word tword = new Word(word, lines, num_occurrences, app_rank);
                concord.put(word, tword);
            }
        }
        return concord;
    }

    /**
     * Gets an arraylist with number_of_lines worth's of elements, each
     * containing an arraylist with each word on the line in order
     *
     * @return Arraylist of each word on each line
     */
    public ArrayList[] set_file_words() {
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
                    if (file_words[i] != null) {
                        //make sure words are all lowercase
                        file_words[i].add(split_file_line[j].toLowerCase());
                    }
                }
            }

        }
        return file_words;
    }
    
    

//    public ArrayList[] set_flat_words_index() {
//        ArrayList[] flat_words_index = new ArrayList[this.flat_words.length];
//        for (int i = 0; i < flat_words_index.length; i++) {
//
//            ArrayList flat_words_index1 = flat_words_index[i];
//
//        }
//    }

   
    public ArrayList<String[]> get_index() {
        ArrayList[] file_words = new ArrayList[this.number_of_lines];
        ArrayList<String[]> index = new ArrayList();
        //to keep track of total word count
        int wordCount = 0; 
        
        //for each line in the file
        for (int i = 0; i < this.number_of_lines; i++) {
            String file_line = this.file_lines[i];
            if (file_line != null) {
                //split each string line of the file
                String[] split_file_line = file_line.split("[\\s.,;:?!--()'\"\\n\\t]");
                file_words[i] = new ArrayList<String>();
                //add each word to it's respective list
                for (int j = 0; j < split_file_line.length; j++) {
                    if (file_words[i] != null) {
                        //make sure words are all lowercase
                        String identifier = Integer.toString(i+1) + ",\t" + Integer.toString(j+1);
                        String addbit = ", Yes";
                        if ( !split_file_line[j].toLowerCase().equals("") ) {
                            addbit = ", No";
                            wordCount++;
                        }
                        String toAdd = split_file_line[j].toLowerCase() + ".\t" + identifier + addbit + ", " + Integer.toString(wordCount);

//                        System.out.println("(" + split_file_line[j].toLowerCase() + ")");

                        file_words[i].add(toAdd);
                        String[] ans = {split_file_line[j].toLowerCase(), Integer.toString(i+1), Integer.toString(j+1), Integer.toString(wordCount)};
                        index.add(ans);
                    }
                }
            }

        }
        return index;
    }
    
    
  
    
   

       
    
    
    
    /**
     * Gets an arraylist of all of the unique words in the text
     *
     * @return arraylist of all of the unique words in the text
     */
    
    public ArrayList<String> set_unique_words() {
        ArrayList<String> unique_words = new ArrayList<String>();
        for (String[] index1 : this.index) {
            if(!unique_words.contains(index1[0])){
                unique_words.add(index1[0]);
            }
        }
        return unique_words;
    }
    /**
     * Gets a list of all the line numbers a word appears on
     *
     * @param target_word
     * @return list of all the line numbers a word appears on
     */
    
        public ArrayList<String> get_list_lines(String target_word) {
//        ArrayList<Integer> list_lines = new ArrayList<Integer>();
        ArrayList<String> list_lines = new ArrayList();
        ArrayList<String[]> index = this.get_index();
        int counter = 1;
        
            for (String[] word : index) {
                if( word[0].equals(target_word) && !list_lines.contains(word[1])){
                    list_lines.add(word[1]);
                }
            }
        
//        for (ArrayList<String> str : this.file_words) {
//            if (str != null) {
//                for (String word : str) {
//                    if (target_word.equals(word) && !list_lines.contains(counter)) {
//                        list_lines.add(counter);
//                    }
//                }
//            }
//            counter++;
//        }
        return list_lines;
    }
    
    
    
    /**
     * Gets total number of lines that a word appears on
     *
     * @param target_word
     * @return
     */
    //Gets the number of lines that a word appears on
     public int get_number_lines(String target_word) {
        return this.get_list_lines(target_word).size();
    }
    
    /**
     * Gets the total number of occurrences for given word
     *
     * @param target_word
     * @return integer number of occurrences for the given word
     */
       public int get_number_occurrences(String target_word) {
        int counter = 0;
        for (String[] index1 : this.index) {
            if(index1[0].equals(target_word)){
                counter++;
            }
        }
        return counter;
    }
    
    /**
     * Gets number of appearances for each unique word and writes to a hash
     * table
     *
     * @return Hashmap mapping every unique word in text to it's number of
     * appearances
     */  
    public HashMap<String, Integer> set_all_appearances() {
        
        List<String> flatarlist = Arrays.asList(this.flat_words);
        
//        Collections.frequency(flatarlist, "the");
        
        HashMap<String, Integer> all_appearances = new HashMap<String, Integer>();
        for (String word : this.unique_words) {
            all_appearances.put(word, Collections.frequency(flatarlist, word));
        }
        return all_appearances;
    }

    /**
     * gets a words appearance rank within the text file, more accurately it is
     * the number of words which have a higher appearance rank than the target
     * word, case where the rank number after a tie is skipped
     *
     * @param target_word
     * @return rank according to appearance
     */
    public int get_appearance_rank(String target_word) {
        //get list of appearances for all words
        int[] appearance_numbers = new int[this.all_appearances.size()];
        int index = 0;
//        List<Collection<Integer>> allapps = Arrays.asList(this.all_appearances.values());
        for (String word : this.all_appearances.keySet()) {
            appearance_numbers[index] = this.all_appearances.get(word);
            index++;
        }

        //get the number of appearances for the target word
        int target_num = this.get_number_occurrences(target_word);

        int rank = 1;

        //check target num against all numbers in list and return number greater than
        for (int i = 0; i < appearance_numbers.length; i++) {
            if (appearance_numbers[i] > target_num) {
                rank = rank + 1;
            }
        }
        return rank;
    }

    /**
     * gets a hash map of all of the appearance ranks for each word in file
     *
     * @return hash map of all appearance ranks for each unique word in text
     */
    public HashMap<String, Integer> set_appearance_ranks() {
        HashMap<String, Integer> appearance_ranks = new HashMap<String, Integer>();
        for (String word : this.unique_words) {
            appearance_ranks.put(word, this.get_appearance_rank(word));
        }
        return appearance_ranks;
    }

    /**
     * parses commonwords.txt and returns an arraylist containing all the words
     * that should be excluded from the concord
     *
     * @return arraylist containing all the words that should be excluded from
     * the concord
     * @throws FileNotFoundException
     * @throws IOException
     */
    //parses commonwords.txt and returns an arraylist containing all the words
    //that should be excluded from the concord
    public ArrayList<String> set_common_words() throws FileNotFoundException, IOException {
//            ArrayList<String> common_words = new ArrayList<String>();
//            File comWordFile = null;
//            if (new File("ClassLibrary" + File.separator + "commonwords.txt").isFile()){
//                comWordFile = new File("ClassLibrary" + File.separator + "commonwords.txt");
//            }
//            else if (new File("src" + File.separator + "ClassLibrary" + File.separator + "commonwords.txt").isFile()){
//                comWordFile = new File("src" + File.separator + "ClassLibrary" + File.separator + "commonwords.txt");
//            }
//            //open file
//            FileReader file_reader = new FileReader(comWordFile);
//            BufferedReader  buffered_reader = new BufferedReader(file_reader);
//            String line;
//            //Write each line in file to element in array
//            while((line = buffered_reader.readLine())!=null){
//                common_words.add(line);
//            }
//            return common_words;
//            

        ArrayList<String> common_words = new ArrayList<String>();

        //open file
        FileReader file_reader = new FileReader("commonwords.txt");
        BufferedReader buffered_reader = new BufferedReader(file_reader);
        String line;
        //Write each line in file to element in array
        while ((line = buffered_reader.readLine()) != null) {
            common_words.add(line);
        }
        return common_words;
    }

    /**
     * Get's all the words within some given distance of all occurrences of a
     * target_word
     *
     * @param target_word
     * @param dist
     * @return ArrayList of all of the words in the file that are found within
     * the given distance of the target word
     */
    public ArrayList<String> get_words_within_distance(String target_word, int dist) {
        ArrayList<String> words = new ArrayList<String>();

        //for word in array of flat words (leaving off 'dist' worth of space on both ends)
        for (int i = dist; i < this.flat_words.length - dist; i++) {
            if (this.flat_words[i].equals(target_word)) {
                //add all words within distance on either side to arraylist
                for (int j = i - dist; j <= i + dist; j++) {
                    //exclude the target word
                    if (i != j && !words.contains(this.flat_words[j])) {
                        words.add(this.flat_words[j]);
                    }
                }
            }
        }
        return words;
    }

    /**
     * saves the seralized concord to filename.con
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void save() throws FileNotFoundException, IOException {
        String name = this.file_name.substring(0, this.file_name.length() - 4) + ".con";
        FileOutputStream fileOut = new FileOutputStream(name);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();
    }

    

    
    
    
    /**
     * Currently only return line numbers that contain the entire phrase,
     * doesn't cover the case where the phrase breaks over lines
     *
     * @param phrase
     * @return ArrayList of lines containing line numbers of phrase occurrences
     */
        public ArrayList<Integer> find_phrase(String phrase){
        ArrayList<Integer> phrase_lines = new ArrayList();
        
        
        int counter = 0;
        String[] split_phrase = phrase.toLowerCase().split("[ ]");
        
      
        for (String[] word : this.index) {
            //if first word found
            if (word[0].equals(split_phrase[0])){
//            System.out.println("FOUND FIRST WORD LINE NUMBER " + word[1]);

                //check all words in phrase
                ArrayList<Boolean> match = new ArrayList();
                for (int i = 0; i < split_phrase.length; i++) {
//                    System.out.println("\tIN FOR LOOP I = " + i);
                    if(this.index.get(counter+i)[0].equals(split_phrase[i])){
//                        System.out.println("\t\tMATCH TRUE");
                        match.add(true);
                    }
                    else{
//                        System.out.println("\t\tMATCH FALSE");
//                        System.out.println("\t\t\t Index:" + this.index.get(counter+i)[0]);
//                        System.out.println("\t\t\t Phrase:"+ split_phrase[i]);
                        match.add(false);
                    }
                }
                
                if (!match.contains(false)) {
//                    System.out.println("\t\t\t\t\tADDING TO PHRASE LINES!!!!!!!!!!!!!!!");
                    String[] split_num = word[1].split("[ ]");
                    
//                    phrase_lines.add(Integer.parseInt(split_num[0]));
                    phrase_lines.add(Integer.parseInt(word[1])) ;
                }
            }
            counter++;
        }
        return phrase_lines;
    }
}