/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.config.monitor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.util.LinkedMultiValueMap;

/**
 * @author Dave Syer
 *
 */
public class PropertyPathEndpointTests {

	private PropertyPathEndpoint endpoint = new PropertyPathEndpoint(
			new CompositePropertyPathNotificationExtractor(
					Collections.<PropertyPathNotificationExtractor> emptyList()));

	@Before
	public void init() {
		StaticApplicationContext publisher = new StaticApplicationContext();
		this.endpoint.setApplicationEventPublisher(publisher);
		publisher.refresh();
	}

	@Test
	public void testNotifyByForm() throws Exception {
		assertEquals(0,
				this.endpoint.notifyByForm(new LinkedMultiValueMap<String, String>(),
						new ArrayList<String>()).size());
	}

	@Test
	public void testNotifySeveral() throws Exception {
		List<String> request = new ArrayList<String>();
		request.add("/foo/bar.properties");
		request.add("/application.properties");
		assertEquals("[bar, *]",
				this.endpoint
						.notifyByForm(new LinkedMultiValueMap<String, String>(), request)
						.toString());
	}

	@Test
	public void testNotifyAll() throws Exception {
		assertEquals("[*]", this.endpoint
				.notifyByPath(new LinkedMultiValueMap<String, String>(), Collections
						.<String, Object> singletonMap("path", "application.yml"))
				.toString());
	}
}
