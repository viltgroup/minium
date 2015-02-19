package minium.web.internal.compressor;

import java.io.IOException;
import java.util.Collection;

import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;

public interface Compressor {

    static class NullCompressor implements Compressor {
        @Override
        public String compress(ClassLoader classLoader, Collection<String> jsResources) {
            return Joiner.on("\n\n").join(Collections2.transform(jsResources, ResourceFunctions.classpathFileToStringFunction(classLoader)));
        }
    }

    public static final Compressor NULL = new NullCompressor();

    String compress(ClassLoader classLoader, Collection<String> jsResources) throws IOException;

}
