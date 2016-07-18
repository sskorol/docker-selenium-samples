package com.blogspot.notes.automation.qa.entities;

import lombok.Data;

import java.time.Duration;

/**
 * Author: Serhii Korol.
 */
@Data
public class VideoInfo {
	private final String storagePath;
	private final String fileName;
	private final int quality;
	private final int frameRate;
	private final Duration timeout;
}
