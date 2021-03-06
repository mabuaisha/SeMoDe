package de.uniba.dsg.serverless.benchmark;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.uniba.dsg.serverless.cli.BenchmarkUtility;
import de.uniba.dsg.serverless.model.SeMoDeException;

public class BenchmarkConcurrentTest extends BenchmarkUtilityTest{

	/**
	 * Tests the overall execution, if invocation of the benchmarking works in
	 * general.
	 * 
	 * @throws SeMoDeException
	 * @throws URISyntaxException
	 */
	@Test
	public void simpleConcurrentTest() throws SeMoDeException, URISyntaxException {
		List<String> args = Arrays.asList(this.url,
					this.jsonInput, "concurrent", "15", "15");
		BenchmarkUtility utility = new BenchmarkUtility("benchmark");
		int failedRequests = utility.executeBenchmark(args);

		Assert.assertEquals(failedRequests, 0);
	}
}
