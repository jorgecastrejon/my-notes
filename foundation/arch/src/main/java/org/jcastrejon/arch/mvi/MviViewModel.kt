package org.jcastrejon.arch.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class MviViewModel<Action : MviAction, Result : MviResult, State : MviState, Effect : MviEffect>(
    initialState: State
) : ViewModel() {

    private val actions: Channel<Action> = Channel(Channel.UNLIMITED)
    protected val effects: Channel<Effect> = Channel(Channel.UNLIMITED)
    private val _state: MutableStateFlow<State> = MutableStateFlow(value = initialState)
    val state: StateFlow<State> get() = _state
    val viewEffects: Flow<Effect> = effects.receiveAsFlow()

    init {
        viewModelScope.launch {
            actions.consumeAsFlow()
                .actionsToResultTransformer()
                .onEach(::onResult)
                .scan(initialState) { state: State, result: Result -> reduce(state, result) }
                .collect(::setState)
        }
    }

    abstract fun Flow<Action>.actionsToResultTransformer(): Flow<Result>

    abstract fun reduce(previousState: State, result: Result): State
    open fun onResult(result: Result) = Unit

    fun onAction(action: Action) {
        actions.trySend(action)
    }

    private fun setState(state: State) {
        _state.value = state
    }
}