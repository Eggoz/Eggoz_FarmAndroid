package com.antino.eggoz.ui.home

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentExoplayerBinding
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory


class ExoplayerFragment(val context:MainActivity,val link:String,val mid:Int) : Fragment() {
    private lateinit var binding: FragmentExoplayerBinding

    lateinit var uri: Uri
    lateinit var loadcontrol: LoadControl
    lateinit var bandwidthMeter: BandwidthMeter
    lateinit var trackSelector: TrackSelector
    lateinit var factory: DefaultHttpDataSourceFactory
    lateinit var extractorFactor: ExtractorsFactory
    lateinit var mediaSource: MediaSource
    lateinit var simpleExoPlayer: SimpleExoPlayer
    var flag=false
    lateinit var btnFullscreen: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExoplayerBinding.inflate(inflater, container, false)
        btnFullscreen=binding.root.findViewById(R.id.bt_fullscreen)

        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                context.loadHome()
                binding.playerView.player.playWhenReady=false
                true
            } else false
        }

        uri=Uri.parse(link)
        loadcontrol= DefaultLoadControl()
        bandwidthMeter= DefaultBandwidthMeter()
        trackSelector= DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadcontrol)

        factory= DefaultHttpDataSourceFactory("exoplayer_video")
        extractorFactor= DefaultExtractorsFactory()
        mediaSource= ExtractorMediaSource(uri, factory, extractorFactor, null, null)

        binding.playerView.player=simpleExoPlayer
        binding.playerView.keepScreenOn=true

        simpleExoPlayer.prepare(mediaSource)
        simpleExoPlayer.playWhenReady=true
        simpleExoPlayer.addListener(
            object : Player.EventListener {
                override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {

                }

                override fun onTracksChanged(
                    trackGroups: TrackGroupArray?,
                    trackSelections: TrackSelectionArray?
                ) {

                }

                override fun onLoadingChanged(isLoading: Boolean) {

                }

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    if (playbackState== Player.STATE_BUFFERING) {
                        binding.progressBar.visibility= View.VISIBLE
                    }else if (playbackState== Player.STATE_READY)
                    {
                        binding.progressBar.visibility= View.GONE
                    }
                }

                override fun onRepeatModeChanged(repeatMode: Int) {
                }

                override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                }

                override fun onPlayerError(error: ExoPlaybackException?) {
                }

                override fun onPositionDiscontinuity(reason: Int) {
                }

                override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
                }

                override fun onSeekProcessed() {
                }

            })

        btnFullscreen.setOnClickListener {
            if (flag){
                btnFullscreen.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_fullscreen_24))
                context.requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                flag=false
            }else{
                btnFullscreen.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_fullscreen_exit_24))
                context.requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                flag=true

            }
        }


        return binding.root
    }
    override fun onPause() {
        super.onPause()
        simpleExoPlayer.playWhenReady=false
        simpleExoPlayer.playbackState
    }

 /*   override fun onRestart() {
        super.onRestart()
        simpleExoPlayer.playWhenReady=true
        simpleExoPlayer.playbackState
    }*/
}