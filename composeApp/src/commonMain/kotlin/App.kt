import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.navigation.Navigation
import presentation.screens.expense.TitleTopBarType
import presentation.ui.theme.AppTheme
import presentation.ui.theme.getColorsTheme

@Composable
@Preview
fun App() {
    val colors = getColorsTheme()

    PreComposeApp {
        AppTheme {

            val navigator = rememberNavigator()
            val titleTopBar = getTitleTopAppBar(navigator)
            val isEditOrAddExpenses = titleTopBar != TitleTopBarType.DASHBOARD.value

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        elevation = 0.dp,
                        title = {
                            Text(text = titleTopBar, fontSize = 25.sp, color = colors.textColor)
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                navigator.popBackStack()
                            }) {
                                if(isEditOrAddExpenses){
                                    Icon(
                                        modifier = Modifier.padding(start = 16.dp),
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        tint = colors.textColor,
                                        contentDescription = ""
                                    )
                                }else{
                                    Icon(
                                        modifier = Modifier.padding(start = 16.dp),
                                        imageVector = Icons.Default.Apps,
                                        tint = colors.textColor,
                                        contentDescription = ""
                                    )
                                }
                            }
                        },
                        backgroundColor = colors.backgroundColor)
                },
                floatingActionButton = {
                    if(!isEditOrAddExpenses){
                        FloatingActionButton(
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                navigator.navigate("/addExpenses")
                            },
                            shape = RoundedCornerShape(50),
                            backgroundColor = colors.addIconColor,
                            contentColor = Color.White
                        ){
                            Icon(
                                imageVector = Icons.Default.Add,
                                tint = Color.White,
                                contentDescription = ""
                            )
                        }
                    }
                }
            ) {
                Navigation(navigator)
            }
        }
    }
}

@Composable
fun getTitleTopAppBar(navigator: Navigator): String{
    var titleBarBar = TitleTopBarType.DASHBOARD

    val isOnAddExpenses = navigator.currentEntry.collectAsState(null).value?.route?.route.equals("/addExpenses/{id}?")
    if(isOnAddExpenses){
        titleBarBar = TitleTopBarType.ADD
    }

    val isOnEditExpenses = navigator.currentEntry.collectAsState(null).value?.path<Long>("id")
    isOnEditExpenses?.let {
        titleBarBar = TitleTopBarType.EDIT
    }
    return titleBarBar.value
}