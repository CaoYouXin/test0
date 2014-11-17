package argsboot;

import java.util.HashSet;
import java.util.Set;

public interface Loader {

	boolean load(Set<String> paths, String[] chain, StaticsHelper staticsHelper);

	boolean clear(String[] chain, StaticsHelper staticsHelper);

	boolean isReloadSupported();

	boolean isDistributedSupported();

}
