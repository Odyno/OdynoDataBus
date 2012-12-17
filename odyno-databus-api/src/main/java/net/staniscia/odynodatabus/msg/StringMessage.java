package net.staniscia.odynodatabus.msg;

import net.staniscia.odynodatabus.Envelop;

public final class StringMessage implements Envelop<String> {
	
	private static final long serialVersionUID = -6418123090379845471L;
	
	private long timeOfOccurence;
	
	private String treasure;
	
	public StringMessage(String string){
		timeOfOccurence=System.currentTimeMillis();
		this.treasure = string;
	}
	
	public long getTimeOfOccurence() {
		return timeOfOccurence;
	}

	public Class<String> getContentType() {
		return  String.class;
	}

	public String getContent() {
		return treasure;
	}

}
