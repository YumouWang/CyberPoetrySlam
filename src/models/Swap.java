package models;

import broker.util.IProtocol;
import controllers.swap.InvalidSwapException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The swap model class. Encapsulates data about the current swap.
 *
 * @author Yumou
 * @author Nathan
 * @version 10/3/2014
 */
public class Swap {
	/**
	 * Objects associated with Swap
	 */
    int num;
    List<WordType> myTypes;
    List<Word> myWords;
    List<WordType> theirTypes;
    List<String> theirWords;
    /**
     * To check whether the request if from local position
     */
    boolean isRequestor;
    GameState gameState;
    /**
     * To check whether the swap is cancelled
     */
    boolean isCancelled;
    /**
     * The unique ID of the requestor
     */
    String requestorID;

    /**
     * Constructor. Attempts to construct a swap with the given words and types.
     *
     * @param gameState         The gameState to construct the swap against
     * @param inputOfferTypes   The types of the words this gameState is offering
     * @param inputOfferWords   The words this gameState is offering
     * @param inputRequestTypes The types of the words this gameState is requesting/getting
     * @param inputRequestWords The words this gameState is requesting/getting
     * @param isRequestor       Whether or not this gameState is the requestor of the swap. Used to determine
     *                          whether to match offer words or request words with the gameState
     * @param requestorID       The id of the requestor
     * @throws InvalidSwapException Throws an exception if the swap cannot be fulfilled with
     *                              the words in the given GameState
     */
    public Swap(GameState gameState, List<String> inputOfferTypes, List<String> inputOfferWords,
                List<String> inputRequestTypes, List<String> inputRequestWords, boolean isRequestor, String requestorID) throws InvalidSwapException {
        num = inputOfferTypes.size();
        isCancelled = false;
        this.requestorID = requestorID;
        this.isRequestor = isRequestor;
        this.gameState = gameState;
        if (num != inputOfferWords.size() || num != inputRequestTypes.size() || num != inputRequestWords.size()) {
            throw new InvalidSwapException("Invalid swap input");
        }
        // Validate the input get arrays
        this.theirWords = new ArrayList<String>();
        List<String> theirWordsSource;
        // If we are the requestor, their words are the request words, otherwise their words are the offer words
        if (isRequestor) {
            theirWordsSource = inputRequestWords;
            this.theirTypes = validateWordTypes(inputRequestTypes);
        } else {
            theirWordsSource = inputOfferWords;
            this.theirTypes = validateWordTypes(inputOfferTypes);
        }
        for (String getWord : theirWordsSource) {
            String word = getWord;
            if (getWord.isEmpty()) {
                word = "*";
            }
            theirWords.add(word);
        }

        // Validate the input give arrays
        // Check in the gameState to ensure that there is a word that fits the criteria
        this.myWords = new ArrayList<Word>();

        List<String> myWordsSource;
        // If we are the requestor, my words are the offer words, otherwise my words are the request words
        if (isRequestor) {
            myWordsSource = inputOfferWords;
            this.myTypes = validateWordTypes(inputOfferTypes);
        } else {
            myWordsSource = inputRequestWords;
            this.myTypes = validateWordTypes(inputRequestTypes);
        }
        // For each input word, try to find a word in the gameState that matches
        for (int i = 0; i < myWordsSource.size(); i++) {
            Collection<AbstractWord> allAvailableWords = gameState.getUnprotectedArea().getAbstractWordCollection();
            String targetWord = myWordsSource.get(i);
            WordType targetType = myTypes.get(i);
            boolean foundMatchingWord = false;

            // Go through all the words and see if there is a match
            for (AbstractWord candidate : allAvailableWords) {
                Word candidateWord = (Word) candidate;
                // Do not use the same word more than once when constructing a swap request
                if (!myWords.contains(candidateWord)) {
                    // If it matches the targetWord value (or any)
                    if (targetWord.equals(candidateWord.getValue()) || targetWord.equals("*") || targetWord.isEmpty()) {
                        // If it matches the target word type (or any)
                        if (candidateWord.getType() == targetType || targetType == WordType.ANY) {
                            // Then add it to the list of candidate words and exit the loop
                            myWords.add(candidateWord);
                            myTypes.set(i, candidateWord.getType());
                            foundMatchingWord = true;
                            break;
                        }
                    }
                }
            }
            // If there was no match, this swap cannot be fulfilled
            if (!foundMatchingWord) {
                throw new InvalidSwapException("Swap cannot be matched");
            }
        }
        // Swap should be all set now, one last sanity check
        if (num != myTypes.size() || num != myWords.size() || num != theirTypes.size() || num != theirWords.size()) {
            // Should never be reached (Not even with tests)
            throw new InvalidSwapException("Bad swap constructor");
        }
    }

