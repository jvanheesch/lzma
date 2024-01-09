This repository contains a reproducer for the following issue: https://github.com/tukaani-project/xz-java/issues/8

# Problem

Decompressing the
value `{93, 0, 0, 0, 2, 6, 0, 0, 0, 0, 0, 0, 0, 0, 57, -104, 73, -2, -17, -28, -15, 86, -9, -33, -1, -3, -111, 16, 0}` -
which is the result of compressing the string `"sample"` using lzma-js, as
demonstrated [here](https://playcode.io/1718777) - results
in `CorruptedInputException` (`XzJavaTest.xz_java_decompress_lzma_js()`), while decompression with
xz (`XzTest.xz_decompress_lzma_js()`) works flawlessly (as does decompression with lzma-java and lzma-js).

Reproducer: https://github.com/jvanheesch/lzma

# Summary of tests

| Compressed by ↓ \ Decompressed by → | xz | xz-java | lzma-java | lzma-js (*) |
|-------------------------------------|----|---------|-----------|-------------|
| xz == xz-java == lzma-java          | ✓  | ✓       | ✓         | ✓           |
| lzma-js                             | ✓  | ✗ (!)   | ✓         | ✓           |

(*) https://playcode.io/1718777k
