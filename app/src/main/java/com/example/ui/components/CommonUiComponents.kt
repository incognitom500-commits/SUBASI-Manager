package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.NavDestination
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberGoldLight
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenLight
import com.example.ui.theme.FrostedIce
import com.example.ui.theme.FrostedIceDark
import com.example.ui.theme.FrostedIceHover
import com.example.ui.theme.FrostedLavender
import com.example.ui.theme.FrostedRose
import com.example.ui.theme.FrostedSapphire
import com.example.ui.theme.FrostedSapphireDark
import com.example.ui.theme.FrostedTeal
import com.example.ui.theme.FrostedTextPrimary
import com.example.ui.theme.FrostedTextSecondary
import com.example.ui.theme.FrostedTextTertiary
import com.example.ui.theme.GlassBorderIce
import com.example.ui.theme.GlassBorderStroke
import com.example.ui.theme.GlassBorderSubtle
import com.example.ui.theme.GlassCanvasBg
import com.example.ui.theme.GlassCardSurface
import com.example.ui.theme.GlassCardSurfaceHigh
import com.example.ui.theme.GlassCardSurfaceLow
import com.example.ui.theme.Navy100
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy900
import com.example.ui.theme.PureWhite
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate50
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900

fun formatTaka(amount: Double): String {
  return "৳ ${String.format(java.util.Locale.US, "%,.2f", amount)}"
}

fun formatTakaClean(amount: Double): String {
  return if (amount % 1.0 == 0.0) {
    "৳ ${String.format(java.util.Locale.US, "%,d", amount.toLong())}"
  } else {
    "৳ ${String.format(java.util.Locale.US, "%,.2f", amount)}"
  }
}

/**
 * Ambient decorative background mesh creating the signature Frosted Glass refraction backdrop.
 * Paints soft luminous pastel gradient blobs behind translucent glass cards.
 */
@Composable
fun FrostedMeshBackground(
  modifier: Modifier = Modifier,
  content: @Composable BoxScope.() -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(GlassCanvasBg)
      .drawBehind {
        val width = size.width
        val height = size.height

        // Top-left luminous cyan/ice blue radial blur sphere
        drawCircle(
          brush = Brush.radialGradient(
            colors = listOf(
              FrostedIce.copy(alpha = 0.70f),
              FrostedTeal.copy(alpha = 0.35f),
              Color.Transparent
            ),
            center = Offset(width * 0.05f, height * 0.08f),
            radius = width * 0.75f
          ),
          center = Offset(width * 0.05f, height * 0.08f),
          radius = width * 0.75f
        )

        // Bottom-right luminous rose/coral radial blur sphere
        drawCircle(
          brush = Brush.radialGradient(
            colors = listOf(
              FrostedRose.copy(alpha = 0.50f),
              FrostedLavender.copy(alpha = 0.25f),
              Color.Transparent
            ),
            center = Offset(width * 0.95f, height * 0.75f),
            radius = width * 0.70f
          ),
          center = Offset(width * 0.95f, height * 0.75f),
          radius = width * 0.70f
        )

        // Center-left gentle lavender/violet ambient glow
        drawCircle(
          brush = Brush.radialGradient(
            colors = listOf(
              FrostedLavender.copy(alpha = 0.45f),
              Color.Transparent
            ),
            center = Offset(width * 0.15f, height * 0.50f),
            radius = width * 0.55f
          ),
          center = Offset(width * 0.15f, height * 0.50f),
          radius = width * 0.55f
        )
      },
    content = content
  )
}

/**
 * Frosted Glass Card container with translucent white backdrop and crisp border highlight.
 */
@Composable
fun FrostedGlassCard(
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(20.dp),
  backgroundColor: Color = GlassCardSurface,
  borderColor: Color = GlassBorderStroke,
  borderWidth: Dp = 1.dp,
  elevation: Dp = 0.dp,
  content: @Composable () -> Unit
) {
  Surface(
    shape = shape,
    color = backgroundColor,
    border = BorderStroke(borderWidth, borderColor),
    shadowElevation = elevation,
    modifier = modifier
  ) {
    content()
  }
}

/**
 * Frosted Glass Badge / Pill Chip with soft translucent pastel background.
 */
