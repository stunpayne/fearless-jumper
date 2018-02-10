package com.stunapps.fearlessjumper.core;

import org.roboguice.shaded.goole.common.collect.Maps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by anand.verma on 02/02/18.
 */

public class StateMachine
{
    private State currentState;
    private State startState;
    private State terminalState;
    private Map<State, Map<Event, State>> stateTransitionMap;

    /**
     * Special state which will transaction to mapped state after specified number of calls to current state.
     */
    private Map<State, CountDownState> countDownStates;

    public StateMachine(State startState, State terminalState, Map<State, Map<Event, State>> stateTransitionMap, Map<State, CountDownState> countDownStateMap)
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

    public State transitStateOnEvent(Event event)
    {
        if (currentState.equals(terminalState))
        {
            //TODO: state transaction cannot happen.
            return null;
        }
        currentState = stateTransitionMap.get(currentState).get(event);
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
        Set<State> animationStates = countDownStates.keySet();
        if (animationStates.contains(currentState))
        {
            currentState = countDownStates.get(currentState).countDown();
        }
        return currentState;
    }

    public static class CountDownState
    {
        private State fromState;
        private State toState;
        private Integer countDown;
        private Integer currentCount;

        public CountDownState(State fromState, State toState, Integer countDown)
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
    }

    public static class Builder
    {
        private State startState;
        private State terminalState;
        private Map<State, Map<Event, State>> stateTransitionMap = Maps.newConcurrentMap();
        private Map<State, CountDownState> countDownStates = Maps.newConcurrentMap();

        private Set<State> fromStates;
        private Event transitionEvent;

        private Integer countDown;

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

            for (State state : states)
            {
                if (stateTransitionMap.get(state) == null)
                {
                    stateTransitionMap.put(state, new HashMap<Event, State>());
                }
            }
            return this;
        }

        public Builder onEvent(Event event)
        {
            if (fromStates == null || fromStates.isEmpty() || fromStates.size() > 1 || fromStates.iterator().next().equals(terminalState) || event == null)
            {
                //TODO: throw invalid state transaction exception.
            }
            transitionEvent = event;
            return this;
        }

        public Builder onCountDown(Integer countDown)
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
            if (fromStates == null || fromStates.isEmpty() || transitionEvent == null)
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
                        stateTransitionMap.get(fromState).put(transitionEvent, toState);
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

        public Builder onEvents(Map<Event, State> eventStateMap)
        {
            Set<Map.Entry<Event, State>> entrySet = eventStateMap.entrySet();
            for (Map.Entry<Event, State> entry : entrySet)
            {
                Event event = entry.getKey();
                State state = entry.getValue();
                this.onEvent(event).toState(state);
            }
            return this;
        }

        public StateMachine build()
        {
            return new StateMachine(startState, terminalState, stateTransitionMap, countDownStates);
        }
    }
}
