package argsboot.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import utils.Debugger;
import argsboot.Loader;
import argsboot.Module;
import argsboot.Modules;

public class DefaultLoader implements Loader {

	@Override
	public boolean load(Set<String> paths, String[] chain, Modules modules) {
		paths.forEach((String more) -> {
			String[] split = more.split("/");
			Path path = Paths.get(split[0], (split.length < 2) ? new String[0] : Arrays.copyOfRange(split, 1, split.length));
            Debugger.debug(() -> {
                System.out.println("now finding:" + path.toAbsolutePath().toString());
			});
			try {
				Files.find(path, Integer.MAX_VALUE, (Path t, BasicFileAttributes u) -> {
					if (u.isDirectory()) {
						return false;
					}
					return true;
				}).forEach((p) -> {
                    Path relative = path.relativize(p);
                    Debugger.debug(() -> {
                        System.out.println("iterator:" + p.toString());
                        System.out.println("relative:" + relative.toString());
                    });
                    if (relative.toString().endsWith("class")) {
                        int lastIndex = relative.getNameCount() - 1;
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < lastIndex; i++) {
                            sb.append(relative.getName(i).toString()).append('.');
                        }
                        String filename = relative.getName(lastIndex).toString();
                        sb.append(filename.substring(0, filename.length() - 6));
                        Debugger.debug(() -> {
                            System.out.println(sb.toString());
                        });
                        processWithClassName(sb.toString(), chain, modules);
                    } else if (relative.toString().endsWith("jar")) {

                    }
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
        });
		return false;
	}

    private void processWithClassName(String className, String[] chain, Modules modules) {

    }

    @Override
	public boolean isReloadSupported() {
		return false;
	}

}
