package com.ms.datawise.distn.constants;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventLogConst {
	public static final Map<String, String> DistEventMap Stream. of ( new SimpleEntry<>("3001", "Request validation"),
		new SimpleEntry<>("3002", "Requested product check"),
		new SimpleEntry<>("3003", "Check active distributions"),
		new SimpleEntry<>("3004", "History distribution validation"),
		new SimpleEntry<>("3005", "Check comet job id"),
		new SimpleEntry<>("3006", "Determine product checker category"), new SimpleEntry<>("3007", "File Checker"),
		new SimpleEntry<>("3008", "As of date cheeck"),
		new SimpleEntry<>("3009", "Ingestion metadata check"),
		new SimpleEntry<>("3010", "Ingestion metadata response"),
		new SimpleEntry<>("3011", "Ingestion metadata save"),
		new SimpleEntry<>("3012", "Ingestion get file"),
		new SimpleEntry<>("3013", "Populate message queue with subrequest"), new SimpleEntry<>("3014", "Distribution failure"),
		new SimpleEntry<>("3015", "Distribution request"),
		new SimpleEntry<>("3016", "Window completion"), new SimpleEntry<>("3017", "Window timeover"),
		new SimpleEntry<>("3018", "Comet request"),
		new SimpleEntry<>("3019", "File check for internal distribution"), new SimpleEntry<>("3020", "File copy for internal distribution"),
		new SimpleEntry<>("3021", "Metadata copy for internal distribution"),
		new SimpleEntry<>("3022", "Autosys for internal distribution"),
		new SimpleEntry<>("3023", "Pilot request completion"),
		new SimpleEntry<>("3024", "Populate message queue with sourceDirectRequest"),
		new SimpleEntry<>("4012", "Ingestion download file"),
		new SimpleEntry<>("3025", "Email request completion")).collect (Collectors.toMap (SimpleEntry::getKey, SimpleEntry::getValue));
}