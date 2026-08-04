package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.ManagerTab
import com.example.ui.ManagementViewModel
import com.example.ui.components.FrostedGlassCard
import com.example.ui.components.FrostedPillBadge
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.FrostedIce
import com.example.ui.theme.FrostedIceDark
import com.example.ui.theme.FrostedLavender
import com.example.ui.theme.FrostedRose
import com.example.ui.theme.FrostedSapphire
import com.example.ui.theme.FrostedSapphireDark
import com.example.ui.theme.FrostedTextPrimary
import com.example.ui.theme.FrostedTextSecondary
import com.example.ui.theme.FrostedTextTertiary
import com.example.ui.theme.PureWhite

@Composable
fun ManagerScreen(
  viewModel: ManagementViewModel,
  onNavigateBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val activeTab by viewModel.activeManagerTab.collectAsStateWithLifecycle()
  val tabs = ManagerTab.values()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color.Transparent),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {

    // Manager Header Card
    item {
      FrostedGlassCard(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White.copy(alpha = 0.76f),
        borderColor = Color.White.copy(alpha = 0.90f),
        elevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Box(
                modifier = Modifier
                  .size(42.dp)
                  .clip(CircleShape)
                  .background(FrostedLavender.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.AdminPanelSettings,
                  contentDescription = null,
                  tint = Color(0xFF6B21A8),
                  modifier = Modifier.size(24.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "ম্যানেজার প্যানেল",
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = FrostedSapphireDark,
                    fontSize = 18.sp,
                    letterSpacing = (-0.2).sp
                  )
                )
                Text(
                  text = "হিসাব, পণ্য ও কুপন ব্যবস্থাপনা",
                  fontSize = 12.sp,
                  color = FrostedTextSecondary
                )
              }
            }

            OutlinedButton(
              onClick = { viewModel.lockManager() },
              shape = CircleShape,
              border = BorderStroke(1.dp, Color(0xFFFDA4AF)),
              colors = ButtonDefaults.outlinedButtonColors(
                containerColor = FrostedRose.copy(alpha = 0.6f)
              ),
              contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
              modifier = Modifier.testTag("manager_lock_button")
            ) {
              Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = Color(0xFFE11D48),
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text("লক করুন", fontSize = 11.sp, color = Color(0xFF9F1239), fontWeight = FontWeight.Bold)
            }
          }

          // Navigation Tabs Row
          ScrollableTabRow(
            selectedTabIndex = tabs.indexOf(activeTab),
            containerColor = FrostedIce.copy(alpha = 0.5f),
            contentColor = FrostedSapphireDark,
            edgePadding = 4.dp,
            indicator = { tabPositions ->
              TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[tabs.indexOf(activeTab)]),
                color = FrostedSapphire,
                height = 3.dp
              )
            },
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
          ) {
            tabs.forEachIndexed { index, tab ->
              val isSelected = activeTab == tab
              Tab(
                selected = isSelected,
                onClick = { viewModel.setManagerTab(tab) },
                text = {
                  Text(
                    text = tab.title,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 13.sp,
                    color = if (isSelected) FrostedSapphireDark else FrostedTextSecondary
                  )
                },
                icon = {
                  val icon = when (tab) {
                    ManagerTab.SALES -> Icons.Default.Paid
                    ManagerTab.PRODUCTS -> Icons.Default.Inventory2
                    ManagerTab.COUPONS -> Icons.Default.CardGiftcard
                    ManagerTab.REPORTS -> Icons.Default.Assessment
                  }
                  Icon(
                    imageVector = icon,
                    contentDescription = tab.title,
                    tint = if (isSelected) FrostedSapphire else FrostedTextTertiary,
                    modifier = Modifier.size(18.dp)
                  )
                },
                modifier = Modifier.testTag("manager_tab_${tab.name.lowercase()}")
              )
            }
          }
        }
      }
    }

    // Tab Content Host
    item {
      when (activeTab) {
        ManagerTab.SALES -> ManagerSalesTab(viewModel = viewModel)
        ManagerTab.PRODUCTS -> ManagerProductsTab(viewModel = viewModel)
        ManagerTab.COUPONS -> ManagerCouponsTab(viewModel = viewModel)
        ManagerTab.REPORTS -> ManagerReportsTab(viewModel = viewModel)
      }
    }

  }
}

