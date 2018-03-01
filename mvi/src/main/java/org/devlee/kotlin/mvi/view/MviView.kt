/*
 * Copyright (C) 2018, Pavel Lee
 * All rights reserved.
 */

package org.devlee.kotlin.mvi.view

import com.hannesdorfmann.mosby3.mvp.MvpView
import org.devlee.kotlin.mvi.view.state.ViewState

interface MviView<in VS: ViewState>: MvpView {
    fun render(viewState: VS)
}