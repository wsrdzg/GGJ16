package ggj16.ka.bluetooth.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import ggj16.ka.bluetooth.Main;

public class DesktopLauncher {

	private static final boolean RUN_APP = true, PACK_TEXTURES = true;

	public static void main(String[] args) {
		if (PACK_TEXTURES) {
			TexturePacker.Settings settings = new TexturePacker.Settings();
			settings.pot = true;
			settings.paddingX = 2;
			settings.paddingY = 2;
			settings.bleed = true;
			settings.edgePadding = false;
			settings.duplicatePadding = true;
			settings.filterMag = Texture.TextureFilter.Linear;
			settings.filterMin = Texture.TextureFilter.Linear;
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;

			TexturePacker.process(settings, "../unpacked_assets/textures/", "../assets/textures/", "t");
		}

		if (RUN_APP) {
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.width = 500;
			config.height = 700;
			config.useHDPI = true;
			new LwjglApplication(new Main(), config);
		}
	}
}