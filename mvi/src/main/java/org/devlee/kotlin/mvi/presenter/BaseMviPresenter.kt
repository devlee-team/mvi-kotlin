/*
 * Copyright (C) 2018, Pavel Lee
 * All rights reserved.
 */

package org.devlee.kotlin.mvi.presenter

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.devlee.kotlin.mvi.view.MviView
import org.devlee.kotlin.mvi.view.state.ViewState

abstract class BaseMviPresenter<V: MviView<VS>, VS: ViewState>: MviBasePresenter<V, VS>() {

    protected abstract fun prepareViewIntents(): Iterable<Observable<ViewState.Changes<VS>>>

    protected abstract fun createInitialState(): VS

    override fun bindIntents() = subscribeViewState(createViewStateHandler(), {
        v, vs -> v.render(vs)
    })

    private fun mergedViewIntents() =
            Observable.merge(prepareViewIntents()).observeOn(AndroidSchedulers.mainThread())


    private fun createViewStateHandler() = mergedViewIntents()
            .scan(createInitialState(), this::viewStateReducer)


    private fun viewStateReducer(previousState: VS, modify: ViewState.Changes<VS>)
            = modify(previousState)

}