@Composable
fun FrostedPillBadge(
  text: String,
  modifier: Modifier = Modifier,
  backgroundColor: Color = FrostedIce.copy(alpha = 0.65f),
  textColor: Color = FrostedSapphireDark,
  borderColor: Color = Color.White.copy(alpha = 0.8f),
  icon: ImageVector? = null
) {
  Surface(
    shape = CircleShape,
    color = backgroundColor,
    border = BorderStroke(1.dp, borderColor),
    modifier = modifier
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
      if (icon != null) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = textColor,
          modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
      }
      Text(
        text = text,
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 11.sp,
          color = textColor
        )
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
  currentNav: NavDestination,
  isManagerUnlocked: Boolean,
  onNavigateBack: () -> Unit,
  onOpenPinDialog: () -> Unit,
  onLockManager: () -> Unit
) {
  var showInfoDialog by remember { mutableStateOf(false) }

  val title = when (currentNav) {
    NavDestination.HOME -> "ম্যানেজমেন্ট ড্যাশবোর্ড"
    NavDestination.COUPONS -> "কুপন কোড (Coupons)"
    NavDestination.TOOLS -> "টুলস মেনু (Tools)"
    NavDestination.USER_SEARCH -> "প্রোডাক্ট অনুসন্ধান"
    NavDestination.MANAGER -> "ম্যানেজার প্যানেল"
  }

  val isSubScreen = currentNav == NavDestination.USER_SEARCH || currentNav == NavDestination.MANAGER

  Surface(
    color = Color.White.copy(alpha = 0.75f),
    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)),
    shadowElevation = 1.dp
  ) {
    TopAppBar(
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          // Frosted Glass Logo Box
          Box(
            modifier = Modifier
              .size(38.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(FrostedIce.copy(alpha = 0.85f))
              .drawBehind {
                drawCircle(
                  brush = Brush.radialGradient(
                    colors = listOf(Color.White, Color.Transparent),
                    radius = 20.dp.toPx()
                  )
                )
              },
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = "Logo",
              tint = FrostedSapphire,
              modifier = Modifier.size(22.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = title,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                letterSpacing = (-0.2).sp
              ),
              color = FrostedSapphireDark,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
            Text(
              text = "ব্যবসা ও বিক্রয় ব্যবস্থাপনা",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
              ),
              color = FrostedTextSecondary
            )
          }
        }
      },
      navigationIcon = {
        if (isSubScreen) {
          IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.testTag("nav_back_button")
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.6f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "পেছনে যান",
                tint = FrostedTextPrimary,
                modifier = Modifier.size(20.dp)
              )
            }
          }
        }
      },
      actions = {
        // Manager Status Badge / Button
        if (isManagerUnlocked) {
          Surface(
            shape = CircleShape,
            color = EmeraldGreenLight.copy(alpha = 0.7f),
            border = BorderStroke(1.dp, EmeraldGreen.copy(alpha = 0.6f)),
            modifier = Modifier
              .clip(CircleShape)
              .clickable { onLockManager() }
              .padding(horizontal = 2.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
              Icon(
                imageVector = Icons.Default.LockOpen,
                contentDescription = "ম্যানেজার আনলক",
                tint = EmeraldGreenDark,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "ম্যানেজার",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = EmeraldGreenDark
                )
              )
            }
          }
        } else {
          IconButton(
            onClick = onOpenPinDialog,
            modifier = Modifier.testTag("lock_manager_button")
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(FrostedIce.copy(alpha = 0.6f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "ম্যানেজার লগইন",
                tint = FrostedSapphire,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }

        IconButton(
          onClick = { showInfoDialog = true },
          modifier = Modifier.testTag("help_info_button")
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(Color.White.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.HelpOutline,
              contentDescription = "সাহায্য ও তথ্য",
              tint = FrostedTextSecondary,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      },
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = FrostedTextPrimary
      )
    )
  }

  if (showInfoDialog) {
    AlertDialog(
      onDismissRequest = { showInfoDialog = false },
      containerColor = GlassCardSurfaceHigh,
      shape = RoundedCornerShape(24.dp),
      icon = {
        Box(
          modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(FrostedIce.copy(alpha = 0.7f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.HelpOutline,
            contentDescription = null,
            tint = FrostedSapphire,
            modifier = Modifier.size(26.dp)
          )
        }
      },
      title = {
        Text(
          text = "ম্যানেজমেন্ট সিস্টেম তথ্য",
          fontWeight = FontWeight.Bold,
          color = FrostedSapphireDark
        )
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = "এই অ্যাপের মাধ্যমে আপনি খুব সহজে আপনার ব্যবসার প্রোডাক্ট সার্চ, কুপন অফার এবং বিক্রির হিসাব-নিকাশ পরিচালনা করতে পারবেন।",
            style = MaterialTheme.typography.bodyMedium,
            color = FrostedTextSecondary
          )
          Spacer(modifier = Modifier.height(4.dp))
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = FrostedIce.copy(alpha = 0.45f),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = "🔑 ম্যানেজার পিন কোড:",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = FrostedSapphireDark
              )
              Text(
                text = "ডিফল্ট পিন: 147893082 অথবা 1234",
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = FrostedSapphire
              )
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = { showInfoDialog = false },
          shape = CircleShape,
          colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = FrostedSapphire,
            contentColor = PureWhite
          )
        ) {
          Text("ঠিক আছে", fontWeight = FontWeight.Bold)
        }
      }
    )
  }
}

