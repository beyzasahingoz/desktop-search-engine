package BeyzaSahingozProjectFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

/**
 * subject: create heap preorder traversal with html files.
 * mail:beyza.sahingoz@stu.fsm.edu.tr 
 * date: 23.05.2022
 * @author beyza sahingoz
 */
public class BeyzaSahingozBSTClass<T extends Comparable<T>> {

    //defined root
    BeyzaSahingozNodeClass root;

    //this method and parameters created for making and writing to text file preorder BST. 
    void preord(BeyzaSahingozNodeClass node, BufferedWriter bw) throws IOException {
        if (node == null) {
            return;
        }
        //write to the result text file.
        bw.write(node.data + ", " + node.freq + "\n");
        //call it for preorder traversal.
        preord(node.left, bw);
        preord(node.right, bw);
    }

    //this method create for using root.
    void preord(BufferedWriter bw) throws IOException {
        preord(root, bw);
    }

    //remove tags and punctuations
    public static String replaceAllStr(String in) {
        in = in.replaceAll("<[^>]*>", " ");
        in = in.replaceAll("\\p{Punct}", " ");
        return in;
    }

    //this method convert file to string.
    
    int iterativeSearch(BeyzaSahingozNodeClass root, String key)
    {
        // Traverse until root reaches to dead end
        while (root != null) {
            // pass right subtree as new tree
            if (root.data.compareTo(key)<0)
                root = root.right;
 
            // pass left subtree as new tree
            else if (root.data.compareTo(key)>0)
                root = root.left;
            else
                return root.freq; // if the key is found return 1
        }
        return 0;
    }
    //in this method, send ignoreList and input file -as string- as parameters.then i add words to the binary search tree.
    void insert(List<String> ignore, File inp) throws FileNotFoundException, IOException {
        Scanner in = new Scanner(inp);
        while (in.hasNext()) {
            boolean cath = false;
            String txt = in.next();
            //comparing ignorelist words with input words.
            for (int i = 0; i < ignore.size(); i++) {
                if (txt.compareTo(ignore.get(i)) == 0) {
                    cath = true;
                    break;
                }
            }
            if (!cath) {
                //if root equals null, txt is assigned to root and freq is increased.
                if (root == null) {
                    root = new BeyzaSahingozNodeClass(txt);
                    root.freq++;
                    //if is not, create tmp as new node that is assigned to root.
                } else {
                    BeyzaSahingozNodeClass tmp = root;
                    //in while loop, we create binary search tree. 
                    while (tmp != null) {
                        //if tmp value is bigger than txt, txt add to tmp left.
                        if (tmp.data.compareTo(txt) > 0) {
                            if (tmp.left == null) {
                                tmp.left = new BeyzaSahingozNodeClass(txt);
                                tmp.left.freq++;
                                break;
                            } else {
                                tmp = tmp.left;
                            }
                            //if tmp value is smaller than txt, txt add to tmp left.
                        } else if (tmp.data.compareTo(txt) < 0) {
                            if (tmp.right == null) {
                                tmp.right = new BeyzaSahingozNodeClass(txt);
                                tmp.right.freq++;
                                break;
                            } else {
                                tmp = tmp.right;
                            }
                            //if it is equal, just freq is increased.
                        } else {
                            tmp.freq++;
                            break;
                        }
                    }

                }
            }
        }
    }
}
