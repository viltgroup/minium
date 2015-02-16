package minium.docs;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

public class DocumentationDoclet {

	/**
	 * This method is called by the javadoc framework and is required for all
	 * doclets.
	 *
	 * @param root
	 *            the root
	 * @return true if successful
	 */
	public static boolean start(RootDoc root) throws IOException {

		// Iterate classes
		for (ClassDoc clazz : root.classes()) {
			initFiles(clazz.name(), "#" + clazz.name());

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
				printMarkDown(clazz.name(), method, parameters, returnText, link);
			}
		}
		return true;
	}

	/* Auxiliar Methods to clumsy print the Documentation in Markdown */

	private static void printMarkDown(String className, MethodDoc method, ArrayList<ParamTag> parameters, String returnText, String link) throws IOException {
		StringBuilder methodDescription = new StringBuilder();
		methodDescription.append("\n## .");
		methodDescription.append(method.name());
		methodDescription.append(printParameters(method.parameters()));
		methodDescription.append(printMethodComment(method));

		methodDescription.append(printParamDescription(parameters));
		methodDescription.append(printSee(link));
		methodDescription.append(printReturns(returnText));
		methodDescription.append("\n\n");

		print(className, methodDescription.toString());
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