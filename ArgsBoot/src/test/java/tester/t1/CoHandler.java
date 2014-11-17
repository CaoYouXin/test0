package tester.t1;

import argsboot.CfgParam;
import argsboot.ConfigHandler;

/**
 * Created by caoyouxin on 14-11-6.
 */
public class CoHandler implements ConfigHandler<CHandler> {

    public static final String ID = ":fs:a";

    @Override
    public void fn(CfgParam<CHandler> param) {
        System.out.println("fs a config");
        System.out.println(param.getParams());
    }
}