@Composable
fun AppBottomNav(
  currentNav: NavDestination,
  onNavigate: (NavDestination) -> Unit
) {
  Surface(
    color = Color.White.copy(alpha = 0.82f),
    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)),
    shadowElevation = 8.dp
  ) {
    NavigationBar(
      containerColor = Color.Transparent,
      contentColor = FrostedTextPrimary,
      tonalElevation = 0.dp
    ) {
      NavigationBarItem(
        selected = currentNav == NavDestination.HOME,
        onClick = { onNavigate(NavDestination.HOME) },
        icon = {
          Icon(imageVector = Icons.Default.Home, contentDescription = "হোম")
        },
        label = {
          Text(
            "হোম",
            fontWeight = if (currentNav == NavDestination.HOME) FontWeight.Bold else FontWeight.Medium,
            fontSize = 11.sp
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = FrostedSapphire,
          selectedTextColor = FrostedSapphireDark,
          indicatorColor = FrostedIce,
          unselectedIconColor = FrostedTextTertiary,
          unselectedTextColor = FrostedTextTertiary
        ),
        modifier = Modifier.testTag("nav_item_home")
      )

      NavigationBarItem(
        selected = currentNav == NavDestination.COUPONS,
        onClick = { onNavigate(NavDestination.COUPONS) },
        icon = {
          Icon(imageVector = Icons.Default.CardGiftcard, contentDescription = "কুপন")
        },
        label = {
          Text(
            "কুপন কোড",
            fontWeight = if (currentNav == NavDestination.COUPONS) FontWeight.Bold else FontWeight.Medium,
            fontSize = 11.sp
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = FrostedSapphire,
          selectedTextColor = FrostedSapphireDark,
          indicatorColor = FrostedIce,
          unselectedIconColor = FrostedTextTertiary,
          unselectedTextColor = FrostedTextTertiary
        ),
        modifier = Modifier.testTag("nav_item_coupon")
      )

      NavigationBarItem(
        selected = currentNav == NavDestination.TOOLS || currentNav == NavDestination.USER_SEARCH || currentNav == NavDestination.MANAGER,
        onClick = { onNavigate(NavDestination.TOOLS) },
        icon = {
          Icon(imageVector = Icons.Default.Widgets, contentDescription = "টুলস")
        },
        label = {
          Text(
            "টুলস",
            fontWeight = if (currentNav == NavDestination.TOOLS || currentNav == NavDestination.USER_SEARCH || currentNav == NavDestination.MANAGER) FontWeight.Bold else FontWeight.Medium,
            fontSize = 11.sp
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = FrostedSapphire,
          selectedTextColor = FrostedSapphireDark,
          indicatorColor = FrostedIce,
          unselectedIconColor = FrostedTextTertiary,
          unselectedTextColor = FrostedTextTertiary
        ),
        modifier = Modifier.testTag("nav_item_tools")
      )
    }
  }
}

@Composable
fun MetricStatCard(
  title: String,
  value: String,
  subtitle: String? = null,
  icon: ImageVector,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  FrostedGlassCard(
    shape = RoundedCornerShape(20.dp),
    backgroundColor = Color.White.copy(alpha = 0.70f),
    borderColor = Color.White.copy(alpha = 0.85f),
    elevation = 1.dp,
    modifier = modifier
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = MaterialTheme.typography.bodySmall.copy(
            color = FrostedTextSecondary,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
          )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = value,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = FrostedSapphireDark,
            fontSize = 20.sp,
            letterSpacing = (-0.3).sp
          )
        )
        if (subtitle != null) {
          Spacer(modifier = Modifier.height(3.dp))
          Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall.copy(
              color = accentColor,
              fontWeight = FontWeight.SemiBold,
              fontSize = 11.sp
            )
          )
        }
      }
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(CircleShape)
          .background(accentColor.copy(alpha = 0.12f))
          .drawBehind {
            drawCircle(
              brush = Brush.radialGradient(
                colors = listOf(Color.White.copy(alpha = 0.7f), Color.Transparent),
                radius = 24.dp.toPx()
              )
            )
          },
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = accentColor,
          modifier = Modifier.size(24.dp)
        )
      }
    }
  }
}

