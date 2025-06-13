package com.krolewskie_potyczki;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;

public class AudioManager {
    private static AudioManager inst;
    private final Music menuMusic;
    private final Music gameMusic;

    private AudioManager() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/MenuSoundtrack.mp3"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/GameSoundtrack.mp3"));
        menuMusic.setLooping(true);
        gameMusic.setLooping(true);
        Preferences prefs = Gdx.app.getPreferences("MyGameSettings");
        float savedVolume = prefs.getFloat("musicVolume", 0.5f);
        menuMusic.setVolume(savedVolume);
        gameMusic.setVolume(savedVolume);
    }

    public static AudioManager inst() {
        if (inst == null) inst = new AudioManager();
        return inst;
    }

    public void playMenuMusic() {
        gameMusic.stop();
        if (!menuMusic.isPlaying()) menuMusic.play();
    }

    public void playGameMusic() {
        menuMusic.stop();
        if (!gameMusic.isPlaying()) gameMusic.play();
    }

    public void pauseMenuMusic() {
        menuMusic.pause();
    }

    public void pauseGameMusic() {
        if (gameMusic.isPlaying()) gameMusic.pause();
    }

    public void resumeGameMusic() {
        if (!gameMusic.isPlaying()) gameMusic.play();
    }

    public void setVolume(float vol) {
        menuMusic.setVolume(vol);
        gameMusic.setVolume(vol);
    }

    public void dispose() {
        menuMusic.dispose();
        gameMusic.dispose();
    }

    public boolean menuMusicIsPlaying() {
        return menuMusic.isPlaying();
    }
}
