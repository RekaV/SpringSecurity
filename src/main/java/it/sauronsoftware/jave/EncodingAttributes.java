/*
 * JAVE - A Java Audio/Video Encoder (based on FFMPEG)
 * 
 * Copyright (C) 2008-2009 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.sauronsoftware.jave;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Attributes controlling the encoding process.
 * 
 * @author Carlo Pelliccia
 */
public class EncodingAttributes implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The format name for the encoded target multimedia file. Be sure this
	 * format is supported (see {@link Encoder#getSupportedEncodingFormats()}.
	 */
	private String format = null;
        
        /*
         * List that maintains custom attributes
         */
        @SuppressWarnings("rawtypes")
		private ArrayList custom=new ArrayList();
        
        /*
         * The variable that implies whether same quality as the source file will be used for the conversion process.
         */
        private boolean sameQuality=false;

	/**
	 * The start offset time (seconds). If null or not specified no start offset
	 * will be applied.
	 */
	private Float offset = null;

	/**
	 * The duration (seconds) of the re-encoded stream. If null or not specified
	 * the source stream, starting from the offset, will be completely
	 * re-encoded in the target stream.
	 */
	private Float duration = null;

	/**
	 * The attributes for the encoding of the audio stream in the target
	 * multimedia file. If null of not specified no audio stream will be
	 * encoded. It cannot be null if also the video field is null.
	 */
	private AudioAttributes audioAttributes = null;

	/**
	 * The attributes for the encoding of the video stream in the target
	 * multimedia file. If null of not specified no video stream will be
	 * encoded. It cannot be null if also the audio field is null.
	 */
	private VideoAttributes videoAttributes = null;

	/**
	 * Returns the format name for the encoded target multimedia file.
	 * 
	 * @return The format name for the encoded target multimedia file.
	 */
	String getFormat() {
		return format;
	}

	/**
	 * Sets the format name for the encoded target multimedia file. Be sure this
	 * format is supported (see {@link Encoder#getSupportedEncodingFormats()}.
	 * 
	 * @param format
	 *            The format name for the encoded target multimedia file.
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * Returns the start offset time (seconds).
	 * 
	 * @return The start offset time (seconds).
	 */
	Float getOffset() {
		return offset;
	}
        
        /*
         * Returns the list of any custom attributes that will be passed onto ffmpeg
         * @return the list containing the custom attributes
         */
        @SuppressWarnings("rawtypes")
		ArrayList getCustomAttributes() {
            return custom;
        }

	/**
	 * Sets the start offset time (seconds). If null or not specified no start
	 * offset will be applied.
	 * 
	 * @param offset
	 *            The start offset time (seconds).
	 */
	public void setOffset(Float offset) {
		this.offset = offset;
	}

	/**
	 * Returns the duration (seconds) of the re-encoded stream.
	 * 
	 * @return The duration (seconds) of the re-encoded stream.
	 */
	Float getDuration() {
		return duration;
	}

	/**
	 * Sets the duration (seconds) of the re-encoded stream. If null or not
	 * specified the source stream, starting from the offset, will be completely
	 * re-encoded in the target stream.
	 * 
	 * @param duration
	 *            The duration (seconds) of the re-encoded stream.
	 */
	public void setDuration(Float duration) {
		this.duration = duration;
	}
        
        /*
         * Sets the quality of the encoding process to match the original file
         * 
         * @param sameQuality true if the quality of conversion will be the same false otherwise
         */
        public void setSameQuality(boolean sameQuality) {
                this.sameQuality=sameQuality;
        }
        
        /*
         * adds a custom attribute to the conversion process
         * @param attribute the custom attribute that you want to pass onto ffmpeg
         */
        @SuppressWarnings("unchecked")
		public void addAttribute(String attribute) {
                custom.add(attribute);
        }

	/**
	 * Returns the attributes for the encoding of the audio stream in the target
	 * multimedia file.
	 * 
	 * @return The attributes for the encoding of the audio stream in the target
	 *         multimedia file.
	 */
	AudioAttributes getAudioAttributes() {
		return audioAttributes;
	}

	/*
         * gets whether the same quality will be used during the conversion process
         * 
         * @return true is same quality wll be used, false otherwise
         */
        boolean getSameQuality() {
            return sameQuality;
        }
        
        /**
	 * Sets the attributes for the encoding of the audio stream in the target
	 * multimedia file. If null of not specified no audio stream will be
	 * encoded. It cannot be null if also the video field is null.
	 * 
	 * @param audioAttributes
	 *            The attributes for the encoding of the audio stream in the
	 *            target multimedia file.
	 */
	public void setAudioAttributes(AudioAttributes audioAttributes) {
		this.audioAttributes = audioAttributes;
	}

	/**
	 * Returns the attributes for the encoding of the video stream in the target
	 * multimedia file.
	 * 
	 * @return The attributes for the encoding of the video stream in the target
	 *         multimedia file.
	 */
	VideoAttributes getVideoAttributes() {
		return videoAttributes;
	}

	/**
	 * Sets the attributes for the encoding of the video stream in the target
	 * multimedia file. If null of not specified no video stream will be
	 * encoded. It cannot be null if also the audio field is null.
	 * 
	 * @param videoAttributes
	 *            The attributes for the encoding of the video stream in the
	 *            target multimedia file.
	 */
	public void setVideoAttributes(VideoAttributes videoAttributes) {
		this.videoAttributes = videoAttributes;
	}

	public String toString() {
		return getClass().getName() + "(format=" + format + ", offset="
				+ offset + ", duration=" + duration + ", audioAttributes="
				+ audioAttributes + ", videoAttributes=" + videoAttributes
				+ ")";
	}

}
