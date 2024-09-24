package com.ferias.game.pernasdepau.input;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Command {
	MOVE_RIGHT(),
	MOVE_RIGHT_AWSD,
	MOVE_RIGHT_ARROWS_PAD,
	MOVE_RIGHT_JIKL,
	MOVE_RIGHT_NUMERIC_PAD,
	
	
	MOVE_LEFT,
	MOVE_LEFT_AWSD,
	MOVE_LEFT_ARROWS_PAD,
	MOVE_LEFT_JIKL,
	MOVE_LEFT_NUMERIC_PAD,
	
	
	MOVE_DOWN,
	MOVE_DOWN_AWSD,
	MOVE_DOWN_ARROWS_PAD,
	MOVE_DOWN_JIKL,
	MOVE_DOWN_NUMERIC_PAD,
	
	
	MOVE_UP,
	MOVE_UP_AWSD,
	MOVE_UP_ARROWS_PAD,
	MOVE_UP_JIKL,
	MOVE_UP_NUMERIC_PAD;
	

	static private Map<Command, int[]> keysMap = new HashMap<Command, int[]>();
	
	static {
		keysMap.put(MOVE_RIGHT, new int[]{KeyEvent.VK_RIGHT, KeyEvent.VK_KP_RIGHT, KeyEvent.VK_6, KeyEvent.VK_D});
		keysMap.put(MOVE_LEFT, new int[]{KeyEvent.VK_LEFT, KeyEvent.VK_KP_LEFT, KeyEvent.VK_NUMPAD4, KeyEvent.VK_A});
		keysMap.put(MOVE_DOWN, new int[]{KeyEvent.VK_DOWN, KeyEvent.VK_KP_DOWN, KeyEvent.VK_NUMPAD2, KeyEvent.VK_S});
		keysMap.put(MOVE_UP, new int[]{KeyEvent.VK_UP, KeyEvent.VK_KP_UP, KeyEvent.VK_NUMPAD8, KeyEvent.VK_W});
		
		keysMap.put(MOVE_RIGHT_NUMERIC_PAD, new int[]{KeyEvent.VK_NUMPAD6});
		keysMap.put(MOVE_LEFT_NUMERIC_PAD, new int[]{KeyEvent.VK_NUMPAD4});
		keysMap.put(MOVE_DOWN_NUMERIC_PAD,new int[]{KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD5});
		keysMap.put(MOVE_UP_NUMERIC_PAD, new int[]{KeyEvent.VK_NUMPAD8});
		
		keysMap.put(MOVE_RIGHT_AWSD, new int[]{KeyEvent.VK_D});
		keysMap.put(MOVE_LEFT_AWSD, new int[]{KeyEvent.VK_A});
		keysMap.put(MOVE_DOWN_AWSD, new int[]{KeyEvent.VK_S});
		keysMap.put(MOVE_UP_AWSD, new int[]{KeyEvent.VK_W});
		
		keysMap.put(MOVE_RIGHT_JIKL, new int[]{KeyEvent.VK_L});
		keysMap.put(MOVE_LEFT_JIKL, new int[]{KeyEvent.VK_J});
		keysMap.put(MOVE_DOWN_JIKL, new int[]{KeyEvent.VK_K});
		keysMap.put(MOVE_UP_JIKL, new int[]{KeyEvent.VK_I});
		
		keysMap.put(MOVE_RIGHT_ARROWS_PAD, new int[]{KeyEvent.VK_RIGHT, KeyEvent.VK_KP_RIGHT});
		keysMap.put(MOVE_LEFT_ARROWS_PAD, new int[]{KeyEvent.VK_LEFT, KeyEvent.VK_KP_LEFT});
		keysMap.put(MOVE_DOWN_ARROWS_PAD, new int[]{KeyEvent.VK_DOWN, KeyEvent.VK_KP_DOWN});
		keysMap.put(MOVE_UP_ARROWS_PAD, new int[]{KeyEvent.VK_UP, KeyEvent.VK_KP_UP});
	}

	public int[] getKeys() {
		return Command.keysMap.get(this);
	}

	public boolean isEquivalent(Command commandName) {
		return this.name().length() > commandName.name().length() ?
				this.name().substring(0, commandName.name().length()).equals(commandName.name()) :
				commandName.name().substring(0, this.name().length()).equals(this.name());
	}
	
	public Command getEquivalent() {
		Command[] base = {Command.MOVE_DOWN, Command.MOVE_UP, Command.MOVE_LEFT, Command.MOVE_RIGHT};
		
		for (Command cbase : base) {
			if (isEquivalent(cbase)) {
				return cbase;
			}
		}
		
		return this;
	}
	
	public static List<Command> getCommands(CommandSet set) {
		return Arrays.asList(Command.values()).stream().filter(c -> c.name().contains(set.name())).collect(Collectors.toList());
	}
	
	public CommandSet getCommandSet() {
		for (CommandSet set : CommandSet.values()) {
			if (this.name().contains(set.name()))
					return set;
		}
		return null;
	}
}
