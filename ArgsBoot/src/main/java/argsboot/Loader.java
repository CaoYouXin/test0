package argsboot;

import java.util.Set;

public interface Loader {

	boolean load(Set<String> paths, String[] chain, RuntimeHelper runtimeHelper);
	
	boolean isReloadSupported();
	
}
