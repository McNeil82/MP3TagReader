package de.moralis.models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Test {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("No file given.");
            return;
        }
        try {
            ReadFile myFile = new ReadFile(new RandomAccessFile(args[0], "r"));
            System.err.println("ID3-Version:		" + myFile.getVersion());
            System.err.println("Unsynchronisation:	" + myFile.hasUnsynchronisation());
            System.err.println("Extended Header:	" + myFile.hasExtendedHeader());
            System.err.println("Experimental Indicator:	" + myFile.hasExperimentalIndicator());
            System.err.println("Tag Size:		" + myFile.getTagSize() + " bytes");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            RandomAccessFile test = new RandomAccessFile(args[0], "r");
            test.skipBytes(10);
            for (int i = 10; i < 20; i++) {
                int b = test.read();
                System.err.print("'" + b + "'");
                System.err.print(" --- ");
                System.err.println("'" + (char) b + "'");
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
