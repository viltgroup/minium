package com.vilt.minium.recorder.impl;

import static java.nio.ByteOrder.LITTLE_ENDIAN;
import static org.monte.media.AudioFormatKeys.ByteOrderKey;
import static org.monte.media.AudioFormatKeys.ChannelsKey;
import static org.monte.media.AudioFormatKeys.ENCODING_PCM_SIGNED;
import static org.monte.media.AudioFormatKeys.SampleRateKey;
import static org.monte.media.AudioFormatKeys.SampleSizeInBitsKey;
import static org.monte.media.AudioFormatKeys.SignedKey;
import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.FormatKeys.MediaType.AUDIO;
import static org.monte.media.FormatKeys.MediaType.FILE;
import static org.monte.media.FormatKeys.MediaType.VIDEO;
import static org.monte.media.VideoFormatKeys.COMPRESSOR_NAME_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DataClassKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.FixedFrameRateKey;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.AWTException;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.VideoFormatKeys;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import com.vilt.minium.WebElementsDriver;

/**
 * Uses virtual audio capture device:
 * http://sourceforge.net/projects/virtualaudiodev/
 * 
 * @author rui.figueira
 */
public class WebElementsScreenRecorder extends ScreenRecorder {
	
	private File movieFile;

	public WebElementsScreenRecorder() throws HeadlessException, IOException, AWTException {
		this(null);
	}
	
	public void start(File file) throws IOException {
		movieFile = file;
		super.start();
	}
	
	@Override
	public void stop() throws IOException {
		super.stop();
	}

	@Override
	protected File createMovieFile(Format fileFormat) throws IOException {
		if (movieFile == null) {
			return super.createMovieFile(fileFormat);			
		}
		return movieFile;
	}
	
	public WebElementsScreenRecorder(WebElementsDriver<?> wd) throws HeadlessException, IOException, AWTException {
		super(
				GraphicsEnvironment
					.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice()
					.getDefaultConfiguration(), 
				getWebDriverRectangle(wd),
				new Format(MediaTypeKey, FILE, MimeTypeKey, MIME_AVI), 
				new Format(
						MediaTypeKey, VIDEO, 
						EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, 
						CompressorNameKey, COMPRESSOR_NAME_AVI_TECHSMITH_SCREEN_CAPTURE, 
						DepthKey, (int) 24, 
						FrameRateKey, Rational.valueOf(15), 
						QualityKey, 1.0f, 
						KeyFrameIntervalKey, (int) (15 * 60),
						DataClassKey, byte[].class,
						FixedFrameRateKey, true), 
				new Format(
						MediaTypeKey, MediaType.VIDEO, 
						EncodingKey, "black", 
						FrameRateKey, Rational.valueOf(30)),
				new Format(
						VideoFormatKeys.MediaTypeKey, AUDIO, 
						VideoFormatKeys.EncodingKey, ENCODING_PCM_SIGNED, 
						VideoFormatKeys.FrameRateKey, new Rational(44100L, 1L), 
						SampleSizeInBitsKey, Integer.valueOf(16), 
						ChannelsKey, Integer.valueOf(2), 
						SampleRateKey, new Rational(44100L, 1L), 
						SignedKey, Boolean.valueOf(true), 
						ByteOrderKey, LITTLE_ENDIAN),
				null);
	}

	private static Rectangle getWebDriverRectangle(WebElementsDriver<?> wd) {
		if (wd == null) return null;

		Dimension size = wd.manage().window().getSize();
		Point position = wd.manage().window().getPosition();
		return new Rectangle(position.x, position.y, size.width, size.height);
	}
}