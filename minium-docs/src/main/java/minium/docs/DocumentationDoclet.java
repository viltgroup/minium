package minium.docs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

public class DocumentationDoclet {

	private final static String DOCFILE_ELEMENTS = "Elements";
	private final static String DOCFILE_INTERACTIONS = "Interactions";

	private static HashSet<String> elements_file;
	private static HashSet<String> interactions_file;

	/*
	 * 
	 * generateDocs("api/_elements.md", "Elements", BasicWebElements.class,
	 * FreezableElements.class, ConditionalWebElements.class,
	 * ExtensionsWebElements.class, EvalWebElements.class,
	 * TargetLocatorWebElements.class, PositionWebElements.class);
	 * generateDocs("api/_interactions.md", "Interactions", Interactable.class);
	 * generateDocs("api/_waitInteractions.md", "Wait Interactions",
	 * WaitInteractable.class); generateDocs("api/_debugInteractions.md",
	 * "Debug Interactions", DebugInteractable.class);
	 * generateDocs("api/_interactionListeners.md", "Interaction Listeners",
	 * InteractionListeners.class);
	 */

	private static void init() throws IOException {

		// Elements
		elements_file = new HashSet();
		elements_file.add("BasicWebElements");
		elements_file.add("ConditionalWebElements");
		elements_file.add("ExtensionsWebElements");
		elements_file.add("EvalWebElements");
		elements_file.add("TargetLocatorWebElements");
		elements_file.add("PositionWebElements");
		initFiles(DOCFILE_ELEMENTS, "#" + DOCFILE_ELEMENTS);

		// Interactions

		interactions_file = new HashSet();
		interactions_file.add("Interactable");
		initFiles(DOCFILE_INTERACTIONS, "#" + DOCFILE_INTERACTIONS);
	}

	/**
	 * This method is called by the javadoc framework and is required for all
	 * doclets.
	 *
	 * @param root
	 *            the root
	 * @return true if successful
	 */
	public static boolean start(RootDoc root) throws IOException {

		init();

		String fileName = null;

		// Iterate classes
		for (ClassDoc clazz : root.classes()) {

			if (elements_file.contains(clazz.name())) {
				fileName = DOCFILE_ELEMENTS;
			} else if (interactions_file.contains(clazz.name())) {
				fileName = DOCFILE_INTERACTIONS;
			}

			if (fileName != null) {

				// Iterate methods
				for (MethodDoc method : clazz.methods(false)) {
					ArrayList<ParamTag> parameters = new ArrayList();
					String returnText = "";
					String link = "";

					// Parameters comments
					if (method.commentText() != null && method.commentText().length() > 0) {
						for (ParamTag paramTag : method.paramTags()) {
							parameters.add(paramTag);
						}

						// returns (get only the last one)
						for (Tag t : method.tags("return")) {
							if (t.text() != null && t.text().length() > 0)
								returnText = t.text();
						}

						// Links (Get only the last one)
						for (Tag t : method.tags("see")) {
							if (t.text() != null && t.text().length() > 0)
								link = t.text();
						}
					}

					printMarkDown(fileName, method, parameters, returnText, link);

				}
			}
			fileName = null;
		}
		return true;
	}

	/* Auxiliar Methods to clumsy print the Documentation in Markdown */

	private static void printMarkDown(String fileName, MethodDoc method, ArrayList<ParamTag> parameters, String returnText, String link) throws IOException {
		StringBuilder methodDescription = new StringBuilder();
		methodDescription.append("\n## .");
		methodDescription.append(method.name());
		methodDescription.append(printParameters(method.parameters()));
		methodDescription.append(printMethodComment(method));

		methodDescription.append(printParamDescription(parameters));
		methodDescription.append(printSee(link));
		methodDescription.append(printReturns(returnText));
		methodDescription.append("\n\n");

		print(fileName, methodDescription.toString());
	}

	private static String printMethodComment(MethodDoc method) {

		return (method.commentText() != null && method.commentText() != "") ? "\n`" + removeHTMLComments(method.commentText()) + "`\n" : "";

	}

	private static String printParamDescription(ArrayList<ParamTag> parameters) {
		StringBuilder paramDesc = new StringBuilder("");
		if (parameters.size() > 0) {
			paramDesc.append("\n\nParameter | Description\n	--------- | -----------");
			for (ParamTag param : parameters) {
				paramDesc.append("\n|" + param.parameterName() + "|" + param.parameterComment());
			}
		}
		return paramDesc.toString();

	}

	private static String printReturns(String returnText) {
		if (returnText != null && returnText != "") {
			return "\n* **returns:** " + returnText;
		} else {
			return "";
		}
	}

	private static String printSee(String link) {
		String seeText = "";
		Pattern p = Pattern.compile("href=\"(.*?)\"");
		Matcher m = p.matcher(link);
		String url = null;
		if (m.find()) {
			url = m.group(1);
		}

		seeText = url != null ? "\n* **see:** [ " + url + "]" + "(" + url + ")" : "";

		return seeText;

	}

	private static Object printParameters(Parameter[] parameters) {
		StringBuilder sb = new StringBuilder("(");
		for (Parameter parameter : parameters) {
			sb.append(parameter.name());
		}
		sb.append(")");
		return sb.toString();
	}

	private static String removeHTMLComments(String commentText) {
		return commentText.replaceAll("(?s)<!--.*?-->", "");
	}

	private static void print(String name, String comment) throws IOException {
		if (comment != null && comment.length() > 0) {
			new FileWriter("_" + name + ".md", true).append(comment).close();
		}
	}

	private static void initFiles(String name, String md) throws IOException {

		new FileWriter("_" + name + ".md", false).append(md).close();

	}
}