package de.uniba.dsg.serverless.cli;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.uniba.dsg.serverless.azure.AzureLogHandler;
import de.uniba.dsg.serverless.model.SeMoDeException;
import de.uniba.dsg.serverless.model.WritableEvent;
import de.uniba.dsg.serverless.util.BenchmarkingRESTAnalyzer;

public final class AzurePerformanceDataUtility extends CustomUtility {

	private static final Logger logger = LogManager.getLogger(AzurePerformanceDataUtility.class.getName());

	public AzurePerformanceDataUtility(String name) {
		super(name);
	}

	public void start(List<String> args) {

		if (args.size() < 5) {
			logger.fatal("Wrong parameter size: " + "\n(1) Application ID " + "\n(2) API Key " + "\n(3) Function Name"
					+ "\n(4) Start time filter of performance data" + "\n(5) End time filter of performance data"
					+ "\n(6) Optional - REST calls file");
			return;
		}

		String applicationID = args.get(0);
		String apiKey = args.get(1);
		String functionName = args.get(2);
		String startTimeString = args.get(3);
		String endTimeString = args.get(4);

		try {
			this.validateStartEnd(startTimeString, endTimeString);

			LocalDateTime startTime = this.parseTime(startTimeString);
			LocalDateTime endTime = this.parseTime(endTimeString);

			String fileName = this.generateFileName(functionName);

			List<Map<String, WritableEvent>> elementList = new ArrayList<>();

			AzureLogHandler azureLogHandler = new AzureLogHandler(applicationID, apiKey, functionName, startTime,
					endTime);
			
			elementList.add(azureLogHandler.getPerformanceData());

			// if a benchmarking file is selected
			if (args.size() == 6) {
				BenchmarkingRESTAnalyzer restAnalyzer = new BenchmarkingRESTAnalyzer(Paths.get(args.get(5)));
				elementList.add(restAnalyzer.extractRESTEvents());
			}

			azureLogHandler.writePerformanceDataToFile(fileName, elementList);
		} catch (SeMoDeException e) {
			logger.fatal(e.getMessage());
		}
	}
}
