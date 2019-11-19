package de.uniba.dsg.serverless.calibration;

import de.uniba.dsg.serverless.model.SeMoDeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Class to only parse the Linpack results locally and on providers' platfrom.
 * See {@link #parseLinpack()}.
 * @author mendress
 */
public class LinpackParser {
    public static final String BENCHMARK_NAME = "benchmark.json";

    private final Path linpackCalibrationPath;

    public LinpackParser(Path linpackCalibrationPath) {
        this.linpackCalibrationPath = linpackCalibrationPath;
    }

    /**
     * Parses the Linpack benchmark/calibration result due to the fixed structure of the Linpack output written to the
     * logFiles.
     *
     * @return the average GFLOPS performance for 10000 equations to solve with an array of 25000 leading dimensions.
     * @throws SeMoDeException if the file can't be read.
     */
    public double parseLinpack() throws SeMoDeException {
        if (!Files.exists(linpackCalibrationPath)) {
            throw new SeMoDeException("Calibration benchmark file does not exist.");
        }
        try {
            List<String> lines = Files.readAllLines(linpackCalibrationPath);
            String[] results = lines.get(lines.size() - 7).split("\\s+");
            return Double.parseDouble(results[3]);
        } catch (IOException e) {
            throw new SeMoDeException("Could not read file. ", e);
        }
    }

}
