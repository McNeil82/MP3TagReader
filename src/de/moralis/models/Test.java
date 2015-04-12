package de.moralis.models;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Test {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("No file given.");
            return;
        }

        int totalTagSize = 0;
        try {
            ReadFile myFile = new ReadFile(new RandomAccessFile(args[0], "r"));
            totalTagSize = myFile.getTotalTagSize();

            System.err.println("ID3-Version: " + myFile.getVersion());
            System.err.println("Unsynchronisation: " + myFile.hasUnsynchronisation());
            System.err.println("Extended Header: " + myFile.hasExtendedHeader());
            System.err.println("Experimental Indicator:	" + myFile.hasExperimentalIndicator());
            System.err.println("Total Tag Size (Includes Header): " + totalTagSize + " bytes");
            System.err.println("Frames: " + myFile.getFrames());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.err.println(" ----Tag Bytes (+ 10)---- ");
            RandomAccessFile test = new RandomAccessFile(args[0], "r");
            for (int i = 1; i <= totalTagSize + 10; i++) {
                int b = test.read();
                System.err.print("Byte " + i);
                System.err.print(" --- ");
                System.err.print("'" + b + "'");
                System.err.print(" --- ");
                System.err.println("'" + (char) b + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
