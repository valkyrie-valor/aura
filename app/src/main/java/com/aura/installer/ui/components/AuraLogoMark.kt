package com.aura.installer.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AuraPortalMark(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    glyphColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.28f))
            .background(containerColor)
            .border(
                width = 1.dp,
                color = accentColor.copy(alpha = 0.35f),
                shape = RoundedCornerShape(size * 0.28f),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.size(size * 0.72f)) {
            val w = this.size.width
            val h = this.size.height
            val cx = w / 2f
            val cy = h / 2f

            // Outer diamond portal frame
            val diamondPath = Path().apply {
                moveTo(cx, h * 0.06f)
                lineTo(w * 0.94f, cy)
                lineTo(cx, h * 0.94f)
                lineTo(w * 0.06f, cy)
                close()
            }
            drawPath(
                path = diamondPath,
                color = accentColor.copy(alpha = 0.55f),
                style = Stroke(width = w * 0.075f, join = StrokeJoin.Round),
            )

            // Inner architectural portal square
            val inset = w * 0.22f
            val squarePath = Path().apply {
                moveTo(inset, inset)
                lineTo(w - inset, inset)
                lineTo(w - inset, h - inset)
                lineTo(inset, h - inset)
                close()
            }
            drawPath(
                path = squarePath,
                color = accentColor.copy(alpha = 0.22f),
            )
            drawPath(
                path = squarePath,
                color = accentColor,
                style = Stroke(width = w * 0.07f, join = StrokeJoin.Round),
            )

            // Center precision package download arrow
            val strokeW = w * 0.12f
            drawLine(
                color = glyphColor,
                start = Offset(cx, h * 0.26f),
                end = Offset(cx, h * 0.58f),
                strokeWidth = strokeW,
                cap = StrokeCap.Round,
            )
            val chevron = Path().apply {
                moveTo(w * 0.34f, h * 0.46f)
                lineTo(cx, h * 0.62f)
                lineTo(w * 0.66f, h * 0.46f)
            }
            drawPath(
                path = chevron,
                color = glyphColor,
                style = Stroke(width = strokeW, cap = StrokeCap.Round, join = StrokeJoin.Round),
            )
            // Receiving base tray
            drawLine(
                color = accentColor,
                start = Offset(w * 0.33f, h * 0.72f),
                end = Offset(w * 0.67f, h * 0.72f),
                strokeWidth = w * 0.095f,
                cap = StrokeCap.Round,
            )
        }
    }
}

@Composable
fun VersionBadgePill(
    version: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    val cleanVersion = if (version.startsWith("v", ignoreCase = true)) version else "v$version"
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(containerColor)
            .padding(horizontal = 8.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = cleanVersion,
            style = MaterialTheme.typography.labelMedium,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.SemiBold,
            color = contentColor,
        )
    }
}

@Composable
fun PackageMonogramAvatar(
    displayName: String,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
) {
    val initials = displayName
        .filter { it.isUpperCase() || it.isDigit() }
        .take(2)
        .ifEmpty { displayName.take(2).uppercase() }

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.dp),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initials,
            style = MaterialTheme.typography.titleMedium,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}
