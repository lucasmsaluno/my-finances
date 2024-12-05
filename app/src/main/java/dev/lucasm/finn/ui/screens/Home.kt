package dev.lucasm.finn.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.lucasm.finn.navigation.Screens
import dev.lucasm.finn.ui.components.CustomDropdownMenu
import dev.lucasm.finn.ui.components.TransactionCard
import dev.lucasm.finn.ui.theme.NunitoFontFamily
import dev.lucasm.finn.viewmodels.TransactionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    viewModel: TransactionsViewModel
) {
    val transactionsViewModel = viewModel.state.collectAsState()

    Scaffold (
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        "MyFinances",
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily
                    )
                },
                actions = {
                    Button(
                        onClick = {
                            navController.navigate(Screens.TransactionScreen)
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text("New Transaction")
                    }
                },
            )
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            val colorStops = arrayOf(
                0.5f to MaterialTheme.colorScheme.primary,
                0.5f to MaterialTheme.colorScheme.background
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(
                        brush = Brush.verticalGradient(colorStops = colorStops)
                    )
                    .padding(start = 10.dp, top = 20.dp)
                    .horizontalScroll(rememberScrollState())
            ) {

                    Card (
                        modifier = Modifier.width(200.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onSecondary,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Column (
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text("Incomes")
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = null,
                                    tint = Color(0xFF38CE96)
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                "R$${transactionsViewModel.value.incomes}",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold,
                                fontFamily = NunitoFontFamily,
                                fontSize = 22.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                Card (
                    modifier = Modifier.width(200.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onSecondary,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Column (
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text("Expenses")
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color.Red,
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            "$ ${transactionsViewModel.value.expenses}",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                            fontFamily = NunitoFontFamily,
                            fontSize = 22.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                    Card (
                        modifier = Modifier.width(200.dp),
                        shape = RoundedCornerShape(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onSecondary,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Column (
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text("Total")
                                Icon(
                                    imageVector = Icons.Filled.AddCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF38CE96)
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                "R$${transactionsViewModel.value.expenses}",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold,
                                fontFamily = NunitoFontFamily,
                                fontSize = 22.sp
                            )
                        }
                    }

            }

            Column (
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Transactions",
                        fontSize = 16.sp,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 14.dp)
                    )

                    CustomDropdownMenu(viewModel = viewModel)
                }
                Spacer(modifier = Modifier.size(20.dp))
                LazyColumn {
                    items(transactionsViewModel.value.transactions) { transaction ->
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = {
                                if (it == SwipeToDismissBoxValue.EndToStart) {
                                    viewModel.removeTransaction(transaction)
                                    true

                                } else {
                                    false
                                }
                            }
                        )
                        SwipeToDismissBox(
                            state = dismissState,
                            backgroundContent = {
                                Box(){
                                    Text("Delete")
                                }
                            }
                        ) {
                            TransactionCard(
                                transaction = transaction,
                            )
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}