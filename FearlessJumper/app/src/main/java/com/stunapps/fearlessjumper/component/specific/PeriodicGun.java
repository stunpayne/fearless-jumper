package com.stunapps.fearlessjumper.component.specific;

import com.stunapps.fearlessjumper.component.Component;
import com.stunapps.fearlessjumper.instantiation.prefab.Prefab;
import com.stunapps.fearlessjumper.instantiation.prefab.PrefabRef;

/**
 * Created by sunny.s on 10/03/18.
 */

public class PeriodicGun extends Component
{
	private PrefabRef bulletPrefab;

	private long intervalMillis;
	private long lastShootTime;

	public PeriodicGun(long intervalMillis)
	{
		super(PeriodicGun.class);
		bulletPrefab = PrefabRef.BULLET;
		this.intervalMillis = intervalMillis;
	}

	public Prefab shoot()
	{
		lastShootTime = System.currentTimeMillis();
		return bulletPrefab.get();
	}

	public boolean canShoot()
	{
		return lastShootTime + intervalMillis < System.currentTimeMillis();
	}

	@Override
	public Component cloneComponent() throws CloneNotSupportedException
	{
		return new PeriodicGun(intervalMillis);
	}
}
