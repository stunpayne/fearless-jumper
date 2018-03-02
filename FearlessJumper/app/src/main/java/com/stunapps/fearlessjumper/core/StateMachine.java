package com.stunapps.fearlessjumper.core;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import org.roboguice.shaded.goole.common.collect.Maps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by anand.verma on 02/02/18.
 */

public class StateMachine<State, Transition>
{
    private static final String TAG = "STATE_MACHINE";

    private State currentState;
    private State startState;
    private State terminalState;
    private Map<State, Map<Transition, State>> stateTransitionMap;

    /**
     * Special state which will transaction to mapped state after specified number of calls to current state.
     */
    private Map<State, CountDownState<State>> countDownStates;

    public StateMachine()
    {
    }

    public StateMachine(State startState, State terminalState, Map<State, Map<Transition, State>> stateTransitionMap, Map<State, CountDownState<State>> countDownStateMap)
    {
        this.startState = startState;
        this.terminalState = terminalState;
        this.stateTransitionMap = stateTransitionMap;
        this.countDownStates = countDownStateMap;
        this.currentState = startState;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public State transitStateOnEvent(Transition transition)
    {
        if (currentState.equals(terminalState))
        {
            //TODO: state transaction cannot happen.
            return null;
        }
        State nextState = stateTransitionMap.get(currentState).get(transition);
        if (nextState != null)
        {
            currentState = nextState;
            if(countDownStates.containsKey(currentState)){
                final CountDownState<State> countDownState = countDownStates.get(currentState);
                HandlerThread handlerThread = new HandlerThread("background-thread");
                handlerThread.start();
                new Handler(handlerThread.getLooper()).postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        currentState = countDownState.toState;
                    }
                }, countDownState.countDown);
            }
        }
        else Log.w(TAG, "No transition " + transition + "exists from state " + currentState);
        return currentState;
    }

    public State getStartState()
    {
        return startState;
    }

    public State getTerminalState()
    {
        return terminalState;
    }

    /**
     * Get current state. If current state is configured as countdown state,
     * Update countdown number and if countdown reaches zero, update current state
     * with new countdown state and return it as current state.
     *
     * @return
     */
    public State getCurrentState()
    {
        /*
        Set<State> states = countDownStates.keySet();
        if (states.contains(currentState))
        {
            currentState = countDownStates.get(currentState).countDown();
        } */
        return currentState;
    }

    public static class CountDownState<State>
    {
        private State fromState;
        private State toState;
        private Long countDown;
        private Long currentCount;

        public CountDownState(State fromState, State toState, Long countDown)
        {
            this.fromState = fromState;
            this.toState = toState;
            this.countDown = countDown;
            this.currentCount = countDown;
        }

        public State countDown()
        {
            currentCount--;
            if (currentCount <= 0)
            {
                resetCountDown();
                return toState;
            } else
            {
                return fromState;
            }
        }

        public void resetCountDown()
        {
            currentCount = countDown;
        }

        @Override
        public String toString()
        {
            return "CountDownState{" + "fromState=" + fromState + ", toState=" + toState +
                    ", countDown=" + countDown + '}';
        }
    }

    public static class Builder<State, Transition>
    {
        private State startState;
        private State terminalState;
        private Map<State, Map<Transition, State>> stateTransitionMap = Maps.newConcurrentMap();
        private Map<State, CountDownState> countDownStates = Maps.newConcurrentMap();
        private Map<Transition, State> anyStateTransitionMap = Maps.newConcurrentMap();

        private Set<State> fromStates;
        private Set<State> allStates = new HashSet<>();
        private Transition transition;

        private Long countDown;
        private boolean buildingAnyState;

        public Builder startState(State state)
        {
            startState = state;
            return this;
        }

        public Builder from(State state)
        {
            Set<State> fromStates = new HashSet<>();
            fromStates.add(state);
            from(fromStates);
            return this;
        }

        public Builder from(Set<State> states)
        {
            /**
             * If start state is not configured, set first state as start state.
             */
            if (startState == null)
            {
                //TODO: Throw invalid start state exception.
            }

            fromStates = states;
            allStates.addAll(fromStates);

            for (State state : states)
            {
                if (stateTransitionMap.get(state) == null)
                {
                    stateTransitionMap.put(state, new HashMap<Transition, State>());
                }
            }
            return this;
        }

        public Builder fromAnyStateOnEvent(Transition transition)
        {
            buildingAnyState = true;
            this.transition = transition;
            return this;
        }

        public Builder onEvent(Transition transition)
        {
            if (fromStates == null || fromStates.isEmpty() || fromStates.size() > 1 || fromStates.iterator().next().equals(terminalState) || transition == null)
            {
                //TODO: throw invalid state transaction exception.
            }
            this.transition = transition;
            return this;
        }

        public Builder onCountDown(Long countDown)
        {
            if (fromStates == null || fromStates.isEmpty() || fromStates.size() > 1 || fromStates.iterator().next().equals(terminalState) || countDown == null)
            {
                //TODO: throw invalid state transaction exception.
            }
            this.countDown = countDown;
            return this;
        }

        public Builder toState(State toState)
        {
            allStates.add(toState);
            if (buildingAnyState)
            {
                anyStateTransitionMap.put(transition, toState);
                buildingAnyState = false;
                return this;
            }

            if (fromStates == null || fromStates.isEmpty() || transition == null)
            {
                //TODO: throw invalid state transaction exception.
            }

            if (countDown != null)
            {
                for (State fromState : fromStates)
                {
                    if (!fromState.equals(terminalState))
                    {
                        countDownStates.put(fromState, new CountDownState(fromState, toState, countDown));
                    }
                }
                countDown = null;
            } else
            {
                if (fromStates.size() > 1)
                {
                    //TODO: Throw invalid from state. Only one from state expected.
                } else
                {
                    State fromState = fromStates.iterator().next();
                    if (!fromState.equals(terminalState))
                    {
                        stateTransitionMap.get(fromState).put(transition, toState);
                    }
                }
            }
            return this;
        }

        public Builder terminalState(State state)
        {
            terminalState = state;
            return this;
        }

        public Builder onEvents(Map<Transition, State> eventStateMap)
        {
            Set<Map.Entry<Transition, State>> entrySet = eventStateMap.entrySet();
            for (Map.Entry<Transition, State> entry : entrySet)
            {
                Transition transition = entry.getKey();
                State state = entry.getValue();
                this.onEvent(transition).toState(state);
            }
            return this;
        }

        public StateMachine build()
        {
        	addAnyStateTransitions();
            return new StateMachine(startState, terminalState, stateTransitionMap, countDownStates);
        }

		private void addAnyStateTransitions()
		{
            for (State state : allStates)
			{
				for (Map.Entry<Transition, State> anyStateTransition : anyStateTransitionMap
						.entrySet())
				{
				    if (stateTransitionMap.get(state) == null)
				        stateTransitionMap.put(state, Maps.<Transition, State>newConcurrentMap());
					stateTransitionMap.get(state)
							.put(anyStateTransition.getKey(), anyStateTransition.getValue());
				}
			}
		}
	}

    @Override
    public StateMachine clone() throws CloneNotSupportedException
    {
        return new StateMachine(startState, terminalState, stateTransitionMap, countDownStates);
    }

	@Override
	public String toString()
	{
		return "StateMachine{" + "currentState=" + currentState + ", startState=" + startState +
				", terminalState=" + terminalState + ", stateTransitionMap=" + stateTransitionMap +
				", countDownStates=" + countDownStates + '}';
	}
}
