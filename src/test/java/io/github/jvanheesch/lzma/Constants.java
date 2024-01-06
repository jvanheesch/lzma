package io.github.jvanheesch.lzma;

public final class Constants {

    // dict size used by lzma js (= 2^25)
    public static int DICT_SIZE = 33554432;
    public static final String UNCOMPRESSED = "sample";
    // compression result of xz, xz-java and lzma-java
    public static final byte[] COMPRESSED = {93, 0, 0, 0, 2, -1, -1, -1, -1, -1, -1, -1, -1, 0, 57, -104, 73, -2, -17, -28, -15, 86, -9, -33, -1, -3, -111, 16, 0};
    public static final byte[] COMPRESSED_LZMA_JS = {93, 0, 0, 0, 2, 6, 0, 0, 0, 0, 0, 0, 0, 0, 57, -104, 73, -2, -17, -28, -15, 86, -9, -33, -1, -3, -111, 16, 0};
}
