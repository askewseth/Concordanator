import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;


/***
 * @param file_name name of the file
 * @param flat_words 1D array of all words in the file
 * @param file_lines 1D array of strings, all lines in file
 * @param number_of_lines number of lines in the file
 * @author seth
 */
public class concord{
    int number_of_lines;
    String file_name;
    String[] file_lines, flat_words;
    ArrayList[] file_words;
    HashMap<String, Integer[]> concord = new HashMap<String, Integer[]>();
    HashMap<String, Integer> number_occurances = new HashMap<String, Integer>();
    ArrayList<String> unique_words;
    String flat_words_full;
    
    public class word{
        String word;
        String book_name;
        ArrayList<Integer> line_numbers;
        int number_apperances, apperance_rank;
        
        public word(String w, String b, ArrayList<Integer>lns, int n, int a){
            this.word = w;
            this.book_name = b;
            this.line_numbers = lns;
            this.number_apperances = n;
            this.apperance_rank = a;
        }
    }
    
    public concord(String file_name) throws IOException{
        this.file_name = file_name;
        this.number_of_lines = get_number_lines();
        this.file_lines = this.get_file_lines();
        this.file_words = this.get_file_words();
        this.flat_words_full = Arrays.toString(this.file_lines);
        this.flat_words = flat_words_full.split("[\\s.,;\\n\\t]");
        this.unique_words = this.get_unique_words();
//        this.concord = this.get_concord();
    }
    
    public int get_number_lines() throws IOException{
        LineNumberReader lnr = new LineNumberReader( new FileReader( new File(this.file_name)));
        lnr.skip(Long.MAX_VALUE);
        int number_of_lines = lnr.getLineNumber() + 1;
        lnr.close();
        return number_of_lines;
    }
    /*
        Parses the .txt file and writes each line to an array file_lines
    */
    public String[] get_file_lines() throws FileNotFoundException, IOException {
               
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
    
    public HashMap<String, Integer[]> get_concord() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList[] get_file_words() {
        ArrayList[] file_words = new ArrayList[this.number_of_lines];
        
        for (int i = 0; i < this.number_of_lines; i++) {
            String file_line = file_lines[i];
            if (file_line != null) {
                String[] split_file_line = file_line.split("[\\s.,;:?!-()'\"\\n\\t]");
                file_words[i] = new ArrayList<String>();
                for (int j = 0; j < split_file_line.length; j++) {
                    if(file_words[i] != null){
                    file_words[i].add(split_file_line[j].toLowerCase());
                    }
                }
            }
            
        }
        return file_words;
    }

    public ArrayList<String> get_unique_words() {
        ArrayList<String> unique_words = new ArrayList<String>();
        for (int i = 0; i < this.file_words.length; i++) {
            if (file_words[i]!=null){
                for (int j = 0; j < file_words[i].size(); j++) {
                    if (!(unique_words.contains((String) file_words[i].get(j)))){
                        unique_words.add((String) file_words[i].get(j));
                    }
                }
            }
        }
        return unique_words;
    }
}