package dev.lucasm.finn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import dev.lucasm.finn.data.model.Transaction
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.lucasm.finn.viewmodels.TransactionsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Transaction(
    navController: NavController,
    viewModel: TransactionsViewModel
) {
    var titleInput by remember { mutableStateOf("") }
    var priceInput by remember { mutableStateOf("") } // Armazena o valor como texto para validação
    var selectedOption by remember { mutableStateOf("income") }
    var showErrorDialog by remember { mutableStateOf(false) } // Controle do diálogo de erro
    var showDatePickerDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text("New Transaction", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding() + 30.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nome da transação
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = titleInput,
                onValueChange = { titleInput = it },
                label = { Text("Transaction Name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.size(20.dp))

            // Valor da transação
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = priceInput,
                onValueChange = { input ->
                    priceInput = input.filter { it.isDigit() || it == '.' } // Permite apenas números e ponto
                },
                label = { Text("Transaction Amount") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.size(30.dp))

            // Tipo de transação (Income ou Expense)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            1.dp,
                            if (selectedOption == "income") MaterialTheme.colorScheme.secondary else Color.LightGray,
                            RoundedCornerShape(4.dp)
                        )
                        .padding(16.dp)
                        .width(120.dp)
                        .clickable { selectedOption = "income" },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Income")
                }

                Box(
                    modifier = Modifier
                        .border(
                            1.dp,
                            if (selectedOption == "expense") Color.Red else Color.LightGray,
                            RoundedCornerShape(4.dp)
                        )
                        .padding(16.dp)
                        .width(120.dp)
                        .clickable { selectedOption = "expense" },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Expense")
                }
            }

            Spacer(modifier = Modifier.size(30.dp))

            // Seleção de data
            if (showDatePickerDialog) {
                DatePickerDialog(
                    onDismissRequest = { showDatePickerDialog = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                datePickerState
                                    .selectedDateMillis?.let { millis ->
                                        selectedDate = millis.toBrazilianDateFormat()
                                    }
                                showDatePickerDialog = false
                            }) {
                            Text(text = "Choose Date")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            OutlinedTextField(
                value = selectedDate,
                onValueChange = {},
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .onFocusEvent {
                        if (it.isFocused) {
                            showDatePickerDialog = true
                            focusManager.clearFocus(force = true)
                        }
                    },
                label = { Text("Date") },
                readOnly = true
            )

            Spacer(modifier = Modifier.size(30.dp))

            // Botão para adicionar transação
            Button(
                onClick = {
                    if (titleInput.isBlank() || priceInput.isBlank() || selectedDate.isBlank()) {
                        showErrorDialog = true
                    } else {
                        viewModel.insertTransaction(
                            Transaction(
                                title = titleInput,
                                price = priceInput.toDoubleOrNull() ?: 0.0,
                                type = selectedOption,
                                date = selectedDate
                            )
                        )
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Add Transaction")
            }

            // Diálogo de erro
            if (showErrorDialog) {
                AlertDialog(
                    onDismissRequest = { showErrorDialog = false },
                    title = { Text("Error") },
                    text = { Text("Please fill in all fields before adding the transaction.") },
                    confirmButton = {
                        Button(onClick = { showErrorDialog = false }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}

fun Long.toBrazilianDateFormat(pattern: String = "dd/MM/yyyy"): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(pattern, Locale("pt-br")).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }
    return formatter.format(date)
}

