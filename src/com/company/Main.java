package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.lang.Math;
import java.util.Scanner;

public class Main {

    private static final int M = 3;


    public static void main(String[] args) throws IOException {
	// write your code here
        file();
    }

    public static void file() throws IOException {
        Random random = new Random();
        int i = 0;
        int ran = 0;
        File Dir = new File("resource\\file");
        if (!Dir.exists()) {
            try {
                Dir.mkdir();
            } catch (SecurityException var10) {

            }
        }
        File F1 = new File("resource\\file", "F1.txt");
        File F2 = new File("resource\\file", "F2.txt");
        do {
            ran = random.nextInt(3);
            switch (ran) {
                case 0:
                    try (FileWriter writer = new FileWriter(F1, true)) {
                        writer.append("a");
                        writer.flush();
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    break;
                case 1:
                    try (FileWriter writer = new FileWriter(F1, true)) {
                        writer.append("b");
                        writer.flush();
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    break;
                case 2:
                    try (FileWriter writer = new FileWriter(F1, true)) {
                        writer.append("c");
                        writer.flush();
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    break;
                default:
                    break;
            }
        } while (F1.length() < 10240);

        do {
            ran = random.nextInt(100);
            if (ran <= 19) {
            try (FileWriter writer = new FileWriter(F2, true)) {
                writer.append("a");
                writer.flush();
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        } else if (ran <= 39) {
            try (FileWriter writer = new FileWriter(F2, true)) {
                writer.append("b");
                writer.flush();
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        } else {
            try (FileWriter writer = new FileWriter(F2, true)) {
                writer.append("c");
                writer.flush();
            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }
        }
        } while (F2.length() < 10240);
        shenon_Single();
        shenon_Double();
    }

    private static void shenon_Single() throws IOException {
        FileReader reader_F1 = new FileReader("resource\\file\\F1.txt");
        FileReader reader_F2 = new FileReader("resource\\file\\F2.txt");
        FileReader reader_F3 = new FileReader("resource\\file\\F3.txt");
        int ch;
        int all_F1 = 0, all_F2 = 0, all_F3 = 0;
        int a_F1 = 0, b_F1 = 0, c_F1 = 0;
        int a_F2 = 0, b_F2 = 0, c_F2 = 0;
        float freq_a_F1 = 0, freq_b_F1 = 0, freq_c_F1 = 0;
        float freq_a_F2 = 0, freq_b_F2 = 0, freq_c_F2 = 0;
        HashMap <String, Float> map = new HashMap<>();
        HashMap <String, Float> map_prob = new HashMap<>(); //Вероятности

        //Подсчет вероятностей
        while((ch = reader_F1.read()) != -1) {
            all_F1++;
            switch ((char)ch) {
                case 'a':
                    a_F1++;
                    break;
                case 'b':
                    b_F1++;
                    break;
                case 'c':
                    c_F1++;
                    break;
                default:
                    break;
            }
        }
        freq_a_F1 = (float)a_F1 / all_F1;
        freq_b_F1 = (float)b_F1 / all_F1;
        freq_c_F1 = (float)c_F1 / all_F1;

        while((ch =reader_F2.read()) != -1) {
            all_F2++;
            switch ((char)ch) {
                case 'a':
                    a_F2++;
                    break;
                case 'b':
                    b_F2++;
                    break;
                case 'c':
                    c_F2++;
                    break;
                default:
                    break;
            }
        }
        freq_a_F2 = (float)a_F2 / all_F2;
        freq_b_F2 = (float)b_F2 / all_F2;
        freq_c_F2 = (float)c_F2 / all_F2;

        while((ch = reader_F3.read()) != -1) {
            String str = String.valueOf((char)ch);
            str = str.toLowerCase();
            str = str.replaceAll("[^а-яё]", "");
            if (str.isEmpty()) {
                continue;
            }
            if (!map.containsKey(str)) {
                map.put(str, 1.0f);
            } else {
                float i = map.get(str) + 1;
                map.put(str, i);
            }
        }
        for(Map.Entry entry: map.entrySet()) {
            all_F3 += (Float)entry.getValue();
        }

        for(Map.Entry entry: map.entrySet()) {
            map_prob.put(entry.getKey().toString(), (Float)entry.getValue() / all_F3);
        }

        System.out.println(all_F3);
        System.out.println(map_prob);



        //System.out.println(a_F1 + " " + b_F1 + " " + c_F1);
        //System.out.println(a_F2 + " " + b_F2 + " " + c_F2);
        System.out.println(freq_a_F1 + " " + freq_b_F1 + " " + freq_c_F1);
        //System.out.println(freq_a_F2 + " " + freq_b_F2 + " " + freq_c_F2);


        //Подсчет энтропии Шенона
        double shenon_F1 = 0;
        float[] freq_F1 = {freq_a_F1, freq_b_F1, freq_c_F1};
        for (int i = 0; i < M; i++) {
            shenon_F1 += freq_F1[i] * log_2(2, 1 / freq_F1[i]);
        }

        System.out.println(shenon_F1); //Практическая
        //System.out.println(log_2(2, 3)); //Теоретическая

        double shenon_F2 = 0;
        float[] freq_F2 = {freq_a_F2, freq_b_F2, freq_c_F2};
        for (int i = 0; i < M; i++) {
            shenon_F2 += freq_F2[i] * log_2(2, 1 / freq_F2[i]);
        }
        System.out.println(shenon_F2); //Практическая

        double shenon_F3 = 0;
        for(Map.Entry entry: map_prob.entrySet()) {
            shenon_F3 += (Float)entry.getValue() * log_2(2, 1 / (Float)entry.getValue());
        }
        System.out.println(shenon_F3); //Практическая

    }

    private static void shenon_Double() throws IOException {
        FileReader reader_F1 = new FileReader("resource\\file\\F1.txt");
        FileReader reader_F2 = new FileReader("resource\\file\\F2.txt");
        FileReader reader_F3 = new FileReader("resource\\file\\F3.txt");
        Scanner scan = new Scanner(reader_F3);

        int ch;
        int all_F1 = 0, all_F2 = 0, all_F3 = 0;
        HashMap<String, Float> map_F1 = new HashMap<>();
        HashMap<String, Float> map_F2 = new HashMap<>();
        HashMap<String, Float> map_F3 = new HashMap<>();
        HashMap<String, Float> map_F1_prob = new HashMap<>();
        HashMap<String, Float> map_F2_prob = new HashMap<>();
        HashMap<String, Float> map_F3_prob = new HashMap<>();

        //Подсчет вероятностей

        //1
        ch = reader_F1.read();
        String str = String.valueOf((char)ch);
        while ((ch = reader_F1.read()) != -1) {
            str += String.valueOf((char)ch);
            if (!map_F1.containsKey(str)) {
                map_F1.put(str, 1.0f);
            } else {
                float i = map_F1.get(str) + 1;
                map_F1.put(str, i);
            }
            str = String.valueOf((char)ch);
        }

        for(Map.Entry entry: map_F1.entrySet()) {
            all_F1 += (Float)entry.getValue();
        }

        for(Map.Entry entry: map_F1.entrySet()) {
            map_F1_prob.put(entry.getKey().toString(), (Float)entry.getValue() / all_F1);
        }

        //2
        ch = reader_F2.read();
        str = String.valueOf((char)ch);
        while ((ch = reader_F2.read()) != -1) {
            str += String.valueOf((char)ch);
            if (!map_F2.containsKey(str)) {
                map_F2.put(str, 1.0f);
            } else {
                float i = map_F2.get(str) + 1;
                map_F2.put(str, i);
            }
            str = String.valueOf((char)ch);
        }

        for(Map.Entry entry: map_F2.entrySet()) {
            all_F2 += (Float)entry.getValue();
        }

        for(Map.Entry entry: map_F2.entrySet()) {
            map_F2_prob.put(entry.getKey().toString(), (Float)entry.getValue() / all_F2);
        }

        //3
        while (scan.hasNextLine()) {
            str += scan.nextLine();
        }
        str = str.toLowerCase();
        str = str.replaceAll("[^а-яё]", "");
        for (int i = 0; i < str.length() - 1; i++) {
            String temp = str.substring(i , i + 1) + str.substring(i + 1 , i + 2);
            if (!map_F3.containsKey(temp)) {
                map_F3.put(temp, 1.0f);
            } else {
                float j = map_F3.get(temp) + 1;
                map_F3.put(temp, j);
            }
        }

        for(Map.Entry entry: map_F3.entrySet()) {
            all_F3 += (Float)entry.getValue();
        }

        for(Map.Entry entry: map_F3.entrySet()) {
            map_F3_prob.put(entry.getKey().toString(), (Float)entry.getValue() / all_F3);
        }

        //Подсчет энтропии Шенона

        //1
        double shenon_F1 = 0;
        for(Map.Entry entry: map_F1_prob.entrySet()) {
            shenon_F1 += (Float)entry.getValue() * log_2(2, 1 / (Float)entry.getValue());
        }
        System.out.println(shenon_F1 / 2); //Практическая

        //2
        double shenon_F2 = 0;
        for(Map.Entry entry: map_F2_prob.entrySet()) {
            shenon_F2 += (Float)entry.getValue() * log_2(2, 1 / (Float)entry.getValue());
        }
        System.out.println(shenon_F2 / 2); //Практическая

        //3
        double shenon_F3 = 0;
        for(Map.Entry entry: map_F3_prob.entrySet()) {
            shenon_F3 += (Float)entry.getValue() * log_2(2, 1 / (Float)entry.getValue());
        }
        System.out.println(shenon_F3 / 2); //Практическая
    }

    static double log_2 (double base, double number) {
        return Math.log10(number) / Math.log10(base);
    }

}
