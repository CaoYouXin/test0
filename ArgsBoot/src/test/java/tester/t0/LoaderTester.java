package tester.t0;

import argsboot.ArgsBoot;
import argsboot.CompletedHandler;

/**
 * Created by caoyouxin on 14-11-4.
 */
public class LoaderTester {

    public static final boolean debugging = true;

    public static void main(String[] args) {
        Object fs = ArgsBoot.call((CompletedHandler<Integer>)(r) -> {
            return 1;
        } , "fs");
        System.out.println(fs);
    }

}
