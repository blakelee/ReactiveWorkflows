package net.blakelee.reactiveworkflows.test

import net.blakelee.reactiveworkflows.R
import net.blakelee.reactiveworkflows.test.first.FirstCoordinator
import net.blakelee.reactiveworkflows.test.first.FirstScreen
import net.blakelee.reactiveworkflows.test.second.SecondCoordinator
import net.blakelee.reactiveworkflows.test.second.SecondScreen
import net.blakelee.library.AbstractViewFactory

class TestViewFactory : AbstractViewFactory(listOf(
        bindLayout(FirstScreen.KEY, R.layout.test_layout_one) { screen ->
            FirstCoordinator(screen as FirstScreen)
        },
        bindLayout(SecondScreen.KEY, R.layout.test_layout_two) { screen ->
            SecondCoordinator(screen as SecondScreen)
        })
)
