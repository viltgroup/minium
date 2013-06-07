/*
 * Copyright (C) 2013 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vilt.minium.speech.impl;

import static java.lang.String.format;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javazoom.jl.player.Player;

import com.google.common.collect.Lists;

public class ServerSideGoogleSpeechInteraction extends GoogleSpeechInteraction {

	private static final String TEXT_TO_SPEECH_SERVICE = "http://translate.google.com/translate_tts";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:11.0) Gecko/20100101 Firefox/11.0";
	private static final int LINE_MAX_SIZE = 100;

	private static ExecutorService service = Executors.newSingleThreadExecutor();

	private List<Future<Void>> operations = Lists.newArrayList();

	public ServerSideGoogleSpeechInteraction(Locale locale, String text) {
		super(locale, text);
	}

	@Override
	public void waitToPerform() {
		ensureOperationsFinished();
	}
	
	@Override
	protected void doPerform() {
		try {
			List<String> sentences = splitText();
			
			List<Future<InputStream>> streams = getSentenceStreams(sentences);
			
			for (Future<InputStream> stream : streams) {
				asyncPlay(stream);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public boolean isComplete() {
		for (Future<Void> operation : operations) {
			if (!operation.isDone()) return false;
		}
		operations.clear();
		return true;
	}

	public void waitUntilCompleted() {
		ensureOperationsFinished();
	}

	private List<Future<InputStream>> getSentenceStreams(List<String> sentences) {
		List<Future<InputStream>> streams = Lists.newArrayList();
		for (final String sentence : sentences) {
			streams.add(service.submit(new Callable<InputStream>() {
				
				public InputStream call() throws Exception {
					String encodedSentence = URLEncoder.encode(sentence, "utf-8");
					
					// Create url based on input params
					String strUrl = format("%s?ie=UTF-8&tl=%s&q=%s", TEXT_TO_SPEECH_SERVICE, locale, encodedSentence);
					URL url = new URL(strUrl);
					
					// Establish connection
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					// Get method
					connection.setRequestMethod("GET");
					// Set User-Agent to "mimic" the behavior of a web browser. In this
					// example, I used my browser's info
					connection.addRequestProperty("User-Agent", USER_AGENT);
					connection.connect();
					
					// Get content
					return new BufferedInputStream(connection.getInputStream());
				}
			}));
		}
		
		return streams;
	}

	protected void asyncPlay(final Future<InputStream> stream) {
		Future<Void> operation = service.submit(new Callable<Void>() {
	
			public Void call() throws Exception {
				// code snippet from:
				// http://nxhoaf.wordpress.com/2013/01/05/simple-text-to-speech-in-java-based-on-google-translate-api/
				Player player = new Player(stream.get());
				player.play();
	
				return null;
			}
		});
		operations.add(operation);
	}

	private void ensureOperationsFinished() {
		for (Future<Void> operation : operations) {
			try {
				operation.get();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			} catch (ExecutionException e) {
				// what can we do...
			}
		}
		operations.clear();
	}

	private List<String> splitText() {
	    String[] sentences = text.split("[!?,\\.:;\\t\\n\\r]");
	    List<String> results = Lists.newArrayList();
	    
	    for (String sentence : sentences) {
	    	sentence = sentence.trim();
			while (sentence.length() >= LINE_MAX_SIZE) {
	    		int indexOf = sentence.substring(0, LINE_MAX_SIZE).lastIndexOf(' ');
	    		if (indexOf < 0) {
	    			throw new IllegalArgumentException("Text has too long words...");
	    		}
	    		results.add(sentence.substring(0, indexOf).trim());
	    		sentence = sentence.substring(indexOf).trim();
	    	}
			results.add(sentence);
		}
	    return results;
	}

}
