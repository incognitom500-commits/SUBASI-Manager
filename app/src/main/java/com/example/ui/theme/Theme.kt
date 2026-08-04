package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
  primary = FrostedSapphire,
  onPrimary = PureWhite,
  primaryContainer = FrostedIce,
  onPrimaryContainer = FrostedSapphireDark,
  secondary = Slate700,
  onSecondary = PureWhite,
  secondaryContainer = FrostedLavender,
  onSecondaryContainer = FrostedSapphireDark,
  tertiary = EmeraldGreenDark,
  onTertiary = PureWhite,
  tertiaryContainer = EmeraldGreenLight,
  onTertiaryContainer = Color(0xFF064E3B),
  background = GlassCanvasBg,
  onBackground = FrostedTextPrimary,
  surface = PureWhite,
  onSurface = FrostedTextPrimary,
  surfaceVariant = Color(0xFFEFF3FA),
  onSurfaceVariant = FrostedTextSecondary,
  outline = GlassBorderStroke,
  outlineVariant = Color(0xFFE2E8F0),
  error = CrimsonRed,
  onError = PureWhite,
  errorContainer = CrimsonRedLight,
  onErrorContainer = Color(0xFF7F1D1D)
)

private val DarkColorScheme = darkColorScheme(
  primary = Color(0xFFA5C8FE),
  onPrimary = FrostedSapphireDark,
  primaryContainer = Color(0xFF00487E),
  onPrimaryContainer = FrostedIce,
  secondary = Color(0xFFC4D6ED),
  onSecondary = Color(0xFF283141),
  secondaryContainer = Color(0xFF3F4759),
  onSecondaryContainer = Color(0xFFDBE2F9),
  tertiary = Color(0xFF6EE7B7),
  onTertiary = Color(0xFF064E3B),
  tertiaryContainer = Color(0xFF065F46),
  onTertiaryContainer = EmeraldGreenLight,
  background = Color(0xFF101418),
  onBackground = Color(0xFFE1E2E8),
  surface = Color(0xFF1A1F26),
  onSurface = Color(0xFFE1E2E8),
  surfaceVariant = Color(0xFF252B35),
  onSurfaceVariant = Color(0xFFC4C7D0),
  outline = Color(0xFF404855),
  outlineVariant = Color(0xFF2F3642),
  error = Color(0xFFFFB4AB),
  onError = Color(0xFF690005),
  errorContainer = Color(0xFF93000A),
  onErrorContainer = Color(0xFFFFDAD6)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep consistent Navy brand theme
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
