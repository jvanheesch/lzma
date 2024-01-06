package io.github.jvanheesch.lzma;

import lzma.sdk.lzma.Decoder;
import lzma.sdk.lzma.Encoder;
import lzma.streams.LzmaInputStream;
import lzma.streams.LzmaOutputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static io.github.jvanheesch.lzma.Constants.COMPRESSED;
import static io.github.jvanheesch.lzma.Constants.COMPRESSED_LZMA_JS;
import static io.github.jvanheesch.lzma.Constants.DICT_SIZE;
import static io.github.jvanheesch.lzma.Constants.UNCOMPRESSED;
import static lzma.sdk.lzma.Encoder.EMatchFinderTypeBT4;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LzmaJavaTest {

    @Test
    public void lzma_java_compress() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Encoder encoder = new Encoder();

        encoder.setDictionarySize(DICT_SIZE);
        encoder.setEndMarkerMode(true);
        encoder.setMatchFinder(EMatchFinderTypeBT4);
        encoder.setNumFastBytes(255);

        try (LzmaOutputStream compressor = new LzmaOutputStream(out, encoder)) {
            compressor.write(UNCOMPRESSED.getBytes());
        }

        assertArrayEquals(COMPRESSED, out.toByteArray());
    }

    @Test
    public void lzma_java_decompress() throws IOException {
        String decompressed = decompress(COMPRESSED);

        assertEquals(UNCOMPRESSED, decompressed);
    }

    @Test
    public void lzma_java_decompress_lzma_js() throws IOException {
        String decompressed = decompress(COMPRESSED_LZMA_JS);

        assertEquals(UNCOMPRESSED, decompressed);
    }

    private static String decompress(byte[] compressed) throws IOException {
        try (
                LzmaInputStream decompressor = new LzmaInputStream(
                        new ByteArrayInputStream(compressed),
                        new Decoder()
                )
        ) {
            return new String(decompressor.readAllBytes());
        }
    }
}
