package com.example.cryptotracker.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cryptotracker.viewmodel.AssestsViewModel


@Composable
fun HomeScreen(
    viewModel: AssestsViewModel,
    navController: NavHostController
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ){
//        Text(text = "Home Screen")
//        Spacer(modifier = Modifier.size(20.dp))
        //Button(onClick = {
//            navController.navigate(BottomNavItem.Favorites.route)
//        }) {
//            Text(text = "Go to Favorites")
//        }
//        Spacer(modifier = Modifier.size(20.dp))
//        Button(onClick = {
//            navController.navigate(BottomNavItem.Profile.route)
//        }) {
//            Text(text = "Go to Profile")
//        }
        AssetScreen(viewModel,navController)
    }
}

@Composable
fun FavoritesScreen(navController: NavHostController) {
    //var text by remember { mutableSetOf("Hello")}
    var text by rememberSaveable {
        mutableStateOf("Hello")
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ){

//        Button(onClick = {
//            navController.navigate(BottomNavItem.Home.route)
//        }) {
//            Text(text = "Go to Home")
//        }
//        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "Favorites Screen")
        Spacer(modifier = Modifier.size(20.dp))
        Button(onClick = {
            text="Bye"
        }) {
            Text(text = "Change Text")
        }
    }
}

@Composable
fun ProfileScreen(navController: NavHostController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ){
//        Button(onClick = {
//            navController.navigate(BottomNavItem.Profile.route)
//        }) {
//            Text(text = "Go to Home")
//        }
//        Spacer(modifier = Modifier.size(20.dp))
//        Button(onClick = {
//            navController.navigate(BottomNavItem.Profile.route)
//        }) {
//            Text(text = "Go to Favorites")
//        }
//        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "Profile Screen")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(assetId: String, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = assetId) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPaddings ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddings)
        )

    }
}

@Composable
fun NavigationGraph(assestsViewModel: AssestsViewModel,navController:NavHostController){
    val assetIdKey="assetId"
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route){ HomeScreen(assestsViewModel, navController)  }
        composable(BottomNavItem.Favorites.route){ FavoritesScreen(navController) }
        composable(BottomNavItem.Profile.route){ ProfileScreen(navController) }
        composable("${BottomNavItem.Home.route}/{$assetIdKey}") { backStackEntry ->
            DetailScreen(
                assetId = backStackEntry.arguments?.getString(assetIdKey) ?: "missing asset",
                navController
            )
        }
    }
}

@Composable
fun BottomTabBar(navController: NavHostController){
    val tabBarItems= listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites,
        BottomNavItem.Profile
    )
    BottomAppBar {
        val navBackStack by navController.currentBackStackEntryAsState()
        val currentRoute=navBackStack?.destination?.route

        tabBarItems.forEach { barItem ->
            val isSelected = currentRoute == barItem.route
            NavigationBarItem(
                selected = isSelected,
                label = {
                        Text(text = barItem.title)
                },
                onClick = {
                    navController.navigate(barItem.route){
                        launchSingleTop=true
                    }
                    //evitar que se recomponga la misma ruta
//                    navController.graph.startDestinationRoute.let{route->
//
//                    }

                },
                icon = {
                    Icon(
                    imageVector = if (isSelected) barItem.selectedIcon else barItem.unselectedIcon,
                    contentDescription = barItem.title )
                })
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(assestsViewModel: AssestsViewModel){
    val navController= rememberNavController()
    Scaffold (
        bottomBar = { BottomTabBar(navController = navController)}
    ){
        NavigationGraph(assestsViewModel,navController = navController)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview(){
    MainScreen(assestsViewModel = AssestsViewModel())
}