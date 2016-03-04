/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.  Tony made a change.
 */
package ClassLibrary;

import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.HashMap;
import java.util.Map;
import ClassLibrary.Concord.Word;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cory Sabol - cssabol@uncg.edu
 *
 * This class depends on the Concordance class.
 */
public class CmdRepl implements ActionListener {

    private String[] args;
    private boolean exit;
    private String prompt = "> ";
    private final InputStreamReader reader;
    private final BufferedReader in;
    private String cmdStr;
    private boolean conLoaded;
    private final Bookshelf shelf;
    private Concord concord;
    private final String OSName = System.getProperty("os.name").substring(0, 3);
    private final String userDir;
    private final String CONCORD_DIRECTORY = "books";  // Name of the folder which contains the concordances.
    private String concordDirectory;   // Holds the exact path of the concordance based on the user's OS environment.
    private final ArrayList<String> commonWords;
    private final String COMMON_WORDS_FILE = "commonwords.txt";
    private String commonWordFile;
    private boolean commonWordsAvailable;
    private ActionListener listener;
    private final Map<String, String> env = System.getenv();
	// These two values only matter on *nix systems right now.
    // Given more time we would make this work for m$ wangblows as well.
    private int cols = 80;
    private int lines = 20;
    private boolean isNix = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getID()) {
            case 1:
                System.out.print("\r|=       | Stage 1 of 8");
                break;
            case 2:
                System.out.print("\r|==      | Stage 2 of 8");
                break;
            case 3:
                System.out.print("\r|===     | Stage 3 of 8");
                break;
            case 4:
                System.out.print("\r|====    | Stage 4 of 8");
                break;
            case 5:
                System.out.print("\r|=====   | Stage 5 of 8");
                break;
            case 6:
                System.out.print("\r|======  | Stage 6 of 8");
                break;
            case 7:
                System.out.print("\r|======= | Stage 7 of 8");
                break;
            case 8:
                System.out.print("\r|=========| Stage 8 of 8\n");
                break;
            default:
                System.out.println("ERROR:  Invalid action event.");
        }
    }

    private enum Commands {

        load,
        help,
        listbooks,
        addbook,
        listcons,
        searchcons,
        build,
        summary,
        summarylines,
        summaryoccur,
        summaryrank,
        numoccur,
        numlines,
        rank,
        phrase,
        invalid,
        unload,
        exit;
    }

    /**
     * *
     * Constructor for the repl.
     *
     * @param args
     */
    public CmdRepl() {
        //this.args = args;
        this.reader = new InputStreamReader(System.in);
        this.in = new BufferedReader(reader);
        this.exit = false;
        this.conLoaded = false;
        this.commonWords = new ArrayList<String>();
        this.shelf = new Bookshelf();
        this.listener = this;

        this.userDir = System.getProperty("user.dir");

        this.concordDirectory = this.userDir + File.separator + CONCORD_DIRECTORY;    // set the concordance directory.
        this.commonWordFile = this.userDir + File.separator + "ClassLibrary" + File.separator + this.COMMON_WORDS_FILE;
        if (!new File(this.concordDirectory).isDirectory()) {
            // This checks to see if the concordance directory is present in the command line.
            // If not, then user is running in Netbeans and the proper folder will be appended.
            this.concordDirectory = this.userDir + File.separator + "src" + File.separator + CONCORD_DIRECTORY;
            this.commonWordFile = this.userDir + File.separator + "src" + File.separator + "ClassLibrary" + File.separator + this.COMMON_WORDS_FILE;
        }

        this.commonWordsAvailable = this.populateCommonWords();
    }

    /**
     * *
     * Start the interactive interface to of the program.
     */
    public void startRepl(String prompt) throws IOException {
        this.args = args;

        if (!prompt.isEmpty() && prompt.length() < 5 && prompt.endsWith(" ")) {
            this.prompt = prompt;
        }

        /**
         * commands available: load <title | path>
         * help listbooks [keyword] listcons [keyword] prompt => [title of con]
         * > build <title | path> (possibly redundant) summary <keyword>
         * numoccur <keyword>
         * numlines <title>
         * surrwords <keyword, offset>
         * phrase <phrase>
         */
        System.out.println("Welcome to Concordanator, "
                + "the best darn concordance tool around ;^)\n"
                + "type help for list of commands.\n");
        // Main repl loop.
        do {

            System.out.print(this.prompt);
            cmdStr = in.readLine();
            // Tokenize the command entered.
            ArrayList<String> cmd = tokenizeCmd(cmdStr, " ");
            evalCmd(cmd);

        } while (!exit);
    }

    /**
     * *
     * Is this method actually necessary? Look into this more - cory
     *
     * @param cmdStr - The command string to tokenize
     * @param delim - the delimiter to separate tokens by
     * @return ArrayList<String> of tokens
     */
    private ArrayList<String> tokenizeCmd(String cmdStr, String delim) {
        ArrayList<String> tknStrs = new ArrayList<String>();
        // Break the command str into tokens
        StringTokenizer tknz = new StringTokenizer(cmdStr);

        while (tknz.hasMoreElements()) {
            tknStrs.add(tknz.nextToken(delim));
        }

        return tknStrs;
    }

    private void evalCmd(ArrayList<String> cmd) {
        List<String> cmdArg = new ArrayList<String>();
        Commands command = Commands.valueOf("invalid");

        if (cmd.size() < 1) {
            cmd = new ArrayList<String>();
            cmd.add(" ");
        }

        if (cmd.size() > 1) {
            cmdArg = cmd.subList(1, cmd.size());
        }

        try {
            command = Commands.valueOf(cmd.get(0));
        } catch (IllegalArgumentException e) {
            command = Commands.valueOf("invalid");
        }
        switch (command) {
            case load:
                if (this.loadConcordance(cmdArg.toString()
                        .substring(1, cmdArg.toString().length() - 1))) {
                    this.conLoaded = true;
                }
                break;
            case help:
                this.printHelp();
                break;
            case listbooks:
                if (cmdArg.isEmpty()) {
                    this.listbooks();
                } else {
                    this.listbooks(cmdArg.toString().substring(1,
                            cmdArg.toString().length() - 1));
                }
                break;
            case listcons:
                if (cmdArg.isEmpty()) {
                    // This section fires if there are no arguments.
                    this.listCords();
                } else {
                    // This section fires for any other case.
                    this.listCords(cmdArg.toString().substring(1,
                            cmdArg.toString().length() - 1));
                }
                break;
            case searchcons:
                if (cmdArg.size() == 1) {
                    this.listCords(cmdArg.get(0), 1);
                } else if (cmdArg.size() == 2 && this.isInteger(cmdArg.get(1), 10)) {
                    int numAppear = Integer.parseInt(cmdArg.get(1));
                    this.listCords(cmdArg.get(0), numAppear);
                } else {
                    System.out.println("ERROR: Incorrect usage.  Searchcons requires a keyword and an integer representing the minimum number of appearances.");
                }
                break;
            case addbook:
                if (cmdArg.toString().equals("[]")) {
                    System.out.println("ERROR: Incorrect usage.  Please provide a path to the book to be added.");
                } else {
                    this.addBook(cmdArg.toString().substring(1,
                            cmdArg.toString().length() - 1));
                }
                break;
            case build:
                this.buildConcordance(cmdArg.toString()
                        .substring(1, cmdArg.toString().length() - 1));
                break;
            case summary:
                //System.out.println(cmdArg.get(0));
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else if (cmdArg.isEmpty()) {
                    // Show a summary of the whole concordance
                    this.showConSummary();
                    break;
                } else {
                    this.showWordSummary(cmdArg.get(0).toLowerCase());
                }
                break;
            case summarylines:
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {
                    this.showSummaryLines();
                }
                break;
            case summaryrank:
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {
                    this.showSummaryRank();
                }
                break;
            case summaryoccur:
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {
                    this.showSummaryOccur();
                }
                break;
            case numoccur:
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {
                    if (cmdArg.isEmpty()) {
                        System.out.println("Incorrect Usage: Number of word occurences requires an argument.");
                    } else if (cmdArg.size() > 1) {
                        System.out.println("Incorrect Usage: Number of occurences requires a single word for search.");
                    } else {
                        this.numOccurences(cmdArg.get(0).trim().toLowerCase());
                    }
                }
                break;
            case numlines:
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {
                    if (cmdArg.isEmpty()) {
                        System.out.println("Incorrect Usage: Number of lines requires an argument.");
                    } else if (cmdArg.size() > 1) {
                        System.out.println("Incorrect Usage: Number of lines requires a single word for search.");
                    } else {
                        this.numberOfLines(cmdArg.get(0).trim().toLowerCase());
                    }
                }
                break;
            case rank:
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {
                    if (cmdArg.isEmpty()) {
                        System.out.println("Incorrect Usage: Rank requires an argument.");
                    } else if (cmdArg.size() > 1) {
                        System.out.println("Incorrect Usage: Rank requires a single word for search.");
                    } else {
                        this.rank(cmdArg.get(0).trim().toLowerCase());
                    }
                }
                break;
            case phrase:
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {

                }
                break;
            case unload:
                if (!conLoaded) {
                    System.out.println("Error: no concordance loaded.");
                } else {
                    if (cmdArg.isEmpty()) {
                        System.out.println("SUCCESS: Concordance unloaded.");
                        this.prompt = "> ";
                        this.conLoaded = false;
                    } else {
                        System.out.println("Incorrect Usage: Unload does not require an argument.");

                    }
                }
                break;
            case exit:
                this.exit = true;
                break;
            case invalid:
                System.out.println("Invalid command.  Type 'help' for list of commands.");
        }
    }

    /**
     *
     * @param c - Concord object to be saved
     * @param conPath - The path to which to save the concordance must also have
     * the file name appended to it
     */
    private void saveConcordance(Concord c, String conPath) {
        IO<Concord> io = new IO<Concord>(conPath);

        io.serialize(c);
    }

    /**
     * *
     *
     * @param conPath The path or the name of the concordance to load.
     * @return
     */
    private boolean loadConcordance(String title) {
        boolean success = false;
        title = title.replace(",", ""); // What if there is a comma in the book title??
        String[] bookInformation = shelf.pullBook(title);
        if (bookInformation == null) {
            System.out.println("Error: No concordance found. Please check the name and try again.");
        } else {
            String conPath = bookInformation[2].substring(0, bookInformation[2].lastIndexOf(File.separator)) + File.separator + bookInformation[0] + " by " + bookInformation[1] + ".con";
            File conFile = new File(conPath);
            if (conFile.isFile()) {
                IO<Concord> io = new IO<Concord>(conPath);
                this.concord = io.deserialize(conPath);
                System.out.println("SUCCESS: Concordance loaded.");
                this.prompt = "\n" + conFile.getName().substring(0, conFile.getName().length() - 4) + " > ";
                success = true;
            } else {
                System.out.println("Error: No concordance found. Please check the name and try again.");
            }
        }
        return success;

    }

    /**
     * *
     *
     * @param arg
     * @return integer position of the argument
     */
    public int findArg(String arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param The string word to show a summary for
     *
     */
    private void showWordSummary(String word) {
        HashMap<String, Word> words = this.concord.getConcord();
        boolean wordExists = false;

        if (words.containsKey(word)) {
            wordExists = true;
        }

        if (!this.commonWords.contains(word) && wordExists == true) {
            int rank = this.concord.get_appearance_rank(word);
            int numLines = this.concord.get_number_lines(word);
            int occur = this.concord.get_number_occurrences(word);

            //Find the word and print out the lines in which it occurs
            ArrayList<Integer> lines = this.concord.getWordLines(word);
            String linesStr = "";

            for (int i = 0; i < lines.size(); i++) {
                linesStr = linesStr + lines.get(i) + " ";
                if (i % 10 == 0 && i >= 9) {
                    linesStr = linesStr + "\n";
                }
            }

            System.out.println("Summary of " + word + ": \n"
                    + "Word:                       " + word + "\n"
                    + "Rank:                       " + rank + "\n"
                    + "Num of Lines appeared on:   " + numLines + "\n"
                    + "Number of occurrences:      " + occur + "\n"
                    + "Line numbers containing " + word + ":\n");

            System.out.println(linesStr);
        } else if (wordExists == false) {
            System.out.println("Word: " + word + " not found in the text.");
        } else {
            System.out.println("TOO MANY ENTRIES:  The word you are searching for is too common.  Please be more specific.");
        }
    }

    /**
     * Generate a summary of the currently loaded con
     */
    private void showConSummary() {
        HashMap<String, Word> words = this.concord.getConcord();
        Set<String> keys = words.keySet();
        String indexLine = "";
        ArrayList<String> outputBuff = new ArrayList<String>();

        System.out.println("Summary of the Concordance: \n"
                + "Total number of words: " + keys.size() + "\n"
                + "Total number of lines: ");
        System.out.println(String.format("%-15s%-15s%-15s%s", "Word:", "Rank:", "#Lines", "#Occur"));

        for (String v : words.keySet()) {
            Word w = words.get(v);
            // Ew, but it looks so good when printed..
            indexLine = String.format("%-15s%-15s%-15s%s", v,
                    w.getAppearanceRank(), w.getNumberLines(),
                    w.getNumberOccurances() + "\n");
            outputBuff.add(indexLine);
        }

        System.out.println("BUFF SIZE: " + outputBuff.size());
        System.out.println("MAX LINES: " + this.lines);
        System.out.println("IS NIX: " + this.isNix);
        if (outputBuff.size() > this.lines /*&& this.isNix == true*/) {
            try {
                this.pageOutput(this.lines, outputBuff);
            } catch (IOException ex) {
                Logger.getLogger(CmdRepl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void showSummaryLines() {

    }

    private void showSummaryOccur() {

    }

    private void showSummaryRank() {

    }

    private void listbooks() {
        String[] titles = shelf.getAllBookTitles();

        System.out.println("\nTitles: ====================");
        for (String s : titles) {
            System.out.println(s);
        }
        System.out.println();
    }

    private void listbooks(String book) {
        book = book.replace(",", ""); // What if there is a comma in the book title??
        String[] titles = shelf.getBookTitlesByKeyword(book);

        System.out.println("\nTitles: ====================");
        for (String s : titles) {
            System.out.println(s);
        }
        System.out.println();
    }

    private void listCords() {
        String[] cords = shelf.getAllConcordances();

        System.out.println("\nConcordances: ====================");
        for (String s : cords) {
            System.out.println(s);
        }
        System.out.println();
    }

    private void listCords(String concordance) {
        concordance = concordance.replace(",", ""); // What if there is a comma in the book title??
        String[] cords = shelf.getConcordancesByKeyword(concordance);
        System.out.println("\nConcordances: ====================");
        for (String s : cords) {
            System.out.println(s);
        }
        System.out.println();
    }

    private void listCords(String keyword, int numAppear) {
        String[] cords = shelf.getAllConcordances();
        ExecutorService executor = Executors.newFixedThreadPool(cords.length);
        System.out.println("\nConcordances: ====================");
        for (int i = 0; i < cords.length; i++) {
            Runnable worker = new QueryCon(this.concordDirectory + File.separator + cords[i], keyword, numAppear);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Just a loop to simulate a pause in the command line while
            // the concordances are being queried.
        }
        System.out.println();
    }

    private void buildConcordance(String title) {
        title = title.replace(",", "");
        String[] bookInformation = shelf.pullBook(title);
        if (bookInformation == null) {
            System.out.println("Error: Book not found. Please check the name and try again.");
        } else {
            try {
                System.out.println("Building the concordance. This may take a moment for large books.");
                long startTime = System.currentTimeMillis();
                this.concord = new Concord(bookInformation[0], bookInformation[1], bookInformation[2], this);
                long endTime = System.currentTimeMillis();
                this.prompt = "\n" + bookInformation[0] + " by " + bookInformation[1] + " > ";
                this.conLoaded = true;
                int totalTime = (int) (endTime - startTime) / 1000;
                String minute = null;
                if (totalTime / 60 == 1) {
                    minute = " minute ";
                } else {
                    minute = " minutes ";
                }
                System.out.println("SUCCESS: The concordance was built and loaded in " + (totalTime / 60) + minute + "and " + (totalTime % 60) + " seconds.");
            } catch (IOException ex) {
                System.out.println("ERROR:  The Concordance failed to be created.");
            }
        }
    }

    private void numOccurences(String word) {
        if (!this.commonWords.contains(word)) {
            int temp = concord.get_number_occurrences(word);
            System.out.println("The word " + word + " appears " + temp + " times.");
        } else {
            System.out.println("TOO MANY ENTRIES:  The word you are searching for is too common.  Please be more specific.");
        }
    }

    private void numberOfLines(String word) {
        if (!this.commonWords.contains(word)) {
            int temp = concord.get_number_lines(word);
            System.out.println("The word " + word + " appears on " + temp + " lines.");
        } else {
            System.out.println("TOO MANY ENTRIES:  The word you are searching for is too common.  Please be more specific.");
        }
    }

    private void rank(String word) {
        if (!this.commonWords.contains(word)) {
            int temp = concord.get_appearance_rank(word);
            System.out.println("The word " + word + " is ranked: " + temp);
        } else {
            System.out.println("TOO MANY ENTRIES:  The word you are searching for is too common.  Please be more specific.");
        }
    }

    private void addBook(String filePath) {
        if (shelf.addNewBook(filePath)) {
            System.out.println("SUCCESS:  Book was added to the shelf.");
        } else {
            System.out.println("ERROR: The book was not added to the shelf.");
        }
    }

    /**
     * Show the help text Can make help text a file for ease of change in the
     * future.
     */
    private void printHelp() {
        String helpTxt = "Available commmands: \n\n"
                + "load <title | path>     - load a concordance or create one and load it.\n"
                + "help                    - show this help.\n"
                + "listbooks [keyword]     - list all books matching keyword.\n"
                + "listcons [keyword]      - list concordances matching keyword.\n"
                + "build <keyword>         - build a concordance by book title.\n"
                + "summary [keyword]       - display a summary of keyword, if no word is given then\n"
                + "                          a summary of the concordance is given.\n"
                + "summarylines            - show a summary of the loaded con by lines\n"
                + "summaryrank             - show a summary of the loaded con by word rank\n"
                + "summaryoccur            - show a summary of the loaded con by word occurrence\n"
                + "numoccur <keyword>      - find number of occurrences of keyword in loaded concordance.\n"
                + "numlines <title>        - return the number of lines in the file.\n"
                + "rank <word>             - show the ranking of a word in a con by appearance.\n"
                + "phrase <phrase>         - find occurrences of phrase in loaded concordance.\n"
                + "exit                    - close Concordanator application.\n";

        System.out.println(helpTxt);
    }

    private boolean isInteger(String s, int radix) {
        if (s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) {
                    return false;
                } else {
                    continue;
                }
            }
            if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isNix() {
        if (System.getProperty("os").contains("nix")) {
            return true;
        }

        return false;
    }

    private boolean populateCommonWords() {
        boolean success = false;
        if (new File(this.commonWordFile).isFile()) {
            File source = new File(this.commonWordFile);
            try {
                BufferedReader fileIn = new BufferedReader(new FileReader(source));
                while (fileIn.ready()) {
                    String temp = fileIn.readLine().trim();
                    String[] temp2 = temp.split(" ");
                    this.commonWords.add(temp2[0]);
                }
                success = true;
            } catch (IOException ex) {
                System.out.println("Exception Firing");
            }
        }
        return success;
    }

    /**
     * Print out
     *
     * @param maxLines
     * @param maxCols
     * @param buff
     * @return
     */
    private void pageOutput(int maxLines, ArrayList<String> buff) throws IOException {
        Iterator<String> iter = buff.iterator();

        // We need to chunk up the buff into several arraylists
        int numChunks = buff.size() / maxLines;
        int rem = buff.size() % maxLines;
        boolean exit = false;

        int chunkNum = 0;
        String input = "";
        int i = 0;
        while (i < buff.size() - rem && exit == false) {
            System.out.print(buff.get(i));
            // Sub 2 from maxLines to account for prompt and carret
            if ((i + 1) % (maxLines - 2) == 0) {
                System.out.println("enter for next, q to quit [" + (chunkNum + 1) + "/" + (numChunks + 1) + "]");
                input = in.readLine().toLowerCase();
                if (input.equals("q")) {
                    exit = true;
                }
                chunkNum++;
            }
            i++;
        }

        int j = buff.size() - rem;
        while (j < buff.size() && exit == false) {
            System.out.print((buff.get(j)));
            j++;
        }
        System.out.println("END [" + chunkNum + "/" + (numChunks) + "]");
    }
}
