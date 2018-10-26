package com.wojones.pianopushups;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

class TickTock {

    private final int MILLISEC_PER_MINUTE = 60000;
    private Context _context;
    private MediaPlayer _mpTick, _mpTock;
    private Handler _handler;
    private Runnable _runnable;
    private boolean _running = false;
    private int _bpm = 30;

    public TickTock() {
        _handler = new Handler();
        _runnable = new Runnable() {
            @Override
            public void run() {
                if (! _running) {
                    Log.d("HANDLER", "Quitting!");
                    return;
                }
                Log.d("HANDLER", "Playing!");
                _handler.postDelayed(_runnable, MILLISEC_PER_MINUTE / _bpm);

                play();
            }
        };
    }

    public TickTock(Context ctx) {
        this();
        _context = ctx;
    }

    public void play() {
        _mpTick = MediaPlayer.create(_context, R.raw.tick);
        _mpTick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        _mpTick.start();
    }

    public void play(Context ctx) {
        final MediaPlayer mp = MediaPlayer.create(ctx, R.raw.tick);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                // FOR NOW: set not running, as it "self-stops"; when the handler
                // starts calling postDelayed then remove this here
                _running = false;
            }
        });
        mp.start();
    }

    public void setCtx(Context ctx) {
        _context = ctx;
    }

    public void start(Context ctx) {
        _context = ctx;
        start();
    }

    public void start() {
        if (_running) {
            // Loud warning would be good, or perhaps return errors
            return;
        }
        _running = true;
        play();
        _handler.postDelayed(_runnable, MILLISEC_PER_MINUTE / _bpm);

    }

    public void stop() {
        _running = false;
    }

    public void setBPM(int bpm) {
        if (0 != bpm) {
            _bpm = bpm;
        }
    }

    public boolean running() {
        return _running;
    }
}
