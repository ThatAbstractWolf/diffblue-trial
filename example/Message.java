package net.amongmc.core.utils;

import net.amongmc.common.C;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class Message {

    private static final String SERVER_NAME = GradientUtil.text("Test", true, Color.CYAN, Color.ORANGE);
    private static final String LINE = GradientUtil.text("                                                                                ", C.STRIKE, Color.CYAN, Color.WHITE, Color.CYAN, Color.WHITE, Color.CYAN);

    public static void sendMessageWithoutPrefix(CommandSender sender, String message) { sender.sendMessage(C.GRAY + C.translate(message)); }

    public static void sendMessage(CommandSender sender, String message) {
        sendMessage(sender, "[Test]", message);
    }

    public static void sendMessage(CommandSender sender, String prefix, String message) { sender.sendMessage(C.RED + prefix + " " + C.GRAY + C.translate(message)); }

    public static String getLine() {
        return LINE;
    }

    public static String getServerName() {
        return SERVER_NAME;
    }
}
