package minium.visual.internal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;

public class Compressions {

    public static void uncompress(InputStream in, File dest) {
        try {
            GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
            try (TarArchiveInputStream tarIn = new TarArchiveInputStream(gzIn)) {
                TarArchiveEntry entry = null;
                while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {
                    File f = new File(dest, entry.getName());
                    if (entry.isDirectory()) {
                        f.mkdirs();
                    } else {
                        f.getParentFile().mkdirs();
                        try (FileOutputStream destFile = new FileOutputStream(f)) {
                            ByteStreams.copy(tarIn, destFile);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

}