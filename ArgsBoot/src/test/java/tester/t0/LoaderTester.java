package tester.t0;

import argsboot.Loader;
import argsboot.RuntimeHelper;
import argsboot.loader.DefaultLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import utils.ArrayUtils;

/**
 * Created by caoyouxin on 14-11-4.
 */
public class LoaderTester {

    private class AA {

    }

    public void fn () {
        AA aa = new AA();
    }

    public static final boolean debugging = true;

    public static void main(String[] args) {
//        EventHandler<ActionEvent> eventHandler = ;
        Button btn = new Button();
        btn.setOnAction(
            (event) -> {

            }
        );

        LoaderTester lt = new LoaderTester();


        Loader loader = new DefaultLoader();
        loader.load(ArrayUtils.asSet("a"), new String[0], RuntimeHelper.val());
    }

}
