package com.example.minesweeper;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Radio extends Thread {
    private final BlockingQueue<String> titleQueue = new LinkedBlockingQueue<>();
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    public void addTitle(String title) {
        titleQueue.offer(title);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!titleQueue.isEmpty() && !isPlaying) {
                    String title = titleQueue.take();
                    String absolutePath = "/com/example/minesweeper/game/" + title + ".wav";
                    playSound(absolutePath);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void playSound(String absolutePath) {
        Media media = new Media(getClass().getResource(absolutePath).toExternalForm());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.dispose();
            isPlaying = false;
            playNextSound();
        });

        mediaPlayer.play();
        isPlaying = true;
    }

    private void playNextSound() {
        if (!titleQueue.isEmpty()) {
            String nextTitle = titleQueue.poll();
            String nextAbsolutePath = "/com/example/minesweeper/game/" + nextTitle + ".wav";
            playSound(nextAbsolutePath);
        }
    }
}
/*
Omg es ist eine 8
wua wua wua wuaaaaaaao
Kabuuuuooom
Hahahaha
Das war koplett falsch
Sakatisch: Gute Gemacht

 */