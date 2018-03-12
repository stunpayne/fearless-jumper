package com.stunapps.fearlessjumper.manager;

import android.content.SharedPreferences;

import com.google.inject.Singleton;
import com.stunapps.fearlessjumper.game.Environment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by anand.verma on 09/03/18.
 */

@Singleton
public class GameStatsManager
{
	private static final String TAG = GameStatsManager.class.getSimpleName();
	public static final String ENEMIES_ENCOUNTERED = "enemies-encountered";

	private static SharedPreferences dataReader;
	private static SharedPreferences.Editor dataWriter;

	private static final String GAME_PLAY_COUNT = "game_play_count";
	private static final String CURRENT_SCORE = "current_score";
	private static final String PREVIOUS_SCORE_ = "previous_score";
	private static final String PREVIOUS_SCORE_1 = "previous_score1";
	private static final String PREVIOUS_SCORE_2 = "previous_score2";
	private static final String PREVIOUS_SCORE_3 = "previous_score3";
	private static final String SESSION_HIGH_SCORE = "session_high_score";
	private static final String GLOBAL_HIGH_SCORE = "global_high_score";
	private static final String TERMINATED_BY = "terminated_by";

	public GameStatsManager()
	{
		dataReader = Environment.SHARED_PREFERENCES;
		dataWriter = dataReader.edit();
		dataWriter.apply();
	}

	public void handleGameStart()
	{
		maintainScoreHistory();
		resetCurrentScore();
		resetDeathStat();
		increaseGamePlayCount();
	}

	public void resetGameStats(){
		resetSessionHighScore();
		resetCurrentScore();
		resetDeathStat();
	}

	public void updateCurrentScore(long currentScore)
	{
		updateScore(currentScore);
		updateGameScoreStats();
	}

	public void updateDeathStat(String enemyName)
	{
		updateHurtStat(enemyName);
		dataWriter.putString(TERMINATED_BY, enemyName);
		dataWriter.apply();
	}

	public void updateHurtStat(String enemyName)
	{
		Set<String> enemiesEncountered =
				dataReader.getStringSet(ENEMIES_ENCOUNTERED, new HashSet<String>());

		if (!enemiesEncountered.contains(enemyName))
		{
			enemiesEncountered.add(enemyName);
			dataWriter.putStringSet(ENEMIES_ENCOUNTERED, enemiesEncountered);
		}
		int hurtCount = dataReader.getInt(enemyName, 0);
		dataWriter.putInt(enemyName, hurtCount + 1);
		dataWriter.apply();
	}

	public long getGlobalHighScore()
	{
		return dataReader.getLong(GLOBAL_HIGH_SCORE, 0L);
	}

	public long getSessionHighScore()
	{
		return dataReader.getLong(SESSION_HIGH_SCORE, 0L);
	}

	public long getCurrentScore()
	{
		return dataReader.getLong(CURRENT_SCORE, 0L);
	}

	public long getAverageScore()
	{
		long historicalScore = 0L;
		List<Long> scoreHistory = getScoreHistory();
		for (Long score : scoreHistory)
		{
			historicalScore += score;
		}
		int maxGamePlayCountForStats = Math.min(Math.max(getGamePlayCount(), 1), 4);
		return (historicalScore + getCurrentScore()) / maxGamePlayCountForStats;
	}

	public String getDeathStat()
	{
		return dataReader.getString(TERMINATED_BY, "");
	}

	public Map<String, Integer> getHurtStats()
	{
		Set<String> enemiesEncontered =
				dataReader.getStringSet(ENEMIES_ENCOUNTERED, new HashSet<String>());
		Map<String, Integer> hurtStats = new HashMap<>();
		for (String enemeyEncountered : enemiesEncontered)
		{
			int hurtCount = dataReader.getInt(enemeyEncountered, 0);
			hurtStats.put(enemeyEncountered, hurtCount);
		}
		return hurtStats;
	}

	public int getGamePlayCount()
	{
		return dataReader.getInt(GAME_PLAY_COUNT, 0);
	}

	public List<Long> getScoreHistory()
	{
		List<Long> scoreHistroy = new LinkedList<>();
		int gamePlayCount = Math.min(getGamePlayCount(), 4);
		if (gamePlayCount == 1)
		{
			return scoreHistroy;
		}
		for (int i = 1; i < gamePlayCount; i++)
		{
			scoreHistroy.add(dataReader.getLong(PREVIOUS_SCORE_ + i, 0L));
		}
		return scoreHistroy;
	}

	/**
	 * Maintain previous 3 scores.
	 */
	private void maintainScoreHistory()
	{
		long previousScore2 = dataReader.getLong(PREVIOUS_SCORE_2, 0L);
		dataWriter.putLong(PREVIOUS_SCORE_3, previousScore2);

		long previousScore1 = dataReader.getLong(PREVIOUS_SCORE_1, 0L);
		dataWriter.putLong(PREVIOUS_SCORE_2, previousScore1);

		long currentScore = dataReader.getLong(CURRENT_SCORE, 0L);
		dataWriter.putLong(PREVIOUS_SCORE_1, currentScore);

		dataWriter.apply();
	}

	private void updateGameScoreStats()
	{
		long currentScore = dataReader.getLong(CURRENT_SCORE, 0L);

		long sessionHighScore = dataReader.getLong(SESSION_HIGH_SCORE, 0L);

		if (currentScore > sessionHighScore)
		{
			dataWriter.putLong(SESSION_HIGH_SCORE, currentScore);
			long globalHighScore = dataReader.getLong(GLOBAL_HIGH_SCORE, 0L);
			if (currentScore > globalHighScore)
			{
				dataWriter.putLong(GLOBAL_HIGH_SCORE, currentScore);
			}
		}
		dataWriter.apply();
	}

	private void increaseGamePlayCount()
	{
		int gamePlayCount = dataReader.getInt(GAME_PLAY_COUNT, 0);
		dataWriter.putInt(GAME_PLAY_COUNT, (gamePlayCount + 1));
		dataWriter.apply();
	}

	private void resetSessionHighScore()
	{
		dataWriter.putLong(SESSION_HIGH_SCORE, 0L);
		dataWriter.apply();
	}

	private void resetCurrentScore()
	{
		dataWriter.putLong(CURRENT_SCORE, 0L);
		dataWriter.apply();
	}

	private void resetDeathStat()
	{
		dataWriter.putString(TERMINATED_BY, "");
		dataWriter.apply();
	}

	private void updateScore(long currentScore)
	{
		dataWriter.putLong(CURRENT_SCORE, currentScore);
		dataWriter.apply();
	}

	private void resetAllGameStats()
	{
		dataWriter.clear();
		dataWriter.apply();
	}
}
