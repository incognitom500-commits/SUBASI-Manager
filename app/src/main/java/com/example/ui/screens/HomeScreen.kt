package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.ManagerTab
import com.example.ui.ManagementViewModel
import com.example.ui.NavDestination
import com.example.ui.components.FrostedGlassCard
import com.example.ui.components.FrostedPillBadge
import com.example.ui.components.MetricStatCard
import com.example.ui.components.formatTakaClean
import com.example.ui.theme.AmberGold
import com.example.ui.theme.AmberGoldLight
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.EmeraldGreenDark
import com.example.ui.theme.EmeraldGreenLight
import com.example.ui.theme.FrostedIce
import com.example.ui.theme.FrostedIceDark
import com.example.ui.theme.FrostedLavender
import com.example.ui.theme.FrostedRose
import com.example.ui.theme.FrostedSapphire
import com.example.ui.theme.FrostedSapphireDark
import com.example.ui.theme.FrostedTeal
import com.example.ui.theme.FrostedTextPrimary
import com.example.ui.theme.FrostedTextSecondary
import com.example.ui.theme.FrostedTextTertiary
import com.example.ui.theme.GlassBorderStroke
import com.example.ui.theme.GlassBorderSubtle
import com.example.ui.theme.GlassCardSurface
import com.example.ui.theme.GlassCardSurfaceHigh
import com.example.ui.theme.Navy100
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy900
import com.example.ui.theme.PureWhite
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900

