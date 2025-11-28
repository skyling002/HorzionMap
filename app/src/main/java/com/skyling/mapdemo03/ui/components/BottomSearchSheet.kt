package com.skyling.mapdemo03.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSearchSheet(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = true
        )
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        // Peek height includes the handle (drag handle) + search bar + some padding
        sheetPeekHeight = 120.dp, 
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContainerColor = Color.White,
        sheetDragHandle = {
            // Custom drag handle (small gray line)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.LightGray)
                )
            }
        },
        sheetContent = {
            // This entire column is the sheet content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f) // Cover most of the screen when expanded
                    .padding(horizontal = 16.dp)
            ) {
                // 1. The Search Bar (Always visible)
                // Looks like a rounded text field
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(26.dp)) // Pill shape
                        .background(Color(0xFFF2F3F5)) // Light gray background
                        .clickable {
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "去哪里？",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Gray,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 2. History Content (Visible when Expanded)
                // We don't need to manually hide it; it's just below the fold in peek state
                // But for better UX, we might want to fade it in or just let it slide up.
                // BottomSheetScaffold handles the sliding naturally.
                
                HistoryItem(
                    title = "贵阳市",
                    subtitle = "贵阳市，贵州省"
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                 HistoryItem(
                    title = "公司",
                    subtitle = "北京市朝阳区..."
                )
            }
        },
        content = content
    )
}

@Composable
fun HistoryItem(title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // History Icon
        Icon(
            imageVector = Icons.Default.History,
            contentDescription = "History",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            )
        }
    }
    // Divider line
    Spacer(modifier = Modifier.height(8.dp))
    HorizontalDivider(
        modifier = Modifier.padding(start = 40.dp), // Indent to align with text
        thickness = 0.5.dp,
        color = Color.LightGray.copy(alpha = 0.5f)
    )
}

