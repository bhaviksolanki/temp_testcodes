package com.ms.datawise.distn.constants;

public enum Modes {
	// FTP, COMET, fileshare, email
	DATASET_REQUEST_TYPE ("requestType"), DATA_SETUP_REQUEST ("setupRequest"),
	DATA DISTRIBUTION_REQUEST ("distributionRequest"), 
	DATA DISTRIBUTIONV2_REQUEST ("distributionV2Request"), 
	DATA_DISTRIBUTION_COMET_REQUEST ("cometRequest"),
	DATA_DISTRIBUTION_INTERNAL_FILEPUSH_REQUEST ("file PushRequest"), 
	DATA_DISTRIBUTION_INTERNAL_EVENTPUSH_REQUEST ("event PushRequest"), 
	DATA_DISTRIBUTION_INTERNAL_TRIGGERPUSH_REQUEST ("trigger PushRequest"), 
	DATA_DISTRIBUTION_INTERNAL_DUALPUSH_REQUEST ("dualPushRequest"),
	DATA_DISTRIBUTION_PILOT_REQUEST ("pilotRequest"),
	DATA_DISTRIBUTION_FILEPUSH_REQUEST ("file PushRequest"),
	DATA_DISTRIBUTION_TARGET_COMET ("comet"),
	DATA_DISTRIBUTION_START_WINDOW ("startWindow"),
	DATA_DISTRIBUTION_HEART_BEAT ("heartBeat"),
	DATA_DISTRIBUTION_PRECISE_SLA ("PRECISE_SLA"), 
	DATA_DISTRIBUTION_EMAIL_REQUEST ("emailRequest"),;
	
	private final String text;
	Modes (final String text) {
		this.text text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}