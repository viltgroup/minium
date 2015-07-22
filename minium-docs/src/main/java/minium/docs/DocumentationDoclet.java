/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.docs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import minium.BasicElements;
import minium.FreezableElements;
import minium.actions.Configuration;
import minium.actions.Interactable;
import minium.actions.KeyboardInteractable;
import minium.actions.MouseInteractable;
import minium.actions.WaitInteractable;
import minium.web.BasicWebElements;
import minium.web.ConditionalWebElements;
import minium.web.EvalWebElements;
import minium.web.ExtensionsWebElements;
import minium.web.PositionWebElements;
import minium.web.TargetLocatorWebElements;
import minium.web.actions.Browser;
import minium.web.actions.WebConfiguration;

import com.sun.javadoc.RootDoc;
import com.sun.tools.javadoc.Main;

public class DocumentationDoclet {

    private static Class<?>[] CONFIGURATION = {
        Configuration.WaitingPreset.class,
        Configuration.InteractionListenerCollection.class,
        WebConfiguration.CookieCollection.class,
        WebConfiguration.Window.class,
        Configuration.class,
        WebConfiguration.class,

    };
    private static Class<?>[] BROWSER = {
        Browser.Navigation.class,
        Browser.Screenshot.class,
        Browser.class
    };
    private static Class<?>[] WEB_ELEMENTS = {
        BasicElements.class,
        BasicWebElements.class,
        FreezableElements.class,
        ConditionalWebElements.class,
        ExtensionsWebElements.class,
        EvalWebElements.class,
        TargetLocatorWebElements.class,
        PositionWebElements.class
    };
    private static Class<?>[] INTERACTABLES = {
        Interactable.class,
        MouseInteractable.class,
        KeyboardInteractable.class,
        WaitInteractable.class
    };

    public static boolean start(RootDoc root) throws IOException {
        File outputDir = readOutputDir(root);
        outputDir.mkdirs();
        try (Writer writer = createFileWriter(outputDir, "api/methods.json")) {
            ApiJSONGenerator apiJSONGenerator = new ApiJSONGenerator(root, writer);
            apiJSONGenerator.addClass(WEB_ELEMENTS);
            apiJSONGenerator.addClass(INTERACTABLES);
            apiJSONGenerator.addClass(CONFIGURATION);
            apiJSONGenerator.addClass(BROWSER);
            apiJSONGenerator.print();
        }
        try (Writer writer = createFileWriter(outputDir, "api/configuration.md")) {
            new ApiGenerator(root, writer, CONFIGURATION).print();
        }
        try (Writer writer = createFileWriter(outputDir, "api/browser.md")) {
            new ApiGenerator(root, writer, BROWSER).print();
        }
        try (Writer writer = createFileWriter(outputDir, "api/web-elements.md")) {
            new ApiGenerator(root, writer, WEB_ELEMENTS).print();
        }
        try (Writer writer = createFileWriter(outputDir, "api/interactable.md")) {
            new ApiGenerator(root, writer, INTERACTABLES).print();
        }
        return true;
    }

    protected static FileWriter createFileWriter(File outputDir, String path) throws IOException {
        File file = new File(outputDir, path);
        file.getParentFile().mkdirs();
        return new FileWriter(file.getAbsoluteFile());
    }

    public static int optionLength(String option) {
        if (option.equals("-d")) {
            return 2;
        }
        return 0;
    }

    protected static File readOutputDir(RootDoc root) {
        for (int i = 0; i < root.options().length; i++) {
            String[] opt = root.options()[i];
            if (opt[0].equals("-d")) {
                return new File(opt[1]);
            }
        }
        return new File(".");
    }

    public static void main(String[] args) {
        Main.main(new String[] { "-d", "target", "-doclet", DocumentationDoclet.class.getName(), "-sourcepath", "../minium-elements/src/main/java;../minium-webelements/src/main/java;../minium-actions/src/main/java",
//                "minium.web", "minium", "minium.actions"
                "minium.web.actions"
                });
    }
}