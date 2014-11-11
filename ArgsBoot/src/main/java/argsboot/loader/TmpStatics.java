package argsboot.loader;

import argsboot.StaticsHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caoyouxin on 14-11-6.
 */
public class TmpStatics {

    private Map<String, Map<String, Class>> cmds = new HashMap<>();
    private Map<String, Map<String, Map<String, Class>>> cfgs = new HashMap<>();

    public void add(String chain, String cmdName, Class commandHandler) {
        Map<String, Class> stringClassMap = cmds.get(chain);
        if (null == stringClassMap) {
            stringClassMap = new HashMap<>();
            cmds.put(chain, stringClassMap);
        }
        Class aClass = stringClassMap.get(cmdName);
        if (null == aClass) stringClassMap.put(cmdName, commandHandler);
    }

    public void add(String chain, String cmdName, String cfgName, Class configHandler) {
        Map<String, Map<String, Class>> stringMapMap = cfgs.get(chain);
        if (null == stringMapMap) {
            stringMapMap = new HashMap<>();
            cfgs.put(chain, stringMapMap);
        }
        Map<String, Class> stringClassMap = stringMapMap.get(cmdName);
        if (null == stringClassMap) {
            stringClassMap = new HashMap<>();
            stringMapMap.put(cmdName, stringClassMap);
        }
        Class aClass = stringClassMap.get(cfgName);
        if (null == aClass) stringClassMap.put(cfgName, configHandler);
    }

    public boolean toReal(StaticsHelper staticsHelper) {
        cmds.forEach((chain, stringClassMap) -> {
            staticsHelper.getModuleByChainCreateIfNull(Arrays.asList(chain.split(".")));
        });
        return false;
    }

}
