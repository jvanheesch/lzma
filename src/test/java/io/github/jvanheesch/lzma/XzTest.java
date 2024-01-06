package io.github.jvanheesch.lzma;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.github.jvanheesch.lzma.Constants.COMPRESSED;
import static io.github.jvanheesch.lzma.Constants.COMPRESSED_LZMA_JS;
import static io.github.jvanheesch.lzma.Constants.DICT_SIZE;
import static io.github.jvanheesch.lzma.Constants.UNCOMPRESSED;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XzTest {

    @Test
    public void xz_compress() throws IOException {
        String[] command = {
                "bash", "-c",
                String.format("echo -n %s | xz -z -c --format=lzma --lzma1=dict=%s | xxd -p -c 1000000", UNCOMPRESSED, DICT_SIZE)
        };

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        InputStream inputStream = process.getInputStream();
        assertEquals(bytesToHex(COMPRESSED), new String(inputStream.readAllBytes()).trim());
    }

    @Test
    public void xz_decompress() throws IOException {
        String decompressed = decompress(COMPRESSED);

        assertEquals(UNCOMPRESSED, decompressed);
    }

    @Test
    public void xz_decompress_lzma_js() throws IOException {
        String decompressed = decompress(COMPRESSED_LZMA_JS);

        assertEquals(UNCOMPRESSED, decompressed);
    }

    private static String decompress(byte[] compressed) throws IOException {
        String[] command = {
                "bash", "-c",
                "echo -n " + bytesToHex(compressed) + " | xxd -r -p | xz -d -c"
        };

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        InputStream inputStream = process.getInputStream();
        return new String(inputStream.readAllBytes());
    }

    // ChatGPT
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
