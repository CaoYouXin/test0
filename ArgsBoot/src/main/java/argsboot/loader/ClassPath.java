package argsboot.loader;

/**
 * Created by caoyouxin on 14-11-12.
 */

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Class   to   handle   CLASSPATH   construction
 */
public class ClassPath {

    Set<File> _elements = new HashSet<>();

    public ClassPath(String initial) {
        addClasspath(initial);
    }

    public boolean addComponent(String component) {
        if ((component != null) && (component.length() > 0)) {
            try {
                File f = new File(component);
                if (f.exists()) {
                    File key = f.getCanonicalFile();
                    if (!_elements.contains(key)) {
                        _elements.add(key);
                        return true;
                    }
                }
            } catch (IOException e) {
            }
        }
        return false;
    }

    public boolean addComponent(File component) {
        if (component != null) {
            try {
                if (component.exists()) {
                    File key = component.getCanonicalFile();
                    if (!_elements.contains(key)) {
                        _elements.add(key);
                        return true;
                    }
                }
            } catch (IOException e) {
            }
        }
        return false;
    }

    public boolean addClasspath(String s) {
        boolean added = false;
        if (s != null) {
            StringTokenizer t = new StringTokenizer(s, File.pathSeparator);
            while (t.hasMoreTokens()) {
                added |= addComponent(t.nextToken());
            }
        }
        return added;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(1024);
        _elements.forEach((file) -> {
            sb.append(File.pathSeparator);
            sb.append(file.getPath());
        });
        return sb.substring(File.pathSeparator.length());
    }

    public URL[] getUrls() {
        URL[] urls = new URL[_elements.size()];
        int index = 0;
        for (File file : _elements) {
            try {
                urls[index++] = file.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return new URL[0];
            }
        }
        return urls;
    }

    public ClassLoader getClassLoader() {
        URL[] urls = getUrls();
        System.out.println(Arrays.toString(urls));
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        if (parent == null) {
            parent = ClassPath.class.getClassLoader();
        }
        if (parent == null) {
            parent = ClassLoader.getSystemClassLoader();
        }
        URLClassLoader urlClassLoader = new URLClassLoader(urls, parent);
        return urlClassLoader;
    }

}
