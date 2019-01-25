/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentssimilarity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bijo97
 */
public class DocumentsSimilarity {

    /**
     * @param args the command line arguments
     */
    
    public static double[][] getText(String s1, String s2){
        Scanner input = new Scanner(System.in);
        ArrayList<List<String>> docs = new ArrayList<List<String>>();
        ArrayList<String> kata = new ArrayList<String>();
//        System.out.print("Masukkan jumlah dokumen: ");
        int bil = 2;
//        input.nextLine();
         System.out.println(s1);
        System.out.println(s2);
        String[] k = new String[bil];
        k[0] = s1;
        k[1] = s2;
//        for (int i = 0; i < bil; i++){
//            System.out.print("Masukkan dokumen ke-" + (i + 1) + ": ");
//            k[i] = input.nextLine();
//        }
        System.out.println();
        input.close();
        
        System.out.println(k[0]);
        System.out.println(k[1]);
        
        for (int i = 0; i < bil; i++){
            List<String> d = new ArrayList<String>(Arrays.asList(k[i].split("[,\\s.]+")));
            for (int j = 0; j < d.size(); j++){
                if (!kata.contains(d.get(j))){
                    kata.add(d.get(j));
                }
            }
            docs.add(d);
        }
        
//        String kata1 = "Billy Jonathan mengerjakan tugas NLP";
//        String kata2 = "Temannya Billy mengerjakan tugas dari Universitas Kristen Petra";
//        String kata3 = "Universitas Kristen Petra terletak di jalan Siwalankerto";
//        
//        List<String> doc1 = new ArrayList<String>(Arrays.asList(kata1.split(" ")));
//        List<String> doc2 = new ArrayList<String>(Arrays.asList(kata2.split(" ")));
//        List<String> doc3 = new ArrayList<String>(Arrays.asList(kata3.split(" ")));
//        
//        for (int i = 0; i < doc1.size(); i++){
//            if (!kata.contains(doc1.get(i))){
//                kata.add(doc1.get(i));
//            }
//        }
//        
//        for (int i = 0; i < doc2.size(); i++){
//            if (!kata.contains(doc2.get(i))){
//                kata.add(doc2.get(i));
//            }
//        }
//        
//        for (int i = 0; i < doc3.size(); i++){
//            if (!kata.contains(doc3.get(i))){
//                kata.add(doc3.get(i));
//            }
//        }
//        
//        docs.add(doc1);
//        docs.add(doc2);
//        docs.add(doc3);
        
//        for (int i = 0; i < kata.size(); i++){
//            System.out.println(kata.get(i));
//        }
        
//        for (int i = 0; i < kata.size(); i++){
//            System.out.print(kata.get(i) + ": ");
//            for (int j = 0; j < docs.size(); j++){
//                System.out.print(arr[i][j] + " ");
//            }
//            System.out.println();
//        }

        double[][] arr = new double[kata.size()][docs.size()];
        arr = getWeights(docs, kata);
        
        return arr;
    }
    
    public static double[][] getWeights(ArrayList<List<String>> file, ArrayList<String> kal){
        double[][] matriks = new double[kal.size()][file.size()];
        
        for (int i = 0; i < file.size(); i++){
            System.out.println("TF * IDF Dokumen " + (i + 1) + ": ");
            for (int j = 0; j < kal.size(); j++){
                double frekuensi = 0;
                double jumlah = 0;
                
                for (int k = 0; k < file.get(i).size(); k++){
                    if (kal.get(j).equalsIgnoreCase(file.get(i).get(k))){
                        frekuensi += 1;
                    }
                }
                
                for (int k = 0; k < file.size(); k++){
                    if (file.get(k).contains(kal.get(j))){
                        jumlah += 1;
                    }
                }

                double tf = frekuensi / file.get(i).size();
                System.out.print(kal.get(j) + ": " + tf + " * ");
                double idf = 1 + Math.log((double)file.size() / jumlah);
                System.out.print(idf + " = ");
                double weight = tf * idf;
                System.out.println(weight);
//                System.out.println(kal.get(j) + ": " + tf + " " + idf);

                matriks[j][i] = weight;
            }
            System.out.println();
        }
        
        return matriks;
    }
    
