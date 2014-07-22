package aibroker;

import static aibroker.util.StringUtil.isNullOrBlank;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.util.Moment;
import aibroker.util.convenience.Databases;

@SuppressWarnings("serial")
public final class Context extends Properties {

    public static String getLogFolder() {
        String logFolder = (String) instance.get(LOG_FOLDER);
        if (isNullOrBlank(logFolder)) {
            logFolder = System.getProperty("user.home") + "/AiBrokerXP/logfiles/";
            logger.warn(LOG_FOLDER + " is not specified! Using: " + logFolder);
            instance.put(LOG_FOLDER, logFolder);
            instance.store();
        }
        new File(logFolder).mkdirs();
        return logFolder;
    }

    public static Logger getLogger(final Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Rectangle getManagerWindowBounds() {
        final String bounds = (String) instance.get(MANAGER_WINDOW_BOUNDS);
        if (isNullOrBlank(bounds)) {
            return new Rectangle(100, 100, 1024, 768);
        } else {
            final String[] values = bounds.split(",");
            return new Rectangle(Integer.parseInt(values[0]),
                    Integer.parseInt(values[1]), Integer.parseInt(values[2]),
                    Integer.parseInt(values[3]));
        }
    }

    public static Databases getMonitorLastDatabase() {
        final String foo = (String) instance.get(MONITOR_LAST_DATABASE);
        if (foo != null) { return Databases.valueOf(foo); }
        return null;
    }

    public static String getMonitorLastSequence() {
        return (String) instance.get(MONITOR_LAST_SEQUENCE);
    }

    public static Rectangle getMonitorWindowBounds() {
        final String bounds = (String) instance.get(MONITOR_WINDOW_BOUNDS);
        if (isNullOrBlank(bounds)) {
            return new Rectangle(100, 100, 800, 600);
        } else {
            final String[] values = bounds.split(",");
            return new Rectangle(Integer.parseInt(values[0]),
                    Integer.parseInt(values[1]), Integer.parseInt(values[2]),
                    Integer.parseInt(values[3]));
        }
    }

    public static String getQuotesFolder() {
        String quotesFolder = (String) instance.get(QUOTES_FOLDER);
        if (isNullOrBlank(quotesFolder)) {
            quotesFolder = System.getProperty("user.home") + "/AiBrokerXP/quotes/";
            logger.warn(QUOTES_FOLDER + " is not specified! Using: " + quotesFolder);
            instance.put(QUOTES_FOLDER, quotesFolder);
            instance.store();
        }
        new File(quotesFolder).mkdirs();
        return quotesFolder;
    }

    public static String getWekaFolder() {
        String wekaFolder = (String) instance.get(WEKA_FOLDER);
        if (isNullOrBlank(wekaFolder)) {
            wekaFolder = System.getProperty("user.home") + "/AiBrokerXP/wekafiles/";
            logger.warn(WEKA_FOLDER + " is not specified! Using: " + wekaFolder);
            instance.put(WEKA_FOLDER, wekaFolder);
            instance.store();
        }
        new File(wekaFolder).mkdirs();
        return wekaFolder;
    }

    public static void setManagerWindowBounds(final Rectangle val) {
        if (val == null) {
            instance.remove(MANAGER_WINDOW_BOUNDS);
        } else {
            final StringBuilder bounds = new StringBuilder();
            bounds.append(val.x).append(",").append(val.y).append(",");
            bounds.append(val.width).append(",").append(val.height);
            instance.put(MANAGER_WINDOW_BOUNDS, bounds.toString());
        }
        instance.store();
    }

    public static void setMonitorLastDatabase(final Databases val) {
        if (val == null) {
            instance.remove(MONITOR_LAST_DATABASE);
        } else {
            instance.put(MONITOR_LAST_DATABASE, val.toString());
        }
        instance.store();
    }

    public static void setMonitorLastName(final String val) {
        if (val == null) {
            instance.remove(MONITOR_LAST_SEQUENCE);
        } else {
            instance.put(MONITOR_LAST_SEQUENCE, val);
        }
        instance.store();
    }

    public static void setMonitorWindowBounds(final Rectangle val) {
        if (val == null) {
            instance.remove(MONITOR_WINDOW_BOUNDS);
        } else {
            final StringBuilder bounds = new StringBuilder();
            bounds.append(val.x).append(",").append(val.y).append(",");
            bounds.append(val.width).append(",").append(val.height);
            instance.put(MONITOR_WINDOW_BOUNDS, bounds.toString());
        }
        instance.store();
    }

    public static void setupContext(final String applicationName) {
        try {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", applicationName);
            System.setProperty("com.apple.macos.useScreenMenuBar", "true");
            System.setProperty("apple.laf.useScreenMenuBar", "true"); // for older versions of Java
        } catch (final SecurityException e) {
            /* probably running via webstart, do nothing */
        }
    }

    public static final String   APPLICATION_NAME              = "AI-Broker XP";

    private static final String  PROPERTIES_FILE_NAME          = "aibroker.properties";
    private static final String  MONITOR_WINDOW_BOUNDS         = "monitor.window.bounds";
    private static final String  MONITOR_LAST_DATABASE         = "monitor.lastDatabase";
    private static final String  MONITOR_LAST_SEQUENCE         = "monitor.lastSequence";
    private static final String  MANAGER_WINDOW_BOUNDS         = "manager.window.bounds";

    private static final String  LOG_FOLDER                    = "logFolder";
    private static final String  QUOTES_FOLDER                 = "quotesFolder";
    private static final String  WEKA_FOLDER                   = "wekaFolder";

    public static final int      FIRST_YEAR                    = 2000;
    public static final int      LAST_YEAR                     = 2015;
    public static final int      FUTURES_MASS_UPDATE_LAST_YEAR = 2014;

    public static final long     SIBEX_REFRESH_FREQUENCY       = 5000;

    private static final Logger  logger                        = LoggerFactory.getLogger(Context.class);

    private static final Context instance                      = new Context(PROPERTIES_FILE_NAME);

    static {
        final Properties props = new Properties();
        try {
            final InputStream configStream = Context.class.getResourceAsStream("/log4j.properties");
            props.load(configStream);
            configStream.close();
        } catch (final IOException e) {
            System.out.println("Error: Cannot load configuration file ");
        }
        props.setProperty("log4j.appender.fileAppender.File", getLogFolder() + "/HistoricMarketTest.log");
        props.setProperty("log4j.appender.quotesAppender.File", getLogFolder() + "/sibex-quotes-" + Moment.getNow().toIsoDate() + ".csv");
        LogManager.resetConfiguration();
        PropertyConfigurator.configure(props);
    }

    private final File           file;

    private Context(final String fileName) {
        file = new File(fileName);
        logger.info("Using properties file " + file.getAbsolutePath());
        try {
            if (file.exists()) {
                final Reader reader = new FileReader(file);
                load(reader);
                reader.close();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void store() {
        try {
            final Writer writer = new FileWriter(file);
            store(writer, null);
            writer.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
