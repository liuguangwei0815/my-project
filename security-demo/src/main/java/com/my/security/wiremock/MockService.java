/**
 * 
 */
package com.my.security.wiremock;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import com.github.tomakehurst.wiremock.client.WireMock;

/**
 * @author Administrator
 *
 */
public class MockService {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {

		WireMock.configureFor(7018);
		WireMock.removeAllMappings();
		mock("a.txt", "/user/1");

	}

	private static void mock(String filename, String url) {
		try {
			ClassPathResource res = new ClassPathResource("mock/response/" + filename);
			String content = StringUtils.join(FileUtils.readLines(res.getFile(), "UTF-8"), "\n");
			WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
					.willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