    public static double CosSim(List<Double> vektor1, List<Double> vektor2){
        double dot = 0;
        double magnitude1 = 0;
        double magnitude2 = 0;
        for (int i = 0; i < vektor1.size(); i++){
            dot += vektor1.get(i) * vektor2.get(i);
        }
        
        double temp = 0;
        for (int i = 0; i < vektor1.size(); i++){
            temp += Math.pow(vektor1.get(i), 2);
        }
        magnitude1 = Math.sqrt(temp);
        
        temp = 0;
        for (int i = 0; i < vektor2.size(); i++){
            temp += Math.pow(vektor2.get(i), 2);
        }
        magnitude2 = Math.sqrt(temp);
        
        double v = dot / (magnitude1 * magnitude2);
        System.out.println("Cosine Similarity: " + dot + " / (" + magnitude1 + " * " + magnitude2 + ") = " + v);
        System.out.println();
        
        return v;
    }
    
    public static double Euclidean(List<Double> vektor1, List<Double> vektor2){
        double dot = 0;
        double magnitude1 = 0;
        double magnitude2 = 0;
        for (int i = 0; i < vektor1.size(); i++){
            dot += Math.pow(vektor1.get(i) - vektor2.get(i), 2);
        }
        dot = Math.sqrt(dot);
        
        double v = 1 / (1 + dot);
        System.out.println("Euclidean Score: 1 / (1 + " + dot + ")");
        System.out.println();
        
        return v;
    }
    
    public static double Jaccard(String s1, String s2){
        Scanner input = new Scanner(System.in);
        ArrayList<List<String>> docs = new ArrayList<List<String>>();
        ArrayList<String> kata = new ArrayList<String>();
//        System.out.print("Masukkan jumlah dokumen: ");
        int bil = 2;
//        input.nextLine();
         System.out.println(s1);
        System.out.println(s2);
        String[] k = new String[bil];
        k[0] = s1;
        k[1] = s2;
//        for (int i = 0; i < bil; i++){
//            System.out.print("Masukkan dokumen ke-" + (i + 1) + ": ");
//            k[i] = input.nextLine();
//        }
        System.out.println();
        input.close();
        
        System.out.println(k[0]);
        System.out.println(k[1]);
        
        for (int i = 0; i < bil; i++){
            List<String> d = new ArrayList<String>(Arrays.asList(k[i].split("[,\\s.]+")));
            for (int j = 0; j < d.size(); j++){
                if (!kata.contains(d.get(j))){
                    kata.add(d.get(j));
                }
            }
            docs.add(d);
        }
        
        System.out.println("Docs Jaccard:");
        for (int i = 0; i < docs.size(); i++){
            System.out.println(docs.get(i));
        }
        
//        int jumlah = 0;   
//        for (int i = 0; i < docs.size() - 1; i++){
//            for (int j = 0; j < docs.get(i).size(); j++){
//                if (docs.get(i).get(j).equals(docs.get(i + 1).get(j))){
//                    jumlah += 1;
//                }
//            }
//        }
        
        int jumlah = 0;
        double[] frekuensi = new double[kata.size()];
        for (int j = 0; j < kata.size(); j++){
            frekuensi[j] = 0;
            for (int l = 0; l < docs.size(); l++){
                if (docs.get(l).contains(kata.get(j))){
                    frekuensi[j] += 1;
                }
            }
        }
        
        for (int i = 0; i < frekuensi.length; i++){
            if (frekuensi[i] == 2){
                jumlah += 1;
            }
        }
        
        System.out.println(docs.get(0).size());
        System.out.println(docs.get(1).size());
        System.out.println(jumlah + " / " + kata.size());
        double j = (double)jumlah / (double)kata.size();
        System.out.println(j);
        
        return j;
    }
    
