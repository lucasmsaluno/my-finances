package dev.lucasm.finn.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.lucasm.finn.data.model.Transaction
import dev.lucasm.finn.ui.theme.NunitoFontFamily

@Composable
fun TransactionCard (
    transaction: Transaction,
) {

    val amountColor = if (transaction.type == "income") Color.Green else Color.Red

    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column (
            modifier = Modifier.padding(12.dp)
        ) {
            Text(transaction.title, fontSize = 14.sp)
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                transaction.price.toString(),
                fontFamily = NunitoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = amountColor
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Freelancer",fontSize = 14.sp)
                Text(transaction.date,fontSize = 14.sp)
            }
        }
    }
}