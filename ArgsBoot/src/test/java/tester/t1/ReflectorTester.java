package tester.t1;

import argsboot.CommandHandler;
import argsboot.ConfigHandler;

/**
 * Created by caoyouxin on 14-11-6.
 */
public class ReflectorTester {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class commandClass = Class.forName("tester.t1.CHandler");
        Class configClass = Class.forName("tester.t1.CoHandler");
        ConfigHandler ch = (ConfigHandler) configClass.newInstance();
        System.out.println(CommandHandler.class.isAssignableFrom(commandClass));
        System.out.println(ConfigHandler.class.isAssignableFrom(commandClass));
    }

}
