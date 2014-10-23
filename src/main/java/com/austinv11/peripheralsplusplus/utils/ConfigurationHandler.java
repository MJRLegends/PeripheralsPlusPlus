package com.austinv11.peripheralsplusplus.utils;

import com.austinv11.peripheralsplusplus.reference.Config;
import com.austinv11.peripheralsplusplus.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {

	public static Configuration config;

	public static void init(File configFile){
		if (config == null) {
			config = new Configuration(configFile);
		}
		loadConfiguration();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}

	private static void loadConfiguration(){
		try{//Load & read properties
			config.load();
			boolean enableChatBox = config.get("Chatbox", "enableChatBox", true, "If disabled, the recipe will be disabled and the current peripherals would cease to work").getBoolean(true);
			boolean logCoords = config.get("Chatbox", "logCoords", true, "Log the Chat Box peripheral's coordinates when it says a message, disabling this allows for 'naming' the chat box").getBoolean(true);
			double readRange = config.get("Chatbox", "readRange", -1.0, "Range for the Chat Box peripheral's reading. Negative values indicate infinite").getDouble(-1.0);
			double sayRange = config.get("Chatbox", "sayRange", 64.0, "Range for the Chat Box peripheral's say/tell function. Negative values indicate infinite").getDouble(64.0);
			int sayRate = config.get("Chatbox", "sayRate", 1, "Maximum number of messages per second a Chat Box peripheral can say").getInt(1);
			boolean allowUnlimitedVertical = config.get("Chatbox", "allowUnlimitedVertical", true, "Allow the Chat Box peripheral to send messages with unlimited vertical distance, but only if so the program chooses").getBoolean(true);
			reSyncConfig(enableChatBox, logCoords, readRange, sayRange, sayRate, allowUnlimitedVertical);
		}catch (Exception e){//Log exception
			Logger.warn("Config exception!");
			Logger.warn(e.getStackTrace());
		}finally {//Save
			if (config.hasChanged()) {
				config.save();
			}
		}
	}

	private static void reSyncConfig(boolean v0, boolean v1, double v2, double v3, int v4, boolean v5){
		Config.enableChatBox = v0;
		Config.logCoords = v1;
		Config.readRange = v2;
		Config.sayRange = v3;
		Config.sayRate = v4;
		Config.allowUnlimitedVertical = v5;
	}
}
