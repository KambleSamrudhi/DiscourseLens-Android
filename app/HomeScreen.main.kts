#!/usr/bin/env kotlin

package com.example.discourselens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            // 🔷 Logo (gradient)
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF7B61FF),
                                Color(0xFF5A3BFF)
                            )
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("✦", color = Color.White, fontSize = 30.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "DiscourseLens",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "AI-powered writing analysis",
                color = Color(0xFF9CA3AF),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 📦 Card
            Card(
                onClick = { },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF121212)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                Color.Black,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📊")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            "Text Analysis",
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            "Get detailed discourse insights",
                            color = Color(0xFF9CA3AF),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Footer
            Row {
                Text("About", color = Color(0xFF6B7280), fontSize = 12.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("•", color = Color(0xFF6B7280))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Help", color = Color(0xFF6B7280), fontSize = 12.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("•", color = Color(0xFF6B7280))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Terms", color = Color(0xFF6B7280), fontSize = 12.sp)
            }
        }
    }
}