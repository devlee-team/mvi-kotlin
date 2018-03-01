/*
 * Copyright (C) 2018, Pavel Lee
 * All rights reserved.
 */

package org.devlee.kotlin.mvi.view.state

interface ViewState {

    fun newBuilder(): Builder<ViewState>

    interface Builder<out VS: ViewState> {
        fun build(): VS
    }

    interface Changes<VS: ViewState>: (VS) -> (VS) {
        companion object {

            inline fun < VS: ViewState, B: ViewState.Builder<VS>> modify(
                    crossinline modifier: Modifier<B>
            ): ViewState.Changes<VS> {
                return object: ViewState.Changes<VS> {

                    /**
                     * @suppress unchecked cast allow as to avoid the boilerplate generics
                     *           in implementations
                     */
                    @Suppress("UNCHECKED_CAST")
                    override fun invoke(state: VS): VS = modifier(state.newBuilder() as B).build()
                }
            }

            fun <VS: ViewState> nothing() = object: ViewState.Changes<VS> {
                override fun invoke(state: VS): VS = state
            }

        }
    }
}

private typealias Modifier<T> = T.() -> T