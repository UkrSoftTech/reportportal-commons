package com.epam.reportportal.commons.template;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

import javax.inject.Provider;
import java.util.Locale;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Factory bean for {@link TemplateEngine}
 *
 * @author Andrei Varabyeu
 */
public class TemplateEngineProvider implements Provider<TemplateEngine> {

    private final String basePackagePath;

    public TemplateEngineProvider() {
        this("/");
    }

    /**
     * @param basePackagePath Base path for templates in classpath
     */
    public TemplateEngineProvider(String basePackagePath) {
        Preconditions.checkArgument(!isNullOrEmpty(basePackagePath), "Base path for templates is missed");
        this.basePackagePath = basePackagePath;
    }

    @Override
    public TemplateEngine get() {
        Version version = new Version(2, 3, 25);
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(version);

        cfg.setClassForTemplateLoading(this.getClass(), basePackagePath);

        cfg.setIncompatibleImprovements(version);
        cfg.setDefaultEncoding(Charsets.UTF_8.toString());
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return new FreemarkerTemplateEngine(cfg);

    }
}
