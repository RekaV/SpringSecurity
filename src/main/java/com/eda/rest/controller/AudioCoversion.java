package com.eda.rest.controller;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import java.io.File;

import org.apache.log4j.Logger;


public class AudioCoversion {

	private static Logger logger = Logger.getLogger(AudioCoversion.class);
	
	static File convert(File saveFile,String fileName)  {
	
	
		final String SAVE_DIR = System.getProperty("catalina.home")
				+ "/webapps/test/";
		
		int pos=fileName.indexOf(".");
		String name=fileName.substring(0, pos);
		
		File source = saveFile;//new File("D:\\xampp\\tomcat\\webapps\\test\\4.amr");
		File target = new File(SAVE_DIR+name+".mp3");
		try
		{
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(128000));
		audio.setChannels(new Integer(1));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder();
		encoder.encode(source, target, attrs);	
		
		source.delete();
	
		
	}catch(IllegalArgumentException | EncoderException e)
	{
		logger.error("Audio Conversion::"+e);
	}
	
	return target;
}
	
}

