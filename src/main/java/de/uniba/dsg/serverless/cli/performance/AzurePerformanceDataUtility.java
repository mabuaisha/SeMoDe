package de.uniba.dsg.serverless.cli.performance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.uniba.dsg.serverless.cli.CustomUtility;
import de.uniba.dsg.serverless.model.SeMoDeException;
import de.uniba.dsg.serverless.provider.azure.AzureLogHandler;

public final class AzurePerformanceDataUtility extends CustomUtility {

	private static final Logger logger = LogManager.getLogger(AzurePerformanceDataUtility.class.getName());

	private final GenericPerformanceDataFetcher fetcher;
	
	public AzurePerformanceDataUtility(String name) {
		super(name);
		this.fetcher = new GenericPerformanceDataFetcher();
	}

	public void start(List<String> args) {

		if (args.size() < 6) {
			logger.fatal("Wrong parameter size: " + "\n(1) Application ID " + "\n(2) API Key " + "\n(3) Service Name"
					+ "\n(4) Function Name" + "\n(5) Start time filter of performance data"
					+ "\n(6) End time filter of performance data" + "\n(7) Optional - REST calls file");
			return;
		}

		String applicationID = args.get(0);
		String apiKey = args.get(1);
		String serviceName = args.get(2);
		String functionName = args.get(3);
		String startTimeString = args.get(4);
		String endTimeString = args.get(5);

		try {
			this.validateStartEnd(startTimeString, endTimeString);
			LocalDateTime startTime = this.parseTime(startTimeString);
			LocalDateTime endTime = this.parseTime(endTimeString);

			AzureLogHandler azureLogHandler = new AzureLogHandler(applicationID, apiKey, functionName, startTime,
					endTime);

			Optional<String> restFile;
			if (args.size() == 7) {
				restFile = Optional.of(args.get(6));
			} else {
				restFile = Optional.empty();
			}

			this.fetcher.writePerformanceDataToFile("azure", azureLogHandler, serviceName, restFile);
		} catch (SeMoDeException e) {
			logger.fatal(e.getMessage() + "Cause: " + (e.getCause() == null ? "No further cause!" : e.getCause().getMessage()));
		}
	}
}
