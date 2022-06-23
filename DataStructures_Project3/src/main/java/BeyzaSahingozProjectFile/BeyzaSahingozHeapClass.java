package BeyzaSahingozProjectFile;

import static BeyzaSahingozProjectFile.BeyzaSahingozBSTClass.replaceAllStr;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * subject: create heap preorder traversal with html files.
 * mail:beyza.sahingoz@stu.fsm.edu.tr 
 * date: 23.05.2022
 * @author beyza sahingoz
 */
public class BeyzaSahingozHeapClass<T extends Comparable<T>> {
    //define variables.
    int size = 0;
    BeyzaSahingozBSTClass fileArr[];
    ArrayList<String> maxHeap;

    public BeyzaSahingozHeapClass() {
        maxHeap = new ArrayList<String>();
        fileArr = new BeyzaSahingozBSTClass[10];
        size = 0;
    }
    //write parent method
    int parent(int index) {

        return (index - 1) / 2;
    }

    //making heap sort
    public void sort() {
        // Build heap (rearrange array)
        for (int i = maxHeap.size() / 2 - 1; i >= 0; i--) {
            heapify(maxHeap.size(), i);
        }

        // One by one extract an element from heap
        for (int i = maxHeap.size() - 1; i >= 0; i--) {
            // Move current root to end
            Collections.swap(maxHeap, 0, i);

            // call max heapify on the reduced heap
            heapify(i, 0);
        }
    }
    
    //making heapify method.
    void heapify(int n, int i) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n) {
            String left = freq(maxHeap.get(l));
            String smallest = freq(maxHeap.get(largest));
            int lef=Integer.parseInt(left);
            int small=Integer.parseInt(smallest);
            if (lef<small) {
                largest = l;
            }
        }
        // If right child is larger than root
        if (r < n) {
            String right = freq(maxHeap.get(r));
            String smallest = freq(maxHeap.get(largest));
            int rig=Integer.parseInt(right);
            int small=Integer.parseInt(smallest);
            if (rig<small) {
                largest = r;
            }
        }

        // If largest is not root
        if (largest != i) {
            Collections.swap(maxHeap, i, largest);
            // Recursively heapify the affected sub-tree
            heapify(n, largest);
        }
    }
    //print heap elements.
    void print() {
        for (int i = 0; i < maxHeap.size(); i++) {
            System.out.print(maxHeap.get(i) + " ");
        }
        System.out.println();
    }
    //find elements frequency.
    String freq(String in) {
        String txt = "";
        for (int i = 4; i < in.length(); i++) {
            if (i == 5 && in.charAt(i) == '0') {
                continue;
            }
            if (in.charAt(i) != '(' && in.charAt(i) != ')') {
                txt += in.charAt(i);
            }
        }
        return txt;
    }
    //inserting to heap.
    public void insert(String data) {
        maxHeap.add(data);

        // Traverse up and fix violated property
        int curr = size;
        String s1 = freq(maxHeap.get(curr));
        String s2 = freq(maxHeap.get(parent(curr)));

        while (Integer.parseInt(s1) > Integer.parseInt(s2)) {
            Collections.swap(maxHeap, curr, parent(curr));
            curr = parent(curr);
            s1 = freq(maxHeap.get(curr));
            s2 = freq(maxHeap.get(parent(curr)));
        }
        size++;
    }

    //convert file to string.
    public static String fileToString(String filePath) throws Exception {

        String input = null;
        Scanner sc = new Scanner(new File(filePath));
        //file text adding to stringbuffer 
        StringBuffer sb = new StringBuffer();
        while (sc.hasNextLine()) {
            input = sc.nextLine();
            sb.append(" " + input);
        }
        //while return, replace empty blanks.
        return sb.toString();
    }

    //create array with files.
    void createArray() throws IOException, Exception {
        List<String> removeList = new ArrayList<>();
        //file text adding to list.
        try ( BufferedReader br = new BufferedReader(new FileReader(new File("docs\\ignoreList.txt")))) {
            while (br.ready()) {
                removeList.add(br.readLine());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for (int i = 1; i <= 10; i++) {
            BeyzaSahingozBSTClass root = new BeyzaSahingozBSTClass();
            if (i != 10) {
                File file = new File("docs\\cse2250" + i + ".html");
                String s = file.getPath();
                String str = fileToString(s);
                str = replaceAllStr(str);
                root.insert(removeList, file);
                fileArr[i - 1] = root;
            } else {
                File file = new File("docs\\cse225" + i + ".html");
                root.insert(removeList, file);
                fileArr[i - 1] = root;
            }
        }
    }

    //insert files and making all operations.
    void search(String in) throws IOException, Exception {

        createArray();
        ArrayList<String> txts = new ArrayList<>();
        String txt = "";
        for (int i = 0; i < in.length(); i++) {
            if (i == in.length() - 1
                    && (in.charAt(i) >= 65 && in.charAt(i) <= 90 || in.charAt(i) >= 97 && in.charAt(i) <= 122)) {
                txt += in.charAt(i);
                txts.add(txt);
            } else if (in.charAt(i) >= 65 && in.charAt(i) <= 90 || in.charAt(i) >= 97 && in.charAt(i) <= 122) {
                txt += in.charAt(i);
            } else {
                if (!txt.equals("")) {
                    txts.add(txt);
                }
                txt = "";
            }
        }
        searchBST(txts);
        sort();
        print();
        maxHeap.clear();
        size = 0;
    }

    //finding inputs in BST.
    void searchBST(ArrayList txts) {

        for (int i = 0; i < fileArr.length; i++) {
            int temp = i + 1;
            int freq = 0;
            for (int j = 0; j < txts.size(); j++) {
                freq += fileArr[i].iterativeSearch(fileArr[i].root, txts.get(j).toString());
            }
            if (freq != 0) {
                insert("Doc" + temp + "(" + freq + ")");
            }
        }
    }

}
