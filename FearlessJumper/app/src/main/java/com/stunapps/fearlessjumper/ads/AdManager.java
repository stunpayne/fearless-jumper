package com.stunapps.fearlessjumper.ads;

import com.google.android.gms.ads.reward.RewardedVideoAdListener;

/**
 * Created by sunny.s on 11/03/18.
 */

public interface AdManager extends RewardedVideoAdListener
{
	void loadAd();

	boolean showAd();

	void resume();

	void pause();

	void destroy();
}
