package com.stunapps.fearlessjumper.component.spawnable;

import com.stunapps.fearlessjumper.component.Component;

/**
 * Created by sunny.s on 10/03/18.
 */

public class Enemy extends Component
{
	protected EnemyType enemyType;

	public Enemy(EnemyType enemyType)
	{
		super(Enemy.class);
		this.enemyType = enemyType;
	}

	public EnemyType getEnemyType()
	{
		return enemyType;
	}

	public enum EnemyType
	{
		//	Flying dragon
		MINIGON,

		//	Floating Shooter
		GRORUM,

		//	Grounded Fire
		PYRADON,

		// Assault
		DRAXUS,

		// Following
		ZELDROY;

	}

	@Override
	public Component cloneComponent() throws CloneNotSupportedException
	{
		return new Enemy(enemyType);
	}
}
