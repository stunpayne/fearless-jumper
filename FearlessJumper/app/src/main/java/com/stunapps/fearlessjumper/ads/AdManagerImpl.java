package com.stunapps.fearlessjumper.ads;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by sunny.s on 11/03/18.
 */

@Singleton
public class AdManagerImpl implements AdManager
{
	private final String ADS_APP_ID = "ca-app-pub-3940256099942544~3347511713";
	private final String ADS_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";

	private final Context context;
	private final RewardedVideoAd mRewardedVideoAd;

	@Inject
	public AdManagerImpl(Context context)
	{
		this.context = context;

		// Use an activity context to get the rewarded video instance.
		MobileAds.initialize(context, ADS_APP_ID);
		mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
//		mRewardedVideoAd.setRewardedVideoAdListener(this);
	}

	@Override
	public void onRewarded(RewardItem reward)
	{
		Toast.makeText(context,
				"onRewarded! currency: " + reward.getType() + "  amount: " + reward.getAmount(),
				Toast.LENGTH_SHORT).show();
		// Reward the user.
	}

	@Override
	public void onRewardedVideoAdLeftApplication()
	{
		Toast.makeText(context, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoAdClosed()
	{
		Toast.makeText(context, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
		loadAd();
	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int errorCode)
	{
		Toast.makeText(context, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoAdLoaded()
	{
		Toast.makeText(context, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoAdOpened()
	{
		Toast.makeText(context, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRewardedVideoStarted()
	{
		Toast.makeText(context, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void loadAd()
	{
		mRewardedVideoAd.loadAd(ADS_UNIT_ID, new AdRequest.Builder().build());
	}

	@Override
	public boolean showAd()
	{
		if (mRewardedVideoAd.isLoaded())
		{
			mRewardedVideoAd.show();
			return true;
		}
		return false;
	}

	@Override
	public void resume()
	{
		mRewardedVideoAd.resume(context);
	}

	@Override
	public void pause()
	{
		mRewardedVideoAd.pause(context);
	}

	@Override
	public void destroy()
	{
		mRewardedVideoAd.destroy(context);
	}
}
