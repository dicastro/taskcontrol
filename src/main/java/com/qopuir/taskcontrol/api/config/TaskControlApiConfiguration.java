package com.qopuir.taskcontrol.api.config;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class TaskControlApiConfiguration extends ResourceConfig {
	public TaskControlApiConfiguration() {
		register(RequestContextFilter.class);
		
		// Register JacksonJaxbJsonProvider
		register(new Feature() {
			@Override
		    public boolean configure(final FeatureContext context) {
		        final String disableMoxy = CommonProperties.MOXY_JSON_FEATURE_DISABLE + '.' + context.getConfiguration().getRuntimeType().name().toLowerCase();
		        context.property(disableMoxy, true);

		        context.register(JacksonJaxbJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
		        return true;
		    }
		});
		
		packages(false, "com.qopuir.taskcontrol.api.rest.exceptions");
		
		packages(false, "com.qopuir.taskcontrol.api.rest");
	}
}