package com.unisal.bubble;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;

public class BolaAmarela extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 240;
	private static final int CAMERA_HEIGHT = 400;
	private Camera camera1;
	private Scene cena1;
	public ITextureRegion texturaBalaoVermelho, texturaBalaoAzul,
			texturaBalaoAmarelo, texturaBalaoVerde, texturaBackground;
	private BuildableBitmapTextureAtlas myBuildableBitmapTextureAtlas;

	@Override
	public EngineOptions onCreateEngineOptions() {

		camera1 = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions motor1 = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(),
				camera1);
		// TODO Auto-generated method stub
		return motor1;
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		final Context contexto1 = getApplicationContext();
		myBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
				mEngine.getTextureManager(), 1024, 1024,
				BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);

		texturaBalaoVermelho = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(myBuildableBitmapTextureAtlas,
						getApplicationContext(), "bolavermelha.png");
		texturaBalaoAzul = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(myBuildableBitmapTextureAtlas,
						getApplicationContext(), "bolaazul.png");
		texturaBalaoAmarelo = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(myBuildableBitmapTextureAtlas,
						getApplicationContext(), "bolamarela.png");
		texturaBalaoVerde = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(myBuildableBitmapTextureAtlas,
						getApplicationContext(), "bolaverde.png");

		ITexture mBackground = null;
		try {
			mBackground = new BitmapTexture(mEngine.getTextureManager(),
					new IInputStreamOpener() {
						@Override
						public InputStream open() throws IOException {
							return contexto1.getAssets().open(
									"gfx/background.png");
						}
					}, BitmapTextureFormat.RGB_565, TextureOptions.BILINEAR);

			mBackground.load();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		texturaBackground = TextureRegionFactory
				.extractFromTexture(mBackground);

		try {
			myBuildableBitmapTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			myBuildableBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		// TODO Auto-generated method stub

	}

	@Override
	protected Scene onCreateScene() {

		Log.d("teste", "onCreateScene ");
		// TODO Auto-generated method stub
		cena1 = new Scene();
		Sprite fundo = new Sprite(0, 0, texturaBackground,
				getVertexBufferObjectManager());
		SpriteBackground fundoa = new SpriteBackground(fundo);
		cena1.setBackground(fundoa);

		final float centerX = CAMERA_WIDTH / 2;
		final float centerY = CAMERA_HEIGHT / 2;
		Sprite bolamarela1 = new Sprite(centerX, centerY, texturaBalaoAmarelo,
				getVertexBufferObjectManager()) {
			@Override
			// TODO Auto-generated method stub
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				this.setPosition(pSceneTouchEvent.getX(),
						pSceneTouchEvent.getY());
				return true;
			}

		};
		cena1.attachChild(bolamarela1);
		cena1.registerTouchArea(bolamarela1);

		cena1.setTouchAreaBindingOnActionDownEnabled(true);

		return cena1;
	}

}