@Composable
fun HomeScreen(
  viewModel: ManagementViewModel,
  onNavigate: (NavDestination) -> Unit,
  onNavigateManagerTab: (ManagerTab) -> Unit,
  modifier: Modifier = Modifier
) {
  val products by viewModel.productsList.collectAsStateWithLifecycle()
  val sales by viewModel.salesList.collectAsStateWithLifecycle()
  val activeCoupons by viewModel.activeCouponsList.collectAsStateWithLifecycle()
  val salesSummary by viewModel.salesSummary.collectAsStateWithLifecycle()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Transparent),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(18.dp)
  ) {

    // 1. Hero Frosted Glass Card
    item {
      FrostedGlassCard(
        shape = RoundedCornerShape(24.dp),
        backgroundColor = Color.White.copy(alpha = 0.76f),
        borderColor = Color.White.copy(alpha = 0.90f),
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.weight(1f)) {
              FrostedPillBadge(
                text = "ব্যবসা ড্যাশবোর্ড",
                backgroundColor = FrostedIce.copy(alpha = 0.70f),
                textColor = FrostedSapphireDark,
                borderColor = Color.White
              )
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "স্বাগতম আপনার ড্যাশবোর্ডে",
                style = MaterialTheme.typography.headlineSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = FrostedSapphireDark,
                  fontSize = 22.sp,
                  letterSpacing = (-0.3).sp
                )
              )
            }

            Box(
              modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(FrostedIce.copy(alpha = 0.60f))
                .drawBehind {
                  drawCircle(
                    brush = Brush.radialGradient(
                      colors = listOf(Color.White, Color.Transparent),
                      radius = 28.dp.toPx()
                    )
                  )
                },
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Storefront,
                contentDescription = null,
                tint = FrostedSapphire,
                modifier = Modifier.size(28.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "নিচের মেনু থেকে 'টুলস' অথবা 'কুপন কোড' অপশনে প্রবেশ করে আপনার ব্যবসার কার্যক্রম পরিচালনা করুন।",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = FrostedTextSecondary,
              fontSize = 13.sp,
              lineHeight = 19.sp
            )
          )

          Spacer(modifier = Modifier.height(16.dp))
          Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Button(
              onClick = { onNavigate(NavDestination.USER_SEARCH) },
              colors = ButtonDefaults.buttonColors(
                containerColor = FrostedSapphire,
                contentColor = PureWhite
              ),
              shape = CircleShape,
              elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("home_quick_search_btn")
            ) {
              Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("প্রোডাক্ট সার্চ", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            Button(
              onClick = { onNavigate(NavDestination.TOOLS) },
              colors = ButtonDefaults.buttonColors(
                containerColor = FrostedIce,
                contentColor = FrostedSapphireDark
              ),
              border = BorderStroke(1.dp, Color.White),
              shape = CircleShape,
              elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("home_quick_tools_btn")
            ) {
              Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = FrostedSapphire,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text("ম্যানেজার টুলস", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
          }
        }
      }
    }

    // 2. Business Overview Stats
    item {
      Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Text(
          text = "ব্যবসায়িক সারসংক্ষেপ",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = FrostedSapphireDark,
            letterSpacing = (-0.2).sp
          )
        )

        Row(
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          MetricStatCard(
            title = "সর্বমোট বিক্রি",
            value = formatTakaClean(salesSummary.totalSell),
            subtitle = "${salesSummary.totalTransactions} টি বিক্রয় রেকর্ড",
            icon = Icons.Default.Paid,
            accentColor = FrostedSapphire,
            modifier = Modifier.weight(1f)
          )

          MetricStatCard(
            title = "সর্বমোট লাভ",
            value = formatTakaClean(salesSummary.totalProfit),
            subtitle = "${String.format(java.util.Locale.US, "%.1f", salesSummary.profitMargin)}% লাভ মার্জিন",
            icon = Icons.Default.TrendingUp,
            accentColor = EmeraldGreenDark,
            modifier = Modifier.weight(1f)
          )
        }

        Row(
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          MetricStatCard(
            title = "মোট প্রোডাক্ট",
            value = "${products.size} টি",
            subtitle = "ক্যাটালগ তালিকা",
            icon = Icons.Default.Inventory2,
            accentColor = AmberGold,
            modifier = Modifier.weight(1f)
          )

          MetricStatCard(
            title = "অ্যাক্টিভ কুপন",
            value = "${activeCoupons.size} টি",
            subtitle = "চলতি ডিসকাউন্ট",
            icon = Icons.Default.CardGiftcard,
            accentColor = Color(0xFF7C3AED),
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // 3. Main Navigation Hub / Direct Shortcuts
    item {
      Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Text(
          text = "দ্রুত অপশন ও সার্ভিস",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = FrostedSapphireDark,
            letterSpacing = (-0.2).sp
          )
        )

        // User Search Card
        FrostedGlassCard(
          shape = RoundedCornerShape(20.dp),
          backgroundColor = Color.White.copy(alpha = 0.72f),
          borderColor = Color.White.copy(alpha = 0.85f),
          elevation = 1.dp,
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onNavigate(NavDestination.USER_SEARCH) }
            .testTag("home_user_panel_card")
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(FrostedIce.copy(alpha = 0.75f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = FrostedSapphire,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "ইউজার প্যানেল (প্রোডাক্ট খুঁজুন)",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = FrostedSapphireDark
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "প্রোডাক্ট কোড দিয়ে পণ্যের দাম, সাইজ ও কালার দেখুন",
                fontSize = 12.sp,
                color = FrostedTextSecondary
              )
            }
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(FrostedIce.copy(alpha = 0.4f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = FrostedSapphire,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }

        // Active Coupons Card
        FrostedGlassCard(
          shape = RoundedCornerShape(20.dp),
          backgroundColor = Color.White.copy(alpha = 0.72f),
          borderColor = Color.White.copy(alpha = 0.85f),
          elevation = 1.dp,
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onNavigate(NavDestination.COUPONS) }
            .testTag("home_coupons_card")
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(FrostedLavender.copy(alpha = 0.75f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.CardGiftcard,
                contentDescription = null,
                tint = Color(0xFF6B21A8),
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "অ্যাক্টিভ কুপন কোডসমূহ",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = FrostedSapphireDark
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "বিশেষ ডিসকাউন্ট এবং প্রোমো কোড কপি করুন",
                fontSize = 12.sp,
                color = FrostedTextSecondary
              )
            }
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(FrostedLavender.copy(alpha = 0.4f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = Color(0xFF6B21A8),
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }

        // Manager Panel Card
        FrostedGlassCard(
          shape = RoundedCornerShape(20.dp),
          backgroundColor = Color.White.copy(alpha = 0.72f),
          borderColor = Color.White.copy(alpha = 0.85f),
          elevation = 1.dp,
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onNavigate(NavDestination.MANAGER) }
            .testTag("home_manager_panel_card")
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(EmeraldGreenLight.copy(alpha = 0.75f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.QueryStats,
                contentDescription = null,
                tint = EmeraldGreenDark,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "ম্যানেজার প্যানেল (হিসাব ও পণ্য)",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = FrostedSapphireDark
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "বিক্রির হিসাব এন্ট্রি, লাভ-লোকসান এবং পণ্য তালিকা",
                fontSize = 12.sp,
                color = FrostedTextSecondary
              )
            }
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(EmeraldGreenLight.copy(alpha = 0.4f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = EmeraldGreenDark,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    }

    // 4. Recent Sales Activity Preview
    item {
      Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "সাম্প্রতিক বিক্রয়সমূহ",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = FrostedSapphireDark,
              letterSpacing = (-0.2).sp
            )
          )
          Text(
            text = "সব দেখুন",
            color = FrostedSapphire,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
              .clickable {
                onNavigate(NavDestination.MANAGER)
                onNavigateManagerTab(ManagerTab.SALES)
              }
              .padding(4.dp)
          )
        }

        if (sales.isEmpty()) {
          FrostedGlassCard(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White.copy(alpha = 0.65f),
            borderColor = Color.White.copy(alpha = 0.8f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Box(
              modifier = Modifier.padding(22.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "এখনও কোনো বিক্রয় রেকর্ড যোগ করা হয়নি।",
                color = FrostedTextSecondary,
                fontSize = 13.sp
              )
            }
          }
        } else {
          sales.take(4).forEach { sale ->
            FrostedGlassCard(
              shape = RoundedCornerShape(16.dp),
              backgroundColor = Color.White.copy(alpha = 0.70f),
              borderColor = Color.White.copy(alpha = 0.85f),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.weight(1f)
                ) {
                  Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = FrostedIce.copy(alpha = 0.8f),
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier.size(40.dp)
                  ) {
                    Box(contentAlignment = Alignment.Center) {
                      Text(
                        text = sale.productCode,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = FrostedSapphireDark
                      )
                    }
                  }
                  Spacer(modifier = Modifier.width(12.dp))
                  Column {
                    Text(
                      text = sale.productName.ifBlank { "প্রোডাক্ট ${sale.productCode}" },
                      fontWeight = FontWeight.SemiBold,
                      fontSize = 14.sp,
                      color = FrostedSapphireDark
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                      text = "তারিখ: ${sale.date} • বিক্রি: ${formatTakaClean(sale.sellPrice)}",
                      fontSize = 12.sp,
                      color = FrostedTextSecondary
                    )
                  }
                }

                FrostedPillBadge(
                  text = "+${formatTakaClean(sale.profit)}",
                  backgroundColor = EmeraldGreenLight.copy(alpha = 0.7f),
                  textColor = EmeraldGreenDark,
                  borderColor = EmeraldGreen.copy(alpha = 0.4f)
                )
              }
            }
          }
        }
      }
    }

  }
}

