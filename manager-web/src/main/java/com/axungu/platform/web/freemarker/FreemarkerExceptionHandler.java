package com.axungu.platform.web.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Writer;

/**
 * @author jtoms
 * @date 16-11-12
 * @description:
 */

public class FreemarkerExceptionHandler implements TemplateExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(FreemarkerExceptionHandler.class);

	@Override
	public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
		try {
			out.write("[Freemarker Error: " + te.getMessage() + "]");
			log.warn("Freemarker Error: ", te);
		} catch (Exception e) {
			log.warn(e.getMessage());
			throw new TemplateException("Failed to print error message. Cause: " + e, env);
		}
	}

}

