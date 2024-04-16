package com.ghostchu.peerbanhelper.logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class PBHLogger {
    public static LinkedBlockingDeque<String> BUFFER = new LinkedBlockingDeque<>();

    public static void install() {
        PrintStream stdOut = System.out;
        java.io.ByteArrayOutputStream out = new BAOS(stdOut);
        System.setOut(new java.io.PrintStream(out));
    }

    public static class BAOS extends ByteArrayOutputStream {
        private final PrintStream std;

        public BAOS(PrintStream std) {
            this.std = std;
        }

        @Override
        public void flush() throws IOException {
            this.std.writeBytes(buf);
            this.std.println("FLUSh");
            Arrays.fill(buf, (byte) 0);
            super.flush();
            this.std.flush();
        }
    }

}
