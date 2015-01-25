package minium.visual.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.sikuli.basics.FileManager;
import org.sikuli.basics.Settings;
import org.sikuli.natives.Vision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

public class SikuliInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SikuliInitializer.class);

    public static void init() {
        File tessdataDir = maybeExtractTesseractData();
        String tessdataPath = FileManager.slashify(tessdataDir.getAbsolutePath(), true);
        LOGGER.info("Tessdata base directory is {}", tessdataPath);
        Settings.OcrDataPath = tessdataPath;
        Settings.OcrTextRead = true;
        Settings.OcrTextSearch = true;
        Settings.ActionLogs = false;
        Settings.ClickDelay = 0;
        Settings.MoveMouseDelay = 0;
        initOcr(tessdataPath);
    }

    private static File maybeExtractTesseractData() {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        File tessdataDir = new File(tmpDir, "tesseract-ocr");
        File trainedDataFile = new File(tessdataDir, "tessdata/eng.traineddata");
        if (!(trainedDataFile.exists() && trainedDataFile.isFile())) {
            try (InputStream tesseractData = SikuliInitializer.class.getClassLoader().getResourceAsStream("lib/tesseract-ocr-3.02.eng.tar.gz")) {
                LOGGER.debug("Uncompressing to {}", tessdataDir);
                Compressions.uncompress(tesseractData, tmpDir);
            } catch (IOException e) {
                throw Throwables.propagate(e);
            }
        }
        return tessdataDir;
    }

    private static void initOcr(String tessdataPath) {
        FileManager.loadLibrary("VisionProxy");
        Vision.initOCR(tessdataPath);
    }
}
