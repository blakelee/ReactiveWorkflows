package net.blakelee.reactiveworkflows.auth

import net.blakelee.library.AbstractViewFactory
import net.blakelee.reactiveworkflows.R
import net.blakelee.reactiveworkflows.auth.authorizing.AuthorizingCoordinator
import net.blakelee.reactiveworkflows.auth.authorizing.AuthorizingScreen
import net.blakelee.reactiveworkflows.auth.login.LoginCoordinator
import net.blakelee.reactiveworkflows.auth.login.LoginScreen
import net.blakelee.reactiveworkflows.auth.secondfactor.SecondFactorCoordinator
import net.blakelee.reactiveworkflows.auth.secondfactor.SecondFactorScreen

class AuthViewFactory : AbstractViewFactory(listOf(
        bindLayout(LoginScreen.KEY, R.layout.login) { screen ->
            LoginCoordinator(screen as LoginScreen)
        },

        bindLayout(AuthorizingScreen.KEY, R.layout.authorizing) { screen ->
            AuthorizingCoordinator(screen as AuthorizingScreen)
        },

        bindLayout(SecondFactorScreen.KEY, R.layout.second_factor) { screen ->
            SecondFactorCoordinator(screen as SecondFactorScreen)
        }
    )
)