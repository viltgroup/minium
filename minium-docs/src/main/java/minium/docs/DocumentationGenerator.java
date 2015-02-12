package minium.docs;

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Comparator;

import minium.FreezableElements;
import minium.actions.Interactable;
import minium.actions.InteractionListeners;
import minium.actions.WaitInteractable;
import minium.actions.debug.DebugInteractable;
import minium.web.BasicWebElements;
import minium.web.ConditionalWebElements;
import minium.web.EvalWebElements;
import minium.web.ExtensionsWebElements;
import minium.web.PositionWebElements;
import minium.web.TargetLocatorWebElements;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class DocumentationGenerator {

	private File outputDir;
	private PrintStream out;

	public DocumentationGenerator(String outputPath) {
		outputDir = new File(outputPath);
	}

	/**
	 * <!-- begin-minium-doc
	 * http://api.jquery.com/prevUntil/#prevUntil-selector-filter --> Get all
	 * preceding siblings of each element up to but not including the element
	 * matched by the selector, DOM node, or jQuery object.
	 *
	 * @param selector
	 *            A string containing a selector expression to indicate where to
	 *            stop matching preceding sibling elements.
	 * @param filter
	 *            A string containing a selector expression to match elements
	 *            against.
	 * @return result of jQuery .prevUntil() method
	 * @see <a
	 *      href="http://api.jquery.com/prevUntil/#prevUntil-selector-filter">jQuery
	 *      .prevUntil() method</a> <!-- end-minium-doc -->
	 */
	public void generateAllDocs() throws IOException {
		generateDocs("api/_elements.md", "Elements", BasicWebElements.class, FreezableElements.class, ConditionalWebElements.class, ExtensionsWebElements.class, EvalWebElements.class,
				TargetLocatorWebElements.class, PositionWebElements.class);
		generateDocs("api/_interactions.md", "Interactions", Interactable.class);
		generateDocs("api/_waitInteractions.md", "Wait Interactions", WaitInteractable.class);
		generateDocs("api/_debugInteractions.md", "Debug Interactions", DebugInteractable.class);
		generateDocs("api/_interactionListeners.md", "Interaction Listeners", InteractionListeners.class);
	}

	public void generateDocs(String fileName, String section, Class<?>... clazzes) throws IOException {
		start(fileName);
		// print("#%s", section);
		// print(Strings.repeat("=", section.length()));
		// print();

		for (Class<?> clazz : clazzes) {
			generateDocsFor(clazz);
		}
		close();
	}

	protected void generateDocsFor(Class<?> clazz) {
		print("#%s", clazz.getSimpleName());
		// print(Strings.repeat("-", clazz.getSimpleName().length()));
		print();

		Multimap<String, Method> methods = Multimaps.index(FluentIterable.from(ImmutableList.copyOf(clazz.getDeclaredMethods())).filter(new Predicate<Method>() {
			@Override
			public boolean apply(Method method) {
				return method.getDeclaringClass() != Object.class;
			}
		}).toSortedList(new Comparator<Method>() {
			@Override
			public int compare(Method o1, Method o2) {
				return o1.getName().compareTo(o2.getName());
			}
		}), new Function<Method, String>() {
			@Override
			public String apply(Method method) {
				return method.getName();
			}
		});

		for (String methodName : methods.keySet()) {
			String dotIfNotStatic = Modifier.isStatic(Iterables.get(methods.get(methodName), 0).getModifiers()) ? "" : ".";
			print("## %s%s(...)", dotIfNotStatic, methodName);
			print();
			print("Possible arguments:");
			print();

			for (Method method : methods.get(methodName)) {
				print("* %s%s(%s)", dotIfNotStatic, methodName, args(method));
			}
			print();
		}
	}

	protected Object args(Method method) {
		Iterable<String> argTypeNames = FluentIterable.from(ImmutableList.copyOf(method.getParameterTypes())).transform(new Function<Class<?>, String>() {

			@Override
			public String apply(Class<?> clazz) {
				return clazz.getSimpleName();
			}
		});
		return Joiner.on(", ").join(argTypeNames);
	}

	protected void start(String name) throws IOException {
		File file = new File(outputDir, name);
		File parentDir = file.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		out = new PrintStream(file);
	}

	protected void close() {
		out.close();
		out = null;
	}

	protected void print() {
		out.println();
	}

	protected void print(String str, Object... args) {
		out.println(format(str, args));
	}

	public static void main(String[] args) throws IOException {
		String outputDir = "slate/source/includes/generated";
		new DocumentationGenerator(outputDir).generateAllDocs();
		System.out.println("Generated documentation to " + outputDir);
	}
}