    /**
     * Attempts to construct a swap request from a swap string
     *
     * @param gameState   The gameState to construct the swap against
     * @param swapString  The swap string in the format of the protocol
     * @param isRequestor Whether or not this client is the requestor
     * @param requestorID The id of the requestor
     * @throws InvalidSwapException Throws an exception if the
     *                              swap string is poorly formed or the swap cannot be fulfilled
     */
    public static Swap getSwap(GameState gameState, String swapString, boolean isRequestor, String requestorID) throws InvalidSwapException {
        try {
            List<String> inputOfferTypes = new ArrayList<String>();
            List<String> inputOfferWords = new ArrayList<String>();
            List<String> inputRequestTypes = new ArrayList<String>();
            List<String> inputRequestWords = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(swapString, ":");
            // Ignore swap type, requestor, and acceptor
            st.nextToken();
            st.nextToken();
            st.nextToken();
            int num = Integer.decode(st.nextToken());
            for (int i = 0; i < num; i++) {
                inputOfferTypes.add(st.nextToken());
            }
            for (int i = 0; i < num; i++) {
                inputOfferWords.add(st.nextToken());
            }
            for (int i = 0; i < num; i++) {
                inputRequestTypes.add(st.nextToken());
            }
            for (int i = 0; i < num; i++) {
                inputRequestWords.add(st.nextToken());
            }

            return new Swap(gameState, inputOfferTypes, inputOfferWords, inputRequestTypes, inputRequestWords, isRequestor, requestorID);
        } catch (Exception e) {
            throw new InvalidSwapException("Poorly formed swap");
        }
    }
/**
 * Validate all the wordType of swap words to be correct
 * @param wordTypes
 * @return List<WordType>
 * @throws InvalidSwapException
 */
    List<WordType> validateWordTypes(List<String> wordTypes) throws InvalidSwapException {
        List<WordType> types = new ArrayList<WordType>();
        for (String type : wordTypes) {
            try {
                String typeToEval = type;
                if (typeToEval.equals("*")) {
                    typeToEval = "any";
                }
                types.add(WordType.valueOf(typeToEval.toUpperCase()));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new InvalidSwapException("Bad word type argument");
            }
        }
        return types;
    }
/**
 * Get a collection of my words
 * @return List<Word>
 */
    public List<Word> getMyWords() {
        return myWords;
    }
/** 
 * Get the unique ID from swap requestor
 * @return String
 */
    public String getRequestorID() {
        return requestorID;
    }
/**
 * Get a collection of my WordTypes 
 * @return List<WordType>
 */
    public List<WordType> getTheirTypes() {
        return theirTypes;
    }
/**
 * Get the words from another player of swap
 * @return List<String>
 */
    public List<String> getTheirWords() {
        return theirWords;
    }
/**
 * Check whether the request is started by our side
 * @return boolean
 */
    public boolean isRequestor() {
        return isRequestor;
    }
/**
 * Check whether the swap request is cancelled
 * @return boolean
 */
    public boolean getIsCancelled() {
        return isCancelled;
    }
/**
 * Set the state of swap request
 * @param isCancelled
 */
    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public String toString() {
        String swapString = "" + num;
        if (isRequestor) {
            for (WordType type : myTypes) {
                String giveType = type.toString().toLowerCase();
                if (type == WordType.ANY) {
                    giveType = "*";
                }
                swapString += IProtocol.separator + giveType;
            }
            for (Word word : myWords) {
                swapString += IProtocol.separator + word.getValue();
            }
        } else {
            for (WordType type : theirTypes) {
                String giveType = type.toString().toLowerCase();
                if (type == WordType.ANY) {
                    giveType = "*";
                }
                swapString += IProtocol.separator + giveType;
            }
            for (String s : theirWords) {
                swapString += IProtocol.separator + s;
            }
        }
        if (isRequestor) {
            for (WordType type : theirTypes) {
                String getType = type.toString().toLowerCase();
                if (type == WordType.ANY) {
                    getType = "*";
                }
                swapString += IProtocol.separator + getType;
            }
            for (String s : theirWords) {
                swapString += IProtocol.separator + s;
            }
        } else {
            for (WordType type : myTypes) {
                String getType = type.toString().toLowerCase();
                if (type == WordType.ANY) {
                    getType = "*";
                }
                swapString += IProtocol.separator + getType;
            }
            for (Word word : myWords) {
                swapString += IProtocol.separator + word.getValue();
            }
        }
        return swapString;
    }
/**
 * Parse the words from another player and accept them to confirm the swap
 * @param swapString
 * @throws InvalidSwapException
 */
    public void updateTheirWordsForConfirmSwap(String swapString) throws InvalidSwapException {
        List<String> inputOfferTypes = new ArrayList<String>();
        List<String> inputOfferWords = new ArrayList<String>();
        List<String> inputRequestTypes = new ArrayList<String>();
        List<String> inputRequestWords = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(swapString, ":");
        // Ignore swap type, requestor, and acceptor
        st.nextToken();
        st.nextToken();
        st.nextToken();
        int num = Integer.decode(st.nextToken());
        for (int i = 0; i < num; i++) {
            inputOfferTypes.add(st.nextToken());
        }
        for (int i = 0; i < num; i++) {
            inputOfferWords.add(st.nextToken());
        }
        for (int i = 0; i < num; i++) {
            inputRequestTypes.add(st.nextToken());
        }
        for (int i = 0; i < num; i++) {
            inputRequestWords.add(st.nextToken());
        }

        theirWords.clear();
        theirTypes.clear();
        if (isRequestor) {
            theirWords.addAll(inputRequestWords);
            theirTypes.addAll(validateWordTypes(inputRequestTypes));
        } else {
            theirWords.addAll(inputOfferWords);
            theirTypes.addAll(validateWordTypes(inputOfferTypes));
        }
    }
}
