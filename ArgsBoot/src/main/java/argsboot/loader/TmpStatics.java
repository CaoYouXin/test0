package argsboot.loader;

import argsboot.StaticsHelper;
import utils.Suc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caoyouxin on 14-11-6.
 */
public class TmpStatics {

    private Map<String, Map<String, Class>> cmds = new HashMap<>();
    private Map<String, Map<String, Class>> cfgs = new HashMap<>();

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
        String key = chain + ':' + cmdName;
        Map<String, Class> stringClassMap = cfgs.get(key);
        if (null == stringClassMap) {
            stringClassMap = new HashMap<>();
            cfgs.put(key, stringClassMap);
        }
        Class aClass = stringClassMap.get(cfgName);
        if (null == aClass) stringClassMap.put(cfgName, configHandler);
    }

    public boolean toReal(StaticsHelper staticsHelper) {
        Suc suc = new Suc(true);
        cmds.forEach((chain, stringClassMap) -> {
            suc.successAndUpdate(() -> {
                return staticsHelper.batchAddCommand(chain, stringClassMap);
            });
        });
        suc.success(() -> {
            cfgs.forEach((key, stringClassMap) -> {
                suc.successAndUpdate(() -> {
                    return staticsHelper.batchAddConfig(key, stringClassMap);
                });
            });
            return null;
        });
        return suc.val();
    }

}
