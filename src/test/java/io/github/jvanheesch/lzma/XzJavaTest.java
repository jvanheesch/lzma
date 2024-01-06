package io.github.jvanheesch.lzma;

import org.junit.jupiter.api.Test;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.LZMAInputStream;
import org.tukaani.xz.LZMAOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static io.github.jvanheesch.lzma.Constants.COMPRESSED;
import static io.github.jvanheesch.lzma.Constants.COMPRESSED_LZMA_JS;
import static io.github.jvanheesch.lzma.Constants.DICT_SIZE;
import static io.github.jvanheesch.lzma.Constants.UNCOMPRESSED;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XzJavaTest {

    @Test
    public void xz_java_compress() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        LZMA2Options options = new LZMA2Options();
        options.setDictSize(DICT_SIZE);

        try (LZMAOutputStream compressor = new LZMAOutputStream(out, options, -1)) {
            compressor.write(UNCOMPRESSED.getBytes());
        }

        assertArrayEquals(COMPRESSED, out.toByteArray());
    }

    @Test
    public void xz_java_decompress() throws IOException {
        String decompressed = decompress(COMPRESSED);

        assertEquals(UNCOMPRESSED, decompressed);
    }

    @Test
    public void xz_java_decompress_lzma_js() throws IOException {
        String decompressed = decompress(COMPRESSED_LZMA_JS);

        assertEquals(UNCOMPRESSED, decompressed);
    }

    private static String decompress(byte[] compressed) throws IOException {
        try (LZMAInputStream decompressor = new LZMAInputStream(new ByteArrayInputStream(compressed));) {
            return new String(decompressor.readAllBytes());
        }
    }
}
