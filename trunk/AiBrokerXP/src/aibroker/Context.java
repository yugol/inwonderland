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

    public static File getBackupFolder_logs() {
        return new File(getBackupFolderPath(), "logs");
    }

    public static String getBackupFolderPath() {
        final String exportFolder = get(FOLDER_BACKUP_KEY, System.getProperty("user.home") + "/AiBrokerXP/backup/");
        new File(exportFolder).mkdirs();
        return exportFolder;
    }

    public static String getExportFolderPath() {
        final String exportFolder = get(FOLDER_EXPORT_KEY, System.getProperty("user.home") + "/AiBrokerXP/export/");
        new File(exportFolder).mkdirs();
        return exportFolder;
    }

    public static String getLogFolderPath() {
        final String logFolder = get(FOLDER_LOG_KEY, System.getProperty("user.home") + "/AiBrokerXP/logfiles/");
        new File(logFolder).mkdirs();
        return logFolder;
    }

    public static Logger getLogger(final Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Rectangle getManagerWindowBounds() {
        final String bounds = get(MANAGER_WINDOW_BOUNDS_KEY, null);
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
        final String foo = get(MONITOR_LAST_DATABASE_KEY, null);
        if (foo != null) { return Databases.valueOf(foo); }
        return null;
    }

    public static String getMonitorLastSequence() {
        return get(MONITOR_LAST_SEQUENCE_KEY, null);
    }

    public static Rectangle getMonitorWindowBounds() {
        final String bounds = get(MONITOR_WINDOW_BOUNDS_KEY, null);
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
        final String quotesFolder = get(FOLDER_QUOTES_KEY, System.getProperty("user.home") + "/AiBrokerXP/quotes/");
        new File(quotesFolder).mkdirs();
        return quotesFolder;
    }

    public static String getSibexCloseTime() {
        return get(SIBEX_CLOSE_TIME_KEY, "23:30:00");
    }

    public static String getSibexLogFilePath() {
        return getLogFolderPath() + "/sibex-quotes-" + Moment.getNow().toIsoDate() + ".csv";
    }

    public static String getSibexOpenTime() {
        return get(SIBEX_OPEN_TIME_KEY, "09:55:00");
    }

    public static long getSibexPollInterval() {
        return Long.parseLong(get(SIBEX_POLL_INTERVAL_KEY, 5000));
    }

    public static String getWekaFolder() {
        final String wekaFolder = get(FOLDER_WEKA_KEY, System.getProperty("user.home") + "/AiBrokerXP/wekafiles/");
        new File(wekaFolder).mkdirs();
        return wekaFolder;
    }

    public static void setManagerWindowBounds(final Rectangle val) {
        if (val == null) {
            instance.remove(MANAGER_WINDOW_BOUNDS_KEY);
        } else {
            final StringBuilder bounds = new StringBuilder();
            bounds.append(val.x).append(",").append(val.y).append(",");
            bounds.append(val.width).append(",").append(val.height);
            instance.put(MANAGER_WINDOW_BOUNDS_KEY, bounds.toString());
        }
        instance.store();
    }

    public static void setMonitorLastDatabase(final Databases val) {
        if (val == null) {
            instance.remove(MONITOR_LAST_DATABASE_KEY);
        } else {
            instance.put(MONITOR_LAST_DATABASE_KEY, val.toString());
        }
        instance.store();
    }

    public static void setMonitorLastName(final String val) {
        if (val == null) {
            instance.remove(MONITOR_LAST_SEQUENCE_KEY);
        } else {
            instance.put(MONITOR_LAST_SEQUENCE_KEY, val);
        }
        instance.store();
    }

    public static void setMonitorWindowBounds(final Rectangle val) {
        if (val == null) {
            instance.remove(MONITOR_WINDOW_BOUNDS_KEY);
        } else {
            final StringBuilder bounds = new StringBuilder();
            bounds.append(val.x).append(",").append(val.y).append(",");
            bounds.append(val.width).append(",").append(val.height);
            instance.put(MONITOR_WINDOW_BOUNDS_KEY, bounds.toString());
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

    private static String get(final String key, final Object defaultValue) {
        String value = (String) instance.get(key);
        if (isNullOrBlank(value)) {
            value = defaultValue == null ? null : String.valueOf(defaultValue);
            if (defaultValue != null) {
                logger.warn(key + " is not specified! Using: " + value);
                instance.put(key, value);
                instance.store();
            }
        }
        return value;
    }

    public static final String   APPLICATION_NAME              = "AiBrokerXP";

    private static final String  PROPERTIES_FILE_NAME          = "aibroker.properties";
    private static final String  FOLDER_BACKUP_KEY             = "folder.backup";
    private static final String  FOLDER_EXPORT_KEY             = "folder.export";
    private static final String  FOLDER_LOG_KEY                = "folder.log";
    private static final String  FOLDER_QUOTES_KEY             = "folder.quotes";
    private static final String  FOLDER_WEKA_KEY               = "folder.weka";
    private static final String  MANAGER_WINDOW_BOUNDS_KEY     = "manager.window.bounds";
    private static final String  MONITOR_LAST_DATABASE_KEY     = "monitor.lastDatabase";
    private static final String  MONITOR_LAST_SEQUENCE_KEY     = "monitor.lastSequence";
    private static final String  MONITOR_WINDOW_BOUNDS_KEY     = "monitor.window.bounds";
    private static final String  SIBEX_CLOSE_TIME_KEY          = "sibex.close.time";
    private static final String  SIBEX_OPEN_TIME_KEY           = "sibex.open.time";
    private static final String  SIBEX_POLL_INTERVAL_KEY       = "sibex.poll.interval";

    public static final int      FIRST_YEAR                    = 2000;
    public static final int      LAST_YEAR                     = 2015;

    public static final int      FUTURES_MASS_UPDATE_LAST_YEAR = 2014;
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
        props.setProperty("log4j.appender.fileAppender.File", getLogFolderPath() + "/HistoricMarketTest.log");
        props.setProperty("log4j.appender.quotesAppender.File", getSibexLogFilePath());
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
