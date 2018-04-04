package net.blakelee.archtest.test

import net.blakelee.archtest.AbstractViewFactory
import net.blakelee.archtest.R
import net.blakelee.archtest.test.first.FirstCoordinator
import net.blakelee.archtest.test.first.FirstScreen
import net.blakelee.archtest.test.second.SecondCoordinator
import net.blakelee.archtest.test.second.SecondScreen

class TestViewFactory : AbstractViewFactory(listOf(
        bindLayout(FirstScreen.KEY, R.layout.test_layout_one) { screen ->
            FirstCoordinator(screen as FirstScreen)
        },
        bindLayout(SecondScreen.KEY, R.layout.test_layout_two) { screen ->
            SecondCoordinator(screen as SecondScreen)
        })
)
