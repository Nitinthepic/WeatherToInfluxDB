import com.influxdb.client.InfluxDBClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
	public static void main(String[] args) throws IOException {
		String token = "InfluxDB token";
		String bucket = "Bucket Name Here";
		String org = "InfluxDB organization here";
		String url = "Put InfluxDB address here";
		String location = "Put location here, use underscore instead of spaces";
		URL weatherURL = new URL("Put Weather.gov URL here");
		Timer timer = new Timer();
		InfluxDBConnection inConn = new InfluxDBConnection();


		InfluxDBClient influxDBClient = inConn.buildConnection(url, token,
				bucket, org);
		System.out.println("Weather Grabber initialized!");

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				HttpURLConnection con = null;
				try {
					con = (HttpURLConnection) weatherURL.openConnection();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				try {
					con.setRequestMethod("GET");
				} catch (ProtocolException e) {
					throw new RuntimeException(e);
				}
				BufferedReader in = null;
				try {
					in = new BufferedReader(
							new InputStreamReader(con.getInputStream()));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				String inputLine;
				StringBuilder content = new StringBuilder();
				while (true) {
					try {
						if ((inputLine = in.readLine()) == null) break;
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					content.append(inputLine);
				}
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				inConn.writePointTemperature(influxDBClient, location,
						(content.substring(content.indexOf(
						"temperature")+14,
						content.indexOf("temperature")+16)));

				inConn.writePointWind(influxDBClient, location,
						(content.substring(content.indexOf(
								"\"windSpeed\":")+14,
						content.indexOf("\"windSpeed\":")+15)));

			}
		}, 0, 60 * 1000);

	}
}