    public static double[][] readTextFile(String filename, String filename2){
        FileReader fr = null;
        BufferedReader br = null;
        String input = null;
        String input2 = null;
        double[][] res = null;
        String temp1 = "";
        String temp2 = "";
                
        try {
            //Init file and buffered reader
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            //Reading process from file
            while((input = br.readLine())!=null){
                System.out.println(input);
                temp1 = temp1 + input + " ";
            }
            
            //Init file and buffered reader
            fr = new FileReader(filename2);
            br = new BufferedReader(fr);
            //Reading process from file
            while((input2 = br.readLine())!=null){
                System.out.println(input2);
                temp2 = temp2 + input2 + " ";
            }
            
//            System.out.println(input);
//            System.out.println(input2);
            res = getText(temp1, temp2);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DocumentsSimilarity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocumentsSimilarity.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(br!=null)
                    br.close();
                if(fr!=null)
                    fr.close();
            } catch (IOException ex) {
                Logger.getLogger(DocumentsSimilarity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return res;
    }
    
    public static double readTextFile2(String filename, String filename2){
        FileReader fr = null;
        BufferedReader br = null;
        String input = null;
        String input2 = null;
        double jac = 0.0;
        String temp1 = "";
        String temp2 = "";
                
        try {
            //Init file and buffered reader
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            //Reading process from file
            while((input = br.readLine())!=null){
                System.out.println(input);
                temp1 = temp1 + input + " ";
            }
            
            //Init file and buffered reader
            fr = new FileReader(filename2);
            br = new BufferedReader(fr);
            //Reading process from file
            while((input2 = br.readLine())!=null){
                System.out.println(input2);
                temp2 = temp2 + input2 + " ";
            }
            
//            System.out.println(input);
//            System.out.println(input2);
            jac = Jaccard(temp1, temp2);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DocumentsSimilarity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocumentsSimilarity.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(br!=null)
                    br.close();
                if(fr!=null)
                    fr.close();
            } catch (IOException ex) {
                Logger.getLogger(DocumentsSimilarity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return jac;
    }
    
    public static double[] index(String f1, String f2) {
        
        ArrayList<List<Double>> dokumen = new ArrayList<List<Double>>();
        double[][] res = readTextFile(f1, f2);
        
        for (int i = 0; i < res[0].length; i++){
            List<Double> temp = new ArrayList<Double>();
            for (int j = 0; j < res.length; j++){
                temp.add(res[j][i]);
//                System.out.print(res[i][j] + " ");
            }
//            System.out.println();
            dokumen.add(temp);
        }
        
        System.out.println("Isi Dokumen: ");
        for (int i = 0; i < dokumen.size(); i++){
            for (int j = 0; j < dokumen.get(i).size(); j++){
                System.out.print(dokumen.get(i).get(j) + " ");
            }
            System.out.println();
        }
        
        double[] flag = new double[dokumen.size()];
        for (int i = 0; i < flag.length; i++){
            flag[i] = 0;
        }
        
        double hasil = 0.0;
        for (int i = 0; i < dokumen.size() - 1; i++){
            for (int j = 0; j < dokumen.size(); j++){
                if (i != j && flag[j] != 1){
                    hasil = CosSim(dokumen.get(i), dokumen.get(j));
//                    System.out.println("Kesamaan Dokumen " + (i + 1) + " dan dokumen " + (j + 1) + ": " + (int)(hasil * 100) + "%");
                }
            }
            flag[i] = 1;
        }
        
        for (int i = 0; i < flag.length; i++){
            flag[i] = 0;
        }
        
        double hasil2 = 0.0;
        for (int i = 0; i < dokumen.size() - 1; i++){
            for (int j = 0; j < dokumen.size(); j++){
                if (i != j && flag[j] != 1){
                    hasil2 = Euclidean(dokumen.get(i), dokumen.get(j));
//                    System.out.println("Kesamaan Dokumen " + (i + 1) + " dan dokumen " + (j + 1) + ": " + (int)(hasil * 100) + "%");
                }
            }
            flag[i] = 1;
        }
        
        double res2 = readTextFile2(f1, f2);
        double[] arr = new double[3];
        arr[0] = hasil * 100;
        arr[1] = res2 * 100;
        arr[2] = hasil2 * 100;
        return arr;
    }
}
