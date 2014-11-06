package argsboot.loader;

import argsboot.CommandHandler;
import argsboot.ConfigHandler;
import argsboot.Loader;
import argsboot.StaticsHelper;
import utils.Debugger;
import utils.EnumerationUtils;
import utils.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DefaultLoader implements Loader {

    @Override
	public boolean load(Set<String> paths, String[] chain, StaticsHelper staticsHelper) {
        String prefix = StringUtils.join(chain, ".");
        TmpStatics tmp = new TmpStatics();
        paths.forEach((String more) -> {
			String[] split = more.split("/");
			Path path = Paths.get(split[0], (split.length < 2) ? new String[0] : Arrays.copyOfRange(split, 1, split.length));
            Debugger.debug(() -> System.out.println("now finding:" + path.toAbsolutePath().toString()));
			try {
				Files.find(path, Integer.MAX_VALUE, (Path t, BasicFileAttributes u) -> {
                    return !u.isDirectory();
                }).forEach((p) -> {
                    String pAbsFullPathName = p.toAbsolutePath().toString();
                    Debugger.debug(() -> System.out.println("iterator:" + pAbsFullPathName));
                    if (pAbsFullPathName.endsWith("class")) {
                        Path relative = path.relativize(p);
                        Debugger.debug(() -> System.out.println("relative:" + relative.toString()));
                        int lastIndex = relative.getNameCount() - 1;
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < lastIndex; i++) {
                            sb.append(relative.getName(i).toString()).append('.');
                        }
                        String filename = relative.getName(lastIndex).toString();
                        sb.append(filename.substring(0, filename.length() - 6));
                        Debugger.debug(() -> System.out.println(sb.toString()));
                        processWithClassName(sb.toString(), prefix, tmp);
                    } else if (pAbsFullPathName.endsWith("jar")) {
                        JarFile jar = null;
                        try {
                            jar = new JarFile(p.toAbsolutePath().toFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Enumeration<JarEntry> entries = jar != null ? jar.entries() : EnumerationUtils.emptyEnumeration(JarEntry.class);
                        while (entries.hasMoreElements()) {
                            JarEntry jarEntry = entries.nextElement();
                            Debugger.debug(() -> System.out.println(jarEntry.getName()));
                            if (jarEntry.getName().endsWith("class")) {
                                String className = jarEntry.getName().replace('/', '.');
                                className = className.substring(0, className.length() - 6);
                                processWithClassName(className, prefix, tmp);
                            }
                        }
                    }
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
		return tmp.toReal(staticsHelper);
	}

    private void processWithClassName(String className, String prefix, TmpStatics tmpStatics) {
        Class clazz = null;
        Field id = null;
        try {
            clazz = Class.forName(className);
            id = clazz.getField("ID");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // TODO Log
            return;
        }
        String identification = (null != id) ? id.getName() : "-1";
        if (!identification.startsWith(prefix)) return;
        int from = identification.indexOf(':');
        String chain = identification.substring(0, from);
        if (clazz.isAssignableFrom(CommandHandler.class)) {
            String cmdName = identification.substring(from + 1);
            CommandHandler commandHandler = null;
            try {
                commandHandler = (CommandHandler) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                // TODO Log
                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                // TODO Log
                return;
            }
            tmpStatics.add(chain, cmdName, commandHandler);
        }
        if (clazz.isAssignableFrom(ConfigHandler.class)) {
            int to = identification.indexOf(':', from);
            String cmdName = identification.substring(from + 1, to);
            String cfgName = identification.substring(to + 1);
            ConfigHandler configHandler = null;
            try {
                configHandler = (ConfigHandler) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                // TODO Log
                return;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                // TODO Log
                return;
            }
            tmpStatics.add(chain, cmdName, cfgName, configHandler);
        }
    }

    @Override
	public boolean isReloadSupported() {
		return false;
	}

}
