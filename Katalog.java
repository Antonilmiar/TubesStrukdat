import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JOptionPane;

class Node {
    //variabel 
    String judul, author, id;
    Node kanan;
    Node kiri;
}

class BinarySearchTree {
    Node root;
    String key;
    String hasil = "";
    int count=0;

    void insert(String judul, String author, String id) { //memasukkan data ke dalam binary tree
        Node dataBaru = new Node();  
        dataBaru.judul = judul;
        dataBaru.author = author;
        dataBaru.id = id;

        if (isEmpty()) {
            root = dataBaru;
        } else {
            Node current = root;
            Node parent = null;
            boolean diKiri = true;

            while (current != null) {
                parent = current;

                if ((current.judul).compareTo(dataBaru.judul) < 0) {
                    current = current.kanan;
                    diKiri = false;

                } else {
                    current = current.kiri;
                    diKiri = true;
                }
            }

            if (diKiri) {
                parent.kiri = dataBaru;
            } else {
                parent.kanan = dataBaru;
            }
        }
    }

    boolean isEmpty() {
        //untuk mengecek jika root kosong
        return (root == null);
    }
    
    //untuk pencarian dengan parameter key untuk memasukkan data yang kita cari
    public void traverseInorder(String key) {
        this.key = key;
        this.count = 0; // nilai awal jumlah data
        this.hasil = "";
        inorder(root); //memanggil method inorder dengan parameter yang berisi root
        if (hasil.equals("")) { //jika data tidak ditemukan
            hasil = "Data Tidak Ditemukan";
            JOptionPane.showMessageDialog(null, hasil);
        }
        JOptionPane.showMessageDialog(null, count + " data yang berhubungan ditemukan."); //tampilan jumlah data yang ditemukan
    }

    public void inorder(Node akar) { 
        if (akar != null) {
            inorder(akar.kiri); //method rekursif -> method yang memanggil dirinya sendiri berfungsi untuk mengecek tree sebelah kiri
            if ((akar.id).contains(key) || (akar.judul).contains(key)
                    || (akar.author).contains(key)) { //mengecek apakah key yang kita masukkan terdapat dalam data
                hasil += (count+1) + ". ID : " + akar.id + "\n"; //menampilkan ID
                hasil += "      Judul : " + akar.judul + "\n"; // menampilkan Judul
                hasil += "      Author : " + akar.author + "\n" + "\n"; //menampilkan Author
                count++;
            }
            inorder(akar.kanan); //membaca tree bagian kanan
        }
    }
}

public class Katalog {

    String fileName = "GUTINDEX.ALL";
    BufferedReader br;
    BinarySearchTree bst = null;
    String line, strings = "";
    String judul, author = "", id = "";
    boolean start = false;

    public Katalog() {

        try {
            //membaca file
            br = new BufferedReader(new FileReader(new File(fileName)));
            // 
            bst = new BinarySearchTree();
            while ((line = br.readLine()) != null) {
                if (line.equals("TITLE and AUTHOR                                                     ETEXT NO.")) {
                    start = true;
                    continue;
                }
                //memulai membaca data
                if (start) {
                    if (line.length() != 0) {
                        String[] split1 = line.split("[ ]{2,}"); //memotong data berdasarkan [],{2, } spasi yang lebih dari 2
                        if (split1.length > 1) {
                            id = split1[1]; //data dengan index [1] merupakan ID
                        }
                        strings = strings + split1[0]; //data ke-2 merupakan gabungan dari judul author dan keterangan
                    } else {
                        if (strings.length() != 0) {
                            String[] split2 = strings.split(", by "); // data ke-2 (string) di potong berdasarkan ", by "
                            judul = split2[0]; //data index-0 sebagai judul
                            if (split2.length > 1) {
                                author = split2[1]; //data index-1 sebagai author dan keterangan
                            }

                            // memasukkan data ke dalam binary tree
                            bst.insert(judul, author, id);
                        }
                        //mengembalikan data ke-0 untuk membaca line baru
                        strings = "";
                        judul = "";
                        id = "";
                    }
                }
            }

            System.out.println();
        } catch (Exception ex) {
        }
    }
}
