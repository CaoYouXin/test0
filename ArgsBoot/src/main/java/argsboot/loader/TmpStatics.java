package argsboot.loader;

import argsboot.CommandHandler;
import argsboot.ConfigHandler;
import argsboot.StaticsHelper;

/**
 * Created by caoyouxin on 14-11-6.
 */
public class TmpStatics {
    public void add(String chain, String cmdName, CommandHandler commandHandler) {
    }

    public void add(String chain, String cmdName, String cfgName, ConfigHandler<CommandHandler> configHandler) {
    }

    public boolean toReal(StaticsHelper staticsHelper) {

        return false;
    }
}
