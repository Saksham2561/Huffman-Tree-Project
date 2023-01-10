import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Huffman {
    public static void main(String[] args) throws IOException {
        //creating 2 Queues S and T

        Queue<BinaryTree<Pair>> S = new LinkedList<>();

        Queue<BinaryTree<Pair>> T = new LinkedList<>();

        //Reading the text file from the user

        Scanner in = new Scanner(System.in);

        System.out.println("Huffman Coding");

        System.out.print("Enter the name of the file with letters and probability: ");

        String filename = in.nextLine();

        File myFile = new File(filename);

        Scanner file = new Scanner(myFile);


        while (file.hasNextLine()) {
            //breaking the input data into character and double by using a string array and string.split() function.

            String data = file.nextLine();
            String[] dataArray = data.split("\t");


            //typecasting the string array indexes containing the character(letter) and the double (probability)
            char myChar = dataArray[0].charAt(0);
            double myDouble = Double.valueOf(dataArray[1]);


            //creating a binary tree of type Pair and adding it to the Queue S.
            BinaryTree<Pair> huffman = new BinaryTree<Pair>();
            huffman.makeRoot(new Pair(myChar, myDouble));
            S.add(huffman);
        }
        file.close();


        //creating 2 new binary trees in order to build the huffman tree.

        BinaryTree<Pair> A = new BinaryTree<>();
        BinaryTree<Pair> B = new BinaryTree<>();

        while (!S.isEmpty()) {
            if (T.isEmpty()) {
                //removing the first 2 elements of the queue(A and B)
                A = S.remove();
                B = S.remove();
            } else {
                if (S.peek().getData().getProb() <= (T.peek().getData().getProb())) {

                    //front of S < front of T
                    A = S.remove();

                } else {
                    //front of S > front of T
                    A = T.remove();

                }
                //For null pointer exceptions

                if (T.isEmpty() && !S.isEmpty()) {

                    B = S.remove();

                } else if (!T.isEmpty() && S.isEmpty()) {

                    B = T.remove();
                }
                else {
                    //doing the same process for BinaryTree<Pair> B
                    if (S.peek().getData().getProb() <= (T.peek().getData().getProb())) {

                        B = S.remove();
                    }
                    else if (S.peek().getData().getProb() > (T.peek().getData().getProb())) {
                        B = T.remove();
                    }
                }

                }

         /*
            creating a new Binary tree P and assigning  it's root node a random character(R) and equating
            it's probability to the sum of probabilities of A and B and then finally attaching trees
            A and B to the newly constructed Binary Tree P.
          */

                BinaryTree<Pair> P = new BinaryTree<Pair>();
                Pair rootNode = new Pair('0', A.getData().getProb() + B.getData().getProb());
                P.makeRoot(rootNode);
                P.attachLeft(A);
                P.attachRight(B);
                T.add(P);
            }
            //repeating the same process until Queue T has only one element left
            while (T.size() > 1) {
                A = T.remove();
                B = T.remove();

                BinaryTree<Pair> P = new BinaryTree<Pair>();
                Pair rootNode = new Pair('0', A.getData().getProb() + B.getData().getProb());
                P.makeRoot(rootNode);
                P.attachLeft(A);
                P.attachRight(B);
                T.add(P);
            }
//            creating a character array that contains all english alphabets in upper case.
        char[] charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        //creating a string array to store both charArray and encoded strings together
        String[]result=new String[26];

        //creating a string array to store the encoded strings for the huffman tree
        String[]encoded= findEncoding(T.peek());


        //storing encoded strings and charArray elements together side by side
        for(int j=0;j<encoded.length;j++){
            result[j]+=charArray[j]+encoded[j];

        }

        System.out.println("Enter a line of text (uppercase letters only):");

        String userInput = in.nextLine();
        //creating a character Array using the user input

        char[] myChar=new char[userInput.length()];


        for(int z=0;z< userInput.length();z++){
            myChar[z]=userInput.charAt(z);
        }
        //comparing the character array elements ('A'/'B' etc..) to the user Input characters

        System.out.print("Here's the encoded line: ");

        for(int i=0;i< userInput.length();i++){
            for(int j =0;j<26;j++){

                if (myChar[i]==result[j].charAt(4)){
                    //printing the corresponding encoded string if input character matches the alphabet array character

                    System.out.print(encoded[j]+" ");
                }
            }
        }
        //Getting the encoding digits from the result array by using result[index].substring
        System.out.println(" ");
        String[] codes= new String[26];
        for(int i =0;i< result.length;i++){
            codes[i]=result[i].substring(5);
        }

        //printing the decoded line by matching the encoded characters with the result string array
        System.out.print("The decoded line is : ");

        for(int i =0;i<userInput.length();i++){
            for(int j =0;j<26;j++){
                if(encoded[i].equals(codes[j])){
                    System.out.print(myChar[j]);
                }
            }

        }
        System.out.println("");

    }
    //Using methods to find encoded strings .
    //Given by our professor Dr.Srini Sampalli
    private static String[] findEncoding(BinaryTree<Pair> bt){
        String[] result = new String[26];
        findEncoding(bt, result, "");
        return result;
    }
    private static void findEncoding(BinaryTree<Pair> bt, String[] a, String prefix){
// test is node/tree is a leaf
        if (bt.getLeft()==null && bt.getRight()==null){
            a[bt.getData().getValue() - 65] = prefix;
        }
// recursive calls
        else{
            findEncoding(bt.getLeft(), a, prefix+"0");
            findEncoding(bt.getRight(), a, prefix+"1");
        }
    }
}


