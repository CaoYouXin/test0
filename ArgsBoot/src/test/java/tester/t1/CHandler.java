package tester.t1;

import argsboot.CommandHandler;

import java.util.List;

/**
 * Created by caoyouxin on 14-11-6.
 */
public class CHandler implements CommandHandler {

    public static final String ID = ":fs";

    @Override
    public String fn(List<String> param) {
        System.out.println("fs command");
        System.out.println(param);
        return null;
    }
}
