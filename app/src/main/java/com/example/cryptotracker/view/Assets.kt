package com.example.cryptotracker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle


import androidx.compose.material3.Divider
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.example.cryptotracker.model.Assets
import com.example.cryptotracker.viewmodel.AssestsViewModel

@Composable
fun AssetScreen(viewModel: AssestsViewModel){

    val asset=viewModel.assets

    LaunchedEffect(Unit) {
        viewModel.fetchAssets()
    }

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            //.background(MaterialTheme.colorScheme.background.Black)
    ){
       items(asset){currentAsset->
           AssetRow(asset = currentAsset)
           Divider()
       }

//        AssetRow(
//            Assets(
//                id = "bitcoin",
//                name="Bitcoin",
//                symbol = "BTC",
//                price = 65000.0,
//                percentage = 5.75
//            )
//        )
//        Divider()
//        AssetRow(
//            Assets(
//                id = "ethereum",
//                name="Ethereum",
//                symbol = "ETH",
//                price = 35000.0,
//                percentage = -1.78
//            )
//        )
//        Divider()
    }
}
@Composable
fun AssetRow(asset: Assets){
    Row (verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ){
        Box(modifier = Modifier
            .padding(horizontal = 8.dp)){
            if(LocalInspectionMode.current){
                Icon(imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp))
            }else{
                AsyncImage(
                    model = "https://assets.coincap.io/assets/icons/${asset.symbol.lowercase()}@2x.png",
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }

        Column {
            Text(
                text = asset.name,
                color = Color.White
            )
            Text(
                text = asset.symbol,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "$${asset.price}",
            fontSize = 15.sp,
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 8.dp))
        Text(text = "$${asset.percentage}%",
            color = if (asset.percentage>=0) Color.Green else Color.Red,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp))
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AssetRowPreview(){
    AssetScreen(AssestsViewModel())

}