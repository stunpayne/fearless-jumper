package com.stunapps.fearlessjumper.core;

import android.content.SharedPreferences;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.helper.Environment;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by anand.verma on 09/03/18.
 */

@Singleton
public class GameStatsManager
{
	private static final SharedPreferences dataReader = Environment.SHARED_PREFERENCES;
	private static final SharedPreferences.Editor dataWriter =
			Environment.SHARED_PREFERENCES.edit();
	public static final String CURRENT_SCORE = "current_score";
	public static final String PREVIOUS_SCORE_1 = "previous_score1";
	public static final String PREVIOUS_SCORE_2 = "previous_score2";
	public static final String PREVIOUS_SCORE_3 = "previous_score3";
	public static final String SESSION_HIGH_SCORE = "session_high_score";
	public static final String GLOBAL_HIGH_SCORE = "global_high_score";
	public static final String ENEMY_ENCOUNTERED = "enemy_encountered";
	public static final String DEATH_STAT = "death_stat";

	public void onGameStart()
	{
		updateGamePlayCount();
		movePrevious3Scores();
	}

	public void onGameReStart()
	{
		updateGamePlayCount();
		movePrevious3Scores();
	}

	public void onGameOver(String deathBy)
	{
		updateGameScoreStats();
		dataWriter.putString(DEATH_STAT, deathBy);
	}

	public void onGamePause()
	{
		updateGameScoreStats();
	}

	public void onGameExit()
	{
		updateGameScoreStats();
	}

	/**
	 * Call it whenever score is updated.
	 *
	 * @param currentScore
	 */
	public void updateCurrentScore(long currentScore)
	{
		dataWriter.putLong(CURRENT_SCORE, currentScore);
	}

	/**
	 * Call it when any enemy encountered
	 *
	 * @param enemy
	 */
	public void enemyEncountered(String enemy)
	{
		Set<String> enemyEncountered =
				new HashSet<>(dataReader.getStringSet(ENEMY_ENCOUNTERED, new HashSet<String>()));
		if (!enemyEncountered.contains(enemy))
		{
			enemyEncountered.add(enemy);
			dataWriter.putStringSet(ENEMY_ENCOUNTERED, enemyEncountered);
		}
	}

	/**
	 * Maintain previous 3 scores.
	 */
	private void movePrevious3Scores()
	{

		long previousScore2 = dataReader.getLong(PREVIOUS_SCORE_2, 0l);
		dataWriter.putLong(PREVIOUS_SCORE_3, previousScore2);

		long previousScore1 = dataReader.getLong(PREVIOUS_SCORE_1, 0l);
		dataWriter.putLong(PREVIOUS_SCORE_2, previousScore1);

		dataWriter.putLong(PREVIOUS_SCORE_1, 0l);
	}

	private void updateGameScoreStats()
	{
		long currentScore = dataReader.getLong(CURRENT_SCORE, 0l);

		long sessionHighScore = dataReader.getLong(SESSION_HIGH_SCORE, 0l);

		if (currentScore > sessionHighScore)
		{
			dataWriter.putLong(SESSION_HIGH_SCORE, currentScore);
			long globalHighScore = dataReader.getLong(GLOBAL_HIGH_SCORE, 0l);
			if (currentScore > globalHighScore)
			{
				dataWriter.putLong(GLOBAL_HIGH_SCORE, currentScore);
			}
		}
	}

	private void updateGamePlayCount()
	{
		int gamePlayCount = dataReader.getInt("game_play_count", 1);
		dataWriter.putInt("game_play_count", (gamePlayCount + 1));
	}
}
