package de.uniba.dsg.serverless.cli.performance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.uniba.dsg.serverless.cli.CustomUtility;
import de.uniba.dsg.serverless.model.SeMoDeException;
import de.uniba.dsg.serverless.provider.google.GoogleLogHandler;

public class GooglePerformanceDataUtility extends CustomUtility {
	
	private static final Logger logger = LogManager.getLogger(GooglePerformanceDataUtility.class.getName());
	
	private final GenericPerformanceDataFetcher fetcher;

	public GooglePerformanceDataUtility(String name) {
		super(name);
		this.fetcher = new GenericPerformanceDataFetcher();
	}

	@Override
	public void start(List<String> args) {
		
		if (args.size() < 3) {
			logger.fatal("Wrong parameter size: \n(1) Function Name"
					+ "\n(2) Start time filter of performance data" + "\n(3) End time filter of performance data"
					+ "\n(4) Optional - REST calls file");
			return;
		}
		
		String functionName = args.get(0);
		String startTimeString = args.get(1);
		String endTimeString = args.get(2);
		
		try {
			
			this.validateStartEnd(startTimeString, endTimeString);
			
			LocalDateTime startTime = this.parseTime(startTimeString);
			LocalDateTime endTime = this.parseTime(endTimeString);

			GoogleLogHandler logHandler = new GoogleLogHandler(functionName, startTime, endTime);
			
			// if a benchmarking file is selected
			Optional<String> restFile;
			if (args.size() == 4) {
				restFile = Optional.of(args.get(3));
			}else {
				restFile = Optional.empty();
			}

			this.fetcher.writePerformanceDataToFile("google", logHandler, functionName, restFile);
			
		} catch (SeMoDeException e) {
			logger.fatal(e.getMessage() + "Cause: " + (e.getCause() == null ? "No further cause!" : e.getCause().getMessage()));
		}
	}
}
