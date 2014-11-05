package tester.t0;

import argsboot.Loader;
import argsboot.RuntimeHelper;
import argsboot.loader.DefaultLoader;
import utils.ArrayUtils;

/**
 * Created by caoyouxin on 14-11-4.
 */
public class LoaderTester {

    public static final boolean debugging = true;

    public static void main(String[] args) {
        Loader loader = new DefaultLoader();
        loader.load(ArrayUtils.asSet("a"), new String[0], RuntimeHelper.val());
    }

}
