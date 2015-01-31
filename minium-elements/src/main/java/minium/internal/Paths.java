package minium.internal;

import static com.google.common.base.MoreObjects.firstNonNull;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;

public class Paths {

    private static final String CLASSPATH_PROTOCOL = "classpath:";
    private static final String CLASSPATH_WILDCARD_PROTOCOL = "classpath*:";

    public static URL toURL(String urlPath) {
        Preconditions.checkArgument(!urlPath.startsWith(CLASSPATH_WILDCARD_PROTOCOL), "path starts with classpath*, use toURLs instead");
        return Iterables.getFirst(toURLs(urlPath), null);
    }

    public static List<URL> toURLs(String urlPath) {
        Preconditions.checkNotNull(urlPath);

        ClassLoader loader = firstNonNull(Thread.currentThread().getContextClassLoader(), Paths.class.getClassLoader());

        if (urlPath.startsWith(CLASSPATH_PROTOCOL)) {
            String path = urlPath.substring(CLASSPATH_PROTOCOL.length());
            return singletonOrEmpty(loader.getResource(path));
        } else if (urlPath.startsWith(CLASSPATH_WILDCARD_PROTOCOL)) {
            String path = urlPath.substring(CLASSPATH_WILDCARD_PROTOCOL.length());
            try {
                return Collections.list(loader.getResources(path));
            } catch (IOException e) {
                return singletonOrEmpty(loader.getResource(path));
            }
        } else {
            try {
                return singletonOrEmpty(new URL(urlPath));
            } catch (MalformedURLException e) {
                // let's assume it's a local file
                File file = new File(urlPath);
                Preconditions.checkArgument(file.exists(), "File %s does not exist", file);
                Preconditions.checkArgument(file.isFile(), "%s is not a file", file);

                try {
                    return singletonOrEmpty(file.toURI().toURL());
                } catch (MalformedURLException e1) {
                    throw Throwables.propagate(e1);
                }
            }
        }
    }

    private static List<URL> singletonOrEmpty(URL resource) {
        return resource == null ? Collections.<URL>emptyList() : Collections.<URL>singletonList(resource);
    }
}
