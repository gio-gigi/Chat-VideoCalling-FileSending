package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class Zip {
    public static byte[] compress(byte[] in) {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                DeflaterOutputStream defl = new DeflaterOutputStream(out);
                defl.write(in);
                defl.flush();
                defl.close();

                return out.toByteArray();
            } catch (IOException e) {
                System.exit(150);
             return null;
            }
        }

        public static byte[] decompress(byte[] in) {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                InflaterOutputStream infl = new InflaterOutputStream(out);
                infl.write(in);
                infl.flush();
                infl.close();

                return out.toByteArray();
            } catch (IOException e) {
                System.exit(150);
                return null;
            }
        }
   
}