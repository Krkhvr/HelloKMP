package presentation.screens.expense_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.Expense
import domain.model.ExpenseCategory
import kotlinx.coroutines.launch
import presentation.screens.expense.TitleTopBarType
import presentation.ui.theme.getColorsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpenseDetailScreen(
    expense: Expense? = null,
    categoryList: List<ExpenseCategory> = emptyList(),
    addExpenseAndNavigateBack: (Expense) -> Unit
) {
    val colors = getColorsTheme()

    var price by remember { mutableStateOf(expense?.amount ?: 0.0) }
    var description by remember { mutableStateOf(expense?.description ?: "") }
    var category by remember { mutableStateOf(expense?.category?.name ?: "") }

    var categorySelected by remember {
        mutableStateOf(
            expense?.category?.name ?: "Select a category"
        )
    }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(sheetState.targetValue) {
        if (sheetState.targetValue == ModalBottomSheetValue.Expanded) {
            keyboardController?.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            CategoryBottomSheetContent(categoryList) {
                category = it.name
                categorySelected = it.name
                scope.launch {
                    sheetState.hide()
                }
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            ExpenseAmount(
                price = price,
                onPriceChange = {
                    price = it
                },
                keyboardController = keyboardController
            )
            Spacer(modifier = Modifier.height(30.dp))

            ExpenseTypeSelector(categorySelected = categorySelected) {
                scope.launch {
                    sheetState.show()
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            ExpenseDescription(
                descriptionContent = description,
                keyboardController = keyboardController,
                onDescriptionChange = {
                    description = it
                }
            )
            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(45)),
                onClick = {
                    val expenseTemp = Expense(
                        amount = price,
                        category = ExpenseCategory.valueOf(category),
                        description = description
                    )

                    val expenseFromEdit = expense?.id?.let {
                        expenseTemp.copy(id = it)
                    }
                    addExpenseAndNavigateBack(expenseFromEdit ?: expenseTemp)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.purple,
                    contentColor = Color.White
                ),
                enabled = price != 0.0 && description.isNotBlank() && category.isNotBlank()
            ){
                expense?.let {
                    Text(text = TitleTopBarType.EDIT.value)
                    return@Button
                }
                Text(text = TitleTopBarType.ADD.value)
            }
        }
    }
}

@Composable
private fun ExpenseAmount(
    price: Double,
    onPriceChange: (Double) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    val colors = getColorsTheme()
    var text by remember { mutableStateOf("$price") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Amount",
            fontSize = 20.sp,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 25.sp,
                color = colors.textColor,
                fontWeight = FontWeight.ExtraBold
            )
            TextField(
                modifier = Modifier.weight(1f),
                value = text,
                onValueChange = { newText ->
                    val numericText = newText.filter { it.isDigit() || it == '.' }
                    text = if (numericText.isNotEmpty() && numericText.count { it == '.' } <= 1) {
                        try {
                            val newValue = numericText.toDouble()
                            onPriceChange(newValue)
                            numericText
                        } catch (e: Exception) {
                            ""
                        }
                    } else {
                        onPriceChange(0.0)
                        ""
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colors.textColor,
                    backgroundColor = colors.backgroundColor,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = 35.sp, fontWeight = FontWeight.ExtraBold)
            )

            Text(
                text = "USD",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Gray
            )
        }

        Divider(color = Color.Black, thickness = 2.dp)
    }
}

@Composable
private fun ExpenseTypeSelector(
    categorySelected: String,
    openBottonSheet: () -> Unit
) {
    val colors = getColorsTheme()

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Expenses made for",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = categorySelected,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.textColor
            )
        }
        IconButton(
            modifier = Modifier
                .clip(RoundedCornerShape(35))
                .background(colors.backgroundColor),
            onClick = {
                openBottonSheet.invoke()
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "",
                tint = colors.textColor
            )
        }
    }
}

@Composable
fun ExpenseDescription(
    descriptionContent: String,
    keyboardController: SoftwareKeyboardController?,
    onDescriptionChange: (String) -> Unit
) {
    val colors = getColorsTheme()
    var text by remember { mutableStateOf(descriptionContent) }

    Column {
        Text(
            text = "Description",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                if (it.length <= 200) {
                    text = it
                    onDescriptionChange(it)
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colors.textColor,
                backgroundColor = colors.backgroundColor,
                focusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        Divider(color = Color.Black, thickness = 2.dp)
    }
}

@Composable
private fun CategoryBottomSheetContent(
    categories: List<ExpenseCategory>,
    onCategorySelected: (ExpenseCategory) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(categories) {
            CategoryItem(it, onCategorySelected)
        }
    }
    return
}

@Composable
private fun CategoryItem(
    category: ExpenseCategory,
    onCategorySelected: (ExpenseCategory) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
            onCategorySelected(category)
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(40.dp).clip(CircleShape),
            imageVector = category.icon,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(text = category.name)
    }
}