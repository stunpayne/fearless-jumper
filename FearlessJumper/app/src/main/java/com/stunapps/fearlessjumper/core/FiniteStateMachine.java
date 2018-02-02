package com.stunapps.fearlessjumper.core;

import org.roboguice.shaded.goole.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by anand.verma on 02/02/18.
 */

public class FiniteStateMachine
{
    private State currentState;
    private State startState;
    private State terminalState;
    private Map<State, Map<Event, State>> stateTransitionMap;

    private static Builder builder = new Builder();

    public FiniteStateMachine(State startState, State terminalState, Map<State, Map<Event, State>> stateTransitionMap)
    {
        this.startState = startState;
        this.terminalState = terminalState;
        this.stateTransitionMap = stateTransitionMap;
        this.currentState = startState;
    }

    public static Builder builder()
    {
        return builder;
    }

    public State transitStateOnEvent(Event event)
    {
        if (currentState.equals(terminalState))
        {
            //TODO: state transaction cannot happen.
        }
        currentState = stateTransitionMap.get(currentState).get(event);
        return currentState;
    }

    public static class Builder
    {
        private State startState;
        private State terminalState;
        private Map<State, Map<Event, State>> stateTransitionMap = Maps.newConcurrentMap();

        private State fromState;
        private Event transactionEvent;

        public Builder startState(State state)
        {
            startState = state;
            fromState = state;
            stateTransitionMap.put(fromState, new HashMap<Event, State>());
            return this;
        }

        public Builder from(State state)
        {
            /**
             * If start state is not configured, set first state as start state.
             */
            if (startState == null)
            {
                startState = state;
            }

            if (stateTransitionMap.get(state) == null)
            {
                stateTransitionMap.put(state, new HashMap<Event, State>());
            }
            fromState = state;
            return this;
        }

        public Builder onEvent(Event event)
        {
            if (fromState == null || fromState.equals(terminalState))
            {
                //TODO: throw invalid state transaction exception.
            }
            transactionEvent = event;
            return this;
        }

        public Builder toState(State toState)
        {
            if (fromState == null || transactionEvent == null || fromState.equals(terminalState))
            {
                //TODO: throw invalid state transaction exception.
            }

            stateTransitionMap.get(fromState).put(transactionEvent, toState);
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

        public FiniteStateMachine build()
        {
            return new FiniteStateMachine(startState, terminalState, stateTransitionMap);
        }
    }
}
