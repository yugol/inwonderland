package aibroker.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class FileUtil {

    public static final String        CSV_EXTENSION      = "csv";
    public static final String        QDB_EXTENSION      = "qdb";
    public static final String        ZIP_EXTENSION      = "zip";

    private static final List<String> FAT_RESERVED_WORDS = new ArrayList<String>();

    static {
        FAT_RESERVED_WORDS.add("CON");
        FAT_RESERVED_WORDS.add("PRN");
        FAT_RESERVED_WORDS.add("AUX");
        FAT_RESERVED_WORDS.add("CLOCK$");
        FAT_RESERVED_WORDS.add("NUL");
        FAT_RESERVED_WORDS.add("COM0");
        FAT_RESERVED_WORDS.add("COM1");
        FAT_RESERVED_WORDS.add("COM2");
        FAT_RESERVED_WORDS.add("COM3");
        FAT_RESERVED_WORDS.add("COM4");
        FAT_RESERVED_WORDS.add("COM5");
        FAT_RESERVED_WORDS.add("COM6");
        FAT_RESERVED_WORDS.add("COM7");
        FAT_RESERVED_WORDS.add("COM8");
        FAT_RESERVED_WORDS.add("COM9");
        FAT_RESERVED_WORDS.add("LPT0");
        FAT_RESERVED_WORDS.add("LPT1");
        FAT_RESERVED_WORDS.add("LPT2");
        FAT_RESERVED_WORDS.add("LPT3");
        FAT_RESERVED_WORDS.add("LPT4");
        FAT_RESERVED_WORDS.add("LPT5");
        FAT_RESERVED_WORDS.add("LPT6");
        FAT_RESERVED_WORDS.add("LPT7");
        FAT_RESERVED_WORDS.add("LPT8");
        FAT_RESERVED_WORDS.add("LPT9");
        FAT_RESERVED_WORDS.add(".");
        FAT_RESERVED_WORDS.add("..");
    }

    public static String getExtension(final File importFile) {
        return getExtension(importFile.getAbsolutePath());
    }

    public static String getExtension(final String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    public static String getFileNameWithoutExtension(final File file) {
        final String fileName = file.getName();
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public static boolean isReserved(final String fileName) {
        return FAT_RESERVED_WORDS.contains(fileName.toUpperCase());
    }

    public static byte[] readBytes(final File file) {
        final byte[] data = new byte[(int) file.length()];
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            fin.read(data);
        } catch (final IOException e) {
            throw new BrokerException(e);
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static byte[] readBytes(final String path) {
        final File file = new File(path);
        return readBytes(file);
    }

    public static void writeBytes(final File file, final byte[] data) {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(file);
            fout.write(data);
        } catch (final IOException e) {
            throw new BrokerException(e);
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void zipFile(final File file, final File zipFile) throws IOException {
        final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
        zip(file, zos);
        zos.close();
    }

    public static void zipFolder(final File folder, final File zipFile) throws IOException {
        final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
        zip(folder, folder, zos);
        zos.close();
    }

    private static void zip(final File folder, final File base, final ZipOutputStream zos) throws IOException {
        final File[] files = folder.listFiles();
        final byte[] buffer = new byte[8192];
        int read = 0;
        for (int i = 0, n = files.length; i < n; i++) {
            if (files[i].isDirectory()) {
                zip(files[i], base, zos);
            } else {
                final FileInputStream in = new FileInputStream(files[i]);
                final ZipEntry entry = new ZipEntry(files[i].getPath().substring(base.getPath().length() + 1));
                zos.putNextEntry(entry);
                while (-1 != (read = in.read(buffer))) {
                    zos.write(buffer, 0, read);
                }
                in.close();
            }
        }
    }

    private static void zip(final File file, final ZipOutputStream zos) throws IOException {
        final byte[] buffer = new byte[8192];
        int read = 0;
        final FileInputStream in = new FileInputStream(file);
        final ZipEntry entry = new ZipEntry(file.getName());
        zos.putNextEntry(entry);
        while (-1 != (read = in.read(buffer))) {
            zos.write(buffer, 0, read);
        }
        in.close();
    }

}
