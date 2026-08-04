package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.ManagementViewModel
import com.example.ui.NavDestination
import com.example.ui.components.AppBottomNav
import com.example.ui.components.AppTopBar
import com.example.ui.components.FrostedMeshBackground
import com.example.ui.components.PinDialog
import com.example.ui.screens.CouponScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ManagerScreen
import com.example.ui.screens.ToolsScreen
import com.example.ui.screens.UserSearchScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

  private val viewModel: ManagementViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        ManagementApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun ManagementApp(viewModel: ManagementViewModel) {
  val currentNav by viewModel.currentNav.collectAsStateWithLifecycle()
  val isManagerUnlocked by viewModel.isManagerUnlocked.collectAsStateWithLifecycle()
  val isPinDialogOpen by viewModel.isPinDialogOpen.collectAsStateWithLifecycle()
  val pinErrorMessage by viewModel.pinErrorMessage.collectAsStateWithLifecycle()
  val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()

  val snackbarHostState = remember { SnackbarHostState() }

  // Handle toast / snackbar feedback messages
  LaunchedEffect(userMessage) {
    if (userMessage != null) {
      snackbarHostState.showSnackbar(userMessage!!)
      viewModel.clearUserMessage()
    }
  }

  // Handle Android Hardware / Gesture Back Press
  BackHandler(enabled = currentNav != NavDestination.HOME) {
    when (currentNav) {
      NavDestination.USER_SEARCH, NavDestination.MANAGER -> viewModel.navigateTo(NavDestination.TOOLS)
      NavDestination.TOOLS, NavDestination.COUPONS -> viewModel.navigateTo(NavDestination.HOME)
      else -> {}
    }
  }

  FrostedMeshBackground {
    Scaffold(
      containerColor = androidx.compose.ui.graphics.Color.Transparent,
      topBar = {
        AppTopBar(
          currentNav = currentNav,
          isManagerUnlocked = isManagerUnlocked,
          onNavigateBack = {
            when (currentNav) {
              NavDestination.USER_SEARCH, NavDestination.MANAGER -> viewModel.navigateTo(NavDestination.TOOLS)
              else -> viewModel.navigateTo(NavDestination.HOME)
            }
          },
          onOpenPinDialog = { viewModel.openPinDialog() },
          onLockManager = { viewModel.lockManager() }
        )
      },
      bottomBar = {
        AppBottomNav(
          currentNav = currentNav,
          onNavigate = { destination -> viewModel.navigateTo(destination) }
        )
      },
      snackbarHost = { SnackbarHost(snackbarHostState) },
      modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        AnimatedContent(
          targetState = currentNav,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "nav_transition"
        ) { targetNav ->
          when (targetNav) {
            NavDestination.HOME -> HomeScreen(
              viewModel = viewModel,
              onNavigate = { dest -> viewModel.navigateTo(dest) },
              onNavigateManagerTab = { tab ->
                viewModel.setManagerTab(tab)
              }
            )

            NavDestination.COUPONS -> CouponScreen(
              viewModel = viewModel
            )

            NavDestination.TOOLS -> ToolsScreen(
              viewModel = viewModel,
              onNavigate = { dest -> viewModel.navigateTo(dest) }
            )

            NavDestination.USER_SEARCH -> UserSearchScreen(
              viewModel = viewModel,
              onNavigateBack = { viewModel.navigateTo(NavDestination.TOOLS) }
            )

            NavDestination.MANAGER -> ManagerScreen(
              viewModel = viewModel,
              onNavigateBack = { viewModel.navigateTo(NavDestination.TOOLS) }
            )
          }
        }
      }
    }
  }

  // Manager PIN Dialog
  PinDialog(
    isOpen = isPinDialogOpen,
    errorMessage = pinErrorMessage,
    onDismiss = { viewModel.closePinDialog() },
    onSubmitPin = { pin -> viewModel.unlockManagerWithPin(pin) }
  )
}
