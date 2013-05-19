package com.vilt.minium.impl;

import static java.lang.String.format;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.JavascriptExecutor;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.SourceFile;
import com.vilt.minium.MiniumException;
import com.vilt.minium.WebElementsDriver;

/**
 * This class is responsible for injecting the necessary javascript code in the
 * current page so that jQuery expressions can be evaluated.
 * 
 * @author Rui
 */
public class JQueryInvoker {

	private final class ClasspathFileToSourceFileFunction implements Function<String, SourceFile> {
		public SourceFile apply(String input) {
			try {
				return SourceFile.fromInputStream(input, getClasspathFileInputStream(input));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private final class ClasspathFileToStringFunction implements Function<String, String> {
		public String apply(String input) {
			return getFileContent(input);
		}
	}

	// resource paths
	private final Collection<String> jsResources;
	private final Collection<String> cssResources;

	// concatenated styles resources content
	private String styles;

	private String lightInvokerScriptTemplate;
	private String fullInvokerScriptTemplate;

	public JQueryInvoker(Collection<String> jsResources, Collection<String> cssResources) {
		this.jsResources = jsResources;
		this.cssResources = cssResources;

		try {
			String jsScripts = compressCode(Collections2.transform(jsResources, new ClasspathFileToSourceFileFunction()));

			if (cssResources != null) {
				styles = StringUtils.join(Collections2.transform(cssResources, new ClasspathFileToStringFunction()), "\n\n");
			}

			lightInvokerScriptTemplate = getFileContent("minium/templates/jquery-invoker-light.template");
			fullInvokerScriptTemplate = getFileContent("minium/templates/jquery-invoker-full.template");
			fullInvokerScriptTemplate = fullInvokerScriptTemplate.replace("{{jsScript}}", jsScripts);

		} catch (Exception e) {
			throw new MiniumException(e);
		}
	}

	public String compressCode(Collection<SourceFile> inputs) {
		try {
			Compiler compiler = new Compiler();

			CompilerOptions options = new CompilerOptions();
			CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

			// The dummy input name "input.js" is used here so that any warnings or
			// errors will cite line numbers in terms of input.js.

			// compile() returns a Result, but it is not needed here.
			compiler.compile(CommandLineRunner.getDefaultExterns(), Lists.newArrayList(inputs), options);

			// The compiler is responsible for generating the compiled code; it is
			// not
			// accessible via the Result.
			return compiler.toSource();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T invoke(JavascriptExecutor wd, String expression, Object... args) {
		return this.<T> invoke(wd, false, expression, args);
	}

	@SuppressWarnings("unchecked")
	public <T> T invoke(JavascriptExecutor wd, boolean async, String expression, Object... args) {
		try {
			args = convertToValidArgs(args);

			Object[] fullArgs = args == null ? new Object[1] : new Object[args.length + 1];
			fullArgs[0] = async;
			if (args != null)
				System.arraycopy(args, 0, fullArgs, 1, args.length);

			Object result = async ? wd.executeAsyncScript(lightInvokerScript(expression), fullArgs) : wd
				.executeScript(lightInvokerScript(expression), fullArgs);

			if (result instanceof Boolean && ((Boolean) result) == false) {
				fullArgs = args == null ? new Object[2] : new Object[args.length + 2];
				fullArgs[0] = async;
				fullArgs[1] = styles;
				if (args != null)
					System.arraycopy(args, 0, fullArgs, 2, args.length);
				result = async ? wd.executeAsyncScript(fullInvokerScript(expression), fullArgs) : wd.executeScript(fullInvokerScript(expression), fullArgs);
			}

			if (!(result instanceof List))
				throw new IllegalStateException(format("Expected a list with the result in the first position..."));

			return (T) ((List<?>) result).get(0);
		} catch (Exception e) {
			throw new MiniumException(e);
		}
	}

	public Object invokeExpression(WebElementsDriver<?> wd, boolean async, String expression, Object... args) {
		return invoke(wd, async, format("return %s;", expression), args);
	}

	protected String lightInvokerScript(String expression) {
		return lightInvokerScriptTemplate.replace("{{expression}}", expression);
	}

	protected String fullInvokerScript(String expression) {
		return fullInvokerScriptTemplate.replace("{{expression}}", expression);
	}

	protected Collection<String> getJsResources() {
		return jsResources;
	}

	protected Collection<String> getCssResources() {
		return cssResources;
	}

	private String getFileContent(String filename) {
		try {
			InputStream in = getClasspathFileInputStream(filename);
			return IOUtils.toString(in);
		} catch (IOException e) {
			throw new MiniumException(format("Could not load %s from classpath", filename), e);
		}
	}

	public InputStream getClasspathFileInputStream(String filename) {
		return JQueryInvoker.class.getClassLoader().getResourceAsStream(filename);
	}

	private Object[] convertToValidArgs(Object[] args) {
		// we need to get the wrapped web element from every DelegateWebElement
		// in arguments
		Object[] validArgs = new Object[args.length];
		System.arraycopy(args, 0, validArgs, 0, args.length);

		for (int i = 0; i < validArgs.length; i++) {
			Object arg = validArgs[i];
			if (arg instanceof DelegateWebElement) {
				validArgs[i] = ((DelegateWebElement) arg).getWrappedWebElement();
			} else if (arg instanceof Object[]) {
				validArgs[i] = convertToValidArgs((Object[]) arg);
			}
		}
		return validArgs;
	}
}
