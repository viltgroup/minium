package minium.internal;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.io.Resources;

public class Paths {

    private static final String CLASSPATH_PROTOCOL = "classpath:";

    public static URL toURL(String urlPath) {
        Preconditions.checkNotNull(urlPath);
        if (urlPath.startsWith(CLASSPATH_PROTOCOL)) {
            String path = urlPath.substring(CLASSPATH_PROTOCOL.length());
            return Resources.getResource(path);
        } else {
            try {
                return new URL(urlPath);
            } catch (MalformedURLException e) {
                // let's assume it's a local file
                File file = new File(urlPath);
                Preconditions.checkArgument(file.exists(), "File %s does not exist", file);
                Preconditions.checkArgument(file.isFile(), "%s is not a file", file);

                try {
                    return file.toURI().toURL();
                } catch (MalformedURLException e1) {
                    throw Throwables.propagate(e1);
                }
            }
        }
    }
}
