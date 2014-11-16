package argsboot.server;

import argsboot.CommandHandler;
import utils.Bool;

import java.util.List;

/**
 * Created by caoyouxin on 2014/11/16.
 */
public class LoaderServer implements CommandHandler {

    public static final String ID = ":server";

    private static final int PORT = 0;

    @Override
    public String fn(List<String> param) {
        int port = Integer.parseInt(param.get(PORT));
        return Bool.False.strVal();
    }

}
