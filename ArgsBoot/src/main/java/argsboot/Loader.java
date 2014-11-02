package argsboot;

import java.util.Set;

public interface Loader {

	boolean load(Set<String> paths, String[] chain, Modules modules);
	
	boolean isReloadSupported();
	
}
