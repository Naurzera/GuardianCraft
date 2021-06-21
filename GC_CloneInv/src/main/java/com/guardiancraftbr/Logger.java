package com.guardiancraftbr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import java.util.logging.Level;

public class Logger {
    private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Logger.class.getCanonicalName());
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public Logger() {
    }


    /**
     * Prints a message into server's console.
     *
     * @param message Message to be printed.
     */
    public void log(final String message) {
        this.log(Level.INFO, message);
    }

    /**
     * Prints a message into server's console.
     *
     * @param level The message level identifier.
     * @param message Message to be printed.
     */
    public void log(Level level, final String message) {
        logger.log(level, message);
    }

    /**
     * Prints a message into server's console.
     *
     * @param level The message level identifier.
     * @param message Message to be printed.
     * @param throwable Throwable associated with @message.
     */
    public void log(Level level, final String message, Throwable throwable) {
        logger.log(level, message, throwable);
    }

    /**
     * Prints a message into server's console
     * if the given condition is true.
     *
     * @param condition Condition to verify.
     * @param message Message to be sent.
     */
    public void logIf(Boolean condition, final String message) {
        this.logIf(condition, Level.INFO, message);
    }

    /**
     * Prints a message into server's console
     * if the given condition is true.
     *
     * @param condition Condition to verify.
     * @param level The message level identifier.
     * @param message Message to be sent.
     */
    public void logIf(Boolean condition, Level level, final String message) {
        if (condition) {
            this.log(level, message);
        }
    }

    /**
     * Prints a message to the server console.
     *
     * @param condition Condition to verify.
     * @param level The message level identifier.
     * @param message Message to be sent.
     * @param throwable Throwable associated with @message.
     */
    public void logIf(Boolean condition, Level level, final String message, Throwable throwable) {
        if (condition) {
            this.log(level, message, throwable);
        }
    }

    /**
     * Prints a message to the server console.
     *
     * Using this method, you'll be able to log
     * colored message into the server's console.
     *
     * @param message Message to be sent.
     */
    public void logColored(final String message) {
        console.sendMessage(message.replace('&', ChatColor.COLOR_CHAR));
    }
}