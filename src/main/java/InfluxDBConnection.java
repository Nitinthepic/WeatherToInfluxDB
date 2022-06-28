import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.exceptions.InfluxException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class InfluxDBConnection {
	private String token;
	private String bucket;
	private String org;
	private String url;

	public InfluxDBClient buildConnection(String url, String token, String bucket, String org) {
		setToken(token);
		setOrg(org);
		setBucket(bucket);
		setUrl(url);

		return InfluxDBClientFactory.create(getUrl(), getToken().toCharArray(), getOrg(), getBucket());
	}

	public boolean writePoint(InfluxDBClient influxDBClient) {
		boolean flag = false;
		try {
			WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

			writeApi.writeRecord(WritePrecision.NS, "temperature,location=SR " + "value=62.0");
			flag = true;
		} catch (InfluxException e) {
			System.out.println("Exception!!" + e.getMessage());
		}
		return flag;
	}

	public boolean writePointTemperature(InfluxDBClient influxDBClient, String location, String temperature) {
		boolean flag = false;
		try {
			WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

			writeApi.writeRecord(WritePrecision.NS, "temperature,location=" + location + " " + "value=" + temperature);
			flag = true;
		} catch (InfluxException e) {
			System.out.println("Exception!!" + e.getMessage());
		}
		return flag;
	}

	public boolean writePointWind(InfluxDBClient influxDBClient, String location, String wind) {
		boolean flag = false;
		try {
			WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

			writeApi.writeRecord(WritePrecision.NS, "wind,location=" + location + " " + "value=" + wind);
			flag = true;
		} catch (InfluxException e) {
			System.out.println("Exception!!" + e.getMessage());
		}
		return flag;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Measurement(name = "temperature")
	private static class Temperature {

		@Column(tag = true)
		String location;

		@Column
		Double value;

		@Column(timestamp = true)
		Instant time;
	}

	@Measurement(name = "wind")
	private static class Wind {

		@Column(tag = true)
		String location;

		@Column
		Double value;

		@Column(timestamp = true)
		Instant time;
	}
}
