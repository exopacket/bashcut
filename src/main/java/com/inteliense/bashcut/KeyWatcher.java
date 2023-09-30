package com.inteliense.bashcut;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyWatcher implements NativeKeyListener {

	private static File config = null;
	private static ArrayList<Key> keys = new ArrayList<>();

	public static void main(String[] args) throws FileNotFoundException {

		config = new File("/etc/bashcut/keys.conf");
		if(!config.exists()) System.exit(2);

		Scanner scnr = new Scanner(config);
		while(scnr.hasNextLine()) {
			String line = scnr.nextLine();
			String[] parts = line.split(":");
			String keyName = parts[0];
			String script = parts[1];
			keys.add(new Key(keyName, fromKeyString(keyName), script));
		}

		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new KeyWatcher());
	}

	public void nativeKeyPressed(NativeKeyEvent e) {
		int code = e.getKeyCode();
		System.out.println(e.getKeyCode());
		for (Key key : keys) {
			if (key.getKeyCode() == code) {
				new Thread(() -> {
					BashcutApplication.main(new String[]{key.getScript()});
				}).start();
				new Thread(() -> {
					try {
						Thread.sleep(250);
						BashcutApplication.println("test");
					} catch (InterruptedException ex) {
						throw new RuntimeException(ex);
					}
				}).start();
				return;
			}
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) { }

	public void nativeKeyTyped(NativeKeyEvent e) { }

	private static int fromKeyString(String key) {
		if(key.equals("F1")) return 77;
		if(key.equals("F2")) return 78;
		if(key.equals("F3")) return 79;
		if(key.equals("F4")) return 80;
		if(key.equals("F5")) return 81;
		if(key.equals("F6")) return 82;
		if(key.equals("F7")) return 83;
		if(key.equals("F8")) return 84;
		if(key.equals("F9")) return 85;
		if(key.equals("F10")) return 86;
		if(key.equals("F11")) return 87;
		if(key.equals("F12")) return 88;
		return 0;
	}

	private static class Key {
		private String key;
		private int keyCode;
		private String script;

		public Key(String key, int keyCode, String script) {
			this.key = key;
			this.keyCode = keyCode;
			this.script = script;
			if(!(new File(script)).exists()) System.exit(3);
		}

		public String getKey() {
			return key;
		}

		public String getScript() {
			return script;
		}

		public int getKeyCode() {
			return keyCode;
		}
	}

}
