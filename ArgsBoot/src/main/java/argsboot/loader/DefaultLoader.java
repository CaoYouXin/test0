package argsboot.loader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import utils.Debugger;
import argsboot.Loader;
import argsboot.Module;
import argsboot.Modules;

public class DefaultLoader implements Loader {

	@Override
	public boolean load(Set<String> paths, String[] chain, Modules modules) {
		List<String> chainList = Arrays.asList(chain);
		Module targetModule = modules.getRootModuleByChain(chainList);
		if (null == targetModule) {
			targetModule = modules.createChain(chainList);
		}
		paths.forEach((String more) -> {
			String[] split = more.split("/");
			Path path = Paths.get(split[0], Arrays.copyOfRange(split, 1, split.length));
			Debugger.debug(() -> {
				System.out.println(path.toAbsolutePath().toString());
			});
			try {
				Files.find(path, Integer.MAX_VALUE, (Path t, BasicFileAttributes u) -> {
					if (u.isDirectory()) {
						return false;
					}
					return true;
				}).forEach((p) -> {
					
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return false;
	}

	@Override
	public boolean isReloadSupported() {
		return false;
	}

}
