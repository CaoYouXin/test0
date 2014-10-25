package utils.dynamicinit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;

public class InitManager {

	private InitManager(){
	}
	
	public static <K, V> Map<K, V> init(String rPath, Class<K> keyType, Class<V> valueType) throws URISyntaxException, IOException {
		Map<K, V> hashMap = new HashMap<>();
		URL resource = valueType.getResource(rPath);
		Path path = Paths.get(resource.toURI());
		Files.find(path, 2, new BiPredicate<Path, BasicFileAttributes>() {
			@Override
			public boolean test(Path t, BasicFileAttributes u) {
				if (u.isDirectory()) {
					return false;
				}
				return true;
			}
		}).forEach((p) -> {
			String className = getClassName(rPath, p, valueType.getPackage().getName() + '.' + rPath);
			System.out.println(className);
			Class<?> forName = null;
			try {
				forName = Class.forName(className);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Class<?>[] interfaces = forName.getInterfaces();
			boolean isValue = false, hasKeyId = false;
			for (Class<?> klass : interfaces) {
				if (Objects.equals(klass, valueType)) {
					isValue = true;
				} else if (Objects.equals(klass, KeyIdentifier.class)) {
					hasKeyId = true;
				}
			}
			if (isValue && hasKeyId) {
				Object newInstance = null;
				try {
					newInstance = forName.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (null != newInstance) {
					hashMap.put((K) ((KeyIdentifier) newInstance).key(), (V) newInstance);
				}
			}
		});
		return hashMap;
	}
	
	private static String getClassName(String tester, Path path, String pkgName) {
		StringBuilder sb = new StringBuilder();
		boolean isLast = true;
		for (int i = path.getNameCount() - 1; i >= 0; i--) {
			String string = path.getName(i).toString();
			if (Objects.equals(tester, string)) {
				break;
			}
			if (isLast) {
				sb.insert(0, "." + string.substring(0, string.indexOf(".class")));
				isLast = false;
				continue;
			}
			sb.insert(0, "." + path.getName(i));
		}
		return pkgName + sb.toString();
	}
	
}
